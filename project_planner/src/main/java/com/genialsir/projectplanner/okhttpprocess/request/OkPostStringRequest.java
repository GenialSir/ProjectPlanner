package com.genialsir.projectplanner.okhttpprocess.request;

import com.orhanobut.logger.Logger;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author genialsir@163.com (GenialSir) on 2018/3/15.
 */

public class OkPostStringRequest extends OkRequestManager {

    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("application/json; charset=utf-8");

    private String content;
    private MediaType mediaType;

    public OkPostStringRequest(String url, Object tag, Map<String, String> params,
                                  Map<String, String> headers, String content, MediaType mediaType) {
        super(url, tag, params, headers);
        this.content = content;
        this.mediaType = mediaType;
        if(this.content == null){
            Logger.e("OkPostStringRequest content is null.");
        }
        if(this.mediaType == null){
            this.mediaType = MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, content);
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}
