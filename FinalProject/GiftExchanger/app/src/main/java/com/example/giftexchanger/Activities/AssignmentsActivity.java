package com.example.giftexchanger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.giftexchanger.R;

public class AssignmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        TextView textView = (TextView) findViewById(R.id.assignmentsText);
        Button redo = (Button) findViewById(R.id.redoButton);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("Assignments"));

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
