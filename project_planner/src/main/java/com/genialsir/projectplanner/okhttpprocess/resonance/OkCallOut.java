package com.genialsir.projectplanner.okhttpprocess.resonance;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public interface OkCallOut<T>{
    void outputSuccess(T result);
    void outputError(Object result);
    void resFailure(Object result);
    void loadProgress(float progress);
}