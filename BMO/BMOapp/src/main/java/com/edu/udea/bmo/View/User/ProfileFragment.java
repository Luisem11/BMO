package com.edu.udea.bmo.View.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.udea.bmo.Controller.ChatsCursorAdapter;
import com.edu.udea.bmo.Controller.ImageCodeClass;
import com.edu.udea.bmo.Controller.SubjectsCursorAdapter;
import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

/**
 * Created by luisernesto on 4/05/18.
 */

public class ProfileFragment extends Fragment {

    public TextView userNameTextView, notProgramTextView, notChatTextView;
    public ImageView photoImageView;
    private DbHelper mDbHelper;

    private ListView mProgramList, mChatList;
    private SubjectsCursorAdapter mProgramAdapter;
    private ChatsCursorAdapter mChatAdapter;


    public ProfileFragment() {
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        userNameTextView = rootView.findViewById(R.id.name_profile);
        photoImageView = rootView.findViewById(R.id.photo_profile);
        mProgramList = rootView.findViewById(R.id.tutors_list);
        mChatList = rootView.findViewById(R.id.chat_list);
        notChatTextView = rootView.findViewById(R.id.nothing_chat);
        notProgramTextView = rootView.findViewById(R.id.nothing);

        updateUI();

        return rootView;
    }

    public void updateUI() {


        // Referencias UI
        mProgramAdapter = new SubjectsCursorAdapter(getActivity(), null);

        // Setup
        mProgramList.setAdapter(mProgramAdapter);


        // Referencias UI
        mChatAdapter = new ChatsCursorAdapter(getActivity(), null);

        // Setup
        mChatList.setAdapter(mChatAdapter);


        SQLiteDatabase db = new DbHelper(getContext()).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + StatusContract.Column_User.NAME +
                " , " + StatusContract.Column_User.PICTURE +
                " FROM " + StatusContract.TABLE_USER +
                " WHERE " + StatusContract.Column_User.STATE + " = 'ACTIVO' ", null);
        c.moveToFirst();

        userNameTextView.setText(c.getString(0));

        photoImageView.setImageBitmap(ImageCodeClass.decodeCircular(c.getString(1)));

        // Instancia de helper
        mDbHelper = new DbHelper(getActivity());

        // Carga de datos
        loadSubject();

    }

    private void loadSubject() {
        new TutorsLoadTask().execute();
        new ChatLoadTask().execute();
    }

    @Override
    public void onResume() {
        updateUI();
        super.onResume();
    }

    private class TutorsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllProgram();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mProgramAdapter.changeCursor(cursor);
                notProgramTextView.setVisibility(View.GONE);
            } else {

                notProgramTextView.setVisibility(View.VISIBLE);
            }
            setListViewHeightBasedOnChildren(mProgramList);
        }
    }

    private class ChatLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllChats();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mChatAdapter.changeCursor(cursor);
                notChatTextView.setVisibility(View.GONE);
            } else {

                notChatTextView.setVisibility(View.VISIBLE);
            }
            setListViewHeightBasedOnChildren(mChatList);
        }
    }


}
