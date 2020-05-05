package com.example.frodog;

import android.content.Intent;
import android.media.MediaSession2;
import android.os.Bundle;
import android.widget.Toast;

import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.User;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback{

        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public  void onFailure(ErrorResult errorResult){
                    int result = errorResult.getErrorCode();

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(),"네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다. 다시 시도해 주세요:"+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }



                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해주세요"+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name",result.getNickname());
                    intent.putExtra("profile",result.getProfileImagePath());
                    String needScopeAutority ="";
                    if (result.getKakaoAccount().needsScopeAccountEmail()){
                        needScopeAutority =needScopeAutority +"이메일";
                    }
                    if(needScopeAutority.length() !=0){
                        if (needScopeAutority.charAt(0) == ','){
                            needScopeAutority = needScopeAutority.substring(2);
                        }
                        Toast.makeText(getApplicationContext(),needScopeAutority+"에 대한 권한이 허용되지 않았습니다.",Toast.LENGTH_SHORT).show();
                    }

                    if (result.getKakaoAccount().hasEmail() == OptionalBoolean.TRUE)
                            intent.putExtra("email",result.getKakaoAccount().getEmail());
                    else
                        intent.putExtra("email","none");
                    startActivity(intent);
                    finish();
                }

            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요:"+ exception.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
