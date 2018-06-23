package com.example.hyjon.commentshot.view;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hyjon.commentshot.R;
import com.example.hyjon.commentshot.contract.DailyLogMvpContract;
import com.example.hyjon.commentshot.model.DailyLogModel;
import com.example.hyjon.commentshot.presenter.DailyLogPresenter;
import com.melnykov.fab.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DailyLogFragment extends Fragment implements DailyLogMvpContract.View {

    private static final int GALLERY_CODE = 1111;
    private static final int CAMERA_CODE = 1112;

    RecyclerView mRecyclerView;
    FloatingActionButton mFab;
    DailyLogAdapter mDailyLogAdapter;
    DailyLogPresenter mDailyLogPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_log_fragment_view, container, false);
        setRecyclerView(view);
        setAdapter();
        setFloatActionButton(view);
        setPresenter();
        return view;
    }

    private void setFloatActionButton(View view) {
        mFab = view.findViewById(R.id.fab);
        mFab.attachToRecyclerView(mRecyclerView);
        mFab.setOnClickListener(v -> showPictureList());
    }

    private void showPictureList() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE :
                    getSelectedPicture(data.getData());
                    mDailyLogPresenter.getDailyLogModelList();
                default :
                    break;
            }
        }
    }

    private void getSelectedPicture(Uri contentUri) {
        String imagePath = getRealPathFromUri(contentUri);
        mDailyLogPresenter.insertDailyLogModel(new DailyLogModel(imagePath, "text", 1));
    }

    private int getDegreeFromPictureUri(Uri contentUri) {
        ExifInterface exifInterface = null;
        try(InputStream inputStream = getContext().getContentResolver().openInputStream(contentUri)) {
            exifInterface = new ExifInterface(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        return exifOrientationToDegrees(orientation);
    }

    private Bitmap rotate(Bitmap src, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private String getRealPathFromUri(Uri contentUri) {
        int column_index = 0;
        String[] project = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, project, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    private void setRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public byte[] getByteArrayFromDrawable(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void setAdapter() {
        Log.d("HJ-", "setAdapter");
        mDailyLogAdapter = new DailyLogAdapter(getActivity());
        mRecyclerView.setAdapter(mDailyLogAdapter);
    }

    private void setPresenter() {
        mDailyLogPresenter = new DailyLogPresenter(getContext());
        mDailyLogPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        Log.d("HJ-", "onStart");
        super.onStart();
        mDailyLogPresenter.getDailyLogModelList();
    }

    @Override
    public void onDestroy() {
        mDailyLogPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void updateView(List<DailyLogModel> dailyLogModelList) {
        mDailyLogAdapter.setData(dailyLogModelList);
    }
}
