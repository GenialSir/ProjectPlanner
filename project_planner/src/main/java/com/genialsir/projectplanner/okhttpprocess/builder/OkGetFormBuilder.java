package com.genialsir.projectplanner.okhttpprocess.builder;


import com.genialsir.projectplanner.okhttpprocess.request.OkGetFormRequest;
import com.genialsir.projectplanner.okhttpprocess.request.OkRequestCall;
import com.orhanobut.logger.Logger;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public class OkGetFormBuilder extends OkRequestBuilder{


    private static final String TAG = "okGetFormBuilder";

    @Override
    public OkRequestBuilder url(String url) {
        mUrl = url;
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestBuilder tag(Object tag) {
        mTag = tag;
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestBuilder params(Map<String, String> params) {
        mParams = params;
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestBuilder addParams(String key, String value) {
        if(mParams == null){
            mParams = new IdentityHashMap<>();
        }
        mParams.put(key, value);
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestBuilder headers(Map<String, String> headers) {
        mHeaders = headers;
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestBuilder addHeader(String key, String value) {
        if(mHeaders == null){
            mHeaders = new IdentityHashMap<>();
        }
        mHeaders.put(key, value);
        return OkGetFormBuilder.this;
    }

    @Override
    public OkRequestCall buildCall() {
        if(mParams != null){
            mUrl = appendParams(mUrl, mParams);
        }
        Logger.d(TAG, "mUrl is " + mUrl);
        return new OkGetFormRequest(mUrl, mTag, mParams, mHeaders).buildCall();
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("?");
        if(params != null && !params.isEmpty()){
            for(String key : params.keySet()){
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
