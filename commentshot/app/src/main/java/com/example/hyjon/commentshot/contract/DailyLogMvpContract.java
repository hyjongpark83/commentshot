package com.example.hyjon.commentshot.contract;

import com.example.hyjon.commentshot.model.DailyLogModel;

import java.util.List;

public class DailyLogMvpContract {

    public interface View extends MvpContract.MvpView {
        void updateView(List<DailyLogModel> dailyLogModelList);
    }

    public interface Presenter extends MvpContract.MvpPresenter<DailyLogMvpContract.View> {
        void getDailyLogModelList();
        void insertDailyLogModel(DailyLogModel dailyLogModel);
    }
}
