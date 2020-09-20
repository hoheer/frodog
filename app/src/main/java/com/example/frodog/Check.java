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
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Check extends AppCompatActivity {
    String sort ="userid";
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
        //상단바 설정
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("나의 반려 동물");


//리스트뷰에 보여지는 작업들
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


        //강아지 등록 하기 버튼
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Check.this,Pro.class);
            startActivity(intent);
            finish();
        }
    });

    }

    //리스트 item 클릭시 일어나는 행동 -> 수정하기(Edit_Dog)로 넘어가기 설정
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

        intent.putExtra("name",tempData[1].trim());
        intent.putExtra("kind",tempData[3].trim());
        intent.putExtra("birthday",tempData[5].trim());
        intent.putExtra("gender",tempData[7].trim());
        intent.putExtra("nowindex",nowIndex);
        startActivity(intent);
        finish();
        }
    };

    //길게 클릭시 해당 item을 삭제
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
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Check.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };


    //리스트뷰에 데이터베이스에 존재하는 값들을 입력후 보여줌
    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
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
