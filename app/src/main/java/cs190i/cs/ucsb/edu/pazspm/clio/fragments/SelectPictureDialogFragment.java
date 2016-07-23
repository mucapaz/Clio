package cs190i.cs.ucsb.edu.pazspm.clio.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Wesley on 2/5/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SelectPictureDialogFragment extends DialogFragment {
    public static SelectPictureDialogFragment newInstance(int title) {
        SelectPictureDialogFragment frag = new SelectPictureDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        Log.d("SELPICFRAG","ALELUJAH");

        return new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage("HUEHUEHUEHUEHUEHUhEUheUEh")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                PicSelectorFragment.doPositiveClick();
                            }
                        }
                )
                .setNegativeButton("CANCEL",null)
                .create();
    }
}
