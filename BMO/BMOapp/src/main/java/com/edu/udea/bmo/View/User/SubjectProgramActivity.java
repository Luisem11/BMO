package com.edu.udea.bmo.View.User;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectProgramActivity extends AppCompatActivity {


    TextView subjectTextView, tutorTextView, scheduleTextView, titleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_program);
        subjectTextView = findViewById(R.id.sp_subject);
        tutorTextView = findViewById(R.id.sp_tutor);
        scheduleTextView = findViewById(R.id.sp_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_program);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_subject:

                selectSubject();

                break;

            case R.id.ll_tutor:

                selectTutor();
                break;

            case R.id.ll_schedule:
                selectSchedule();

                break;

            case R.id.program:
                if(tutorTextView.getText().toString().equals(" ")||
                        scheduleTextView.getText().toString().equals(" ")||
                        subjectTextView.getText().toString().equals(" ")){
                    Toast.makeText(this,getString(R.string.complete), Toast.LENGTH_SHORT).show();
                    break;
                }

                SQLiteDatabase db = new DbHelper(this).getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(StatusContract.Column_Program.TUTOR, tutorTextView.getText().toString());
                values.put(StatusContract.Column_Program.SCHEDULE, scheduleTextView.getText().toString());
                values.put(StatusContract.Column_Program.SUBJECT, subjectTextView.getText().toString());
                db.insert(StatusContract.TABLE_PROGRAM, null, values);
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
                finish();


                break;


        }
    }

    private void selectTutor() {

        if (subjectTextView.getText().equals(" ")) {
            Toast.makeText(this, "Debe seleccionar una materia primero", Toast.LENGTH_LONG).show();
            return;
        }

        final Spinner spinner;
        LayoutInflater l = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = l.inflate(R.layout.select_subject, null);
        final FrameLayout layout = new FrameLayout(this);
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        spinner = v.findViewById(R.id.subjects_spinner);
        titleTextView = v.findViewById(R.id.title_select);
        titleTextView.setText("Seleccione un Tutor");

        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();
        TextView textView1 = findViewById(R.id.sp_subject);
        String s = textView1.getText().toString();

        Cursor cursor = db.query(
                StatusContract.TABLE_TUTOR_SUBJECT
                        + " INNER JOIN " + StatusContract.TABLE_TUTOR
                        + " ON " + StatusContract.TABLE_TUTOR_SUBJECT + "."
                        + StatusContract.Column_Tutor.MAIL + " = "
                        + StatusContract.TABLE_TUTOR + "."
                        + StatusContract.Column_Tutor.MAIL
                        + " INNER JOIN " + StatusContract.TABLE_SUBJECT
                        + " ON " + StatusContract.TABLE_TUTOR_SUBJECT + "."
                        + StatusContract.Column_Subject.ID + "="
                        + StatusContract.TABLE_SUBJECT + "."
                        + StatusContract.Column_Subject.ID,
                new String[]{StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.NAME, StatusContract.TABLE_TUTOR + "."
                        + StatusContract.Column_Tutor.MAIL, StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.PICTURE},
                StatusContract.TABLE_SUBJECT + "."
                        + StatusContract.Column_Subject.NAME + " LIKE ?",
                new String[]{s},
                null,
                null,
                null);

        List<String> subjects = new ArrayList<>();
        for (boolean b = cursor.moveToFirst(); b; b = cursor.moveToNext()) {

            subjects.add(cursor.getString(0));

        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   //Set a default layout for items
        spinner.setAdapter(adapter);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(layout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                tutorTextView.setText(spinner.getSelectedItem().toString());

                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tutorTextView.setText(" ");
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void selectSchedule() {


        final Spinner spinner;
        LayoutInflater l = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = l.inflate(R.layout.select_subject, null);
        final FrameLayout layout = new FrameLayout(this);
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        spinner = v.findViewById(R.id.subjects_spinner);
        titleTextView = v.findViewById(R.id.title_select);
        titleTextView.setText("Seleccione una horario");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   //Set a default layout for items
        spinner.setAdapter(adapter);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(layout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                scheduleTextView.setText(spinner.getSelectedItem().toString());

                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scheduleTextView.setText(" ");
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        return;
    }

    private void selectSubject() {


        final Spinner spinner;
        LayoutInflater l = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = l.inflate(R.layout.select_subject, null);
        final FrameLayout layout = new FrameLayout(this);
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        spinner = v.findViewById(R.id.subjects_spinner);
        titleTextView = v.findViewById(R.id.title_select);
        titleTextView.setText("Seleccione una materia");

        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();

        Cursor cursor = db.query(StatusContract.TABLE_SUBJECT, null, null, null, null, null, null);

        List<String> subjects = new ArrayList<>();
        for (boolean b = cursor.moveToFirst(); b; b = cursor.moveToNext()) {

            subjects.add(cursor.getString(cursor.getColumnIndex(StatusContract.Column_Subject.NAME)));

        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   //Set a default layout for items
        spinner.setAdapter(adapter);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(layout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                subjectTextView.setText(spinner.getSelectedItem().toString());

                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subjectTextView.setText("");

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}
