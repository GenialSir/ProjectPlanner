package com.genialsir.projectplanner.okhttpprocess.resonance;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public interface OkCallback {
    /**
     * Returns true if the code is in [200..300).
     * */
    void onCodeSuccess(String response);
    /**
     * Returns true if the code not is in [200..300).
     * */
    void onCodeError(String response);
    /**
     * Request is failure.
     * */
    void onReqFailure(String response);

    /**
     * Thread Pool Thread
     */
    void uploadProgress(float progress);
}
