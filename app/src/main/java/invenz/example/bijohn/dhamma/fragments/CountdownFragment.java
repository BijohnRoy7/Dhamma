package invenz.example.bijohn.dhamma.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Date;
import java.util.Map;

import invenz.example.bijohn.dhamma.R;
import invenz.example.bijohn.dhamma.utils.AllVariables;
import invenz.example.bijohn.dhamma.utils.IsInternetConnected;
import invenz.example.bijohn.dhamma.utils.MySharedPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountdownFragment extends Fragment {

    private TextView tvDaysRemain,tvHoursremain, tvMinRemain, tvSecRemain, tvEventText, tvDaysText, tvHrText, tvMinText, tvSecText;
    private Button btStopAlarm;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int year=2018, month=0, day=0, hour=0, minute=0;
    private String eventText;
    long difference;

    private FirebaseFirestore firestore;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isPlayed = false;

    public CountdownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);

        tvDaysRemain = view.findViewById(R.id.idDays_countdown_timer);
        tvHoursremain = view.findViewById(R.id.idHours);
        tvMinRemain = view.findViewById(R.id.idMin);
        tvSecRemain = view.findViewById(R.id.idSec);
        tvEventText = view.findViewById(R.id.idEventText_countdownTimer);
        btStopAlarm = view.findViewById(R.id.idStopAlarm_countdown);

        swipeRefreshLayout = view.findViewById(R.id.idSwipeRefreshLayout_countdownFrag);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUserVisibleHint(true);
            }
        });

        tvDaysText= view.findViewById(R.id.daysText_countdown);
        tvHrText = view.findViewById(R.id.hoursText_countdown);
        tvMinText = view.findViewById(R.id.minText_countdown);
        tvSecText = view.findViewById(R.id.secText_countdown);

        firestore  = FirebaseFirestore.getInstance();

        /*############## getting sharedPreferences ##################*/
        sharedPreferences = getContext().getSharedPreferences("sf", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        IsInternetConnected isInternetConnected = new IsInternetConnected();
        if (isInternetConnected.isConnected(getContext())){

            firestore.collection("SetTimer").document("1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> doc = documentSnapshot.getData();

                    String d = doc.get("date").toString();
                    String m = doc.get("month").toString();
                    String y = doc.get("year").toString();
                    String h = doc.get("hour").toString();
                    String min = doc.get("min").toString();
                    Log.d("ROY", "onSuccess1: "+d+"/"+m+"/"+y);

                    day = Integer.parseInt(d);
                    month = Integer.parseInt(m);
                    year = Integer.parseInt(y);
                    hour = Integer.parseInt(h);
                    minute = Integer.parseInt(min);
                    Log.d("ROY", "onSuccess12: "+hour+":"+minute);

                    eventText = doc.get("event").toString();

                    /*######### Saving alll the values #############*/

                    editor.putString( "day", d);
                    editor.putString("month", m);
                    editor.putString("year", y);
                    editor.putString("hour", h);
                    editor.putString("min", min);
                    editor.putString("eventText", eventText);
                    editor.apply();
                    editor.commit();

                    tvEventText.setText(eventText);
                    setMyTimer();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ROY", "onFailure1(Countdown): "+e);
                }
            });

            return  view;

        }else {


            //String h = sharedPreferences.getString("day", null);
            //Toast.makeText(getContext(), ""+h, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Internet is not connected", Toast.LENGTH_SHORT).show();

            String d = sharedPreferences.getString("day", null);
            String m = sharedPreferences.getString("month", null);
            String y = sharedPreferences.getString("year", null);
            String h = sharedPreferences.getString("hour", null);
            String min = sharedPreferences.getString("min", null);
            String eventText = sharedPreferences.getString("eventText", null);

            //Toast.makeText(getContext(), ""+d+"/"+m+"/"+y+"  "+h+":"+min+"  "+eventText , Toast.LENGTH_LONG).show();

            day = Integer.parseInt(d);
            month = Integer.parseInt(m);
            year = Integer.parseInt(y);
            hour = Integer.parseInt(h);
            minute = Integer.parseInt(min);

            tvEventText.setText(eventText);
            setMyTimer();


            //Log.d("ROY", "onCreateView (countdown): "+AllVariables.daySave);

            return view;
        }


    }

    private void setMyTimer() {

        //getting defferences of time from my method
        difference = getRemainingDays();
        Log.d("ROY", "setMyTimer: "+difference);

        long hourInMilliSec = hour*60*60*1000;
        long minInMilliSec = minute*60*1000;

        difference = difference + hourInMilliSec + minInMilliSec;


        new CountDownTimer
                (difference, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int days = (int) (millisUntilFinished/(24*60*60*1000));
                int hours = (int) (millisUntilFinished/(60*60*1000)%24);
                int minutes = (int) (millisUntilFinished/(60*1000)%60);
                int seconds = (int) (millisUntilFinished/(1000)%60);

                tvDaysRemain.setText(String.format("%d",days));
                tvHoursremain.setText(String.format("%d",hours));
                tvMinRemain.setText(String.format("%d",minutes));
                tvSecRemain.setText(String.format("%d",seconds));

                tvDaysText.setVisibility(View.VISIBLE);
                tvHrText.setVisibility(View.VISIBLE);
                tvMinText.setVisibility(View.VISIBLE);
                tvSecText.setVisibility(View.VISIBLE);

                /*###############  1st Alarm when 5 min left  ##############*/
                if ( tvDaysRemain.getText().equals("0") && tvHoursremain.getText().equals("0") && tvMinRemain.getText().equals("5") && tvSecRemain.getText().equals("0")){
                    //Toast.makeText(getContext(), "John", Toast.LENGTH_SHORT).show();

                    tvHoursremain.setTextColor(Color.RED);
                    tvMinRemain.setTextColor(Color.RED);
                    tvSecRemain.setTextColor(Color.RED);


                    /*######### Creating media player ##############*/
                    if (mediaPlayer == null){
                        mediaPlayer = MediaPlayer.create(getContext(), R.raw.song_a);
                        mediaPlayer.setLooping(true);
                    }
                    mediaPlayer.start();

                    /*################ Countdown timer for stopping the alarm ##################*/
                    new CountDownTimer(60*1000,1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            if(mediaPlayer.isPlaying())
                            {
                                mediaPlayer.stop();
                            }

                        }
                    }.start();

                    /*########### Buton fro stopping the media player ###############*/
                    btStopAlarm.setVisibility(View.VISIBLE);
                    btStopAlarm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mediaPlayer.isPlaying())
                            {
                                mediaPlayer.stop();
                                btStopAlarm.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                }

            }

            @Override
            public void onFinish() {

                tvDaysRemain.setText("0");
                tvHoursremain.setText("0");
                tvMinRemain.setText("0");
                tvSecRemain.setText("0");

                tvDaysText.setVisibility(View.VISIBLE);
                tvHrText.setVisibility(View.VISIBLE);
                tvMinText.setVisibility(View.VISIBLE);
                tvSecText.setVisibility(View.VISIBLE);



                /*######### Creating media player ##############*/
                if (mediaPlayer == null ){
                    //mediaPlayer = MediaPlayer.create(getContext(), R.raw.song);
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.song_a);
                    mediaPlayer.setLooping(true);
                }


                /*################ Countdown timer for stopping the alarm ##################*/
                new CountDownTimer(2*60*1000,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }

                    }
                }.start();

                /*########### Buton fro stopping the media player ###############*/
                btStopAlarm.setVisibility(View.VISIBLE);
                btStopAlarm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                            btStopAlarm.setVisibility(View.INVISIBLE);
                        }

                    }
                });
            }
        }.start();

    }

    public long getRemainingDays() {

        Date currentDate = new Date();
        Date eventDate = new Date(year-1900, month-1,day);

        long t = eventDate.getTime() - currentDate.getTime();

        return eventDate.getTime()- currentDate.getTime();
    }


    /*################# Refresh the fragment when tab is changed ########################*/
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
