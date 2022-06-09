/*
 * Copyright (c) 2021. rogergcc
 */

package com.example.depresol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depresol.databinding.ItemCardBinding;

import java.util.List;

public class FindRecycleAdapter extends RecyclerView.Adapter<FindRecycleAdapter._ViewHolder> {

    Context mContext;
    private final List<FindCard> mData;
    private final FindItemClickListener findItemClickListener;

    public FindRecycleAdapter(Context mContext, List<FindCard> mData, FindItemClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.findItemClickListener = listener;
    }

    @NonNull
    @Override
    public FindRecycleAdapter._ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(viewGroup.getContext());
        ItemCardBinding itemCardBinding = ItemCardBinding.inflate(layoutInflater,viewGroup,false);
        return new _ViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final FindRecycleAdapter._ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final int pos = viewHolder.getAdapterPosition();
        //Set ViewTag
        viewHolder.itemView.setTag(pos);

        viewHolder.setPostImage(mData.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findItemClickListener.onDashboardFindClick(mData.get(i), viewHolder.itemCardBinding.cardViewImage);
            }
        });
    }

    public int getDimensionValuePixels(int dimension)
    {
        return (int) mContext.getResources().getDimension(dimension);
    }


    public int dpToPx(int dp)
    {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    @Override
    public long getItemId(int position) {
        FindCard findCard = mData.get(position);
        return findCard.getId();
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class _ViewHolder extends RecyclerView.ViewHolder{
        ItemCardBinding itemCardBinding;
        public _ViewHolder(@NonNull ItemCardBinding cardBinding) {
            super(cardBinding.getRoot());
            this.itemCardBinding = cardBinding;
        }

        void setPostImage(FindCard courseCard){
            this.itemCardBinding.cardViewImage.setImageResource(courseCard.getImageFind());
            this.itemCardBinding.stagItemFind.setText(courseCard.getFindTitle());
            this.itemCardBinding.stagItemQuantityFind.setText(courseCard.getQuantityFind());
        }

    }
}
