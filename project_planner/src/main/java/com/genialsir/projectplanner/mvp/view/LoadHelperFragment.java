package com.genialsir.projectplanner.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.genialsir.projectplanner.R;
import com.genialsir.projectplanner.annotation.LoadViewInject;
import com.genialsir.projectplanner.mvp.IPresenter;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/16
 */
public abstract class LoadHelperFragment<LHP extends IPresenter, T extends Serializable>
        extends BaseMvpFragment {

    /***
     * Because of the problem of cross-level object references, the BaseMapActivity mPresenter needs
     * to be overridden for use by subclasses.
     */
    protected LHP mLHPresenter;

    //Controls the number of pages loaded in the current RecyclerView page.
    private int page = 1;
    protected RecyclerView recyclerView;
    protected BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    //Load View ID holder.
    private LoadViewInject srlInject;

    @Override
    protected void initData() {
        initLHData();
    }

    @Override
    protected void initView(View contentView, LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        srlInject = getClass().getAnnotation(LoadViewInject.class);
        if (srlInject == null) {
            throw new RuntimeException("LoadViewInject is null.");
        }
        initSwipeRefreshLayout(contentView);
        initLoadView(contentView);
        initLHView(contentView, inflater, container, savedInstanceState);
        mLoadInitialListener();
    }

    @Override
    protected void initListener() {
        initLHListener();
    }

    /**
     * To prevent subclasses from directly using the initialization of the BaseMvpActivity, causing
     * irregular order problems, so re-abstract the BaseMvpActivity method.
     */
    protected abstract void initLHData();

    protected abstract void initLHView(View contentView, LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstanceState);

    protected abstract void initLHListener();

    private void initSwipeRefreshLayout(View contentView) {
        swipeRefreshLayout = contentView.findViewById(srlInject.refreshViewID());
        if (swipeRefreshLayout != null) {
            swipeRefreshLayoutShow(swipeRefreshLayout);
            setSRLayoutColor(R.color.brilliant_blue, R.color.orange);
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        } else {
            Logger.e("swipeRefreshLayout is null.");
        }
    }



    private void initLoadView(View contentView) {
        recyclerView = contentView.findViewById(srlInject.recyclerViewID());
        baseQuickAdapter = getBaseQuickAdapter();
        //toLoad.
        if (baseQuickAdapter == null || recyclerView == null) {
            Logger.e("RecyclerView or BaseQuickAdapter is null.");
            return;
        }
        initAdapter();
        if (!isDisableLoadMore()) {
            baseQuickAdapter.setOnLoadMoreListener(onLoadMoreListener, recyclerView);
        }
        baseQuickAdapter.setPreLoadNumber(3);
    }

    protected abstract BaseQuickAdapter<T, BaseViewHolder> getBaseQuickAdapter();


    private void initAdapter() {
        if (getColumnNumber() > 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, getColumnNumber()));
        } else {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext,
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }
        recyclerView.setAdapter(baseQuickAdapter);
    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    setPage(1);
                    mLoadInitialListener();
                }
            };


    private BaseQuickAdapter.RequestLoadMoreListener onLoadMoreListener =
            new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    setPage(++page);
                    mOnLoadMoreListener();
                }
            };


    protected void addData(List<T> data) {
        if (baseQuickAdapter == null || recyclerView == null) {
            return;
        }
        if (data == null) {
            baseQuickAdapter.loadMoreEnd();
            return;
        } else if (data.size() <= 0) {
            baseQuickAdapter.loadMoreEnd();
            return;
        }
        if (page == 1) {
            baseQuickAdapter.setNewData(data);
        } else {
            baseQuickAdapter.addData(data);
        }
        baseQuickAdapter.loadMoreComplete();
        baseQuickAdapter.notifyDataSetChanged();
    }

    //Initially loaded data.
    protected abstract void mLoadInitialListener();

    //Callback to load more data.
    protected abstract void mOnLoadMoreListener();

    protected int getPage() {
        return page;
    }

    protected void setPage(int page) {
        this.page = page;
    }

    /**
     * Configure how to load more data.
     */
    protected boolean isDisableLoadMore() {
        return false;
    }


    protected int getColumnNumber() {
        return 1;
    }

    protected void setSRLayoutColor(int waitColorID, int loadColorID) {
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(mContext, waitColorID),
                ContextCompat.getColor(mContext, loadColorID));
    }

    protected void swipeRefreshLayoutShow(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    protected void swipeRefreshLayoutHide(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLHPresenter != null) {
            mLHPresenter.finish();
            mLHPresenter = null;
        }
    }

}
