package com.edu.udea.bmo.View.User;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.edu.udea.bmo.Controller.CustomAdapter;
import com.edu.udea.bmo.Controller.ImageCodeClass;
import com.edu.udea.bmo.Model.DB.ChatModel;
import com.edu.udea.bmo.Model.DB.StatusContract;
import com.edu.udea.bmo.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;
    String image, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.list_of_message);
        editText = (EditText) findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton) findViewById(R.id.fab);


        Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.esq);
        image = ImageCodeClass.encodeToBase64(pic);


        //image = getIntent().getStringExtra(StatusContract.Column_Tutor.PICTURE);
        name = getIntent().getStringExtra(StatusContract.Column_Tutor.NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_program);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);



        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                ChatModel model = new ChatModel(text, true);
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);

                //remove user message
                editText.setText("");
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    private class SimsimiAPI extends AsyncTask<List<ChatModel>, Void, String> {
        String stream = null;
        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... params) {
            models = params[0];
            stream = "   ";
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {

            ChatModel chatModel = new ChatModel(s, false, image); //
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models, getApplicationContext());
            listView.setAdapter(adapter);
        }
    }
}
