package com.xx.base.org.superadapter;


import android.support.annotation.LayoutRes;

/**
 * Interface for multiple view types.
 * <p>
 * Created by Lixingxing on 15/11/28.
 */
public interface IMulItemViewType<T> {

    /**
     * @return Will not be called if using a RecyclerView.
     */
    int getViewTypeCount();

    /**
     * Item view type, a non-negative integer is better.
     *
     * @param position current position of ViewHolder
     * @param t        model item
     * @return viewType
     */
    int getItemViewType(int position, T t);

    /**
     * Layout res.
     *
     * @param viewType {@link #getItemViewType(int, T)}
     * @return {@link LayoutRes}
     */
    @LayoutRes
    int getLayoutId(int viewType);
}