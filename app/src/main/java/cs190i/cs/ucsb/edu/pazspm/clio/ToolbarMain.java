package cs190i.cs.ucsb.edu.pazspm.clio;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;


public class ToolbarMain {
    public ToolbarMain(AppCompatActivity context){
        ImageView logo = (ImageView) context.findViewById(R.id.clio_logo);
        logo.setOnClickListener(profileListener);
    }

    private View.OnClickListener menuListener  =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("menu");
        }
    };

    private View.OnClickListener profileListener  =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("profile");
        }
    };

    private View.OnClickListener clioListener  =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("logo");
        }
    };
}
