package com.genialsir.projectplanner.mvp.model;


import com.genialsir.projectplanner.mvp.IModel;

import androidx.lifecycle.LifecycleObserver;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/12
 */
public class BaseModel implements IModel, LifecycleObserver {

    //TODO 根据自己的网络框架来进行构建
    public BaseModel(){

    }

    @Override
    public void finish() {

    }
}
