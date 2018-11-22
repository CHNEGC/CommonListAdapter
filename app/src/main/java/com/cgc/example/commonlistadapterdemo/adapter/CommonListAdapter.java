package com.cgc.example.commonlistadapterdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * CHENGC
 * <p>  公共列表适配器 适用RecyclerView
 * 配置显示Item的多样化
 * <p>
 * 2018/11/14 13:40
 * cgz@leyoujia.com
 */
public class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 记录Item 多样化类型实体
     */
    private LinkedList<String> mLinkedList = new LinkedList<>();
    /**
     * 记录每个Item的创建ViewHolder 和绑定数据实体
     */
    private LinkedList<ItemViewBinder> mBinderLinkedList = new LinkedList<>();

    /**
     * 数据列表
     */
    private List mList;

    private Context mContext;

    private WeakReference<LinkedList<String>> mItemWeakReference = new WeakReference<>(mLinkedList);
    private WeakReference<LinkedList<ItemViewBinder>> mBindWeakReference = new WeakReference<>(mBinderLinkedList);

    public CommonListAdapter(Context mContext) {
        this.mList = new ArrayList<>();
        this.mContext = mContext;
    }

    public CommonListAdapter(Context mContext, List<?> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (null != mBinderLinkedList && mBinderLinkedList.size() > 0) {
            View view = LayoutInflater.from(mContext).inflate(mBinderLinkedList.get(viewType).getItemViewId(), parent, false);
            return mBinderLinkedList.get(viewType).onCreateViewHolder(view);
        }
        return super.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (null != mBinderLinkedList && mBinderLinkedList.size() > 0) {
            mBinderLinkedList.get(getItemViewType(position)).onBinderViewHolder(this, holder, mList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = mList.get(position);
        return indexOfType(obj);
    }


    /**
     * 注册item的类型
     *
     * @param aClass     显示类型
     * @param viewBinder 绑定数据实现
     * @param <T>
     */
    public <T> void register(@NonNull Class<? extends T> aClass, @NonNull ItemViewBinder<T, ?> viewBinder) {
        if (null != mItemWeakReference && null != mItemWeakReference.get() &&
                !mItemWeakReference.get().contains(aClass.getName())) {
            mItemWeakReference.get().add(aClass.getName());
        }
        if (null != mBindWeakReference && null != mBindWeakReference.get() &&
                !mBindWeakReference.get().contains(viewBinder)) {
            mBindWeakReference.get().add(viewBinder);
        }
    }

    public <T> void unRegister(@NonNull Class<? extends T> aClass) {
        int index = -1;
        if (null != mItemWeakReference && null != mItemWeakReference.get() &&
                null != mItemWeakReference.get() && mItemWeakReference.get().size() > 0 && mItemWeakReference.get().contains(aClass.getName())) {
            index = mItemWeakReference.get().indexOf(aClass.getName());
            mItemWeakReference.get().remove(aClass.getName());
        }
        if (null != mBindWeakReference && null != mBindWeakReference.get() &&
                null != mBindWeakReference.get() && mBindWeakReference.get().size() > 0 && index >= 0) {
            mBindWeakReference.get().remove(index);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取item的类型
     *
     * @param o
     * @return
     */
    private int indexOfType(Object o) {
        if (null != mItemWeakReference.get() && mItemWeakReference.get().size() > 0 && mItemWeakReference.get().contains(o.getClass().getName())) {
            return mItemWeakReference.get().indexOf(o.getClass().getName());
        }
        return 0;
    }

    /**
     * 添加item
     *
     * @param object
     */
    public <T> void addItem(@NonNull T object) {
        if (null != object) {
            mList.add(object);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加item
     *
     * @param object
     */
    public <T> void addItem(@NonNull T object, int position) {
        if (null != object) {
            mList.add(position, object);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除item
     *
     * @param object
     * @param <T>
     */
    public <T> void removeItem(@NonNull T object) {
        if (null != object) {
            int position = this.mList.indexOf(object);
            this.mList.remove(object);
            notifyItemRemoved(position);
            if (position != this.mList.size()) {
                notifyItemRangeChanged(0, this.mList.size());
            }
        }
    }

    /**
     * 移除Item
     *
     * @param position
     */
    public void removeItem(int position) {
        if (this.mList != null && this.mList.size() > 0) {
            this.mList.remove(position);
            notifyItemRemoved(position);
            if (position != this.mList.size()) {
                notifyItemRangeChanged(0, this.mList.size());
            }
        }
    }

    /***
     * 添加Item数据
     *
     * @param mList 数据集合
     * @param isRefresh 是否刷新
     */
    public void addAllItem(@NonNull List<?> mList, Boolean isRefresh) {
        if (isRefresh) {
            this.mList.clear();
            if (null != mList && mList.size() > 0) {
                this.mList.addAll(mList);
            }
            notifyDataSetChanged();
        } else {
            if (null != mList && mList.size() > 0) {
                int startIndex = this.mList.size() - 1;
                this.mList.addAll(mList);
                notifyItemChanged(startIndex, this.mList.size());
            }
        }
    }

    public Object getItemEntity(int position) {
        if (null != this.mList && this.mList.size() > 0) {
            return this.mList.get(position);
        }
        return null;
    }

    /**
     * 刷新数据
     *
     * @param mList 数据集合
     */
    public void reFreshData(@NonNull List<?> mList) {
        this.mList.clear();
        if (null != mList && mList.size() > 0) {
            this.mList.addAll(mList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    public List<?> getmList() {
        return mList;
    }

    @NonNull
    public <T> ItemViewBinder<T, ?> getItemViewBinder(@NonNull Class<? extends T> aClass) {
        int key = -1;
        if (null != mItemWeakReference && null != mItemWeakReference.get() && mItemWeakReference.get().size() > 0 && mItemWeakReference.get().contains(aClass.getName())) {
            key = mItemWeakReference.get().indexOf(aClass.getName());
        }
        if (null != mBindWeakReference && null != mBindWeakReference.get() && mBindWeakReference.get().size() > 0 && key >= 0) {
            return mBindWeakReference.get().get(key);
        }
        return null;
    }

    /**
     * 释放内存
     */
    public void onDestroy() {
        if (null != mBindWeakReference) {
            if (null != mBindWeakReference.get()) {
                mBindWeakReference.get().clear();
            }
            mItemWeakReference.clear();
        }
        if (null != mItemWeakReference) {
            if (null != mItemWeakReference.get()) {
                mItemWeakReference.get().clear();
            }
            mItemWeakReference.clear();
        }
        if (null != mList) {
            mList.clear();
            mList = null;
        }
        if (null != mContext) {
            mContext = null;
        }
    }
}
