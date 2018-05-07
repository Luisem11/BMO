package com.edu.udea.bmo.View.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.udea.bmo.Controller.ImageCodeClass;
import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

/**
 * Created by luisernesto on 4/05/18.
 */

public class ProfileFragment extends Fragment {

    public TextView userNameTextView;
    public ImageView photoImageView;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        userNameTextView = rootView.findViewById(R.id.name_profile);
        photoImageView = rootView.findViewById(R.id.photo_profile);


        SQLiteDatabase db = new DbHelper(getContext()).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + StatusContract.Column_User.NAME +
                " , " + StatusContract.Column_User.PICTURE +
                " FROM " + StatusContract.TABLE_USER +
                " WHERE " + StatusContract.Column_User.STATE + " = 'ACTIVO' ", null);
        c.moveToFirst();

        userNameTextView.setText(c.getString(0));
        photoImageView.setImageBitmap(ImageCodeClass.decodeBase64(c.getString(1)));




        return rootView;
    }

}
