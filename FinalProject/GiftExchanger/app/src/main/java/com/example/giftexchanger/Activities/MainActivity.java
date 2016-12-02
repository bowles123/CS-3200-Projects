package com.example.giftexchanger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.giftexchanger.Exchanger.GiftExchanger;
import com.example.giftexchanger.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button button;
    GiftExchanger exchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AppCompatActivity main = this;

        button = (Button) findViewById(R.id.exchangeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main, AssignmentsActivity.class);
                EditText text = (EditText) findViewById(R.id.participantsText);
                String lines = text.getText().toString();
                String newText;

                exchanger = new GiftExchanger(new ArrayList<>(Arrays.asList(lines.split("\n"))));
                newText = exchanger.exchange();
                intent.putExtra("Assignments", newText);
                startActivity(intent);
            }
        });
    }
}
