package com.genialsir.ppdemo.mvp.ui.activity;

import android.app.Activity;

import com.genialsir.ppdemo.R;
import com.genialsir.ppdemo.mvp.contract.SplashContract;
import com.genialsir.ppdemo.mvp.presenter.SplashPresenter;
import com.genialsir.projectplanner.annotation.ContentViewInject;
import com.genialsir.projectplanner.mvp.view.BaseMvpActivity;

import androidx.annotation.NonNull;

/**
 * @author genialsir@163.com (GenialSir) on 2020/1/14
 */
@ContentViewInject(contentViewID = R.layout.activity_splash)
public class SplashActivity extends BaseMvpActivity<SplashPresenter>
        implements SplashContract.View {
    @Override
    public void initContract() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
