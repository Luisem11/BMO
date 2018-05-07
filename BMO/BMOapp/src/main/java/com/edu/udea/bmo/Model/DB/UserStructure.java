package com.edu.udea.bmo.Model.DB;

import android.content.ContentValues;
import android.database.Cursor;


public class UserStructure {

    private String name, user, password, mail, state, picture, sesion, institution;


    public UserStructure(String name, String user, String password, String mail, String picture, String institution) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.mail = mail;
        this.picture = picture;
        this.institution = institution;
        this.state = "INACTIVO";
        this.sesion = "INACTIVO";
    }

    public UserStructure(Cursor cursor) {
        user = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.USER));
        name = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.NAME));
        password = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.PASSWORD));
        mail = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.MAIL));
        picture = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.PASSWORD));
        institution = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.INSTITUTION));
        sesion = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.SESSION));
        state = cursor.getString(cursor.getColumnIndex(StatusContract.Column_User.STATE));
    }
    
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(StatusContract.Column_User.USER, user);
        values.put(StatusContract.Column_User.NAME, name);
        values.put(StatusContract.Column_User.PASSWORD, password);
        values.put(StatusContract.Column_User.MAIL, mail);
        values.put(StatusContract.Column_User.PICTURE, picture);
        values.put(StatusContract.Column_User.INSTITUTION, institution);
        values.put(StatusContract.Column_User.STATE, state);
        values.put(StatusContract.Column_User.SESSION, sesion);
        return values;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getPicture() {
        return picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
