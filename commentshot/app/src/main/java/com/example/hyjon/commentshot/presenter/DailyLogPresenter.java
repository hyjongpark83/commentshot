package com.example.hyjon.commentshot.presenter;

import android.content.Context;

import com.example.hyjon.commentshot.contract.DailyLogMvpContract;
import com.example.hyjon.commentshot.db.DailyLogRepository;
import com.example.hyjon.commentshot.model.DailyLogModel;

import java.util.List;

public class DailyLogPresenter implements DailyLogMvpContract.Presenter {

    private DailyLogMvpContract.View mView;
    private DailyLogRepository mDailyLogRepository;

    public DailyLogPresenter(Context context) {
        mDailyLogRepository = new DailyLogRepository(context);
    }

    @Override
    public void getDailyLogModelList() {
        List<DailyLogModel> modelList = mDailyLogRepository.selectAll();
        mView.updateView(modelList);
    }

    @Override
    public void insertDailyLogModel(DailyLogModel dailyLogModel) {
        mDailyLogRepository.insert(dailyLogModel);
    }

    @Override
    public void attachView(DailyLogMvpContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
