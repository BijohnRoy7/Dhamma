package invenz.example.bijohn.dhamma.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hotchemi.android.rate.AppRate;
import invenz.example.bijohn.dhamma.R;
import invenz.example.bijohn.dhamma.adapters.ViewPageCustomAdapter;
import invenz.example.bijohn.dhamma.fragments.CountdownFragment;
import invenz.example.bijohn.dhamma.fragments.EventsFragment;
import invenz.example.bijohn.dhamma.fragments.MainFragment;
import invenz.example.bijohn.dhamma.utils.MyConstants;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ROY" ;
    private TabLayout myTabLayout;
    private ViewPager myViewPager;
    private ViewPageCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.idMyAppBar);
        setSupportActionBar(toolbar);

        myTabLayout = findViewById(R.id.idTablayout_mainActivity);
        myViewPager = findViewById(R.id.idViewPager_mainActivity);

        SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(MyConstants.TOKEN_KEY, "token");
        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();

        if (!token.equals("token")){
            //Toast.makeText(MainActivity.this, "Not null", Toast.LENGTH_SHORT).show();
            sendRegistrationToServer(token);
        }



        setUpMyViewPager(myViewPager);
        myTabLayout.setupWithViewPager(myViewPager);
        setUpMyTabs();
        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.tabChange);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.tabUnselected);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /*###                    Rate app codes                                ####*/
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

    }


    /*############### ActionBar ###################*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.idShare:


                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Movie Go");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=invenz.example.bijohn.dhamma \n\n"; /*###### PLAYSTORE lINK ######*/
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));

                } catch(Exception e) {
                    //e.toString();
                    Log.d(TAG, "onOptionsItemSelected (MainActivity): "+e);
                }

                //Toast.makeText(this, "Share app", Toast.LENGTH_SHORT).show();

                break;

            case R.id.idExit:
                finish();
                //Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
    /*#########################################*/





    /*###                     Storing Token into server                  ###*/
    private void sendRegistrationToServer(final String token) {


        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, MyConstants.STORE_TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myJsonObject = new JSONObject(response);
                            //String connection = myJsonObject.getString("connection");
                            String code = myJsonObject.getString("code");
                            String message = myJsonObject.getString("message");

                            Log.d(TAG, "onResponse1 (MainActivity):"+code+", "+message);
                            Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /*####### TO GET THE ERROR ###########*/
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse1 (MainActivity): "+error);
            }
        }){
            /*###### METHOD FOR SENDING DATA TO THE PHP FILE #######*/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                //tokenMap.put("user_token", "JOHN");
                return tokenMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*################ SETTING UP FRAGMENTS IN THE VIEW PAGER ###############################*/
    private void setUpMyViewPager(ViewPager myViewPager) {
        customAdapter = new ViewPageCustomAdapter(getSupportFragmentManager());
        customAdapter.setFragment(new MainFragment());
        customAdapter.setFragment(new EventsFragment());
        customAdapter.setFragment(new CountdownFragment());
        myViewPager.setAdapter(customAdapter);
    }

    /*###################### SETTING UP Tabs IN THE Tablayout ########################*/
    private void setUpMyTabs() {
        //TabLayout.Tab tab = myTabLayout.getTabAt(0).setIcon(R.drawable.home_icon).setText("Home");
        TabLayout.Tab tab = myTabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.tabChange);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
/*

        myTabLayout.getTabAt(1).setIcon(R.drawable.event_icon).setText("Events");
        myTabLayout.getTabAt(2).setIcon(R.drawable.countdown_icon).setText("Countdown");
*/

        myTabLayout.getTabAt(1).setIcon(R.drawable.ic_event_black_24dp);
        myTabLayout.getTabAt(2).setIcon(R.drawable.ic_timer_black_24dp);
    }

}
