package com.xx.base.project.refresh.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xhttp.lib.BaseHttpUtils;
import com.xhttp.lib.BaseResult;
import com.xhttp.lib.callback.HttpResultCallBack;
import com.xhttp.lib.config.BaseErrorInfo;
import com.xx.base.org.dialog.BaseDialogUtils;
import com.xx.base.org.superadapter.BaseSuperAdapter;
import com.xx.base.org.util.BaseDensityUtils;
import com.xx.base.org.util.BaseStringUtils;
import com.xx.base.project.dialog.ProDialogUtils;
import com.xx.base.ui.viewgroup.BaseTitleLayout;
import org.jetbrains.annotations.NotNull;
import org.tangtown.com.lib_project.R;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * 封装了下拉刷新上滑加载的用法，网络数据请求，空数据，错误数据处理，列表展示等等逻辑
 * @author Lixingxing
 */
public abstract class ProRefreshViewHelper<T> {

    private int viewStatu = view_default;
    public static final int view_default = 0;
    public static final int view_empty = 1;
    public static final int view_error = 2;

    public static final int data_firstload = 0;
    public static final int data_refresh = 1;
    public static final int data_loadmore = 2;

    public Context context;
    public Activity activity;
    public List<T> mData = new ArrayList<>();
    public List<T> list = new ArrayList<>();

    public ProRefreshViewHelper(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
    }
    public ProRefreshViewHelper(Activity activity) {
        this.context = activity;
        this.activity = activity;
    }
    public ProRefreshViewHelper( Context context) {
        this.context = context;
        this.activity = (Activity)context ;
    }

    /****************************** view 相关*******************************************/
    // 刷新控件
    public RefreshLayout refreshLayout;
    // 刷新主体 id 和 view
    public int mContentViewLayoutId;
    public View contentView;
    // 刷新主体包含的列表  id必须设置为 R.id.refreshView
    protected View viewList;

    // 拓展刷新列表内容布局   列表id必须为 refreshView
    public @LayoutRes
    int getContentViewLayoutId() {
        return 0;
    }

    // 头部标题
    public BaseTitleLayout titleLayout;

    // 是否展示头部标题
    public abstract boolean getShowTitle();

    // 处理头部标题方法
    public void initTitle(View view) {
    }


    // 空数据状态显示的View
    public View mEmptyView = null;

    // 是否显示空页面
    public boolean isShowEmptyView() {
        return true;
    }

    public @LayoutRes
    int getEmptyViewLayoutId() {
        return R.layout.default_empty;
    }

    public View getEmptyView() {
        return null;
    }

    // 初始化设置 emptyView方法
    public void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
        }
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(context).inflate(getEmptyViewLayoutId() == 0 ? R.layout.default_empty : getEmptyViewLayoutId(), null);
        }
    }


    // 页面其他View
    protected FrameLayout default_bottom, default_bottom2;
    protected ViewStub viewStubTop, viewStubBottom;
    protected FrameLayout default_top;

    // 不覆盖 listView 在整个页面的底部
    protected void initBottom(View view) {
        default_bottom = (FrameLayout) view.findViewById(R.id.default_bottom);
        viewStubBottom = (ViewStub) view.findViewById(R.id.ViewStub);
    }

    // 覆盖在 listView底部
    protected void initCoverBottom(View view) {
//        default_bottom2 = (FrameLayout) view.findViewById(R.id.default_bottom2);
    }

    // listView之上,title之下的view
    protected void initTop(View view) {
        default_top = (FrameLayout) view.findViewById(R.id.default_top);
        viewStubTop = (ViewStub) view.findViewById(R.id.viewStubTop);
    }

    protected Dialog dataDialog;


    /*****************************************数据请求参数*****************************/
    // 获取请求路径
    public abstract String getUrl();

    // 获取请求参数
    public abstract Object[] getParam();

    protected String currUrl;
    protected Object[] currParams;
    protected int page = 1;
    protected int oldPage = 1;
    protected String lastId = "0";

    // 初始页数
    public int getFirstPage() {
        return 1;
    }

    // 分页数量
    public int getPageSize() {
        return 200;
    }

    // 设置分页方式
    public PageType getPageType() {
        return PageType.BY_PAGE;
    }

    // 页面列表类型  ListView RecyclerView等
    public @NotNull
    ListType getListType() {
        return ListType.RecylerView;
    }

    // 是否显示获取数据的错误提示
    public boolean getShowErrorMsg() {
        return true;
    }

    public boolean getShowNoDataMsg() {
        return true;
    }

    // 是否显示没有获取数据的提示
    public boolean getShowNoDataMsg1() {
        return false;
    }

    // 是否显示没有刷新到数据的提示
    public boolean getShowNoDataMsg2() {
        return true;
    }

    // 是否显示没有加载到数据的提示
    public boolean getShowNoDataMsg3() {
        return true;
    }

    // 设置是否打开测试模式
    public boolean openTest() {
        return false;
    }


    /****************************常用方法*****************************/

    // 初始化View布局
    public void initView(View view) {
        titleLayout = view.findViewById(R.id.titleLayout);
        if (titleLayout != null) {
            titleLayout.setVisibility(getShowTitle() ? View.VISIBLE : View.GONE);
            initTitle(titleLayout);
        }
        initTop(view);
        initBottom(view);
        initCoverBottom(view);
        initListView(view);
        initOtherView();
        initAdapter();
        initDividerHeight();
    }

    // 设置刷新相关的View
    public void initListView(View view) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        if (getContentViewLayoutId() == 0) {
            mContentViewLayoutId = getListType().contentId;
        }else{
            mContentViewLayoutId = getContentViewLayoutId();
        }
        // 获取刷新内容
        contentView = LayoutInflater.from(context).inflate(mContentViewLayoutId, null);
        refreshLayout.setRefreshContent(contentView);

        if (refreshLayout != null) {
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setEnableLoadMore(false);
            viewList = contentView.findViewById(R.id.refreshView);
            // 如果需要展示空页面
            if (isShowEmptyView()) {
                initEmptyView();
            }
            initRefreshHeadAndFooter();
        }
