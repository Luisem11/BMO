package com.edu.udea.bmo.View.Login;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;
import com.edu.udea.bmo.View.Tutor.MainActivityTutor;
import com.edu.udea.bmo.View.User.MainActivityUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button loginButton, logupButton, logupAsTutorButton;
    private EditText userEditText, passwordEditText;
    private String mail, name;
    private CheckBox sesionCheckButton;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) view.findViewById(R.id.login);
        logupButton = (Button) view.findViewById(R.id.logup_user);
        logupAsTutorButton = (Button) view.findViewById(R.id.logup_tutor);
        userEditText = (EditText) view.findViewById(R.id.user);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        sesionCheckButton = (CheckBox) view.findViewById(R.id.session);
        loginButton.setOnClickListener(this);
        logupButton.setOnClickListener(this);
        logupAsTutorButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.logup_user:
                Fragment frag = new LogupFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, frag, "registrarUsuario")
                        .addToBackStack(null).commit();
                break;

            case R.id.logup_tutor:
                Snackbar.make(view, "En desarrollo ...", Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.login:
                String user = "", pass = "";
                if (userEditText.getText() != null && passwordEditText.getText() != null) {
                    user = userEditText.getText().toString();
                    pass = passwordEditText.getText().toString();
                }
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(getContext(), "Información Incompleta",
                            Toast.LENGTH_SHORT).show();
                } else {
                    DbHelper dbHelper = new DbHelper(getContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor c = db.rawQuery("SELECT " + StatusContract.Column_User.MAIL +
                            ", " + StatusContract.Column_User.NAME +
                            " FROM " + StatusContract.TABLE_USER +
                            " WHERE " + StatusContract.Column_User.USER + " = '" + user +
                            "' AND " + StatusContract.Column_User.PASSWORD + " = '" + pass + "'", null);

                    if (c.moveToFirst()) {
                        mail = c.getString(0);
                        name = c.getString(1);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(StatusContract.Column_User.STATE, "ACTIVO");
                        if (sesionCheckButton.isChecked()) {
                            contentValues.put(StatusContract.Column_User.SESSION, "ACTIVO");
                        }
                        db.updateWithOnConflict(StatusContract.TABLE_USER, contentValues,
                                StatusContract.Column_User.USER + "='" + user + "'", null, SQLiteDatabase.CONFLICT_IGNORE);
                        Intent other = new Intent(getActivity().getApplicationContext(), MainActivityUser.class);
                        Bundle bundleP = new Bundle();
                        onSaveInstanceState(bundleP);
                        other.putExtras(bundleP);
                        getActivity().finish();
                        startActivity(other);


                    } else {

                        c = db.rawQuery("SELECT " + StatusContract.Column_Tutor.MAIL +
                                ", " + StatusContract.Column_Tutor.NAME +
                                " FROM " + StatusContract.TABLE_TUTOR +
                                " WHERE " + StatusContract.Column_Tutor.USER + " = '" + user +
                                "' AND " + StatusContract.Column_Tutor.PASSWORD + " = '" + pass + "'", null);

                        if (c.moveToFirst()) {
                            mail = c.getString(0);
                            name = c.getString(1);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(StatusContract.Column_Tutor.STATE, "ACTIVO");
                            if (sesionCheckButton.isChecked()) {
                                contentValues.put(StatusContract.Column_Tutor.SESSION, "ACTIVO");
                            }
                            db.updateWithOnConflict(StatusContract.TABLE_TUTOR, contentValues,
                                    StatusContract.Column_Tutor.USER + "='" + user + "'", null, SQLiteDatabase.CONFLICT_IGNORE);
                            Intent other = new Intent(getActivity().getApplicationContext(), MainActivityTutor.class);
                            Bundle bundleP = new Bundle();
                            onSaveInstanceState(bundleP);
                            other.putExtras(bundleP);
                            getActivity().finish();
                            startActivity(other);


                        } else {
                            Toast.makeText(getContext(), "Usuario o Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                    db.close();
                }
                break;
        }


    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(StatusContract.Column_User.MAIL, mail);
        savedInstanceState.putString(StatusContract.Column_User.NAME, name);

    }

}
