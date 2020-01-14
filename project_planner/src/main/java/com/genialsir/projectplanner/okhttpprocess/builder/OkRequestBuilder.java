package com.genialsir.projectplanner.okhttpprocess.builder;

import com.genialsir.projectplanner.okhttpprocess.request.OkRequestCall;
import java.util.Map;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public abstract class OkRequestBuilder {

    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mHeaders;
    protected Map<String, String> mParams;

    public abstract OkRequestBuilder url(String url);

    public abstract OkRequestBuilder tag(Object tag);

    public abstract OkRequestBuilder params(Map<String, String> params);

    public abstract OkRequestBuilder addParams(String key, String value);

    public abstract OkRequestBuilder headers(Map<String, String> headers);

    public abstract OkRequestBuilder addHeader(String key, String value);

    public abstract OkRequestCall buildCall();

}