//        else {
//            initEmpty();
//            loadFrameLayout.showEmptyView();
//        }
    }

    /**
     * 在 setAdapter 生成之前的一些操作
     */
    protected abstract void initOtherView();

    // 设置是否有分割线 默认有
    protected boolean isHasDivider(){
        return false;
    }
    protected int getDividerHeight(){
        return BaseDensityUtils.dip2px(1);
    }
    /**
     * 为列表设置间隔 RecylerView模式下,会被 其他的addItemDecoration方法覆盖
     */
    protected void initDividerHeight(){
        if(getListType() == ListType.ListView){
            if(isHasDivider()){
                ((ListView) viewList).setDividerHeight(getDividerHeight());
            }else{
                ((ListView) viewList).setDividerHeight(0);
            }
        }else if(getListType() == ListType.RecylerView){
            if(isHasDivider()){
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                dividerItemDecoration.setDrawable(new ColorDrawable(Color.parseColor("#808080")));
                ((RecyclerView) viewList).addItemDecoration(dividerItemDecoration);

            }
        }
    }

    // 如果是 RecyclerView 设置 LayoutManager
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        return new LinearLayoutManager(context);
    }

    protected void initAdapter() {
        adapter = getListAdapter();
        if (adapter != null && viewList != null) {
            if (viewList instanceof ListView) {
                ((ListView) viewList).setAdapter(adapter);
            } else if (viewList instanceof RecyclerView) {
                ((RecyclerView) viewList).setLayoutManager(getLayoutManager());
                ((RecyclerView) viewList).setAdapter(adapter);
            } else if (viewList instanceof GridView) {
                ((GridView) viewList).setAdapter(adapter);
            } else {

            }
        }
    }

    /**
     * 设置刷新方式 可以在 initOtherView中调用
     *
     * @param onRefreshListener
     */
    protected void setListViewStart(OnRefreshListener onRefreshListener) {
        if (null != onRefreshListener) {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    protected void setListViewBoth(OnRefreshLoadMoreListener onRefreshListener) {
        if (null != onRefreshListener) {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadMore(true);
            refreshLayout.setOnRefreshLoadMoreListener(onRefreshListener);
        }
    }

    /**
     * 获取adapter
     *
     * @return
     */
    public BaseSuperAdapter<T> adapter;

    protected abstract BaseSuperAdapter<T> getListAdapter();

    /**
     * 自定义下拉刷新头部和上拉加载控件
     */
    protected void initRefreshHeadAndFooter() {
    }

    // 获取数据前的处理方法
    public void getData(int type, boolean isShow) {
        if(openTest()){
            getTestRefreshData(type);
        }else{
            currUrl = getUrl();
            if (BaseStringUtils.isEmpty(currUrl) || adapter == null) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (isShowEmptyView()) {
                    changeViewByStatus(view_empty);
                }
                return;
            }
            switch (type) {
                case data_firstload:
                case data_refresh:
                    if (getPageType() == PageType.BY_PAGE) {
                        oldPage = page = getFirstPage();
                    } else if (getPageType() == PageType.BY_LAST_ID) {
                        lastId = "0";
                    }
                    break;
                case data_loadmore:
                    if (getPageType() == PageType.BY_PAGE) {
                        page++;
                    } else if (getPageType() == PageType.BY_LAST_ID) {
                        lastId = adapter.getLastId();
                    }
                    break;
            }
            getRefreshData(type, isShow);
        }
    }
    // 正常获取网络数据的方法
    protected void getRefreshData(final int type, boolean isShow) {
        final long time = System.currentTimeMillis();
        currParams = getParam();
        if (isShow) {
            dataDialog = ProDialogUtils.showDiloag(context);
        }
        list.clear();
        BaseHttpUtils baseHttpUtils = new BaseHttpUtils(dataDialog);
        if (getShowNoDataMsg()) {
            // 设置空数据时的是否文字提醒
            if (type == data_firstload) {
                baseHttpUtils.initShowEmptyMessage(getShowNoDataMsg1());
                baseHttpUtils.initEmptyMsg(getNoDataForUpdate());
            } else if (type == data_refresh) {
                baseHttpUtils.initShowEmptyMessage(getShowNoDataMsg2());
                baseHttpUtils.initEmptyMsg(getNoDataForRefresh());
            } else {
                baseHttpUtils.initShowEmptyMessage(getShowNoDataMsg3());
                baseHttpUtils.initEmptyMsg(getNoDataForLoadMore());
            }
        }
        baseHttpUtils.initUrl(currUrl)
                .initParams(currParams)
                .initClass((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
                .initHttpResultCallBack(new HttpResultCallBack() {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        list = baseResult.getResult().getResult_list();
                        switch (type) {
                            case data_firstload:
                                updateList(0);
                                break;
                            case data_refresh:
                                refreshList(0);
                                break;
                            case data_loadmore:
                                loadMoreList(0);
                                break;
                        }
                    }

                    @Override
                    public void onEmpty(BaseErrorInfo errorInfo) {
                        super.onEmpty(errorInfo);
                        page = oldPage;
                        switch (type) {
                            case data_firstload:
                                updateList(1);
                                break;
                            case data_refresh:
                                refreshList(1);
                                break;
                            case data_loadmore:
                                loadMoreList(1);
                                break;
                        }
                    }

                    @Override
                    public void onFail(BaseErrorInfo errorInfo) {
                        super.onFail(errorInfo);
                        page = oldPage;
                        switch (type) {
                            case data_firstload:
                                updateList(2);
                                break;
                            case data_refresh:
                                refreshList(2);
                                break;
                            case data_loadmore:
                                loadMoreList(2);
                                break;
                        }
                    }

                    @Override
                    public void onFinal(BaseResult baseResult) {
                        super.onFinal(baseResult);
                        int time2 = (int) (System.currentTimeMillis() - time);
                        time2 = (time2 < 800) ? 800 : time2;
                        switch (type) {
                            case data_firstload:
                            case data_refresh:
                                refreshLayout.finishRefresh(time2);
                                break;
                            case data_loadmore:
                                refreshLayout.finishLoadMore(time2);
                                break;
                        }
                    }
                })
                .postList();
    }
    // 获取测试数据的方法
    protected void getTestRefreshData(final int type){
        list = getTestData();
        if( list == null ){
            list = new ArrayList<>();
        }
        if(list.isEmpty()){
            switch (type) {
                case data_firstload:
                    updateList(1);
                    break;
                case data_refresh:
                    refreshList(1);
                    break;
                case data_loadmore:
                    loadMoreList(1);
                    break;
            }
        }else{
            switch (type) {
                case data_firstload:
                    updateList(0);
                    break;
                case data_refresh:
                    refreshList(0);
                    break;
                case data_loadmore:
                    loadMoreList(0);
                    break;
            }
        }

        switch (type) {
            case data_firstload:
            case data_refresh:
                refreshLayout.finishRefresh(800);
                break;
            case data_loadmore:
                refreshLayout.finishLoadMore(800);
                break;
        }

    }
    protected List<T> getTestData(){
        return null;
    }

    // 第一次进入的时候加载数据
    public void updateList(int dataCode) {
        switch (dataCode) {
            case 0: // 请求成功
                onHasDataEvent(data_firstload);
                break;
            case 1: // 空数据
                onNoDataEvent(data_firstload);
                break;
            case 2: // 错误
                onNoDataError(data_firstload);
                break;
        }
        if (mData.size() == 0) {
            onNoAllDataEvent();
        } else {
            onHasAllDataEvent();
        }
    }

    protected void refreshList(int dataCode) {
        switch (dataCode) {
            case 0: // 请求成功
                onHasDataEvent(data_refresh);
                break;
            case 1: // 空数据
                onNoDataEvent(data_refresh);
                break;
            case 2: // 错误
                onNoDataError(data_refresh);
                break;
        }
        if (mData.size() == 0) {
            onNoAllDataEvent();
        } else {
            onHasAllDataEvent();
        }

    }

    protected void loadMoreList(int dataCode) {
        switch (dataCode) {
            case 0: // 请求成功
                onHasDataEvent(data_loadmore);
                break;
            case 1: // 空数据
                onNoDataEvent(data_loadmore);
                break;
            case 2: // 错误
                onNoDataError(data_loadmore);
                break;
        }
        if (mData.size() == 0) {
            onNoAllDataEvent();
        } else {
            onHasAllDataEvent();
        }
    }

    /**
     * 第一次获取，刷新，成功获取到数据的时候
     */
    protected void onHasDataEvent(int code) {
        changeViewByStatus(view_default);
        switch (code) {
            case data_firstload:
                onHasDataEventForUpdate();
                break;
            case data_refresh:
                onHasDataEventForRefresh();
                break;
            case data_loadmore:
                onHasDataEventForLoadMore();
                break;
        }
    }

    protected void onHasDataEventForUpdate() {
        adapter.replaceAll(list);
    }

    protected void onHasDataEventForRefresh() {
        adapter.replaceAll(list);
    }

    protected void onHasDataEventForLoadMore() {
        adapter.addAll(list);
    }


    /**
     * 第一次获取，刷新，加载没有数据的时候
     */
    protected void onNoDataEvent(int code) {
        switch (code) {
            case data_firstload:
                onNoDataEventForUpdate();
                break;
            case data_refresh:
                onNoDataEventForRefresh();
                break;
            case data_loadmore:
                onNoDataEventForLoadMore();
                break;
        }
    }

    private String noDataForUpdate = "暂无内容";

    public String getNoDataForUpdate() {
        return noDataForUpdate;
    }

    protected void onNoDataEventForUpdate() {
        adapter.clear();
        if (mData.size() == 0) {
            changeViewByStatus(view_empty);
        }
    }

    private String noDataForRefresh = "已经是最新";

    public String getNoDataForRefresh() {
        return noDataForRefresh;
    }

    protected void onNoDataEventForRefresh() {
        adapter.clear();
        if (mData.size() == 0) {
            changeViewByStatus(view_empty);
        }
    }

    private String noDataForLoadMore = "已经没有了...";

    public String getNoDataForLoadMore() {
        return noDataForLoadMore;
    }

    protected void onNoDataEventForLoadMore() {
    }


    /**
     * 第一次获取，刷新，加载 出现异常时
     */
    protected void onNoDataError(int code) {
        switch (code) {
            case data_firstload:
                onNoDataErrorForUpdate();
                break;
            case data_refresh:
                onNoDataErrorForRefresh();
                break;
            case data_loadmore:
                onNoDataErrorForLoadMore();
                break;
        }
    }

    protected void onNoDataErrorForUpdate() {
        if (mData.size() == 0) {
            changeViewByStatus(view_empty);
        }
    }

    protected void onNoDataErrorForRefresh() {
    }

    protected void onNoDataErrorForLoadMore() {
    }


    // 做完任意操作后 整体页面没有数据之后的回调
    protected void onNoAllDataEvent() {
    }

    // 做完任意操作后 整体页面有数据之后的回调
    protected void onHasAllDataEvent() {
    }

    // 根据状态切换不同页面
    public void changeViewByStatus(int viewStatu) {
        if (this.viewStatu == viewStatu) {
            return;
        }
        this.viewStatu = viewStatu;
        switch (viewStatu) {
            case view_default: {
                if (contentView != null && refreshLayout != null) {
                    refreshLayout.setRefreshContent(contentView);
                }
                break;
            }
            case view_empty: {
                if (mEmptyView != null && refreshLayout != null) {
                    refreshLayout.setRefreshContent(mEmptyView);
                }
                break;
            }
        }

    }


    public void finish() {
        context = null;
        activity = null;
        mData.clear();
        list.clear();
    }
}
