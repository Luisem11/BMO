package com.edu.udea.bmo.View.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

public class AllTutorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tutors);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monitores Disponibles");


        String id = getIntent().getStringExtra(StatusContract.Column_Tutor.MAIL);

        AllTutorsFragment fragment = (AllTutorsFragment)
                getSupportFragmentManager().findFragmentById(R.id.tutor_detail_container);
        if (fragment == null) {
            fragment = new AllTutorsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.tutor_detail_container, fragment)
                    .commit();
        }

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
