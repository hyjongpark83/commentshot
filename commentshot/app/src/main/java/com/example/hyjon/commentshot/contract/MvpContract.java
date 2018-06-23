package com.example.hyjon.commentshot.contract;

public class MvpContract {
    public interface MvpView {
    }

    public interface MvpPresenter <V extends MvpView> {
        void attachView(V view);
        void detachView();
    }
}
