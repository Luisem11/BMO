package com.edu.udea.bmo.View.User;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edu.udea.bmo.Controller.TutorsCursorAdapter;
import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTutorsFragment extends Fragment {

    private DbHelper mDbHelper;

    private ListView mTutorsList;
    private TutorsCursorAdapter mTutorsAdapter;




    public AllTutorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all_tutors, container, false);


        // Referencias UI
        mTutorsList = (ListView) root.findViewById(R.id.tutors_list);
        mTutorsAdapter = new TutorsCursorAdapter(getActivity(), null);

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
        mDbHelper = new DbHelper(getActivity());

        // Carga de datos
        loadTutors();

        return root;
    }

    private void loadTutors() {
        new TutorsLoadTask().execute();
    }



    private void showDetailScreen(String tutorId) {
        Intent intent = new Intent(getActivity(), TutorDetailActivity.class);
        intent.putExtra(StatusContract.Column_Tutor.MAIL, tutorId);
        startActivity(intent);
    }

    private class TutorsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllTutors();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mTutorsAdapter.changeCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }


}
