package cs190i.cs.ucsb.edu.pazspm.clio.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;


public class BitmapAdapter extends ArrayAdapter<Bitmap> {

    Bitmap cardBackground;
    Bitmap cardFrame;
    Context context;

    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        this.context = context;
        cardFrame = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_frame);
        cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
    }

    private class ViewHolder{
        ImageView imageView;
        ImageView card_frame;
        ImageView background;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        Bitmap picture = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.card_items, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.picture_container);
            viewHolder.background = (ImageView) convertView.findViewById(R.id.background);
            viewHolder.card_frame = (ImageView) convertView.findViewById(R.id.card_frame);
            viewHolder.imageView.bringToFront();
            viewHolder.card_frame.bringToFront();

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.card_frame.setImageBitmap(cardFrame);
        viewHolder.imageView.setImageBitmap(picture);
        viewHolder.background.setImageBitmap(cardBackground);
        return convertView;
    }

}
