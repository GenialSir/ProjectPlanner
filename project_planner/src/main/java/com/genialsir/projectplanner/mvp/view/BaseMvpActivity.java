package com.genialsir.projectplanner.mvp.view;

import android.os.Bundle;
import android.view.View;

import com.genialsir.projectplanner.annotation.ContentViewInject;
import com.genialsir.projectplanner.mvp.IPresenter;
import com.genialsir.projectplanner.mvp.view.rules.HostingAgreement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/12
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends AppCompatActivity
        implements HostingAgreement ,View.OnClickListener{

    protected P mPresenter;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewInject();
        initContract();
        initData();
        initView();
        initListener();
    }

    private void initViewInject() {
        ContentViewInject layoutAnnotation = getClass().getAnnotation(ContentViewInject.class);
        if (layoutAnnotation != null) {
            int mainLayout = layoutAnnotation.contentViewID();
            if (mainLayout > 0) {
                setContentView(mainLayout);
                mUnBinder = ButterKnife.bind(this);
                return;
            }
            throw new RuntimeException("contentViewID is " + mainLayout);
        } else {
            throw new RuntimeException("layoutAnnotation is null.");
        }
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.finish();
            mPresenter = null;
        }
    }
}
