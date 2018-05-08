package com.edu.udea.bmo.Controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;
import com.edu.udea.bmo.View.User.ChatActivity;


/**
 * Adaptador de Monitores
 */
public class ChatsCursorAdapter extends CursorAdapter   {
    public ChatsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_chat, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Referencias UI.
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);

        // Get valores.
        final String name = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.NAME));

        String picture = cursor.getString(cursor.getColumnIndex(StatusContract.Column_Tutor.PICTURE));


        // Setup.
        nameText.setText(name);
        avatarImage.setImageBitmap(ImageCodeClass.decodeCircular(picture));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra(StatusContract.Column_Tutor.NAME,
                        name);
                view.getContext().startActivity(intent);
            }
        });

    }

}
