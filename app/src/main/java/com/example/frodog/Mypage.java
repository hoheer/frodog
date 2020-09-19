package com.example.frodog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

public class Mypage extends AppCompatActivity {

    String Nickname,Email,sProfile;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("마이페이지");


        Intent intent = getIntent();
        TextView nickname = findViewById(R.id.Sign_Nickname);
        // Nickname =intent.getStringExtra("name"); //카카오 api를 통해 사용자의 이름을 가져옴
        Nickname=intent.getExtras().getString("Name");
        nickname.setText(Nickname);
        // Email =intent.getStringExtra("email"); // 카카오 api를 통해 사용자의 이메일을 가져옴
        Email=intent.getExtras().getString("Email");
        TextView email =findViewById(R.id.Sign_Email);
        email.setText(Email);

        image =findViewById(R.id.Profile_Image1); // 카카오 api를 통해 사용자의 프로필 사진을 가져옴
        sProfile=intent.getExtras().getString("profile");
        Glide.with(this).load(sProfile).circleCrop().into(image);

        Button mypet=findViewById(R.id.My_Pet_Page_Button); //반려견 설정

        mypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Mypage.this,Check.class);
                startActivity(intent);

            }
        });

        Button choice =findViewById(R.id.Pet_Choice_Button);//반려견 추천 버튼

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Mypage.this,Dog_pick.class);
                startActivity(intent);

            }
        });
        Button Logout =findViewById(R.id.logout); //로그아웃
        //로그아웃
        Logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"정상적으로 로그아웃되었습니다.",Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent= new Intent(Mypage.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });


        Button Sign_out=findViewById(R.id.Button_Sign_Out); // 회원탈퇴

        //회원탈퇴
        Sign_out.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Mypage.this)
                        .setMessage("탈퇴하시겠습니까")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    //로그인 세션이 닫혀있어 못들어갈 시
                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        Toast.makeText(getApplicationContext(),"로그인 세션이 닫혔습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(Mypage.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    // 카카오에 가입이 되어 있지 않을시
                                    @Override
                                    public void onNotSignedUp(){
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Mypage.this, Login.class);
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
                                        Intent intent = new Intent(Mypage.this, Login.class);
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
