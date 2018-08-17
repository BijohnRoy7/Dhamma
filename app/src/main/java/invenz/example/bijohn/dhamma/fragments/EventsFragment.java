package invenz.example.bijohn.dhamma.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import invenz.example.bijohn.dhamma.models.Event;
import invenz.example.bijohn.dhamma.adapters.EventsCustomAdapter;
import invenz.example.bijohn.dhamma.R;
import invenz.example.bijohn.dhamma.utils.AllVariables;
import invenz.example.bijohn.dhamma.utils.IsInternetConnected;
import invenz.example.bijohn.dhamma.utils.MySharedPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private ListView eventListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventsCustomAdapter customAdapter, customAdapter1;
    private List<Event> events, eventsOfAMonth;

    private MaterialCalendarView calendarView;
    private ArrayList<Date> dates;

    private int month ,year , day;
    private String monthName;

    private FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        eventListView = view.findViewById(R.id.idEventList_eventFrag);
        swipeRefreshLayout = view.findViewById(R.id.idSwipeRefreshLayout_eventFrag);
        events = new ArrayList<>();
        eventsOfAMonth = new ArrayList<>();
        /*##########*/
        dates = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUserVisibleHint(true);
            }
        });



        IsInternetConnected isInternetConnected = new IsInternetConnected();
        if (isInternetConnected.isConnected(getContext())){

            /*############ Firestore ##################*/
            firebaseFirestoreDb.collection("AllEvents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()){

                        for (DocumentSnapshot doc : task.getResult()){
                            Map<String, Object> eventsMap = doc.getData();

                            day = Integer.parseInt(eventsMap.get("date").toString());
                            month = Integer.parseInt(eventsMap.get("month").toString())-1;
                            year = Integer.parseInt(eventsMap.get("year").toString());

                            Log.d("ROY", "onComplete2(eventFrag): month "+month);

                            Date date = new GregorianCalendar(year, month, day).getTime();
                            dates.add(date);

                            switch (month){
                                case 0:
                                    monthName = "january";
                                    break;
                                case 1:
                                    monthName = "February";
                                    break;
                                case 2:
                                    monthName = "March";
                                    break;
                                case 3:
                                    monthName = "April";
                                    break;
                                case 4:
                                    monthName = "May";
                                    break;
                                case 5:
                                    monthName = "June";
                                    break;
                                case 6:
                                    monthName = "July";
                                    break;
                                case 7:
                                    monthName = "August";
                                    break;
                                case 8:
                                    monthName = "September";
                                    break;
                                case 9:
                                    monthName = "October";
                                    break;
                                case 10:
                                    monthName = "November";
                                    break;
                                case 11:
                                    monthName = "December";
                                    break;
                            }


                            events.add(new Event(eventsMap.get("date").toString()+" "+monthName, eventsMap.get("event").toString(), month));
                        }


                        if (AllVariables.eventsPref!=null){
                            AllVariables.eventsPref = null;
                            AllVariables.eventsPref = events;
                        }


                        /*####################### FOR CURRENT MONTH ##############################*/
                        Date date = new Date();
                        int currentDate = date.getMonth();
                        eventsOfAMonth.clear();

                        Iterator<Event> it = events.iterator();
                        while (it.hasNext()){
                            Event event1 = it.next();
                            if (event1.getMonth() == date.getMonth()){
                                eventsOfAMonth.add(event1);
                            }
                        }

                        initializeCalendar();
                        customAdapter1 = new EventsCustomAdapter(eventsOfAMonth, getContext());
                        eventListView.setAdapter(customAdapter1);
                        ////////////////////////////////////////////////////////////////////////////


                        /*######################### WHEN MONTH IS CHANGED #######################*/
                        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                                //Toast.makeText(getActivity(), "Month changed: "+date.getMonth(), Toast.LENGTH_SHORT).show();
                                eventsOfAMonth.clear();

                                Iterator<Event> it = events.iterator();
                                while (it.hasNext()){
                                    Event event1 = it.next();
                                    if (event1.getMonth() == date.getMonth()){
                                        eventsOfAMonth.add(event1);
                                    }
                                }

                                if (eventsOfAMonth.size()!=0){
                                    initializeCalendar();
                                    customAdapter = new EventsCustomAdapter(eventsOfAMonth, getContext());
                                    eventListView.setAdapter(customAdapter);
                                }else {
                                    initializeCalendar();
                                    Event ev = new Event("","No event",0);
                                    eventsOfAMonth.add(ev);
                                    customAdapter = new EventsCustomAdapter(eventsOfAMonth, getContext());
                                    eventListView.setAdapter(customAdapter);
                                }
                                //customAdapter.notifyDataSetChanged();
                            }
                        });
                        /////////////////////////////////////////////////////////////////////////////////




                    }else {
                        Log.d("ROY", "onComplete1(EventsFrag): "+task.getException());
                    }

                }
            });
            /*############ Firestore ends ##################*/

        }
        else {

            //*#################### Net is not connected ####################*//*
            Toast.makeText(getContext(), "Internet is not connected", Toast.LENGTH_SHORT).show();
            /////////////////////////////////////////////////////////////////////////////////
        }

        return  view;
    }



    /*######################## METHOD FOR HIGHLIGHTING A DATE ##################################*/
    private void initializeCalendar() {
        //calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        for (int i=0; i<dates.size();i++){
            calendarView.setDateSelected(dates.get(i), true);
        }

    }


    /*################# Refresh the fragment when tab is changed ########################*/
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
