package com.genialsir.ppdemo.mvp.ui.activity;


import android.widget.TextView;

import com.genialsir.ppdemo.R;
import com.genialsir.projectplanner.annotation.ContentViewInject;
import com.genialsir.projectplanner.mvp.view.BaseMvpActivity;

import butterknife.BindView;


@ContentViewInject(contentViewID = R.layout.activity_main)
public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initContract() {

    }
}
