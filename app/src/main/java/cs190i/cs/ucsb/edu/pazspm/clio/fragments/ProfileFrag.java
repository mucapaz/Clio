package cs190i.cs.ucsb.edu.pazspm.clio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;

/**
 * Created by pedro_000 on 3/13/2016.
 */
public class ProfileFrag extends Fragment {
    public ProfileFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
