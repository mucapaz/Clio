package cs190i.cs.ucsb.edu.pazspm.clio.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import cs190i.cs.ucsb.edu.pazspm.clio.fragments.TabFragmentData;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> pageNames;
    private TabFragmentData fragmentManager;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = new TabFragmentData();
        pageNames = new ArrayList<String>();
        pageNames.add("Album");
        pageNames.add("Home");
        pageNames.add("Camera");
    }

    public Fragment getItem(int num) {
        return fragmentManager.getFragment(num);
    }

    @Override
    public int getCount() {
        return pageNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageNames.get(position);
    }

}


