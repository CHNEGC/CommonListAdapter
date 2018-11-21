package com.cgc.example.commonlistadapterdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * CHENGC
 * <p>
 * 2018/11/14 14:44
 * cgz@leyoujia.com
 */
public abstract class ItemViewBinder<T, VH extends RecyclerView.ViewHolder> {

    protected abstract int getItemViewId();

    protected abstract @NonNull
    VH onCreateViewHolder(@NonNull View itemView);

    protected abstract void onBinderViewHolder(@NonNull CommonListAdapter adapter, @NonNull VH holder, @NonNull T item, @NonNull int position);
}
