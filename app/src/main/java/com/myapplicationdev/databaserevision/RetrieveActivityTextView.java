package com.myapplicationdev.databaserevision;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RetrieveActivityTextView extends AppCompatActivity {

    Button btnGetNotes;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_tv);

        btnGetNotes = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);

        DBHelper db = new DBHelper(RetrieveActivityTextView.this);
        ArrayList<String> data = db.getNotesInStrings();
        db.close();
        String txt = "";
        for (int i = 0; i < data.size(); i++) {
            txt += (i+1) + ". " + data.get(i) + "\n";
        }
        tvResults.setText(txt);

        btnGetNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(RetrieveActivityTextView.this);
                ArrayList<String> data = db.getNotesInStrings();
                db.close();
                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    txt += (i+1) + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);



            }
        });

    }
}