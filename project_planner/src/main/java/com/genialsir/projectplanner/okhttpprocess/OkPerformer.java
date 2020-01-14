package com.genialsir.projectplanner.okhttpprocess;

import android.os.Handler;
import android.os.Looper;

import com.genialsir.projectplanner.okhttpprocess.builder.OKPostStringBuilder;
import com.orhanobut.logger.Logger;
import com.genialsir.projectplanner.okhttpprocess.builder.OkGetFormBuilder;
import com.genialsir.projectplanner.okhttpprocess.builder.OkPostFormBuilder;
import com.genialsir.projectplanner.okhttpprocess.request.OkRequestCall;
import com.genialsir.projectplanner.okhttpprocess.resonance.OkCallback;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public class OkPerformer {

    private static final int DEFAULT_TIME_OUT_SECONDS = 50;

    private OkHttpClient mOkHttpClient;

    //Handlers main thread and child thread communication.
    private Handler mOkDelivery;


    {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();
        mOkDelivery = new Handler(Looper.getMainLooper());
    }

    private OkPerformer(){}

    public static OkPerformer getInstance(){
        return VariableHolder.instance;
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public Handler getDelivery(){
        return mOkDelivery;
    }


    public void perform(OkRequestCall okRequestCall, OkCallback okCallback){
        if(okCallback == null){
            Logger.e("callback can not be null");
            return;
        }
        try {
            okRequestCall.getCall().enqueue(new OkResponseCallback(okCallback));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static OkGetFormBuilder okGet(){
        return new OkGetFormBuilder();
    }

    public static OkPostFormBuilder okPost(){
        return new OkPostFormBuilder();
    }

    public static OKPostStringBuilder okPostString(){
        return new OKPostStringBuilder();
    }


    /**
     * OkHttp response callback
     */
    private class OkResponseCallback implements Callback {

        private OkCallback mCallback;
        private OkResponseCallback(final OkCallback callback){
            mCallback = callback;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            if(call == null || call.request() == null || e == null){
                Logger.e("call or call.request() or e belong to null.");
                return;
            }
            responseFailed(call.request().toString() + "\n" + e.toString(), mCallback);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response == null || response.body() == null){
                Logger.e("call or response.body().string() belong to null.");
                return;
            }

            if(response.isSuccessful()){
                responseSucceed(response.body().string(), mCallback);
                return;
            }
            responseCodeError(response.toString(), mCallback);
        }
    }


    /**
     * The response data is successful after the request.
     * @param result Response result.
     * @param callback Response event.
     */
    private void responseSucceed(final String result, final OkCallback callback){
        if(callback == null){
            return;
        }
        mOkDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onCodeSuccess(result);
            }
        });
    }


    /**
     * The response code that failed after the request.
     * @param result Response result.
     * @param callback Response event.
     */
    private void responseCodeError(final String result, final OkCallback callback){
        if(callback == null){
            return;
        }
        mOkDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onCodeError(result);
            }
        });
    }
    /**
     * The server response failed.
     * @param result Response result.
     * @param callback Response event.
     */
    private void responseFailed(final String result, final OkCallback callback){
        if(callback == null){
            return;
        }
        mOkDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onReqFailure(result);
            }
        });
    }



    private static class VariableHolder{
        private static final OkPerformer instance = new OkPerformer();
        private Object readResolve() throws ObjectStreamException {
            return instance;
        }
    }
}
