package com.example.hyjon.commentshot.contract;

import com.example.hyjon.commentshot.model.DailyLogModel;

public class DetailMvpContract {
    public interface View extends MvpContract.MvpView {
    }

    public interface Presenter extends MvpContract.MvpPresenter<DetailMvpContract.View> {
        void deleteLog(int id);
        void updateLog(DailyLogModel dailyLogModel);
    }
}
