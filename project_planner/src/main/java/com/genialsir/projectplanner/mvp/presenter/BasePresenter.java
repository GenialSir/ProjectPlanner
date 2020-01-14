package com.genialsir.projectplanner.mvp.presenter;


import com.genialsir.projectplanner.mvp.IModel;
import com.genialsir.projectplanner.mvp.IPresenter;
import com.genialsir.projectplanner.mvp.IView;
import com.genialsir.projectplanner.tools.Preconditions;

import org.greenrobot.eventbus.EventBus;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/12
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter,
        LifecycleObserver {

    protected M mModel;
    protected V mRootView;

    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null.", IView.class.getName());
        mRootView = rootView;
        initialization();

    }

    public BasePresenter(M model, V rootView) {
        Preconditions.checkNotNull(model, "%s cannot be null.", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null.", IView.class.getName());
        mModel = model;
        mRootView = rootView;
        initialization();
    }

    @Override
    public void initialization() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mRootView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    public boolean useEventBus() {
        return false;
    }

    @Override
    public void finish() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mModel != null) {
            mModel.finish();
        }
        mModel = null;
        mRootView = null;
    }
}
