package com.example.frodog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditDog extends AppCompatActivity implements View.OnClickListener {
    private EditText birtday,pet_name, kind ,editText;
    DatePickerDialog datePicker;
    private CheckBox boy,girl,noting;
    private Button edit;
    private DbOpenHelper mDbOpenHelper;
    private String ID,name,kind1;
    String gender=" ";
    int position;
    Long nowIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dog);

        edit=findViewById(R.id.Edit_Button);
        birtday=findViewById(R.id.Dog_Birthday_Text_1);
        pet_name=findViewById(R.id.Pet_name_1);
        kind=findViewById(R.id.Dog_check_1);
        boy=findViewById(R.id.boy_1);
        girl=findViewById(R.id.girl_1);
        noting=findViewById(R.id.nothing_1);
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        Intent intent=getIntent();

        editText = (EditText) findViewById(R.id.Dog_Birthday_Text_1); //날짜를 입력받는 edittext를 불러옴
        editText.setInputType(InputType.TYPE_NULL); //초기 입력값은 Null로 설정
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();    //클릭 리스너를 이용해, 캘런더를 불러오고 거기에서 년,월,일을 가져온다
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //datapickdialog를 불러와서 입력을 받고 그것을 보여주게 한다.
                datePicker = new DatePickerDialog(EditDog.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
                    }
                }, year, month, day); //선택시 년 월 일로 표시후 그걸 입력
                datePicker.show();


            }
        });


        String t1 =intent.getExtras().getString("name");
        String t2= intent.getExtras().getString("p2");
        String t3=intent.getExtras().getString("p3");
        String t4=intent.getExtras().getString("p4");
        nowIndex=intent.getExtras().getLong("nowindex");
        edit.setOnClickListener(this);

        pet_name.setOnClickListener(this);


        girl.setOnClickListener(this);


        boy.setOnClickListener(this);


        noting.setOnClickListener(this);

        position=intent.getExtras().getInt("position");

        pet_name.setText(t1);
        birtday.setText(t3);
        kind.setText(t2);


        if(t4.equals("수컷")){
            boy.setChecked(true);
        }
        else if(t4.equals("중성")){
            noting.setChecked(true);
        }
        else{
            girl.setChecked(true);

        }








    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Edit_Button:

                ID=pet_name.getText().toString();
                name=kind.getText().toString();
                kind1=birtday.getText().toString();
                //  kind1=kind.getSelectedItem().toString();
                mDbOpenHelper.updateColumn(nowIndex,ID,name,kind1,gender);
                pet_name.requestFocus();
                pet_name.setCursorVisible(true);
                Intent intent =new Intent(EditDog.this,Check.class);
                mDbOpenHelper.close();
                startActivity(intent);
                finish();
            break;


            case R.id.boy_1:
                girl.setChecked(false);
                noting.setChecked(false);
                gender = "수컷";
                break;

            case R.id.girl_1:
                boy.setChecked(false);
                noting.setChecked(false);
                gender = "암컷";
                break;
            case R.id.nothing_1:
                boy.setChecked(false);
                girl.setChecked(false);
                gender="중성";
                break;

        }

        }

    }
