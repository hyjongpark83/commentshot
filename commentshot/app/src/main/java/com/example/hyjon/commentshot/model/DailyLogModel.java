package com.example.hyjon.commentshot.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DailyLogModel implements Parcelable {

    private int index;
    private String pictureFilePath;
    private String log;
    private int importance;

    public DailyLogModel(int index, String pictureFilePath, String log, int importance) {
        this.index = index;
        this.pictureFilePath = pictureFilePath;
        this.log = log;
        this.importance = importance;
    }

    public DailyLogModel(String pictureFilePath, String log, int importance) {
        this.index = 0;
        this.pictureFilePath = pictureFilePath;
        this.log = log;
        this.importance = importance;
    }

    public String getPictureFilePath() {
        return pictureFilePath;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPictureFilePath(String pictureFilePath) {
        this.pictureFilePath = pictureFilePath;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.pictureFilePath);
        dest.writeString(this.log);
        dest.writeInt(this.importance);
    }

    protected DailyLogModel(Parcel in) {
        this.index = in.readInt();
        this.pictureFilePath = in.readString();
        this.log = in.readString();
        this.importance = in.readInt();
    }

    public static final Creator<DailyLogModel> CREATOR = new Creator<DailyLogModel>() {
        @Override
        public DailyLogModel createFromParcel(Parcel source) {
            return new DailyLogModel(source);
        }

        @Override
        public DailyLogModel[] newArray(int size) {
            return new DailyLogModel[size];
        }
    };
}
