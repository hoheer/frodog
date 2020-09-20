package com.example.frodog;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Check extends AppCompatActivity {
    String sort ="userid";
    EditText pet_name;
    long nowIndex;
    ArrayAdapter<String> arrayAdapter;
    Button save ;
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("나의 반려 동물");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView =findViewById(R.id.db_list_view_1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);
        save=findViewById(R.id.save_check);
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
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
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Check.this,Pro.class);
            startActivity(intent);
            finish();
        }
    });

    }
    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);




        Intent intent =new Intent(Check.this,EditDog.class);

        intent.putExtra("name",tempData[0].trim());
        intent.putExtra("p2",tempData[1].trim());
        intent.putExtra("p3",tempData[2].trim());
        intent.putExtra("p4",tempData[3].trim());
//        intent.putExtra("p5",tempData[4].trim());
        intent.putExtra("nowindex",nowIndex);
            //intent.putExtra("position",position);
        startActivity(intent);
        finish();
        }
    };
    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3] ;
            AlertDialog.Builder dialog = new AlertDialog.Builder(Check.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Check.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mDbOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            //setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Check.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                           // setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };
    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
      //  Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
            tempID = setTextLength(tempID,20);
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            tempName = setTextLength(tempName,20);
            String tempKind = iCursor.getString(iCursor.getColumnIndex("kind"));
            tempKind = setTextLength(tempKind,20);
            String tempGender = iCursor.getString(iCursor.getColumnIndex("gender"));
            tempGender = setTextLength(tempGender,20);
            String Result ="이름 " + tempID +"\n" +"품종 " + tempName  +"\n"+"생일 " +tempKind  +"\n"+ "성별 "+tempGender ;
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








}
