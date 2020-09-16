package com.example.frodog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditDog extends AppCompatActivity implements View.OnClickListener {
    private EditText birtday,pet_name;
    private Spinner kind;
    private CheckBox boy,girl,noting;
    private Button edit;
    private DbOpenHelper mDbOpenHelper;
    private String ID,name,kind1;
    String gender="";
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
        boy=findViewById(R.id.boy);
        girl=findViewById(R.id.girl);
        noting=findViewById(R.id.nothing);
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        Intent intent=getIntent();




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


        if(t4.equals("수컷")){
            boy.setChecked(true);
        }
        else if(t4.equals("중성")){
            noting.setChecked(true);
        }
        else{
            girl.setChecked(true);

        }

        pet_name.setText(t1);
        birtday.setText(t3);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Edit_Button:

                ID=pet_name.getText().toString();
                name=birtday.getText().toString();
                //  kind1=kind.getSelectedItem().toString();
                kind1= "고양이";
                mDbOpenHelper.updateColumn(nowIndex,ID,name,kind1,gender);
                pet_name.requestFocus();
                pet_name.setCursorVisible(true);
                Intent intent =new Intent(EditDog.this,Check.class);
                startActivity(intent);
            break;
            case R.id.boy:
                girl.setChecked(false);
                noting.setChecked(false);
                gender = "수컷";
                break;

            case R.id.girl:
                boy.setChecked(false);
                noting.setChecked(false);
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
