package com.example.remotemultifeedcamerasimulator;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MainActivity";
    private Handler cameraFeedHandler = new Handler();
    private Runnable run1 = new CameraFeedThread(cameraFeedHandler);
    private Runnable run2 = new CameraFeedThread(cameraFeedHandler);
	private Thread thread1;
    private Thread thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate");
    }

    public void camera1Click(View view) {
        ((CameraFeedThread) run1).setPhotos(
                new Drawable[] {
                    ContextCompat.getDrawable(this, R.drawable.camera1_image1),
                    ContextCompat.getDrawable(this, R.drawable.camera1_image2),
                    ContextCompat.getDrawable(this, R.drawable.camera1_image3),
                    ContextCompat.getDrawable(this, R.drawable.camera1_image4),
                    ContextCompat.getDrawable(this, R.drawable.camera1_image5)
                });
        ((CameraFeedThread) run1).setMsTimeout(2000);
        ((CameraFeedThread) run1).setImageView((ImageView) findViewById(R.id.camera1_image_view));
        if (thread1 != null) {
            thread1.interrupt();
            thread1 = null;
        }
        else {
            thread1 = new Thread(run1);
            thread1.start();
        }
    }

    public void camera2Click(View view) {
        ((CameraFeedThread) run2).setPhotos(
                new Drawable[] {
                        ContextCompat.getDrawable(this, R.drawable.camera2_image1),
                        ContextCompat.getDrawable(this, R.drawable.camera2_image2),
                        ContextCompat.getDrawable(this, R.drawable.camera2_image3),
                        ContextCompat.getDrawable(this, R.drawable.camera2_image4),
                        ContextCompat.getDrawable(this, R.drawable.camera2_image5)
                });
        ((CameraFeedThread) run2).setMsTimeout(1000);
        ((CameraFeedThread) run2).setImageView((ImageView) findViewById(R.id.camera2_image_view));
        if (thread2 != null) {
            thread2.interrupt();
            thread2 = null;
        }
        else {
            thread2 = new Thread(run2);
            thread2.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (thread1 != null && ((CameraFeedThread)run1).shouldContinue()) {
            thread1 = new Thread(run1);
            thread1.start();
        }

        if (thread2 != null && ((CameraFeedThread)run2).shouldContinue()) {
            thread2 = new Thread(run2);
            thread2.start();
        }
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (thread1 != null && ((CameraFeedThread)run1).shouldContinue()) {
            thread1 = new Thread(run1);
            thread1.start();
        }

        if (thread2 != null && ((CameraFeedThread)run2).shouldContinue()) {
            thread2 = new Thread(run2);
            thread2.start();
        }
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread1 != null) {
            thread1.interrupt();
            thread1 = null;
        }

        if (thread2 != null) {
            thread2.interrupt();
            thread2 = null;
        }
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (thread1 != null) {
            thread1.interrupt();
            thread1 = null;
        }

        if (thread2 != null) {
            thread2.interrupt();
            thread2 = null;
        }
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"onDestroy");
    }
}
