package com.example.frodog;

import android.provider.BaseColumns;


public final class MyPet_DB {
    public static final class CreateDB implements BaseColumns {
        public static final String USERID = "userid";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String GENDER = "gender";
        public static final String MyPet_table = "usertable";
        public static final String _CREATE0 = "create table if not exists "+MyPet_table+"("
                +_ID+" integer primary key autoincrement, "
                +USERID+" text not null , "
                +NAME+" text not null , "
                +AGE+" integer not null , "
                +GENDER+ " text not null );";
    }


}
