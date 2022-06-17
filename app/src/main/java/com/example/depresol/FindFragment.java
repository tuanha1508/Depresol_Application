package com.example.depresol;

import static java.lang.Math.abs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.depresol.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




public class FindFragment extends Fragment
        implements
        FindClickListener {

    private static final String TAG = "FindFragment";
    FragmentSearchBinding binding;
    Context mcontext;
    int cnt = 0;
    public FindFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] imageId = {R.drawable.icon_play , R.drawable.icon_play , R.drawable.icon_play, R.drawable.icon_play};
        String[] title = {"Christopher","Craig","Sergio","Mubariz","Mike","Michael","Toa","Ivana","Alex"};
        String[] descrip = {"Heye","Supp","Let's Catchup","Dinner tonight?","Gotta go",
                "i'm in meeting","Gotcha","Let's Go","any Weekend Plans?"};

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mcontext = this.getContext();

        MyFind myFind = MyFind.get();
        List<Find> data = myFind.getData();
        int currentItem = 1;
        setupViewpager(currentItem, data);

        cnt++;
        update();
        return view;
    }


    private void setupViewpager(int currentItem, List<Find> findList) {
        FindTopicsViewpapers findTopicsViewpapers = new FindTopicsViewpapers(findList, mcontext, this);
        binding.viewPager.setAdapter(findTopicsViewpapers);
        binding.viewPager.setCurrentItem(currentItem);
        binding.viewPager.setOffscreenPageLimit(1);
        int nextItemVisiblePx = (int) getResources().getDimension(R.dimen.viewpager_next_item_visible);
        int currentItemHorizontalMarginPx = (int) getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
        int pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer = (page, position) -> {
            page.setTranslationX(-pageTranslationX * position);
            page.setScaleY(1 - (0.15f * abs(position)));
        };
        binding.viewPager.setPageTransformer(pageTransformer);
        binding.viewPager.addItemDecoration(new HorizontalMarginItemDecoration(
                mcontext, R.dimen.viewpager_current_item_horizontal_margin_testing,
                R.dimen.viewpager_next_item_visible_testing)
        );
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                MyUtilsApp.showLog(TAG, String.format(Locale.ENGLISH, "%d/%d", position + 1, findList.size()));
            }
        });
    }


    @Override
    public void onScrollPagerItemClick(Find courseCard, ImageView imageView) {
        MyUtilsApp.showLog(TAG, "LogD onScrollPagerItemClick : " + courseCard.toString());
        MyUtilsApp.showToast(mcontext, courseCard.getName());
    }

    public void update(){
        //binding.tvFindTitle.setText(String.valueOf(cnt));
//        View viewToLoad = LayoutInflater.from(this.).inflate(R.layout.item_play_video, null,false);
        //readDocument();
        View v = getLayoutInflater().inflate(R.layout.item_play_video, null);
        binding.llPopular.addView(v);
        View txt;
        txt = binding.llPopular.getChildAt(1);
        ImageView img;
        img = txt.findViewById(R.id.img_play_video);
        String ID = "kfw7MYah2n0";
        String url = "https://img.youtube.com/vi/"+ID+"/0.jpg";
        Glide.with(this).load(url).override(150,150).optionalCircleCrop().into(img);
//        Animation a = new RotateAnimation(0.0f, 360.0f,
//                Animation.RELATIVE_TO_SELF, 1.1f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        a.setRepeatCount(Animation.INFINITE);
//        a.setFillAfter(true);
//        a.setDuration(2500);
//        img.setAnimation(a);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                img.animate().rotationBy(360).withEndAction(this).setDuration(3000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        img.animate().rotationBy(360).withEndAction(runnable).setDuration(3000).setInterpolator(new LinearInterpolator()).start();


//        TextView linh ;
//        linh = txt.findViewById(R.id.title_information);
//        linh.setText("Khánh Linh cute");


    }
}