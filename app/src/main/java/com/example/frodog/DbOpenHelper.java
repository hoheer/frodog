package com.example.frodog;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
// 테이플 MyPet_table으로 변경
    private static final String DATABASE_NAME = "PetDB.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
                    db.execSQL(MYPetDB.CreateDB._CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MYPetDB.CreateDB.MyPet_table_1);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String userid, String name, String kind ,String gender ){
        ContentValues values = new ContentValues();
        values.put(MYPetDB.CreateDB.USERID, userid);
        values.put(MYPetDB.CreateDB.NAME, name);
        values.put(MYPetDB.CreateDB.KIND, kind);
        values.put(MYPetDB.CreateDB.GENDER, gender);
        return mDB.insert(MYPetDB.CreateDB.MyPet_table_1, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String userid, String name ,String kind, String gender){
        ContentValues values = new ContentValues();
        values.put(MYPetDB.CreateDB.USERID, userid);
        values.put(MYPetDB.CreateDB.NAME, name);
        values.put(MYPetDB.CreateDB.KIND, kind);
        values.put(MYPetDB.CreateDB.GENDER, gender);
        return mDB.update(MYPetDB.CreateDB.MyPet_table_1, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(MYPetDB.CreateDB.MyPet_table_1, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id){
        return mDB.delete(MYPetDB.CreateDB.MyPet_table_1, "_id="+id, null) > 0;
    }
    // Select DB
    public Cursor selectColumns(){
        return mDB.query(MYPetDB.CreateDB.MyPet_table_1, null,null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM user_table ORDER BY " + sort + ";", null);
        return c;
    }
}
