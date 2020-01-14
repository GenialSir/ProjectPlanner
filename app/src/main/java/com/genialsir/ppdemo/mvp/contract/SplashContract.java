package com.genialsir.ppdemo.mvp.contract;

import android.app.Activity;

import com.genialsir.projectplanner.mvp.IModel;
import com.genialsir.projectplanner.mvp.IView;

/**
 * @author genialsir@163.com (GenialSir) on 2020/1/14
 */
public interface SplashContract {
    interface Model extends IModel{

    }

    interface View extends IView{
        Activity getActivity();
    }
}
