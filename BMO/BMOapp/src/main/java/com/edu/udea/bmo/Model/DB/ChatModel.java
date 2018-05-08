package com.edu.udea.bmo.Model.DB;

/**
 * Created by reale on 2/28/2017.
 */

public class ChatModel {
    public String message;
    public boolean isSend;
    public String image;


    public ChatModel(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }


    public ChatModel(String message, boolean isSend, String image) {
        this.message = message;
        this.isSend = isSend;
        this.image = image;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
