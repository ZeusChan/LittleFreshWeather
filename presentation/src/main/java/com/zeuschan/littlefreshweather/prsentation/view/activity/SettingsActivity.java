package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.view.SettingsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsActivity extends BaseActivity implements SettingsView, View.OnClickListener {

    private Unbinder mUnbinder;

    @BindView(R.id.ib_settings_toolbar_back) ImageButton ibToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_settings);
        mUnbinder = ButterKnife.bind(this);

        ibToolbarBack.setOnClickListener(this);
    }

    @Override
    protected void clearMemory() {
        mUnbinder.unbind();
        setContentView(new FrameLayout(this));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_settings_toolbar_back: {
                onBackPressed();
            } break;
        }
    }
}
