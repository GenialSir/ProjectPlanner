package com.genialsir.projectplanner.okhttpprocess.request;

import java.util.Map;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/26.
 */

public class OkGetRequest extends OkRequestManager{

    public OkGetRequest(String url, Object tag, Map<String, String> params,
                           Map<String, String> headers) {
        super(url, tag, params, headers);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.get().build();
    }


}
