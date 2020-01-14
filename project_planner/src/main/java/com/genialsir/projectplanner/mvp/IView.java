package com.genialsir.projectplanner.mvp;


import androidx.annotation.NonNull;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/12
 */
public interface IView {

    void showLoading();

    void hideLoading();

    void showMessage(@NonNull String message);
}
