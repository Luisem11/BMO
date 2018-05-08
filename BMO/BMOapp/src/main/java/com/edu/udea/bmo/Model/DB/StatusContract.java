package com.edu.udea.bmo.Model.DB;

import android.provider.BaseColumns;

public class StatusContract {
    public static final String DB_NAME = "bmo.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER = "user";
    public static final String TABLE_TUTOR = "tutor";
    public static final String TABLE_SUBJECT = "subject";
    public static final String TABLE_TUTOR_SUBJECT = "tutorsubject";
    public static final String TABLE_CHAT = "chat";
    public static final String TABLE_PROGRAM = "program";

    public class Column_User {
        public static final String NAME = "name";
        public static final String USER = "user";
        public static final String PASSWORD = "password";
        public static final String INSTITUTION = "institution";
        public static final String MAIL = "mail";
        public static final String PICTURE = "picture";
        public static final String STATE = "state";
        public static final String SESSION = "session";
    }

    public class Column_Tutor {
        public static final String NAME = "name";
        public static final String USER = "user";
        public static final String PASSWORD = "password";
        public static final String MAIL = "mail";
        public static final String SCORE = "score";
        public static final String INSTITUTION = "institution";
        public static final String PICTURE = "picture";
        public static final String STATE = "state";
        public static final String SESSION = "session";
        public static final String ID = BaseColumns._ID;
    }

    public class Column_Subject {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
    }

    public class Column_Chat {
        public static final String USER = "user";
        public static final String TUTOR = "tutor";
        public static final String MSG = "msg";
        public static final String ORDER = "orderc";
        public static final String SENDBY = "sendby";
        public static final String VIEWD = "viwed";
    }

    public class Column_Program {
        public static final String TUTOR = "tutor";
        public static final String SUBJECT = "subject";
        public static final String SCHEDULE = "schedule";
    }
}
