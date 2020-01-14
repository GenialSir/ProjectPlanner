package com.genialsir.projectplanner.okhttpprocess.request;

import com.genialsir.projectplanner.okhttpprocess.OkPerformer;
import com.genialsir.projectplanner.okhttpprocess.builder.OkPostFormBuilder;
import com.genialsir.projectplanner.okhttpprocess.resonance.OkCallback;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/28.
 */

public class OkPostFormRequest extends OkRequestManager{

    private List<OkPostFormBuilder.FileType> mFileData;

    public OkPostFormRequest(String url, Object tag, Map<String, String> params,
                                Map<String, String> headers,
                                List<OkPostFormBuilder.FileType> fileData) {
        super(url, tag, params, headers);
        mFileData = fileData;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if(mFileData == null || mFileData.isEmpty()){
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder);

        OkPostFormBuilder.FileType fileType;
        String fileKey;
        String fileName;
        File file;
        for(int i = 0; i < mFileData.size(); i++){
            fileType = mFileData.get(i);
            fileKey = fileType.getFileKey();
            fileName = fileType.getFileName();
            file = fileType.getFile();

            RequestBody fileBody = RequestBody
                    .create(MediaType.parse(guessMimeType(fileName)), file);
            builder.addFormDataPart(fileKey, fileName, fileBody);
        }
        return builder.build();
    }


    private void addParams(FormBody.Builder builder) {
        if(mParams == null || mParams.isEmpty()){
            builder.add("1", "1");
            return;
        }
        for(String key : mParams.keySet()){
            builder.add(key, mParams.get(key));
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if(mParams != null && !mParams.isEmpty()){
            for(String key : mParams.keySet()){
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, mParams.get(key)));
            }
        }
    }


    private String guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if(contentTypeFor == null){
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final OkCallback okCallBack) {
        if(okCallBack == null){
            return requestBody;
        }

        return new OkCountRequestBody(requestBody, new OkCountRequestBody.DelegateListener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                OkPerformer.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        okCallBack.uploadProgress(bytesWritten * 1.0f / contentLength);
                    }
                });
            }
        });
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

}
