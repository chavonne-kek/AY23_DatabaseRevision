package com.myapplicationdev.databaserevision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnInsertRecord, btnRetrieveRecordsTV, btnRetrieveRecordsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertRecord = findViewById(R.id.btnInsertRecord);
        btnRetrieveRecordsTV = findViewById(R.id.btnGetRecord);
        btnRetrieveRecordsLV = findViewById(R.id.btnRetrieveRecord);

        btnInsertRecord.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        });

        btnRetrieveRecordsTV.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RetrieveActivityTextView.class);
            startActivity(intent);
        });

        btnRetrieveRecordsLV.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RetrieveActivityListView.class);
            startActivity(intent);
        });

    }
}