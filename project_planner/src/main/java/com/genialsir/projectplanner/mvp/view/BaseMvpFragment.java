package com.genialsir.projectplanner.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genialsir.projectplanner.annotation.ContentViewInject;
import com.genialsir.projectplanner.mvp.IPresenter;
import com.genialsir.projectplanner.mvp.view.rules.HostingAgreement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author genialsir@163.com (GenialSir) on 2019/12/13
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends Fragment
        implements HostingAgreement, View.OnClickListener {

    protected P mPresenter;
    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initContract();
        ContentViewInject layoutAnnotation = getClass().getAnnotation(ContentViewInject.class);
        if (layoutAnnotation != null) {
            int mainLayout = layoutAnnotation.contentViewID();
            if (mainLayout > 0) {
                View contentView = inflater.inflate(mainLayout, container, false);
                mUnBinder = ButterKnife.bind(BaseMvpFragment.this, contentView);
                initData();
                initView(contentView, inflater, container, savedInstanceState);
                return contentView;
            }
            throw new RuntimeException("contentViewID is " + mainLayout);
        } else {
            throw new RuntimeException("layoutAnnotation is null.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }

    protected abstract void initData();

    protected abstract void initView(View contentView, LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState);

    protected abstract void initListener();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.finish();
        }
        mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
