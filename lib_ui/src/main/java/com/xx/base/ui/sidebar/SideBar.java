package com.xx.base.ui.sidebar;


import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 
 * @author Mr.Z
 */
public class SideBar extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener	onTouchingLetterChangedListener;
	// 26个字母
	public static String[] mSideLetter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };

	private int	choose	= -1;																																			// 选中
	private Paint paint	= new Paint();
	private TextView mTextDialog;
	private int letterWidth;
	private float letterHeight;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		letterWidth = getMeasuredWidth();
		//得到一个字母要显示的高度
		letterHeight = getMeasuredHeight()*1f/mSideLetter .length;
	}
	/**
	 * 获取文本的高度
	 * @param text
	 * @return
	 */
	private int getTextHeight(String text) {
		//获取文本的高度
		Rect bounds = new Rect();
		paint.getTextBounds(text,0,text.length(), bounds);
		return bounds.height();
	}

	/**
	 * 重写这个方法
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < mSideLetter .length; i++) {

			paint.setColor(Color.parseColor("#f9ad73"));
			paint.setTypeface(Typeface.DEFAULT);
			paint.setAntiAlias(true);
			paint.setTextSize(40);

			float letterCharWidth = paint.measureText(mSideLetter [i]);
			float x = letterWidth/2 - letterCharWidth/2;
			float y = letterHeight * i + letterHeight;

			paint.setColor(choose==i?Color.parseColor("#f9ad73"):Color.parseColor("#f9ad73"));

			canvas.drawText(mSideLetter [i], x, y, paint);

			paint.reset();// 重置画笔
		}
//		for (int i = 0; i < mSideLetter.length; i++) {
//			paint.setColor(Color.parseColor("#f9ad73"));
//			paint.setTypeface(Typeface.DEFAULT);
//			paint.setAntiAlias(true);
//			paint.setTextSize(40);
//			// 选中的状态
//			if(i == choose) {
//				paint.setColor(Color.parseColor("#ffffff"));
//				paint.setFakeBoldText(true);
//			}
//			// x坐标等于中间-字符串宽度的一半.
//			float xPos = letterWidth / 2 - paint.measureText(b[i]) / 2;
//			float yPos = getTextHeight * i + singleHeight;
//			canvas.drawText(b[i], xPos, yPos, paint);
//
//            // 选中的状态
//            if(i == choose) {
//                paint.setColor(Color.parseColor("#f9ad73"));
//                canvas.drawCircle(xPos, yPos, 10f, paint);
//            }
//			paint.reset();// 重置画笔
//		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		final int c = (int) (y / letterHeight);
		switch (action) {
			case MotionEvent.ACTION_UP:
				// 抬起
				choose = -1;
				setBackground(new ColorDrawable(0x00000000));
				invalidate();
				if(mTextDialog != null) {
					mTextDialog.setVisibility(View.INVISIBLE);
				}
				break;
			default:
				setBackground(new ColorDrawable(0x70000000));
				if(oldChoose != c) {
					if(c >= 0 && c < mSideLetter.length) {
						if(listener != null) {
							listener.onTouchingLetterChanged(mSideLetter[c]);
						}
						if(mTextDialog != null) {
							mTextDialog.setText(mSideLetter[c]);
							mTextDialog.setVisibility(View.VISIBLE);
						}
						choose = c;
						invalidate();
					}
				}
				break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 * 
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		void onTouchingLetterChanged(String s);
	}

}