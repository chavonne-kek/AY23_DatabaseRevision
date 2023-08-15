package com.myapplicationdev.databaserevision;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RetrieveActivityListView extends AppCompatActivity {
    Button btnGetNotes;
    ListView lvNote;
    ArrayAdapter<Note> adapter;
    ArrayList<Note> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_lv);

        btnGetNotes = findViewById(R.id.btnGetTasks);
        lvNote = findViewById(R.id.lv);

        al = new ArrayList<>();
        adapter = new ArrayAdapter<>(RetrieveActivityListView.this, android.R.layout.simple_list_item_1, al);
        lvNote.setAdapter(adapter);

        DBHelper db = new DBHelper(RetrieveActivityListView.this);
        ArrayList<Note> noteList = db.getNotesInObjects();
        db.close();
        al.clear();
        al.addAll(noteList);
        adapter.notifyDataSetChanged();
        btnGetNotes.setOnClickListener(v -> {
            DBHelper db1 = new DBHelper(RetrieveActivityListView.this);
            ArrayList<Note> noteList1 = db1.getNotesInObjects();
            db1.close();
            al.clear();
            al.addAll(noteList1);
            adapter.notifyDataSetChanged();
        });

        lvNote.setOnItemClickListener((parent, view, position, id) -> {
            LayoutInflater inflater = (LayoutInflater) RetrieveActivityListView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewDialog = inflater.inflate(R.layout.input_edit, null);

            EditText etContent = viewDialog.findViewById(R.id.et1);
            EditText etNumber = viewDialog.findViewById(R.id.et2);
            String priority = Integer.toString(noteList.get(position).getPriority());
            etContent.setText(noteList.get(position).getContent());
            etNumber.setText(priority);

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(RetrieveActivityListView.this);
            myBuilder.setView(viewDialog);
            myBuilder.setTitle("Edit Note");

            myBuilder.setNegativeButton("Update", (dialog, which) -> {
                String newContent = etContent.getText().toString();
                int newNum = Integer.parseInt(etNumber.getText().toString());

                db.updateNote(position + 1, newContent, newNum);
                db.close();
                DBHelper db12 = new DBHelper(RetrieveActivityListView.this);
                ArrayList<Note> noteList12 = db12.getNotesInObjects();
                db12.close();
                al.clear();
                al.addAll(noteList12);
                adapter.notifyDataSetChanged();
            });

            myBuilder.setNeutralButton("Delete", (DialogInterface dialog, int which) -> {
                db.deleteNote(position + 1);
                DBHelper db12 = new DBHelper(RetrieveActivityListView.this);
                ArrayList<Note> noteList12 = db12.getNotesInObjects();
                db12.close();
                al.clear();
                al.addAll(noteList12);
                adapter.notifyDataSetChanged();
                db12.close();
            });

            myBuilder.setPositiveButton("Cancel", null);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        });

    }
}