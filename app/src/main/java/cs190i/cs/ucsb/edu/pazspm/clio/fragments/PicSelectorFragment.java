package cs190i.cs.ucsb.edu.pazspm.clio.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;
import cs190i.cs.ucsb.edu.pazspm.clio.adapters.CustomImageAdapter;
import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;


/**
 * Created by Wesley on 3/13/2016.
 */
public class PicSelectorFragment extends Fragment {

    public PicSelectorFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        RoundedImageView riv = (RoundedImageView) layout.findViewById(R.id.rounded_image);
        riv.setElevation(10);

        TextView tv = (TextView) layout.findViewById(R.id.id_id);
        tv.setText(MainActivity.id);

        //get username, name and bio from server and profile pic

        //get all pics from server

        //CircularImageLayout profilePic = (CircularImageLayout) layout.findViewById(R.id.framelayout_info);
        //profilePic.picture.setImageResource(R.drawable.sample_0);

        GridView gridView = (GridView) layout.findViewById(R.id.allpics_gridView);
        gridView.setAdapter(new CustomImageAdapter(container.getContext()));

        return layout;
    }

    public static void doPositiveClick(){//send picture to server here (probably need to reference imageview/view as a parameter
        Log.d("PICSELFRAG", "TAFUNFANO");
    }
}
