package com.example.depresol;

import static java.lang.Math.abs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mcontext = this.getContext();

        MyFind myFind = MyFind.get();
        List<Find> data = myFind.getData();
        int currentItem = 1;
        setupViewpager(currentItem, data);


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
}