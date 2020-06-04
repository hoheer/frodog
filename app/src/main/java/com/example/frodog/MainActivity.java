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
    DatePickerDialog datePicker;
    EditText editText;
    Adapter sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        TextView nickname = findViewById(R.id.Sign_Nickname);
        Nickname =intent.getStringExtra("name"); //카카오 api를 통해 사용자의 이름을 가져옴
        nickname.setText(Nickname);
        Email =intent.getStringExtra("email"); // 카카오 api를 통해 사용자의 이메일을 가져옴
        TextView email =findViewById(R.id.Sign_Email);
        email.setText(Email);

        Button Logout =findViewById(R.id.logout); //로그아웃
        Button Sign_out=findViewById(R.id.Button_Sign_Out); // 회원탈퇴
        Button Map=findViewById(R.id.Button_Map);//지도



       //강아지 종류 선택 spinner 사용
        //강아지 종류 values/kind.xml에 위치
       final Spinner spinner=(Spinner)findViewById(R.id.Dog_check); //spinner 를사용하여 목록을 불러오고 선택하는 방식
        sAdapter = ArrayAdapter.createFromResource(this,R.array.kind,android.R.layout.simple_spinner_dropdown_item);//spinner의 밑으로 스크로를 내리를 레이아웃을 가져와서 목록을 띄우게함
        spinner.setAdapter((SpinnerAdapter) sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템을 선택했을때 추가적인 행동 밑에 주석은 선택시 아이템에 대한 toast 메세지를 생성
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MainActivity.this,
                 //       (CharSequence) sAdapter.getItem(position), Toast.LENGTH_SHORT).show();





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //생일 선택
        editText=(EditText)findViewById(R.id.Dog_Birthday_Text); //날짜를 입력받는 edittext를 불러옴
        editText.setInputType(InputType.TYPE_NULL); //초기 입력값은 Null로 설정
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal =Calendar.getInstance();    //클릭 리스너를 이용해, 캘런더를 불러오고 거기에서 년,월,일을 가져온다
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //datapickdialog를 불러와서 입력을 받고 그것을 보여주게 한다.
                datePicker=new DatePickerDialog(MainActivity.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(year+"년" + (monthOfYear + 1) + "월" +dayOfMonth + "일" );
                    }
                },year,month,day); //선택시 년 월 일로 표시후 그걸 입력
        datePicker.show();



            }
        });

        //체크 박스 선택으로 강아지의 성별을 체크 하는 방식
        /*findViewById(R.id.Dog_man).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked(v);
            }
        });
        findViewById(R.id.girl).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked(v);
            }
        });
        */



        //저장후 엑티비티





        //지도로 넘어가는 버튼
        //지도는 에뮬로 돌렸을시 작동이 안댐 폰으로 구동시만 작동함.
        Map.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Map.class);
                startActivity(intent);
            }
        });



        //로그아웃
        Logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"정상적으로 로그아웃되었습니다.",Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent= new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });



        //회원탈퇴
        Sign_out.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("탈퇴하시겠습니까")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    //로그인 세션이 닫혀있어 못들어갈 시
                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        Toast.makeText(getApplicationContext(),"로그인 세션이 닫혔습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(MainActivity.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    // 카카오에 가입이 되어 있지 않을시
                                    @Override
                                    public void onNotSignedUp(){
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    // 회원 탈퇴 실패시
                                    @Override
                                    public void onFailure(ErrorResult errorResult){

                                        int result =errorResult.getErrorCode();

                                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                            Toast.makeText(getApplicationContext(),"네트워크 여결이 원활하지 않습니다. 다시 시도해 주세요",Toast.LENGTH_SHORT).show();


                                        }else {
                                            Toast.makeText(getApplicationContext(),"회원탈퇴에 실패하셨습니다 다시 시도해주세요",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    //정상적으로 회원탈퇴가 되었을 시
                                    @Override
                                    public void onSuccess(Long result) {
                                        Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
        dialog.dismiss();







                                }
                            })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        }
            });




        }

//체크박스 검사
    /*
    public String checked(View v){
        // TODO Auto-generated method stub

        CheckBox b1 = findViewById(R.id.Dog_man);
        CheckBox b2 = findViewById(R.id.girl);
        String t1="";
        if(b1.isChecked()){
            t1="수컷";
            b2.setChecked(false);
        }
        if(b2.isChecked()){
            t1="암컷";
            b1.setChecked(false);
        }
        return t1;
    }
*/
}



