package com.xx.base.ui.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qqkj.base.ui.R;
import com.xx.base.ui.util.BasePrexUtils;

/**
 * Created by lixingxing on 2018/10/8.
 */

public class IconLayout extends RelativeLayout {
    private Context mContext;
    private ImageView imageView;
    private TextView textView;

    /**
     * 左右结构，图片在左，文字在右
     */
    public static final int STYLE_ICON_LEFT = 0;
    /**
     * 左右结构，图片在右，文字在左
     */
    public static final int STYLE_ICON_RIGHT = 1;
    /**
     * 上下结构，图片在上，文字在下
     */
    public static final int STYLE_ICON_UP = 2;
    /**
     * 上下结构，图片在下，文字在上
     */
    public static final int STYLE_ICON_DOWN = 3;

    
    private int icon_src = R.mipmap.ic_launcher;
    public void setIcon_src(int icon_src) {
        this.icon_src = icon_src;
        if(imageView!=null){
            imageView.setImageResource(icon_src);
        }
    }

    private int icon_src_press = 0;
    /**
     * 文字内容
     */
    private String icon_text = "";

    public void setIcon_text(String icon_text) {
        this.icon_text = icon_text;
        if(textView == null){
            initText();
            return;
        }
        if(textView!=null){
            textView.setText(icon_text);
        }
    }

    /**
     * 两个控件的位置结构
     */
    private int icon_location;
    /**
     * 两个控件之间的间距，默认为8dp
     */
    private int spacing = 8;

    public void setSpacing(int spacing) {
        this.spacing = spacing;
        if(textView != null){
            initText();
            return;
        }
    }

    /**
     * 文字大小
     */
    float textSize = 10;
    /**
     * 文字颜色
     */
    int icon_text_color = Color.parseColor("#000000");

    public void setIcon_text_color(int icon_text_color) {
        this.icon_text_color = icon_text_color;
        if(textView == null){
            initText();
            return;
        }
        if(textView!=null){
            textView.setTextColor(icon_text_color);
        }
    }

    /**
     * 标示onTouch方法的返回值，用来解决onClick和onTouch冲突问题
     */
    private boolean isCost = true;

    private OnClickListener onClickListener = null;

    public interface OnClickListener {
        void onClick(View v);
    }



    public IconLayout(Context context) {
        this(context,null);
    }

    public IconLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconLayout);

        icon_src = typedArray.getResourceId(R.styleable.IconLayout_icon_src,icon_src);
        icon_src_press = typedArray.getResourceId(R.styleable.IconLayout_icon_src_press,icon_src_press);
        icon_text = typedArray.getString(R.styleable.IconLayout_icon_text);
        icon_location  = typedArray.getInt(R.styleable.IconLayout_icon_location,0);
        //设置两个控件之间的间距
        spacing = typedArray.getDimensionPixelSize(R.styleable.IconLayout_icon_spacing, (int) BasePrexUtils.dip2px(8f));
        //设置文本字体大小
        textSize = typedArray.getDimensionPixelSize(R.styleable.IconLayout_icon_text_size, 15);
        icon_text_color = typedArray.getColor(R.styleable.IconLayout_icon_text_color,icon_text_color);
        typedArray.recycle();

        initView();

    }

    private void initView(){
       initImageView();
        if(icon_text == null || "".equals(icon_text)){

        }else{
           initText();
        }
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                setTouchStyle(event.getAction());
                return false;
            }
        });

    }

    private void initImageView() {
        if(imageView == null){
            imageView = new ImageView(mContext);
        }
        imageView.setImageResource(icon_src);
        imageView.setId(R.id.icon);
        addView(imageView);
    }

    private void initText() {
        if(textView == null){
            textView = new TextView(mContext);
            addView(textView);
        }
        setIconStyle(icon_location);
        textView.setId(R.id.icon_text);
        textView.setText(icon_text);
        textView.setTextSize(textSize);
        textView.setTextColor(icon_text_color);
    }

    /**
     * 根据按下或者抬起来改变背景和文字样式
     *
     * @param state
     * @return isCost
     */
    private boolean setTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {
//            if (backColorPress != 0) {
//                setBackgroundColor(backColorPress);
//            }
            if (icon_src_press != 0) {
                imageView.setImageResource(icon_src_press);
            }
//            if (textColorPress != null) {
//                tvContent.setTextColor(textColorPress);
//            }
        }
        if (state == MotionEvent.ACTION_UP) {
//            if (backColor != 0) {
//                setBackgroundColor(backColor);
//            }
            if (icon_src != 0) {
                imageView.setImageResource(icon_src);
            }
//            if (textColor != null) {
//                tvContent.setTextColor(textColor);
//            }
        }
        return isCost;
    }



    /**
     * 设置图标位置
     * 通过重置LayoutParams来设置两个控件的摆放位置
     * @param style
     */
    public void setIconStyle(int style) {
        icon_location = style;
        RelativeLayout.LayoutParams lp;
        switch (style) {
            case STYLE_ICON_LEFT:
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                imageView.setLayoutParams(lp);
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                lp.leftMargin = spacing;
                textView.setLayoutParams(lp);
                break;
            case STYLE_ICON_RIGHT:
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                textView.setLayoutParams(lp);
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                lp.addRule(RelativeLayout.RIGHT_OF, textView.getId());
                lp.leftMargin = spacing;
                imageView.setLayoutParams(lp);
                break;
            case STYLE_ICON_UP:
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                imageView.setLayoutParams(lp);
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.BELOW, imageView.getId());
                lp.topMargin = spacing;
                textView.setLayoutParams(lp);
                break;
            case STYLE_ICON_DOWN:
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                textView.setLayoutParams(lp);
                lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.BELOW, textView.getId());
                lp.topMargin = spacing;
                imageView.setLayoutParams(lp);
                break;
            default:
                break;
        }
    }

//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
}
