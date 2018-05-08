package com.edu.udea.bmo.Model.DB;

import android.content.ContentValues;
import android.database.Cursor;


public class TutorStructure {

    private String name, user, password, mail, state, picture, sesion, institution, score;


    public TutorStructure(String name, String user, String password, String mail, String picture, String institution) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.mail = mail;
        this.picture = picture;
        this.institution = institution;
        this.score = "4";
        this.state = "INACTIVO";
        this.sesion = "INACTIVO";
    }

    public TutorStructure(Cursor cursor) {
        user = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.USER));
        name = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.NAME));
        password = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.PASSWORD));
        mail = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.MAIL));
        picture = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.PICTURE));
        institution = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.INSTITUTION));
        sesion = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.SESSION));
        state = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.STATE));
        score = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.SCORE));

    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(StatusContract.Column_Tutor.USER, user);
        values.put(StatusContract.Column_Tutor.NAME, name);
        values.put(StatusContract.Column_Tutor.PASSWORD, password);
        values.put(StatusContract.Column_Tutor.MAIL, mail);
        values.put(StatusContract.Column_Tutor.PICTURE, picture);
        values.put(StatusContract.Column_Tutor.INSTITUTION, institution);
        values.put(StatusContract.Column_Tutor.STATE, state);
        values.put(StatusContract.Column_Tutor.SESSION, sesion);
        values.put(StatusContract.Column_Tutor.SCORE, sesion);
        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
