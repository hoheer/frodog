package com.example.frodog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class MainActivity extends Activity {
    String Nickname;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        Nickname =intent.getStringExtra("name"); //카카오 api를 통해 사용자의 이름을 가져옴
        Email =intent.getStringExtra("email"); // 카카오 api를 통해 사용자의 이메일을 가져옴
        //프로필 이동버튼
        Button profile =findViewById(R.id.Button_Profile);

        profile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Pro.class
                );
                intent.putExtra("Name",Nickname);
                intent.putExtra("Email",Email);
                startActivity(intent);
            }
        });
        //캘린더 이동 버튼
        Button calender =findViewById(R.id.Button_Calender);
        calender.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Calender.class);
                startActivity(intent);
            }
        });

//팝업창 테스트
        new AlertDialog.Builder(this) // TestActivity 부분에는 현재 Activity의 이름 입력.
                .setMessage("AlertDialog 테스트")     // 제목 부분 (직접 작성)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {      // 버튼1 (직접 작성)
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show(); // 실행할 코드
                        Intent pro =new Intent(MainActivity.this,Pro.class);
                        pro.putExtra("Name",Nickname);
                        pro.putExtra("Email",Email);
                        startActivity(pro);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {     // 버튼2 (직접 작성)
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "취소 누름", Toast.LENGTH_SHORT).show(); // 실행할 코드

                    }
                })
                .show();
    }
}