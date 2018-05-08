package com.edu.udea.bmo.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.udea.bmo.Model.DB.DbHelper;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;


/**
 * Adaptador de Materias
 */
public class SubjectsCursorAdapter extends CursorAdapter implements View.OnClickListener {
    Cursor c;

    public SubjectsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_subject, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        c = cursor;

        // Referencias UI.
        TextView tutorTextView = (TextView) view.findViewById(R.id.is_tutor);
        TextView subjectTextView = (TextView) view.findViewById(R.id.is_subject);
        RelativeLayout itemRelativeLayout = (RelativeLayout) view.findViewById(R.id.item_subject);


        // Setup.
        tutorTextView.setText(cursor.getString(cursor.getColumnIndex(StatusContract.Column_Program.TUTOR)));
        subjectTextView.setText(cursor.getString(cursor.getColumnIndex(StatusContract.Column_Program.SUBJECT)));
        itemRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {


        LayoutInflater l = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = l.inflate(R.layout.show_item_detail, null);
        final FrameLayout layout = new FrameLayout(view.getContext());
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        TextView subjectTextView = v.findViewById(R.id.id_subject);
        TextView scheduleTextView = v.findViewById(R.id.id_schedule);
        TextView tutorTextView = v.findViewById(R.id.id_tutor);
        tutorTextView.setText(c.getString(c.getColumnIndex(StatusContract.Column_Program.TUTOR)));
        scheduleTextView.setText(c.getString(c.getColumnIndex(StatusContract.Column_Program.SCHEDULE)));
        subjectTextView.setText(c.getString(c.getColumnIndex(StatusContract.Column_Program.SUBJECT)));


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(layout);
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Asesoria Programada")
                .setPositiveButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                            }
                        })
                .setNegativeButton("Cancelar Asesoria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SQLiteDatabase db = new DbHelper(view.getContext()).getWritableDatabase();
                        db.delete(StatusContract.TABLE_PROGRAM,
                                StatusContract.Column_Program.TUTOR + " LIKE ? AND " +
                                        StatusContract.Column_Program.SUBJECT + " LIKE ? AND " +
                                        StatusContract.Column_Program.SCHEDULE + " LIKE ? ",
                                new String[]{"" + c.getString(c.getColumnIndex(StatusContract.Column_Program.TUTOR)),
                                        "" + c.getString(c.getColumnIndex(StatusContract.Column_Program.SUBJECT)),
                                        "" + c.getString(c.getColumnIndex(StatusContract.Column_Program.SCHEDULE))});

                        Toast.makeText(view.getContext(), "Cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

}
