package invenz.example.bijohn.dhamma.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageCustomAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();


    public ViewPageCustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    //my method
    public void setFragment(Fragment fragment){
        fragments.add(fragment);
    }

}
