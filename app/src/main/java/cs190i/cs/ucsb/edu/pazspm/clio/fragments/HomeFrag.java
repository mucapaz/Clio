package cs190i.cs.ucsb.edu.pazspm.clio.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;
import cs190i.cs.ucsb.edu.pazspm.clio.adapters.BitmapAdapter;
import cs190i.cs.ucsb.edu.pazspm.clio.adapters.CustomSwipeFlingAdapterView;
import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;

public class HomeFrag extends Fragment {

    private ArrayList<Bitmap> picList;
    private BitmapAdapter picAdapter;
    private CustomSwipeFlingAdapterView cardContainer;

    boolean ok;

    public HomeFrag() {
        //((MainActivity) getActivity()).setHomeFrag(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).setHomeFrag(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                cardContainer = (CustomSwipeFlingAdapterView) rootView.findViewById(R.id.frame);
                ImageView upButton = (ImageView) rootView.findViewById(R.id.upButton);
                ImageView downButton = (ImageView) rootView.findViewById(R.id.downButton);

                upButton.setOnClickListener(upListener);
                downButton.setOnClickListener(downListener);

                picList = new ArrayList<Bitmap>();

                picList.add(BitmapFactory.decodeResource(getResources(),R.drawable.menu));


                picAdapter = new BitmapAdapter(getContext(), R.layout.card_items, picList);


                cardContainer.setAdapter(picAdapter);
                cardContainer.setFlingListener(new CustomSwipeFlingAdapterView.onFlingListener() {

                    @Override
                    public void removeFirstObjectInAdapter() {
                        if (picList.size() > 0) {
                            picList.remove(0);
                            picAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onLeftCardExit(Object o) {
                        //DO WHEN SWIPE LEFT
                    }

                    @Override
                    public void onRightCardExit(Object o) {
                        //DO WHEN SWIPE RIGHT
                    }

                    @Override
                    public void onAdapterAboutToEmpty(int i) {
                        if (i == 1){
                            //picList.add(BitmapFactory.decodeResource(getResources(),R.drawable.teste));
                            ok = false;
                            System.out.println("lol -----");

                            ((MainActivity) getActivity()).requestImageFromServer();

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            picAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onScroll(float v) {
                        View view = cardContainer.getSelectedView();
                        view.findViewById(R.id.item_swipe_right_indicator).setAlpha(v < 0 ? -v : 0);
                        view.findViewById(R.id.item_swipe_left_indicator).setAlpha(v > 0 ? v : 0);
                    }

                });
                }catch (Exception ignored){

                }
            }
        }).start();
        return rootView;
    }

    private View.OnClickListener upListener  =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cardContainer.getTopCardListener().selectRight();
        }
    };

    private View.OnClickListener downListener  =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cardContainer.getTopCardListener().selectLeft();
        }
    };

    public void putBitmap(Bitmap bitmap) {
        picList.add(bitmap);
        ok = true;

        System.out.println("-------------------------------------------");

    }

    public void update(){
        picAdapter.notifyDataSetChanged();
    }
}
