package com.example.networkpicturedownloader;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String DYERS_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/dyers.jpg";
    final static String MOON_PINE_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/moonpine.jpg";
    final static String PLUM_ESTATE_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/plum.jpg";
    final static String USHIMACHI_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/takanawa.jpg";
    static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
    static final String PROGRESS_MESSAGE   = "File Downloading . . .";
    static final String COMPLETION_MESSAGE = "Download complete";

    Button btnStartDyersProgress, btnStartMoonPineProgress, btnStartPlumEstateProgress, btnStartUshimachiProgress;
    ProgressDialog progressBar;
    ImageView imageView;

    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;
    private String oldFileUrl, fileUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartDyersProgress = (Button) findViewById(R.id.btnStartDyersProgress);
        btnStartMoonPineProgress = (Button) findViewById(R.id.btnStartMoonPineProgress);
        btnStartPlumEstateProgress = (Button) findViewById(R.id.btnStartPlumEstateProgress);
        btnStartUshimachiProgress = (Button) findViewById(R.id.btnStartUshimachiProgress);

        imageView = (ImageView) findViewById(R.id.FileDownloadimageView);
        btnStartDyersProgress.setOnClickListener(this);
        btnStartMoonPineProgress.setOnClickListener(this);
        btnStartPlumEstateProgress.setOnClickListener(this);
        btnStartUshimachiProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        oldFileUrl = fileUrl;
        switch (v.getId()) {
            case R.id.btnStartDyersProgress:
                fileUrl = DYERS_URL;
                break;
            case R.id.btnStartMoonPineProgress:
                fileUrl = MOON_PINE_URL;
                break;
            case R.id.btnStartPlumEstateProgress:
                fileUrl = PLUM_ESTATE_URL;
                break;
            default:
                fileUrl = USHIMACHI_URL;
        }

        if (!oldFileUrl.equals(fileUrl)) {
            progressBar = new ProgressDialog(v.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage(PROGRESS_MESSAGE);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            progressBarStatus = 0;
            fileSize = 0;

            Thread thread = new Thread() {
                public void run() {
                    downloadFile();
                }
            };
            thread.start();
        }
        else {
            Toast.makeText(MainActivity.this, "Image is already being displayed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadFile() {
        while (progressBarStatus != 100) {
            int count;
            try {
                URL url = new URL(fileUrl);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(FILE_PATH);

                int lengthOfFile = connection.getContentLength();
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    output.write(data, 0, count);
                    progressBarStatus = (int) ((total * 100) / lengthOfFile);
                    progressBarHandler.post(new Runnable() {

                        @Override
                        public void run() {

                            progressBar.setProgress(progressBarStatus);
                            if (progressBarStatus == 100) {
                                progressBar.setMessage(COMPLETION_MESSAGE);
                                imageView.setImageDrawable(Drawable
                                        .createFromPath(FILE_PATH));
                            }
                        }
                    });
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

        }

        if (progressBarStatus >= 100) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Problem with putting the thread to sleep.");
                e.printStackTrace();
            }

            progressBar.dismiss();
        }
    }
}
