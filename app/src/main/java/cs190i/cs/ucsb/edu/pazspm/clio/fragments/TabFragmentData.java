package cs190i.cs.ucsb.edu.pazspm.clio.fragments;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by pedro_000 on 3/13/2016.
 */
public class TabFragmentData {
    private ArrayList<Fragment> fragList;

    public TabFragmentData(){
        fragList = new ArrayList<Fragment>();
        fragList.add(new PicSelectorFragment());
        fragList.add(new HomeFrag());
        fragList.add(new CameraFrag());
    }

    public Fragment getFragment(int position){
        return fragList.get(position);
    }
}
