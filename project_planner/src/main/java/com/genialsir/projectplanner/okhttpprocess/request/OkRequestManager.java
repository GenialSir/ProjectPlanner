package com.genialsir.projectplanner.okhttpprocess.request;

import com.orhanobut.logger.Logger;
import com.genialsir.projectplanner.okhttpprocess.resonance.OkCallback;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public abstract class OkRequestManager {


    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mParams;
    protected Map<String, String> mHeaders;
    protected Request.Builder mBuilder = new Request.Builder();


    protected OkRequestManager(String url, Object tag, Map<String, String> params,
                               Map<String, String> headers){
        mUrl = url;
        mTag = tag;
        mParams = params;
        mHeaders = headers;
        if(mUrl == null){
            Logger.e("OkRequestManager mUrl can not be null");
        }
    }


    public OkRequestCall buildCall(){
        return new OkRequestCall(OkRequestManager.this);
    }

    public Request generateRequest(OkCallback okCallBack){
        RequestBody requestBody = wrapRequestBody(buildRequestBody(), okCallBack);
        prepareBuilder();
        return buildRequest(mBuilder, requestBody);
    }

    private void prepareBuilder() {
        mBuilder.url(mUrl).tag(mTag);
        appendHeaders();
    }

    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if(mHeaders == null || mHeaders.isEmpty()){
            Logger.e("The headers can not be blank!");
            return;
        }

        for(String key : mHeaders.keySet()){
            headerBuilder.add(key, mHeaders.get(key));
        }

        mBuilder.headers(headerBuilder.build());
    }


    protected RequestBody wrapRequestBody(RequestBody requestBody, final OkCallback okCallBack){
        return requestBody;
    }

    protected abstract RequestBody buildRequestBody();

    protected abstract Request buildRequest(Request.Builder builder, RequestBody requestBody);


    @Override
    public String toString() {
        return "OkHttp3Request {" +
                "mUrl = '" + mUrl + '\'' +
                ", mTag = " + mTag +
                ", mParams = " + mParams +
                ", mHeaders = " + mHeaders +
                '}';
    }
}
