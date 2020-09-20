package com.example.frodog;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Pro extends AppCompatActivity implements View.OnClickListener {
    DatePickerDialog datePicker;
    EditText editText ;
    CheckBox boy,girl,nothing;
    //데이터베이스 함수
    Button Save;
    EditText pet_name ,kind_of_dog;
    String ID,name,kind;
    String gender = "";

    ArrayAdapter<String> arrayAdapter;

    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);



        //상단바 설정
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("나의 반려 동물 등록");


        //저장
        Save=findViewById(R.id.Save_Button);
        Save.setOnClickListener(this);
        //품종
        kind_of_dog=findViewById(R.id.Dog_check);
        kind_of_dog.setOnClickListener(this);
        //이름
        pet_name=findViewById(R.id.Pet_name);
        pet_name.setOnClickListener(this);
        //체크박스
        girl =findViewById(R.id.girl);
        girl.setOnClickListener(this);
    //체크박스
        boy= findViewById(R.id.boy);
        boy.setOnClickListener(this);
    //체크박스
        nothing=findViewById(R.id.nothing);
        nothing.setOnClickListener(this);



        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();




        //생일 선택
        editText = (EditText) findViewById(R.id.Dog_Birthday_Text); //날짜를 입력받는 edittext를 불러옴
        editText.setInputType(InputType.TYPE_NULL); //초기 입력값은 Null로 설정
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();    //클릭 리스너를 이용해, 캘런더를 불러오고 거기에서 년,월,일을 가져온다
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //datapickdialog를 불러와서 입력을 받고 그것을 보여주게 한다.
                datePicker = new DatePickerDialog(Pro.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
                    }
                }, year, month, day); //선택시 년 월 일로 표시후 그걸 입력
                datePicker.show();


            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Save_Button:
                ID=pet_name.getText().toString();  //강아지 이름
                name=kind_of_dog.getText().toString(); // 강아지 품종
                kind=editText.getText().toString(); // 강아지 생일
                mDbOpenHelper.open();
              mDbOpenHelper.insertColumn(ID, name, kind,gender);
             // mDbOpenHelper.deleteAllColumns();
                pet_name.requestFocus();
                pet_name.setCursorVisible(true);
                mDbOpenHelper.close();
                Intent intent =new Intent(Pro.this,Check.class);
                startActivity(intent);
                finish();
                break;
                //체크박스 설정들
            case R.id.boy:
                girl.setChecked(false);
                nothing.setChecked(false);
                gender = "수컷";
                break;

            case R.id.girl:
                boy.setChecked(false);
                nothing.setChecked(false);
                gender = "암컷";
                break;
            case R.id.nothing:
                boy.setChecked(false);
                girl.setChecked(false);
                gender="중성";
                break;

        }

    }

}
