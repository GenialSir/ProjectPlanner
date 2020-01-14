package com.genialsir.projectplanner.okhttpprocess.builder;

import com.genialsir.projectplanner.okhttpprocess.request.OkPostStringRequest;
import com.genialsir.projectplanner.okhttpprocess.request.OkRequestCall;

import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author genialsir@163.com (GenialSir) on 2018/3/15.
 */

public class OKPostStringBuilder extends OkRequestBuilder{

    private String content;
    private MediaType mediaType;

    @Override
    public OKPostStringBuilder url(String url) {
        mUrl = url;
        return this;
    }

    @Override
    public OKPostStringBuilder tag(Object tag) {
        mTag = tag;
        return this;
    }

    @Override
    public OKPostStringBuilder params(Map<String, String> params) {
        mParams = params;
        return this;
    }

    @Override
    public OKPostStringBuilder addParams(String key, String value) {
        if(mParams == null){
            mParams = new IdentityHashMap<>();
        }

        mParams.put(key, value);
        return this;
    }

    @Override
    public OKPostStringBuilder headers(Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    @Override
    public OKPostStringBuilder addHeader(String key, String value) {
        if(mHeaders == null){
            mHeaders = new IdentityHashMap<>();
        }

        mHeaders.put(key, value);
        return this;
    }

    public OKPostStringBuilder content(String content){
        this.content = content;
        return this;
    }

    public OKPostStringBuilder mediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public OkRequestCall buildCall() {
        return new OkPostStringRequest(mUrl, mTag, mParams, mHeaders,
                content, mediaType).buildCall();
    }
}
