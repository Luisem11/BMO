package com.edu.udea.bmo.View.Login;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.udea.bmo.Controller.ImageCodeClass;
import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.Model.DB.UserStructure;
import com.edu.udea.bmo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogupFragment extends Fragment implements View.OnClickListener {


    private Button logupButton;
    private ImageView userPhoto;
    private Bitmap photoBitmap = null;



    public LogupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_up, container, false);

        userPhoto = view.findViewById(R.id.photoUser);
        logupButton = (Button) view.findViewById(R.id.logupButton);
        logupButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        EditText editTextName, editTextUser, editTextMail, editTextPassword, editTextInstitution;
        ImageView imageViewPhoto;
        String name, user, mail, password, photo, institution;

        editTextName = (EditText) getView().findViewById(R.id.userNameLogup);
        editTextUser = (EditText) getView().findViewById(R.id.userLogup);
        editTextMail = (EditText) getView().findViewById(R.id.emailLogup);
        editTextPassword = (EditText) getView().findViewById(R.id.passwordLogup);
        editTextInstitution = (EditText) getView().findViewById(R.id.institutionLogup);

        name = editTextName.getText().toString();
        user = editTextUser.getText().toString();
        mail = editTextMail.getText().toString();
        password = editTextPassword.getText().toString();
        institution = editTextInstitution.getText().toString();


        if (name.equals("") || user.equals("") || mail.equals("") ||password.equals("")|| photoBitmap ==null) {
            Toast.makeText(getContext(), "Falta Información", Toast.LENGTH_SHORT).show();
        } else {
            photo = ImageCodeClass.encodeToBase64(photoBitmap);
            DbHelper dbHelper = new DbHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query1 = "SELECT " + StatusContract.Column_User.USER + " FROM " + StatusContract.TABLE_USER + " WHERE " + StatusContract.Column_User.USER + " = '" + user + "'";
            String query2 = "SELECT " + StatusContract.Column_User.MAIL + " FROM " + StatusContract.TABLE_USER + " WHERE " + StatusContract.Column_User.MAIL + " = '" + mail + "'";
            String query3 = "SELECT " + StatusContract.Column_Tutor.USER + " FROM " + StatusContract.TABLE_TUTOR + " WHERE " + StatusContract.Column_Tutor.USER + " = '" + user + "'";
            String query4 = "SELECT " + StatusContract.Column_Tutor.MAIL + " FROM " + StatusContract.TABLE_TUTOR + " WHERE " + StatusContract.Column_Tutor.MAIL + " = '" + mail + "'";
            Cursor c1 = db.rawQuery(query1, null);
            Cursor c2 = db.rawQuery(query2,null);
            Cursor c3 = db.rawQuery(query1, null);
            Cursor c4 = db.rawQuery(query2,null);


            if (c1.getCount()!=0||c2.getCount()!=0||c3.getCount()!=0||c4.getCount()!=0) {
                if((c1.getCount()!=0&&c2.getCount()!=0)||(c3.getCount()!=0&&c4.getCount()!=0)){
                    Toast.makeText(getContext(),"El correo y el usuario están en uso",Toast.LENGTH_SHORT).show();
                }else{
                    if(c1.getCount()!=0||c3.getCount()!=0){
                        Toast.makeText(getContext(),"El usuario ya está en uso",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"El correo ya está en uso",Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                UserStructure userDb = new UserStructure(name,user,password,mail, photo, institution);
                Snackbar.make(view, R.string.logupS, Snackbar.LENGTH_SHORT).show();
                db.insert(StatusContract.TABLE_USER,null,userDb.toContentValues());
                Fragment frag = new LoginFragment();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
            }
        }

    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
        userPhoto.setImageBitmap(photoBitmap);
    }




}
