package com.genialsir.projectplanner.tools;

import android.os.Bundle;

/**
 * @author genialsir@163.com (GenialSir) on 2018/1/30.
 */

public class EventBusHelper {

    private String mSign;
    private Bundle mBundle;



    private EventBusHelper(String sign, Bundle bundle){
        mSign = sign;
        mBundle = bundle;
    }

    public static EventBusHelper getInstance(String sign, Bundle bundle){


        return new EventBusHelper(sign, bundle);
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public String getSign() {
        return mSign;
    }

}
