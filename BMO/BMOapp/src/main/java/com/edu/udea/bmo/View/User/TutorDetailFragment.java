package com.edu.udea.bmo.View.User;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.TutorStructure;
import com.edu.udea.bmo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorDetailFragment extends Fragment {


    private static final String ARG_TUTOR_ID = "tutorId";


    private String mTutorMail;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mEmail;
    private TextView mSubject;
    private TextView mInstitution;
    private String subjects;

    private DbHelper mTutorsDbHelper;




    public TutorDetailFragment() {
        // Required empty public constructor
    }

    public static TutorDetailFragment newInstance(String tutorId) {
        TutorDetailFragment fragment = new TutorDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TUTOR_ID, tutorId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTutorMail = getArguments().getString(ARG_TUTOR_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tutor_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mEmail = (TextView) root.findViewById(R.id.dt_email);
        mSubject = (TextView) root.findViewById(R.id.dt_subject);
        mInstitution = (TextView) root.findViewById(R.id.dt_institution);

        mTutorsDbHelper = new DbHelper(getActivity());

        loadTutor();

        return root;
    }

    private void loadTutor() {
        new GetTutorByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void showTutor(TutorStructure tutor, String subject) {
        mCollapsingView.setTitle(tutor.getName());
        mEmail.setText(tutor.getMail());
        mSubject.setText(subject);
        mInstitution.setText(tutor.getInstitution());
    }


    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }



    private class GetTutorByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            Cursor c = mTutorsDbHelper.getSubjectFromTutorMail(mTutorMail);
            StringBuilder stringBuilder = new StringBuilder();
            for (boolean b = c.moveToFirst(); b; b = c.moveToNext()){
                stringBuilder.append( c.getString(0) + " " );
            }
            subjects = new String(stringBuilder);

            return mTutorsDbHelper.getTutorById(mTutorMail);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showTutor(new TutorStructure(cursor), subjects);
            } else {
                showLoadError();
            }
        }

    }



}
