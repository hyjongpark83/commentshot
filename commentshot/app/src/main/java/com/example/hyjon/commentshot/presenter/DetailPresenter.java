package com.example.hyjon.commentshot.presenter;

import android.content.Context;

import com.example.hyjon.commentshot.contract.DailyLogMvpContract;
import com.example.hyjon.commentshot.contract.DetailMvpContract;
import com.example.hyjon.commentshot.db.DailyLogRepository;
import com.example.hyjon.commentshot.model.DailyLogModel;

public class DetailPresenter implements DetailMvpContract.Presenter {

    private DetailMvpContract.View mView;
    private DailyLogRepository mRepository;

    public DetailPresenter(Context context) {
        mRepository = new DailyLogRepository(context);
    }

    @Override
    public void attachView(DetailMvpContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void deleteLog(int id) {
        mRepository.delete(id);
    }

    @Override
    public void updateLog(DailyLogModel dailyLogModel) {
        mRepository.update(dailyLogModel);
    }
}
