package com.example.NailedOwner.AddSaloonPhoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import  com.example.NailedOwner.R;

import java.util.ArrayList;


public class SliderAdapterHome extends PagerAdapter {


    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapterHome(Context context, ArrayList<Integer> images){
            this.context = context;
            this.images = images;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        return images.size();

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myImageLayout = inflater.inflate(R.layout.slider_image,container,false);
//        ImageView myImage  = myImageLayout.findViewById(R.id.image_slider);

       // String ImageSlideURl=images.get(position).getImages();

        /*if(!ImageSlideURl.equalsIgnoreCase(""))
        {
            Picasso.get().load(ImageSlideURl).placeholder(R.drawable.home_top_shoes).into(myImage);
        }*/
      // myImage.setImageResource(images.get(position));
        container.addView(myImageLayout,0);
        return myImageLayout;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
