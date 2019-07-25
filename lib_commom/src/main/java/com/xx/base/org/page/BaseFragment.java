package com.xx.base.org.page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 延时加载的 Fragment, 只有显示的时候才会去加载视图和数据请求等操作
 */
public abstract class BaseFragment extends Fragment {

    protected View view = null;
    protected Context baseContext = null;
    protected int resId = 0;

    protected BaseFragment(){
    }

    protected BaseFragment(@LayoutRes int resId){
        this.resId = resId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            hasBundle(args);
        }
    }
    protected void hasBundle(Bundle args){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null == view){
            if(resId != 0){
                view = inflater.inflate(resId,container,false);
            }
        }
        return view;
    }

    protected abstract void firstInitViews(View view);

    protected boolean isFirstVisible = true;
    protected boolean isFirstInvisible = true;
    protected boolean isPrepared;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            firstInitViews(view);
            onFirstUserVisible();
            onShow();
        } else {
            isPrepared = true;
        }
    }

    // 手机进行了锁屏开屏或者home操作
    boolean isFromPhone = false;
    boolean isVisibleToUser = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
                onShow();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
            } else {
                onUserInvisible();
                onHide();
            }
        }
    }

    protected void onShow(){}
    protected void onHide(){}
    protected abstract void onFirstUserVisible();
    protected abstract void onUserVisible();
    protected abstract void onUserInvisible();


    @Override
    public void onResume() {
        super.onResume();
        if(isFromPhone){
            // 之前有锁屏或者 home操作
            // 直接调用onResume 而不调用setUserVisibleHint
            isFromPhone = false;
            if(isVisibleToUser){
                if (isFirstVisible) {
                    isFirstVisible = false;
                    initPrepare();
                } else {
                    onUserVisible();
                }
                onShow();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isFromPhone = true;
        if(isVisibleToUser){
            if (isFirstInvisible) {
                isFirstInvisible = false;
            } else {
                onUserInvisible();
                onHide();
            }
        }
    }

    @Override
    public void onDestroy() {
        DetoryViewAndThing();
        super.onDestroy();
    }
    protected abstract void DetoryViewAndThing();

}