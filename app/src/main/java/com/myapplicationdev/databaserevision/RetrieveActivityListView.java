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

    ListView lv;
    ArrayAdapter<Note> aa;
    ArrayList<Note> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_lv);

        btnGetNotes = findViewById(R.id.btnGetTasks);
        lv = findViewById(R.id.lv);

        al = new ArrayList<>();
        aa = new ArrayAdapter<Note>(RetrieveActivityListView.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        DBHelper db = new DBHelper(RetrieveActivityListView.this);
        ArrayList<Note> getNote = db.getNotesInObjects();
        db.close();
        aa.clear();
        aa.addAll(getNote);
        aa.notifyDataSetChanged();
        btnGetNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(RetrieveActivityListView.this);
                ArrayList<Note> getNote = db.getNotesInObjects();
                db.close();
                aa.clear();
                aa.addAll(getNote);
                aa.notifyDataSetChanged();

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input_edit, null);

                EditText etContent = viewDialog.findViewById(R.id.et1);
                etContent.setText(getNote.get(position).getContent());
                EditText etNumber = viewDialog.findViewById(R.id.et2);
                String priority = Integer.toString(getNote.get(position).getPriority());
                etNumber.setText(priority);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(RetrieveActivityListView.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Edit Note");

                myBuilder.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(RetrieveActivityListView.this);
                        String newContent = etContent.getText().toString();
                        int newNum = Integer.parseInt(etNumber.getText().toString());
                        db.updateNote(position, newContent, newNum);
                        ArrayList<Note> getNote = db.getNotesInObjects();
                        db.close();
                        aa.clear();
                        aa.addAll(getNote);
                        aa.notifyDataSetChanged();
                    }
                });

                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        //Option: Implement dialog to edit a record
        //Option: Implement context to delete a record

    }
}