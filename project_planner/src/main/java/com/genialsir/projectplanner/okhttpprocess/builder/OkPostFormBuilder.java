package com.genialsir.projectplanner.okhttpprocess.builder;

import com.genialsir.projectplanner.okhttpprocess.request.OkPostFormRequest;
import com.genialsir.projectplanner.okhttpprocess.request.OkRequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public class OkPostFormBuilder extends OkRequestBuilder{


    private List<FileType> fileData = new ArrayList<>();

    @Override
    public OkRequestBuilder url(String url) {
        mUrl = url;
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestBuilder tag(Object tag) {
        mTag = tag;
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestBuilder params(Map<String, String> params) {
        mParams = params;
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestBuilder addParams(String key, String value) {
        if(mParams == null){
            mParams = new IdentityHashMap<>();
        }

        mParams.put(key, value);
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestBuilder headers(Map<String, String> headers) {
        mHeaders = headers;
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestBuilder addHeader(String key, String value) {
        if(mHeaders == null){
            mHeaders = new IdentityHashMap<>();
        }

        mHeaders.put(key, value);
        return OkPostFormBuilder.this;
    }

    public OkPostFormBuilder addFile(String fileKey, String fileName, File file){
        fileData.add(new FileType(fileKey, fileName, file));
        return OkPostFormBuilder.this;
    }

    public OkPostFormBuilder addFiles(String fileKey, String fileName, List<File> files){
        for(File file : files){
            fileData.add(new FileType(fileKey, fileName, file));
        }
        return OkPostFormBuilder.this;
    }

    @Override
    public OkRequestCall buildCall() {
        return new OkPostFormRequest(mUrl, mTag, mParams, mHeaders, fileData).buildCall();
    }


    public static class FileType{

        private String fileKey;
        private String fileName;
        private File file;

        private FileType(String fileKey, String fileName, File file){
            this.fileKey = fileKey;
            this.fileName = fileName;
            this.file = file;
        }

        public String getFileKey() {
            return fileKey;
        }

        public String getFileName() {
            return fileName;
        }

        public File getFile() {
            return file;
        }
    }
}
