package com.example.frodog;

import android.provider.BaseColumns;


public final class MYPetDB {
    public static final class CreateDB implements BaseColumns {
        public static final String USERID = "userid";
        public static final String NAME = "name";
        public static final String KIND = "kind";
        public static final String GENDER = "gender";
        public static final String MyPet_table_1 = "user_table";
        public static final String _CREATE1 = "create table if not exists "+MyPet_table_1+"("
                +_ID+" integer primary key autoincrement, "
                +USERID+" text not null , "
                +NAME+" text not null , "
                +KIND+" integer not null , "
                +GENDER+ " text not null );";
    }


}
