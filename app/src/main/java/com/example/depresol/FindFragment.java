package com.example.depresol;

import static android.content.ContentValues.TAG;
import static java.lang.Math.abs;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




public class FindFragment extends Fragment
        implements
        FindClickListener {

    private static final String TAG = "FindFragment";
    FragmentSearchBinding binding;
    FirebaseFirestore db;
    Context mcontext;
    public FindFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mcontext = this.getContext();
        db = FirebaseFirestore.getInstance();
        MyFind myFind = MyFind.get();
        List<Find> data = myFind.getData();
        int currentItem = 1;
        setupViewpager(currentItem, data);


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
public void add_item(String title , String description , String url , int index){
        String link = "https://img.youtube.com/vi/"+url+"/0.jpg";

        View v = getLayoutInflater().inflate(R.layout.item_play_video, null);
        binding.llPopular.addView(v);
        View item_view = binding.llPopular.getChildAt(index);
        ImageView img = item_view.findViewById(R.id.img_play_video);
        TextView txt_tile = item_view.findViewById(R.id.title_information);
        TextView txt_description = item_view.findViewById(R.id.description);

        Glide.with(this).load(link).override(150,150).optionalCircleCrop().into(img);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                img.animate().rotationBy(360).withEndAction(this).setDuration(3000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        img.animate().rotationBy(360).withEndAction(runnable).setDuration(3000).setInterpolator(new LinearInterpolator()).start();

        txt_tile.setText(title);
        txt_description.setText(description);
    }
    public void update(){
        //binding.tvFindTitle.setText(String.valueOf(cnt));
//        View viewToLoad = LayoutInflater.from(this.).inflate(R.layout.item_play_video, null,false);

        db.collection("Video")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int index = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("AAAA", document.getId() + " => " + document.getString("name"));
                                add_item(document.getString("title") , document.getString("description") , document.getString("url") , index);
                                index = index + 1;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}