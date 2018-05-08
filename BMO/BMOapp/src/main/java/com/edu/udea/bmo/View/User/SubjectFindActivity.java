package com.edu.udea.bmo.View.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.udea.bmo.Controller.TutorsCursorAdapter;
import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectFindActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    private ListView mTutorsList;
    private TutorsCursorAdapter mTutorsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_find);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // Referencias UI
        mTutorsList = (ListView) findViewById(R.id.tutors_list);
        mTutorsAdapter = new TutorsCursorAdapter(this, null);

        // Setup
        mTutorsList.setAdapter(mTutorsAdapter);

        // Eventos
        mTutorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mTutorsAdapter.getItem(i);
                String currentTutorId = currentItem.getString(
                        currentItem.getColumnIndex(StatusContract.Column_Tutor.MAIL));

                showDetailScreen(currentTutorId);
            }
        });


        // Instancia de helper
        mDbHelper = new DbHelper(this);





        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();

        String s = getIntent().getStringExtra(StatusContract.Column_Subject.NAME);

        getSupportActionBar().setTitle("Monitores de " + s);


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
                new String[]{StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.ID,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.MAIL,
                        StatusContract.TABLE_TUTOR + "."+ StatusContract.Column_Tutor.NAME,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.USER,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.PASSWORD,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.INSTITUTION,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.SCORE,
                        StatusContract.TABLE_TUTOR + "." + StatusContract.Column_Tutor.PICTURE},
                StatusContract.TABLE_SUBJECT + "."+ StatusContract.Column_Subject.NAME
                        + " LIKE ?",
                new String[]{s},
                null,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            mTutorsAdapter.changeCursor(cursor);
        } else {
            // Mostrar empty state
        }
    }

    private void showDetailScreen(String tutorId) {
        Intent intent = new Intent(this, TutorDetailActivity.class);
        intent.putExtra(StatusContract.Column_Tutor.MAIL, tutorId);
        startActivity(intent);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

}
