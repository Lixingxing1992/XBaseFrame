/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xx.base.ui.anim;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;

// TODO: Auto-generated Javadoc

/**
 * 描述：动画工具类.
 *
 */
public class BaseAnimationUtils {
	
	/** 定义动画的时间. */
	public final static long aniDurationMillis = 1L;

	/**
	 * 用来改变当前选中区域的放大动画效果
	 * 从1.0f放大1.2f倍数
	 *
	 * @param view the view
	 * @param scale the scale
	 */
	public static void largerView(View view, float scale) {
		if (view == null) {
			return;
		}

		// 置于所有view最上层
		view.bringToFront();
		int width = view.getWidth();
		float animationSize = 1 + scale / width;
		scaleView(view, animationSize);
	}

	/**
	 * 用来还原当前选中区域的还原动画效果.
	 *
	 * @param view the view
	 * @param scale the scale
	 */
	public static void restoreLargerView(View view, float scale) {
		if (view == null) {
			return;
		}
		int width = view.getWidth();
		float toSize = 1 + scale / width;
		// 从1.2f缩小1.0f倍数
		scaleView(view, -1 * toSize);
	}

	/**
	 * 缩放View的显示.
	 *
	 * @param view 需要改变的View
	 * @param toSize 缩放的大小，其中正值代表放大，负值代表缩小，数值代表缩放的倍数
	 */
	private static void scaleView(final View view, float toSize) {
		ScaleAnimation scale = null;
		if (toSize == 0) {
			return;
		} else if (toSize > 0) {
			scale = new ScaleAnimation(1.0f, toSize, 1.0f, toSize,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
		} else {
			scale = new ScaleAnimation(toSize * (-1), 1.0f, toSize * (-1),
					1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
		}
		scale.setDuration(aniDurationMillis);
		scale.setInterpolator(new AccelerateDecelerateInterpolator());
		scale.setFillAfter(true);
		view.startAnimation(scale);
	}
	
	/**
	 * 跳动-跳起动画.
	 *
	 * @param view the view
	 * @param offsetY the offset y
	 */
    public static void playJumpAnimation(final View view, final float offsetY) {
        float originalY = 0;
        float finalY = - offsetY;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(500);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playLandAnimation(view,offsetY);
            }
        });

        view.startAnimation(animationSet);
    }

    /**
     * 跳动-落下动画.
     *
     * @param view the view
     * @param offsetY the offset y
     */
	public static void playLandAnimation(final View view, final float offsetY) {
        float originalY =  - offsetY;
        float finalY = 0;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(500);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //两秒后再调
                view.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        playJumpAnimation(view,offsetY);
                    }
                },1000);
            }
        });
        view.startAnimation(animationSet);
    }

	/**
	 * 抽出-缩进动画.
	 *
	 * @param view the view
	 */
	public static void playTranXAnimation(final View view, final float offsetX) {
		float originalY = 0;
		float finalY = - offsetX;
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new TranslateAnimation( originalY,finalY,0, 0));
		animationSet.setDuration(300);
		animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animationSet.setFillAfter(true);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				playTranAnimation(view,offsetX);
			}
		});
		view.startAnimation(animationSet);
	}

	/**
	 * 抽出-缩进动画.
	 *
	 * @param view the view
	 */
	public static void playTranAnimation(final View view, final float offsetX) {
		float originalX =  - offsetX;
		float finalX = 0;
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new TranslateAnimation(originalX,finalX,0, 0));
		animationSet.setDuration(200);
		animationSet.setInterpolator(new AccelerateInterpolator());
		animationSet.setFillAfter(true);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//两秒后再调
				view.postDelayed(new Runnable(){

					@Override
					public void run(){
						playTranXAnimation(view,offsetX);
					}
				}, 2000);
			}
		});

		view.startAnimation(animationSet);
	}

    /**
     * 旋转动画
     * @param v
     * @param durationMillis
     * @param repeatCount  Animation.INFINITE
     * @param repeatMode  Animation.RESTART
     */
    public static void playRotateAnimation(View v, long durationMillis, int repeatCount, int repeatMode) {
        //创建AnimationSet对象
        AnimationSet animationSet = new AnimationSet(true);
        //创建RotateAnimation对象
        RotateAnimation rotateAnimation = new RotateAnimation(0f,+360f,
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画持续
        rotateAnimation.setDuration(durationMillis);
        rotateAnimation.setRepeatCount(repeatCount);
        //Animation.RESTART
        rotateAnimation.setRepeatMode(repeatMode);
        //动画插入器
        rotateAnimation.setInterpolator(v.getContext(), android.R.anim.decelerate_interpolator);
        //添加到AnimationSet
        animationSet.addAnimation(rotateAnimation);
        //开始动画
        v.startAnimation(animationSet);
	}

	/**
	 * 抖动动画，左右抖动
	 * @param context 上下文
	 * @param v 要执行动画的view
	 */
	public static void shake(Context context, View v){
		Animation shake = new TranslateAnimation(0, 10, 0, 0);//移动方向
		shake.setDuration(1000);//执行总时间
		shake.setInterpolator(new CycleInterpolator(7));//循环次数
		v.startAnimation(shake);
	}
	/**
	 * 缩放动画，按下时缩放，抬起时恢复
	 * @param v 要执行动画的view
	 * @param event 触摸事件
	 * @param listener 点击事件
	 * @return  触摸结果
	 */
	public static boolean setClickAnim(View v, MotionEvent event, View.OnClickListener listener){
		float start = 1.0f;
		float end = 0.95f;
		Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		Animation endAnimation = new ScaleAnimation(end, start, end, start,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(200);
		scaleAnimation.setFillAfter(true);
		endAnimation.setDuration(200);
		endAnimation.setFillAfter(true);
		switch (event.getAction()) {
			// 按下时调用
			case MotionEvent.ACTION_DOWN:
				v.startAnimation(scaleAnimation);
				v.invalidate();
				break;
			// 抬起时调用
			case MotionEvent.ACTION_UP:
				v.startAnimation(endAnimation);
				v.invalidate();
				if(listener!=null){
					listener.onClick(v);
				}
				break;
			// 滑动出去不会调用action_up,调用action_cancel
			case MotionEvent.ACTION_CANCEL:
				v.startAnimation(endAnimation);
				v.invalidate();
				break;
		}
		// 不返回true，Action_up就响应不了
		return true;
	}


	/**
	 * listView的动画
	 */

	/**
	 * 移动
	 * @param view
	 */
	public static void translateListView(View view) {
		TranslateAnimation animation = new TranslateAnimation(-600f, 0f, 0f, 0f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		view.startAnimation(animation);
	}

	public static void translateListView2(View view) {
		TranslateAnimation animation = new TranslateAnimation(view.getWidth(), 0f, 0f, 0f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		view.startAnimation(animation);
	}

	/**
	 * 缩放
	 * @param view
	 */
	public static void scaleListView(View view) {
		ScaleAnimation animation =new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		view.startAnimation(animation);
	}

}