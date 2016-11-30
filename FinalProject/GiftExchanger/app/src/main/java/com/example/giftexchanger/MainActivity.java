package com.example.giftexchanger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    GiftExchanger exchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.exchangeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.participantsText);
                String lines = text.getText().toString();
                String newText;

                exchanger = new GiftExchanger(new ArrayList<>(Arrays.asList(lines.split("\n"))));
                newText = exchanger.exchange();
                text.setText(newText);
                button.setVisibility(View.GONE);
            }
        });
    }
}
