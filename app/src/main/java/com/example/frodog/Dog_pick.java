package com.example.frodog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Dog_pick extends AppCompatActivity {
    RadioGroup a1_group,a2_group,a3_group,a4_group,a5_group;
    boolean answer1,answer2,answer3,answer4,answer5;

    Button pick_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_pick);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("추천");
//첫번째 질문 boolean 값 넘기기
        a1_group=findViewById(R.id.question_1_button);
        a1_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.answer_1:
                        answer1 = true;
                        break;
                    case R.id.answer_2:
                        answer1 = false;

                        break;
                }
            }
        });
//두번째 질문 값 넘기기
        a2_group=findViewById(R.id.question_2_button);
        a2_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.answer_3:
                        answer2 = true;
                        break;
                    case R.id.answer_4:
                        answer2 = false;

                        break;
                }
            }
        });
 //세번째 질문 값 넘기기
        a3_group=findViewById(R.id.question_3_button);
        a3_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.answer_5:
                        answer3 = true;
                        break;
                    case R.id.answer_6:
                        answer3 = false;

                        break;
                }
            }
        });
//네번째 질문 값 넘기기
        a4_group=findViewById(R.id.question_4_button);
        a4_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.answer_7:
                        answer4 = true;
                        break;
                    case R.id.answer_8:
                        answer4 = false;

                        break;
                }
            }
        });
//다섯번째 질문 값 넘기기
        a5_group=findViewById(R.id.question_5_button);
        a5_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.answer_9:
                        answer5 = true;
                        break;
                    case R.id.answer_10:
                        answer5 = false;

                        break;
                }
            }
        });


        pick_save=findViewById(R.id.pick_save);
        pick_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dog_pick.this,test1.class);
                intent.putExtra("t1",answer1);
                intent.putExtra("t2",answer2);
                intent.putExtra("t3",answer3);
                intent.putExtra("t4",answer4);
                intent.putExtra("t5",answer5);
                startActivity(intent);
                finish();
            }
        });
    }
}
