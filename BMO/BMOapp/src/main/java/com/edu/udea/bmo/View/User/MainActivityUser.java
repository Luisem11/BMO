package com.edu.udea.bmo.View.User;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;
import com.edu.udea.bmo.View.Login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivityUser extends AppCompatActivity {

    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setToolbar(); // Añadir la toolbar

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        tabs.setHorizontalScrollBarEnabled(true);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), getString(R.string.title_section1));
        adapter.addFragment(new MainUserFragment(), getString(R.string.title_section3));
        viewPager.setAdapter(adapter);
    }

    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.cv_all_tutors:
                intent = new Intent(this,AllTutorsActivity.class);
                break;

            case R.id.cv_near_tutors:
                intent = new Intent(this, MapsActivity.class);
                break;

            case R.id.cv_next_tutor:
                intent = new Intent(this, SubjectProgramActivity.class);
                break;

            case R.id.cv_subjects:
                selectSubject();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_main_user, new TutorsFragment()).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, ConfigActivity.class);
                break;

            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                break;

            case R.id.action_logout:


                DbHelper dbHelper = new DbHelper(getApplication().getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(StatusContract.Column_User.STATE, "INACTIVO");
                contentValues.put(StatusContract.Column_User.SESSION, "INACTIVO");
                db.updateWithOnConflict(StatusContract.TABLE_USER, contentValues,
                        StatusContract.Column_User.SESSION + "='ACTIVO'", null, SQLiteDatabase.CONFLICT_IGNORE);
                db.close();
                Intent other = new Intent(getApplication().getApplicationContext(), LoginActivity.class);
                Bundle bundleP = new Bundle();
                onSaveInstanceState(bundleP);
                other.putExtras(bundleP);
                finish();
                startActivity(other);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private void selectSubject() {


        final TextView titleTextView;
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

                                Intent intent = new Intent(getApplicationContext(),SubjectFindActivity.class);

                                intent.putExtra(StatusContract.Column_Subject.NAME, spinner.getSelectedItem().toString());

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}
