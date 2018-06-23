package com.example.hyjon.commentshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hyjon.commentshot.contract.DetailMvpContract;
import com.example.hyjon.commentshot.model.DailyLogModel;
import com.example.hyjon.commentshot.presenter.DetailPresenter;
import com.example.hyjon.commentshot.utils.Constants;

public class DetailActivity extends Activity implements DetailMvpContract.View {

    private ImageButton mDeleteButton;
    private ImageButton mCancelButton;
    private ImageButton mSaveButton;
    private ImageView mImageView;
    private EditText mEditText;
    private DetailMvpContract.Presenter mPresenter;

    private DailyLogModel mDailyLogModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datail_cardview_layout);
        setPresenter();
        initView();
        setViewFromBundle(getIntent());
    }

    private void setPresenter() {
        mPresenter = new DetailPresenter(this);
        mPresenter.attachView(this);
    }

    private void setViewFromBundle(Intent intent) {
        mDailyLogModel = intent.getParcelableExtra(Constants.INTENT_DAILY_LOG_MODEL);

        Glide.with(this).load(mDailyLogModel.getPictureFilePath()).into(mImageView);
        mEditText.setText(mDailyLogModel.getLog());
    }

    private void initView() {
        mImageView = findViewById(R.id.detail_picture_view);
        mEditText = findViewById(R.id.detail_edittext);
        mDeleteButton = findViewById(R.id.edit_delete_button);
        mCancelButton = findViewById(R.id.edit_cancel_button);
        mSaveButton = findViewById(R.id.edit_save_button);

        mSaveButton.setOnClickListener(this::updateDailyLog);
        mDeleteButton.setOnClickListener(this::deleteLog);
        mCancelButton.setOnClickListener(v -> finish());
    }

    private void updateDailyLog(View view) {
        mDailyLogModel.setLog(mEditText.getText().toString());
        mPresenter.updateLog(mDailyLogModel);
        finish();
    }

    private void deleteLog(View view) {
        mPresenter.deleteLog(mDailyLogModel.getIndex());
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
