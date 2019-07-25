package com.xx.base.org.page;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.xx.base.org.util.BaseActivityStack;
import com.xx.base.org.util.BaseStatusBarUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lixingxing on 2019/4/16.
 */
public abstract class BaseActivity extends AppCompatActivity{
    Bundle savedInstanceState;
    public Context baseContext;
    public Map<String, BroadcastReceiver> broadcastReceiverMap = new HashMap<String, BroadcastReceiver>();
    public FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        BaseStatusBarUtils.darkMode(this);
        if(null!=savedInstanceState){
            if(hasBundle(savedInstanceState)){
                initActivity();
            }
        }else{
            initActivity();
        }
    }
    /**
     * 如果有 savedInstanceState 执行的方法 。  一般Activity中 用onSaveInstanceState方法会用到
     *  return True的时候，会继续执行下面的代码 return False的时候，则不执行
     * @param savedInstanceState
     * @return
     */
    protected boolean hasBundle(Bundle savedInstanceState){
        return true;
    }

    /**
     * 当 activity设置  launchmode="singleTask" 时，如果调用相同的activity，会调用到此方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        initActivity();
    }

    protected void initActivity(){
        baseContext = this;
        BaseActivityStack.create().addActivity(this);
        manager = this.getSupportFragmentManager();

        this.onCreateBefore(savedInstanceState);
        this.initDefaultDatas(getIntent()); // 获取参数
        this.setRootView(); // 设置布局文件
        this.initView();   // 初始化控件
        this.getData(); // 获取数据
        this.registerReceivers(); // 注册广播
        this.doAction(); // 其他的操作
    }

    /**
     *  onCreate方法
     */
    public void onCreateBefore(Bundle savedInstanceState){
    }

    public void initDefaultDatas(Intent intent) {
        initDefaultData(intent);
    }
    /**
     * 获取参数
     */
    public abstract void initDefaultData(Intent intent);

    /**
     * 设置布局文件
     */
    public abstract void setRootView();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 获取网络数据
     */
    public abstract void getData();


    /**
     * 注册广播
     */
    public void registerReceivers(){}


    public void addReceivers(String brName, BroadcastReceiver broadcastReceiver) {
        if (!broadcastReceiverMap.containsKey(brName)) {
            registerReceiver(broadcastReceiver, new IntentFilter(brName));
            broadcastReceiverMap.put(brName, broadcastReceiver);
        }
    }
    public void clearReceiver() {
        Iterator it = broadcastReceiverMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            BroadcastReceiver broadcastReceiver = broadcastReceiverMap.get(key);
            if (null != broadcastReceiver) {
                try {
                    unregisterReceiver(broadcastReceiver);
                } catch (Exception e) {
                }
            }
        }
        broadcastReceiverMap.clear();
    }

    /**
     * 其他的操作
     */
    public void doAction(){}


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityStack.create().removeActivity(this);
        clearReceiver();
    }

    public <T extends View> T initView(int viewId) {
        return initView(viewId, null, null);
    }
    public <T extends View> T initView(int viewId, View.OnClickListener clickListener) {
        return initView(viewId, null, clickListener);
    }
    public <T extends View> T initView(int viewId, View parentView) {
        View view = null;
        if (parentView == null) {
            view = findViewById(viewId);
        } else {
            view = parentView.findViewById(viewId);
        }
        return (T) view;
    }
    public <T extends View> T initView(int viewId, View parentView, View.OnClickListener clickListener) {
        View view = null;
        if (parentView == null) {
            view = findViewById(viewId);
        } else {
            view = parentView.findViewById(viewId);
        }
        if (null != clickListener) {
            view.setOnClickListener(clickListener);
        }
        return (T) view;
    }



    View focusView = null;

    public void setFocusView(View focusView) {
        this.focusView = focusView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(focusView != null){
                int[] l = { 0, 0 };
                focusView.getLocationInWindow(l);
                int left = l[0], top = l[1], bottom = top + focusView.getHeight(), right = left
                        + focusView.getWidth();
                if (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom){
                }else{
                    // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                    View v = getCurrentFocus();
                    if (isShouldHideInput(v, event)) {
                        hideSoftInput(v.getWindowToken());
                    }
                }
            }else{
                // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                View v = getCurrentFocus();
                if (isShouldHideInput(v, event)) {
                    hideSoftInput(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                if(getWindow().getAttributes().softInputMode!=WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
                {
//                    CcAppUtil.showSoftInput(this);
                }
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
