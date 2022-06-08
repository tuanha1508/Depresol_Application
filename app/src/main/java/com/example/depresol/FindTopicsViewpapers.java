package com.example.depresol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depresol.databinding.ItemPaperCardBinding;
import com.example.depresol.FindClickListener;
import com.example.depresol.Find;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;


public class FindTopicsViewpapers extends RecyclerView.Adapter<FindTopicsViewpapers.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Find> mFindList;
    private Context mContext;
    private FindClickListener findClickListener;

    public FindTopicsViewpapers(List<Find> mFindList, Context context, FindClickListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mFindList = mFindList;
        this.findClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.item_pager_card, parent, false);
//        return new ViewHolder(view);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
//        return new ViewHolder(v);

        ItemPaperCardBinding itemPagerCardBinding = ItemPaperCardBinding.inflate(inflater, parent, false);
        return new ViewHolder(itemPagerCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setBind(mFindList.get(position));

        holder.binding.cardViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findClickListener.onScrollPagerItemClick(mFindList.get(position), holder.binding.image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFindList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ItemPaperCardBinding binding;

        ViewHolder(@NonNull ItemPaperCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setBind(Find matchCourse) {

            binding.tvTitulo.setText(matchCourse.getName());
            binding.tvCantidadCursos.setText(matchCourse.getNumberOfCourses());

            Glide.with(itemView.getContext())
                    .load(matchCourse.getImageResource())
//                .transform(new CenterCrop(), new RoundedCorners(24))
//                .transform(new RoundedCorners(40))
                    .transform(new CenterCrop())
                    .into(binding.image);
        }


    }
}
