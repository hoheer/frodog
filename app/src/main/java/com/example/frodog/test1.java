package com.example.frodog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class test1 extends AppCompatActivity {

    //boolean 값 넘기기 확인 테스트 창
    TextView t2,t3,t4,t5,t6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        Intent intent =getIntent();
       Boolean tt4= intent.getExtras().getBoolean("t1");
        Boolean tt5= intent.getExtras().getBoolean("t2");
        Boolean tt6= intent.getExtras().getBoolean("t3");
        Boolean tt7= intent.getExtras().getBoolean("t4");
        Boolean tt8= intent.getExtras().getBoolean("t5");

        t2=findViewById(R.id.test3);
        t2.setText(tt4.toString());

        t3=findViewById(R.id.test4);
        t3.setText(tt5.toString());
        t4=findViewById(R.id.test5);
        t4.setText(tt6.toString());
        t5=findViewById(R.id.test6);
        t5.setText(tt7.toString());
        t6=findViewById(R.id.test7);
        t6.setText(tt8.toString());
    }
}
