package com.example.iti.sidemenumodule.helperclasses;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.iti.sidemenumodule.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmed_telnet on 6/9/2016.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    Context context;
private String imagePath;


    public ImageViewPagerAdapter(Context context,String imagePath) {
        this.context = context;
        this.imagePath=imagePath;
//      Timer  swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (currentPage == NUM_PAGES) {
//                            currentPage = 0;
//                        }
//                        featureViewPager.setCurrentItem(currentPage++, true);
//                    }
//                });
//            }
//        }, 500, 3000);
    }

    @Override
    public int getCount() {
        return 1;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.e("instantiateItem", position + "");

        ImageView imageView = new ImageView(context);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.abc_control_padding_material);

        imageView.setPadding(padding, padding, padding, padding);

        Log.i("swipe images", imagePath);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.mipmap.logo)
                .into(imageView);
        container.addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}