package com.edu.udea.bmo.Model.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edu.udea.bmo.Controller.ImageCodeClass;
import com.edu.udea.bmo.R;


public class DbHelper extends SQLiteOpenHelper {

    private Context c;

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
        this.c = context.getApplicationContext();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_USER + " ("
                + StatusContract.Column_User.MAIL + " TEXT  PRIMARY KEY NOT NULL,"
                + StatusContract.Column_User.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_User.USER + " TEXT NOT NULL,"
                + StatusContract.Column_User.PASSWORD + " TEXT NOT NULL,"
                + StatusContract.Column_User.INSTITUTION + " TEXT,"
                + StatusContract.Column_User.PICTURE + " TEXT,"
                + StatusContract.Column_User.STATE + " TEXT NOT NULL,"
                + StatusContract.Column_User.SESSION + " TEXT NOT NULL,"
                + "UNIQUE (" + StatusContract.Column_User.USER + "))");

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_TUTOR + " ("
                + StatusContract.Column_Tutor.ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + StatusContract.Column_Tutor.MAIL + " TEXT NOT NULL,"
                + StatusContract.Column_Tutor.NAME + " TEXT NOT NULL,"
                + StatusContract.Column_Tutor.USER + " TEXT NOT NULL,"
                + StatusContract.Column_Tutor.PASSWORD + " TEXT NOT NULL,"
                + StatusContract.Column_Tutor.INSTITUTION + " TEXT,"
                + StatusContract.Column_Tutor.SCORE + " TEXT,"
                + StatusContract.Column_Tutor.PICTURE + " TEXT,"
                + StatusContract.Column_Tutor.STATE + " TEXT NOT NULL,"
                + StatusContract.Column_Tutor.SESSION + " TEXT NOT NULL,"
                + "UNIQUE (" + StatusContract.Column_User.USER + "))");

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_SUBJECT + " ("
                + StatusContract.Column_Subject.ID + " INTEGER PRIMARY KEY,"
                + StatusContract.Column_Subject.NAME + " TEXT NOT NULL )");

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_TUTOR_SUBJECT + " ("
                + StatusContract.Column_Tutor.MAIL + " TEXT NOT NULL, "
                + StatusContract.Column_Subject.ID + " INTEGER NOT NULL," +
                " CONSTRAINT PK_INT_SUB PRIMARY KEY ( " + StatusContract.Column_Subject.ID +
                " , " + StatusContract.Column_Tutor.MAIL + " ))");

        db.execSQL("CREATE TABLE " + StatusContract.TABLE_PROGRAM + " ("
                + StatusContract.Column_Tutor.ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + StatusContract.Column_Program.TUTOR + " TEXT NOT NULL, "
                + StatusContract.Column_Program.SUBJECT + " TEXT NOT NULL, "
                + StatusContract.Column_Program.SCHEDULE + " INTEGER NOT NULL)");


        Bitmap pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.esq);
        String pict = ImageCodeClass.encodeToBase64(pic);

        UserStructure user = new UserStructure("Maria Fernanda Esquivel", "esquivel", "peru", "esquivel@aaaa.com", pict, "Universidad Nacional");
        db.insert(StatusContract.TABLE_USER, null, user.toContentValues());

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.hombre);
        pict = ImageCodeClass.encodeToBase64(pic);

        user = new UserStructure("Pedro Perez", "pe", "1", "pedroperez@aaaa.com", pict, "UdeA");
        db.insert(StatusContract.TABLE_USER, null, user.toContentValues());

        //Add Tutor

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.hombre);
        pict = ImageCodeClass.encodeToBase64(pic);

        TutorStructure Tutor = new TutorStructure("Juan Perez", "ju", "1", "juanperez@aaaa.com", pict, "UdeA");
        db.insert(StatusContract.TABLE_TUTOR, null, Tutor.toContentValues());

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.obrero);
        pict = ImageCodeClass.encodeToBase64(pic);

        Tutor = new TutorStructure("Sara Martinez", "sm", "1", "sara@aaaa.com", pict, "UdeM");
        db.insert(StatusContract.TABLE_TUTOR, null, Tutor.toContentValues());

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.hombre);
        pict = ImageCodeClass.encodeToBase64(pic);

        Tutor = new TutorStructure("Pedro López", "pl", "1", "pedro@aaaa.com", pict, "UdeA");
        db.insert(StatusContract.TABLE_TUTOR, null, Tutor.toContentValues());

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.esq);
        pict = ImageCodeClass.encodeToBase64(pic);

        Tutor = new TutorStructure("Paula Mesa", "pm", "1", "paulamesa@aaaa.com", pict, "Eafit");
        db.insert(StatusContract.TABLE_TUTOR, null, Tutor.toContentValues());

        pic = BitmapFactory.decodeResource(c.getResources(), R.drawable.hom);
        pict = ImageCodeClass.encodeToBase64(pic);

        Tutor = new TutorStructure("Frank Rivera", "", "fr", "frank@aaaa.com", pict, "ITM");
        db.insert(StatusContract.TABLE_TUTOR, null, Tutor.toContentValues());

        //Insert Subjects

        ContentValues values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Matemáticas");
        values.put(StatusContract.Column_Subject.ID, 1);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);

        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Química");
        values.put(StatusContract.Column_Subject.ID, 2);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);

        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Física");
        values.put(StatusContract.Column_Subject.ID, 3);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Cálculo");
        values.put(StatusContract.Column_Subject.ID, 4);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Lengua Castellana");
        values.put(StatusContract.Column_Subject.ID, 5);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Inglés");
        values.put(StatusContract.Column_Subject.ID, 6);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Geometría");
        values.put(StatusContract.Column_Subject.ID, 7);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Astronomía");
        values.put(StatusContract.Column_Subject.ID, 8);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Biología");
        values.put(StatusContract.Column_Subject.ID, 9);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Subject.NAME, "Geología");
        values.put(StatusContract.Column_Subject.ID, 10);
        db.insert(StatusContract.TABLE_SUBJECT, null, values);


        //Associate Subjects

        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "juanperez@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 1);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "juanperez@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 3);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "juanperez@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 2);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);

        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "sara@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 1);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "sara@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 2);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "sara@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 4);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "sara@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 6);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);

        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "pedro@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 6);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "pedro@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 10);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "pedro@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 9);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values = new ContentValues();
        values.put(StatusContract.Column_Tutor.MAIL, "paulamesa@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 4);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values.put(StatusContract.Column_Tutor.MAIL, "paulamesa@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 5);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values.put(StatusContract.Column_Tutor.MAIL, "paulamesa@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 7);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values.put(StatusContract.Column_Tutor.MAIL, "frank@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 4);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
        values.put(StatusContract.Column_Tutor.MAIL, "frank@aaaa.com");
        values.put(StatusContract.Column_Subject.ID, 8);
        db.insert(StatusContract.TABLE_TUTOR_SUBJECT, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + StatusContract.TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + StatusContract.TABLE_TUTOR_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + StatusContract.TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + StatusContract.TABLE_TUTOR);
        db.execSQL("DROP TABLE IF EXISTS " + StatusContract.TABLE_USER);
        onCreate(db);
    }


    public Cursor getAllTutors() {
        return getReadableDatabase()
                .query(
                        StatusContract.TABLE_TUTOR,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

    }


    public Cursor getTutorById(String tutorId) {
        Cursor c = getReadableDatabase().query(
                StatusContract.TABLE_TUTOR,
                null,
                StatusContract.Column_Tutor.MAIL + " LIKE ?",
                new String[]{tutorId},
                null,
                null,
                null);
        return c;
    }

    public Cursor getSubjectFromTutorMail(String mail) {

        return getReadableDatabase().query(
                StatusContract.TABLE_TUTOR_SUBJECT
                        + " INNER JOIN " + StatusContract.TABLE_SUBJECT
                        + " ON " + StatusContract.TABLE_TUTOR_SUBJECT + "."
                        + StatusContract.Column_Subject.ID + "="
                        + StatusContract.TABLE_SUBJECT + "."
                        + StatusContract.Column_Subject.ID,
                new String[]{StatusContract.Column_Subject.NAME},
                StatusContract.Column_Tutor.MAIL + " LIKE ?",
                new String[]{mail},
                null,
                null,
                null);

    }

    public Cursor getAllProgram() {

        return getReadableDatabase()
                .query(
                        StatusContract.TABLE_PROGRAM,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAllChats() {
        Cursor c = getReadableDatabase().query(
                StatusContract.TABLE_TUTOR,
                null,
                StatusContract.Column_Tutor.MAIL + " LIKE  ?",
                new String[]{"P%"},
                null,
                null,
                null);
        return c;
    }
}
