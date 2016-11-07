package com.example.remotemultifeedcamerasimulator;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by User on 9/29/2016.
 */
public class CameraFeedThread extends Thread implements Runnable {
    private Handler _handler;
    private ImageView imageView;
    private int msTimeout;
    private Drawable[] photos;
    private boolean shouldContinue = true;

    public CameraFeedThread(Handler handler) {
        _handler = handler;
    }

    public void setMsTimeout(int timeout) {
        msTimeout = timeout;
    }

    public void setPhotos(Drawable[] pics) {
        photos = pics;
    }

    public void setImageView(ImageView view) {
        imageView = view;
    }

    public boolean shouldContinue() {
        return shouldContinue;
    }

    @Override
    public void run() {
        shouldContinue = true;
        if (photos != null) {
            while(shouldContinue) {
                for (int i = 0; i < photos.length; i++) {
                    final int iteration = i;
					_handler.post(new Runnable() {
						@Override
						public void run() {
							imageView.setImageDrawable(photos[iteration]);
						}
					});
                    try {
                        sleep(msTimeout);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
    }

    @Override
    public void interrupt() {
        shouldContinue = false;
    }
}
