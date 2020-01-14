package com.genialsir.ppdemo.mvp.presenter;

import com.genialsir.ppdemo.mvp.contract.SplashContract;
import com.genialsir.projectplanner.mvp.presenter.BasePresenter;

/**
 * @author genialsir@163.com (GenialSir) on 2020/1/14
 */
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
        super(model, rootView);
    }
}
