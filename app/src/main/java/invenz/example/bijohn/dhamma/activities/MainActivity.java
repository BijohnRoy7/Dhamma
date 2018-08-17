package invenz.example.bijohn.dhamma.activities;

import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import invenz.example.bijohn.dhamma.R;
import invenz.example.bijohn.dhamma.adapters.ViewPageCustomAdapter;
import invenz.example.bijohn.dhamma.fragments.CountdownFragment;
import invenz.example.bijohn.dhamma.fragments.EventsFragment;
import invenz.example.bijohn.dhamma.fragments.MainFragment;


public class MainActivity extends AppCompatActivity {

    private TabLayout myTabLayout;
    private ViewPager myViewPager;
    private ViewPageCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTabLayout = findViewById(R.id.idTablayout_mainActivity);
        myViewPager = findViewById(R.id.idViewPager_mainActivity);

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
        TabLayout.Tab tab = myTabLayout.getTabAt(0).setIcon(R.drawable.home_icon).setText("Home");
        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.tabChange);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        myTabLayout.getTabAt(1).setIcon(R.drawable.event_icon).setText("Events");
        myTabLayout.getTabAt(2).setIcon(R.drawable.countdown_icon).setText("Countdown");
    }

}
