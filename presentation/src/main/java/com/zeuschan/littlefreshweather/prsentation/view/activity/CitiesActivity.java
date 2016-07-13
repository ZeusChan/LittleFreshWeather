package com.zeuschan.littlefreshweather.prsentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zeuschan.littlefreshweather.model.entity.CityEntity;
import com.zeuschan.littlefreshweather.prsentation.R;
import com.zeuschan.littlefreshweather.prsentation.presenter.CitiesPresenter;
import com.zeuschan.littlefreshweather.prsentation.view.CitiesView;
import com.zeuschan.littlefreshweather.prsentation.view.adapter.CitiesCandidatesApdapter;

import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;

public class CitiesActivity extends BaseActivity implements CitiesView, View.OnClickListener, TextWatcher, ListView.OnItemClickListener {
    public static final String CITY_ID = "city_id";
    public static final String LOC_CITY_ID = "loc_city_id";
    public static final String LOCATE_RESULT = "locate_result";

    private CitiesPresenter mPresenter;
    private CitiesCandidatesApdapter mAdapter;
    private List<CityEntity> mCandidates = new ArrayList<>();
    private String mLocateCityId;
    private String mCurCityId;
    private boolean mIsLocateSucceeded = false;
    //Unbinder mUnbinder;

//    @BindView(R.id.rl_loading_progress) RelativeLayout rlLoadingProgress;
//    @BindView(R.id.rl_failed_retry) RelativeLayout rlFailedRetry;
//    @BindView(R.id.bt_failed_retry) Button btFailedRetry;
//    @BindView(R.id.et_cities_city_name) EditText etCityName;
//    @BindView(R.id.lv_cities_candidates) ListView lvCandidates;
//    @BindView(R.id.tv_cities_toolbar_title) TextView tvToolbarTitle;
//    @BindView(R.id.ib_cities_toolbar_back) ImageButton ibToolbarBack;
//    @BindView(R.id.bt_cities_located) Button btLocatedCityName;
//    @BindView(R.id.bt_cities_cur) Button btCurCityName;
//    @BindView(R.id.ll_cities_located) LinearLayout llLocated;
//    @BindView(R.id.ll_cities_root) LinearLayout llCitiesRoot;

    private RelativeLayout rlLoadingProgress;
    private RelativeLayout rlFailedRetry;
    private Button btFailedRetry;
    private EditText etCityName;
    private ListView lvCandidates;
    private TextView tvToolbarTitle;
    private ImageButton ibToolbarBack;
    private Button btLocatedCityName;
    private Button btCurCityName;
    private LinearLayout llLocated;
    private LinearLayout llCitiesRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        mAdapter = new CitiesCandidatesApdapter(this.getApplicationContext(), R.layout.ll_cities_candidates_item, mCandidates);
        lvCandidates.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_cities);

        rlLoadingProgress = (RelativeLayout)findViewById(R.id.rl_loading_progress);
        rlFailedRetry = (RelativeLayout)findViewById(R.id.rl_failed_retry);
        btFailedRetry = (Button)findViewById(R.id.bt_failed_retry);
        etCityName = (EditText)findViewById(R.id.et_cities_city_name);
        lvCandidates = (ListView)findViewById(R.id.lv_cities_candidates);
        tvToolbarTitle = (TextView)findViewById(R.id.tv_cities_toolbar_title);
        ibToolbarBack = (ImageButton)findViewById(R.id.ib_cities_toolbar_back);
        btLocatedCityName = (Button)findViewById(R.id.bt_cities_located);
        btCurCityName = (Button)findViewById(R.id.bt_cities_cur);
        llLocated = (LinearLayout)findViewById(R.id.ll_cities_located);
        llCitiesRoot = (LinearLayout)findViewById(R.id.ll_cities_root);

        //mUnbinder = ButterKnife.bind(this);
        lvCandidates.setOnItemClickListener(this);
        ibToolbarBack.setOnClickListener(this);
        btLocatedCityName.setOnClickListener(this);
        btCurCityName.setOnClickListener(this);
        btFailedRetry.setOnClickListener(this);
        etCityName.addTextChangedListener(this);
        tvToolbarTitle.setText(R.string.city_selection);

        Intent intent = getIntent();
        mLocateCityId = intent.getStringExtra(LOC_CITY_ID);
        mCurCityId = intent.getStringExtra(CITY_ID);
        mIsLocateSucceeded = intent.getBooleanExtra(LOCATE_RESULT, false);

        mPresenter = new CitiesPresenter(this);
        mPresenter.getBackgroundImage(llCitiesRoot, R.drawable.city);
        mPresenter.getImageViewSrc(ibToolbarBack, R.drawable.ic_arrow_back_white_24dp);
        mPresenter.setLocatedCityId(mLocateCityId, mCurCityId);
    }

    @Override
    protected void uninitView() {
        etCityName.removeTextChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    protected void clearMemory() {
        mPresenter.destroy();
        mCandidates.clear();

        uninitView();
        //mUnbinder.unbind();
        //setContentView(new FrameLayout(this));
    }

    @Override
    public void navigateToCityWeatherActivity(String cityId) {
        Intent intent = new Intent(this, CityWeatherActivity.class);
        intent.putExtra(CityWeatherActivity.CITY_ID, cityId);
        startActivity(intent);
        finish();
    }

    @Override
    public void refreshCandidatesList(List<CityEntity> candidates) {
        mCandidates.clear();
        mCandidates.addAll(candidates);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        rlLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideRetry() {
        rlFailedRetry.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        rlFailedRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        rlLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCityNameEdit() {
        etCityName.setVisibility(View.INVISIBLE);
        etCityName.clearFocus();
    }

    @Override
    public void showCityNameEdit() {
        etCityName.setVisibility(View.VISIBLE);
        etCityName.requestFocus();
    }

    @Override
    public void showLocatedCityName() {
        if (mLocateCityId != null && mIsLocateSucceeded && mCurCityId != null) {
            llLocated.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLocatedCityName() {
        llLocated.setVisibility(View.GONE);
    }

    @Override
    public void setLocatedCityName(String locatedName, String curName) {
        btLocatedCityName.setText(locatedName);
        btCurCityName.setText(curName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CityEntity entity = mCandidates.get(position);
        navigateToCityWeatherActivity(entity.getCityId());
    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.getCandidates(etCityName.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_failed_retry: {
                mPresenter.loadData();
            } break;
            case R.id.ib_cities_toolbar_back: {
                onBackPressed();
            } break;
            case R.id.bt_cities_located: {
                navigateToCityWeatherActivity(mLocateCityId);
            } break;
            case R.id.bt_cities_cur: {
                navigateToCityWeatherActivity(mCurCityId);
            } break;
        }
    }
}
