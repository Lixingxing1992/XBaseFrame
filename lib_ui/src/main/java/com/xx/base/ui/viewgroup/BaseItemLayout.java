package com.xx.base.ui.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.qqkj.base.ui.R;

/**
 * 条目展示的view
 * @author Lixingxing
 */
public class BaseItemLayout extends FrameLayout {
    private Context mContext;

    private ImageView itemLayout_leftImage;
    private ImageView itemLayout_rightImage;
    private TextView itemLayout_text;
    private TextView itemLayout_text2;

    //左边图片
    private int item_leftImageResource = 0;
    //右边图片
    private int item_RightImageResource = 0;
    //文字内容
    private String item_text = "";
    //文字颜色
    private int item_textColor = Color.BLACK;

    //是否显示第二段文字
    private boolean item_text2Visible = false;

    public BaseItemLayout( Context context) {
        this(context,null);
    }

    public BaseItemLayout( Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseItemLayout( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
        initView();
        changeByValue();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.BaseItemLayout);

        item_leftImageResource = typedArray.getResourceId(R.styleable.BaseItemLayout_item_leftImageResource,0);
        item_RightImageResource = typedArray.getResourceId(R.styleable.BaseItemLayout_item_rightImageResource,0);
        item_text = typedArray.getString(R.styleable.BaseItemLayout_item_text);
        item_textColor = typedArray.getColor(R.styleable.BaseItemLayout_item_textColor,Color.BLACK);
        item_text2Visible = typedArray.getBoolean(R.styleable.BaseItemLayout_item_text2Visible,false);

        typedArray.recycle();

    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_itemlayout, this);
        itemLayout_leftImage = view.findViewById(R.id.itemLayout_leftImage);
        itemLayout_rightImage = view.findViewById(R.id.itemLayout_rightImage);
        itemLayout_text = view.findViewById(R.id.itemLayout_text);
        itemLayout_text2 = view.findViewById(R.id.itemLayout_text2);
    }


    private void changeByValue(){
        itemLayout_leftImage.setImageResource(item_leftImageResource);
        itemLayout_rightImage.setImageResource(item_RightImageResource);
        itemLayout_text.setText(item_text);
        itemLayout_text.setTextColor(item_textColor);
        itemLayout_text2.setVisibility(item_text2Visible?View.VISIBLE:View.GONE);
    }


}
