package com.example.hyjon.commentshot;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hyjon.commentshot.view.DailyLogFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Func0;

public class MainActivity extends AppCompatActivity {
//https://android.jlelse.eu/android-cardview-101-everything-you-should-know-5bbf1c873f5a?source=search_post---------4
//http://answerofgod.tientry/Android%EC%97%90%EC%8story.com/4%9C-image-BLOB-MySQL%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%98%EA%B8%B0
//http://superwony.tistory.com/5
//find what to do
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        setContentView(R.layout.activity_main);
        checkPermission();
    }
    private void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionReadStorage == PackageManager.PERMISSION_DENIED ||
                permissionWriteStorage == PackageManager.PERMISSION_DENIED ||
                permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, 1);
        } else {
            startDailyLog();
            new StyleableToast
                    .Builder(getApplicationContext())
                    .text("read/write storage permission authorized")
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.LTGRAY)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissionDenied = Arrays.stream(grantResults)
                                        .anyMatch(grant -> grant == PackageManager.PERMISSION_DENIED);

        if (isPermissionDenied) {
            finish();
        } else {
            new StyleableToast
                    .Builder(getApplicationContext())
                    .text("read/write storage and camera permission authorized")
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.LTGRAY)
                    .show();
            startDailyLog();
        }
    }

    private void startDailyLog() {
        DailyLogFragment dailyLogFragment = new DailyLogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, dailyLogFragment);
        fragmentTransaction.commit();
    }
}
