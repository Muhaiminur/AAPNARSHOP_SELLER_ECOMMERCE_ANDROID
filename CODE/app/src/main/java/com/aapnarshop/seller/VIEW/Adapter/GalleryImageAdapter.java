package com.aapnarshop.seller.VIEW.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aapnarshop.seller.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class GalleryImageAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> images;
    private ArrayList<String> imageList;


    public GalleryImageAdapter(Activity localContext, ArrayList<String> images, ArrayList<String> imageList) {
        context = localContext;
        this.images = images;
        this.imageList = imageList;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        try{
            String image = images.get(position);
            ImageView imageView;


            convertView = LayoutInflater.from(context).inflate(R.layout.layout_gridview, parent, false);

            imageView = convertView.findViewById(R.id.slider_image_view);

            for (String s : imageList) {
                if (s.equalsIgnoreCase(images.get(position))) {
                    convertView.findViewById(R.id.textViewID).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.imageCheckIVId).setVisibility(View.VISIBLE);
                }
            }

            Glide.with(context).load(image)
                    .placeholder(R.drawable.ic_gallery).centerCrop()
                    .into(imageView);


        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return convertView;

    }
}
