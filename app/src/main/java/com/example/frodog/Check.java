package com.example.frodog;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Check extends Activity {
    String sort ="userid";
    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView =findViewById(R.id.db_list_view_1);
        listView.setAdapter(arrayAdapter);
       // listView.setOnItemClickListener(onClickListener);
       // listView.setOnItemLongClickListener(longClickListener);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        showDatabase(sort);
/*
        TextView UserName = findViewById(R.id.User_Name);
        TextView UserEmail = findViewById(R.id.User_Email);
        TextView Dogkind = findViewById(R.id.Dog_Kind);
        TextView Dogbir = findViewById(R.id.Dog_birthday);
        TextView Dogsex = findViewById(R.id.Dog_sex_check);
        Intent intent = getIntent();

        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("Email");
        String birthday = intent.getExtras().getString("birthday");
        String kind = intent.getExtras().getString("kind");
        String sex = intent.getExtras().getString("sex");


        UserName.setText(name);
        UserEmail.setText(email);
        Dogkind.setText(kind);
        Dogbir.setText(birthday);
        Dogsex.setText(sex);


 */


    }


    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
            tempID = setTextLength(tempID,10);
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            tempName = setTextLength(tempName,10);
            String tempAge = iCursor.getString(iCursor.getColumnIndex("age"));
            tempAge = setTextLength(tempAge,10);
            String tempGender = iCursor.getString(iCursor.getColumnIndex("gender"));
            tempGender = setTextLength(tempGender,10);

            String Result = tempID + tempName + tempAge + tempGender;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

/*
@Override
public void onBackPressed(){

}

 */
}
