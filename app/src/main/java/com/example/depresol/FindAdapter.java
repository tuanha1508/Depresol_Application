package com.example.depresol;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depresol.databinding.ItemShopCardBinding;
import com.example.depresol.FindClickListener;
import com.example.depresol.Find;
import com.bumptech.glide.Glide;

import java.util.List;


public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private List<Find> mData;
    private FindClickListener findClickListener;
    public FindAdapter(List<Find> mData, FindClickListener listener) {
        this.mData = mData;
        this.findClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemShopCardBinding itemCardBinding = ItemShopCardBinding.inflate(inflater,parent,false);
        return new FindAdapter.ViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setBind(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findClickListener.onScrollPagerItemClick(mData.get(position), holder.itemCardBinding.image);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemShopCardBinding itemCardBinding;
        public ViewHolder(@NonNull ItemShopCardBinding cardBinding) {
            super(cardBinding.getRoot());
            this.itemCardBinding = cardBinding;
        }

        void setBind(Find matchCourse){

            itemCardBinding.tvTitulo.setText(matchCourse.getName());
            itemCardBinding.tvCantidadCursos.setText(matchCourse.getNumberOfCourses());

            Glide.with(itemView.getContext())
                    .load(matchCourse.getImageResource())
                    .into(itemCardBinding.image);
        }
    }

}