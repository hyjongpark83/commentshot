package com.example.hyjon.commentshot.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hyjon.commentshot.DetailActivity;
import com.example.hyjon.commentshot.R;
import com.example.hyjon.commentshot.model.DailyLogModel;
import com.example.hyjon.commentshot.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DailyLogAdapter extends RecyclerView.Adapter<DailyLogAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<DailyLogModel> mDailyLogModelList;

    public DailyLogAdapter(Activity activity) {
        mDailyLogModelList = new ArrayList<>();
        mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_cardview_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        DailyLogModel dailyLogModel = mDailyLogModelList.get(position);
        String imageFilePath = dailyLogModel.getPictureFilePath();
        Glide.with(mActivity).load(imageFilePath).into(holder.mPictureView);
        holder.mLogText.setText(dailyLogModel.getLog());
        holder.mContainer.setOnClickListener(view -> startDetailMode(holder, dailyLogModel));
    }

    private void startDetailMode(ItemViewHolder viewHolder, DailyLogModel dailyLogModel) {
        Intent intent = new Intent();
        intent.setClass(mActivity, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_DAILY_LOG_MODEL, dailyLogModel);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    public int getItemCount() {
        return mDailyLogModelList.size();
    }

    public void setData(List<DailyLogModel> modelList) {
        mDailyLogModelList.clear();
        mDailyLogModelList.addAll(modelList);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mContainer;
        ImageView mPictureView;
        TextView mLogText;
        EditText mLogEditText;
        ImageButton mImportanceButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.container);
            mPictureView = itemView.findViewById(R.id.pictureView);
            mLogText = itemView.findViewById(R.id.logText);
            mLogEditText = itemView.findViewById(R.id.logEditText);
            mImportanceButton = itemView.findViewById(R.id.importanceIcon);
        }
    }
}
