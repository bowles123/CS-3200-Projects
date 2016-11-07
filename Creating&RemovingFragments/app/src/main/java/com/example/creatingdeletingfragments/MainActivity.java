package com.example.creatingdeletingfragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MainActivityFragment fragment;
    boolean shouldAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.addFragment);
        Button removeButton = (Button) findViewById(R.id.removeFragment);
        fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentByTag("ADDED");
        shouldAdd = true;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldAdd) {
                    fragment = new MainActivityFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.relativeLayout, fragment, "ADDED");
                    transaction.addToBackStack("add");
                    transaction.commit();
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    shouldAdd = false;
                }
            }
        });
    }
}
