package com.edu.udea.bmo.Controller;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;


/**
 * Adaptador de Monitores
 */
public class TutorsCursorAdapter extends CursorAdapter {
    public TutorsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_chat, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.NAME));

        String picture = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.PICTURE));


        // Setup.
        nameText.setText(name);
        avatarImage.setImageBitmap(ImageCodeClass.decodeCircular(picture));

    }

}
