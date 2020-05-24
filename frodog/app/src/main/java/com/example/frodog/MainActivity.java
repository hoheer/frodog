package com.example.frodog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.Calendar;

@SuppressWarnings("ALL")
public class MainActivity extends Activity {



  /*  private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
*/
    String Nickname;
   // String Profile;
    String Email;
    DatePickerDialog datePicker;
    EditText editText;
    Adapter sAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getAppKeyHash();
        TextView nickname = findViewById(R.id.Sign_Nickname);
        // ImageView profile = findViewById(R.id.Sign_Profile);
        TextView email =findViewById(R.id.Sign_Email);
        Intent intent = getIntent();
        Nickname =intent.getStringExtra("name");
        //Profile = intent.getStringExtra("profile");
        Email =intent.getStringExtra("email");
        Button Logout =findViewById(R.id.logout); //로그아웃
        Button Sign_out=findViewById(R.id.Button_Sign_Out); // 회원탈퇴
        Button Map=findViewById(R.id.Button_Map);//지도
        nickname.setText(Nickname);
        email.setText(Email);
       // Glide.with(this).load(Profile).into(profile);
       //강아지 종류 선택
        Spinner spinner=(Spinner)findViewById(R.id.Dog_check); //spinner 를사용하여 목록을 불러오고 선택하는 방식
        sAdapter = ArrayAdapter.createFromResource(this,R.array.kind,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        editText=(EditText)findViewById(R.id.Dog_Birthday_Text);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal =Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

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







        //지도로 넘어가는 버튼
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
                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        Toast.makeText(getApplicationContext(),"로그인 세션이 닫혔습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(MainActivity.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onNotSignedUp(){
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(ErrorResult errorResult){

                                        int result =errorResult.getErrorCode();

                                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                            Toast.makeText(getApplicationContext(),"네트워크 여결이 원활하지 않습니다. 다시 시도해 주세요",Toast.LENGTH_SHORT).show();


                                        }else {
                                            Toast.makeText(getApplicationContext(),"회원탈퇴에 실패하셨습니다 다시 시도해주세요",Toast.LENGTH_SHORT).show();

                                        }
                                    }

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




}



