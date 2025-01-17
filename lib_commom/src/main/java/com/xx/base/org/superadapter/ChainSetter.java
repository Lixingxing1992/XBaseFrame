package com.xx.base.org.superadapter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

/**
 * Some convenient methods called like a chain.
 * <p>
 * Created by Lixingxing on 16/4/1.
 */
interface ChainSetter<VH> {

    VH setText(int viewId, CharSequence text);

    VH setTextColor(int viewId, int textColor);

    VH setTextColor(int viewId, ColorStateList colorStateList);

    VH setButtonText(int viewId, CharSequence text);

    VH setMovementMethod(int viewId, MovementMethod method);

    VH setImageResource(int viewId, @DrawableRes int resId);

    VH setImageDrawable(int viewId, Drawable drawable);

    VH setImageBitmap(int viewId, Bitmap bitmap);

    VH setImageUri(int viewId, Uri imageUri);

    VH setScaleType(int viewId, ImageView.ScaleType type);

    VH setBackgroundColor(int viewId, @ColorInt int bgColor);

    VH setBackgroundResource(int viewId, @DrawableRes int bgRes);

    VH setColorFilter(int viewId, ColorFilter colorFilter);

    VH setColorFilter(int viewId, int colorFilter);

    VH setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value);

    VH setVisibility(int viewId, int visibility);

    VH setVisibility(int visibility, int... viewIds);

    VH setMax(int viewId, int max);

    VH setProgress(int viewId, int progress);

    VH setRating(int viewId, float rating);

    VH setTag(int viewId, Object tag);

    VH setEnabled(int viewId, boolean enabled);

    VH setAdapter(int viewId, Adapter adapter);

    VH setAdapter(int viewId, RecyclerView.Adapter adapter);

    VH setChecked(int viewId, boolean checked);

    VH setOnClickListener(int viewId, View.OnClickListener listener);

    VH setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    VH setOnTouchListener(int viewId, View.OnTouchListener listener);

}
