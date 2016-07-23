package cs190i.cs.ucsb.edu.pazspm.clio.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;

public class CustomImageAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Bitmap> mThumbIds = MainActivity.imageStorageWrapper.generateBitmapFromStorage(6);


    public CustomImageAdapter(Context context) {
        mContext = context;

    }

    public int getCount() {
        //Log.d("IMAGEADAPTER"," " + mThumbIds.size());
        return mThumbIds.size();

    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(590, 590));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(1, 0, 1, 1);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mThumbIds.get(position));
        return imageView;
    }

    /* GET PICTURES FROM SERVER AND PUT IN AN ARRAY
     */
    // Keep all Images in array

}