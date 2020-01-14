package com.genialsir.projectplanner.okhttpprocess.request;

import com.orhanobut.logger.Logger;
import com.genialsir.projectplanner.okhttpprocess.OkPerformer;
import com.genialsir.projectplanner.okhttpprocess.resonance.OkCallback;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/25.
 */

public class OkRequestCall {

    private OkRequestManager mReqManager;
    private Request mRequest;
    private Call mCall;


    OkRequestCall(OkRequestManager reqManager){
        mReqManager = reqManager;
    }


    public void execute(OkCallback okCallback){
        if(okCallback == null){
            Logger.e("Not the callOut null and callOut must to config");
            return;
        }
        generateCall(okCallback);

        OkPerformer.getInstance().perform(OkRequestCall.this, okCallback);
    }

    private Call generateCall(OkCallback okCallBack) {
        mRequest = generateRequest(okCallBack);

        mCall = OkPerformer.getInstance().getOkHttpClient().newCall(mRequest);
        return mCall;
    }

    private Request generateRequest(OkCallback okCallBack) {
        return mReqManager.generateRequest(okCallBack);
    }

    public Call getCall(){
        return mCall;
    }

}
