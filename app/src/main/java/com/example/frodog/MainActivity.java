package com.example.frodog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    String Nickname;
    String Email;
    ImageView t1;
    String sProfile;
    String image;
    private DbOpenHelper mDbOpenHelper;

    private static final int REQUEST_TAKE_ALBUM = 1111;
    private static final int REQUEST_IMAGE_CROP = 2222;
    private String mCurrentPhotoPath;
    private  Uri PhotoURI, albumURI;

//backup_file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        t1 = findViewById(R.id.profile_iamge3);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();


            }
        });
        Nickname = intent.getStringExtra("name"); //카카오 api를 통해 사용자의 이름을 가져옴
        Email = intent.getStringExtra("email"); // 카카오 api를 통해 사용자의 이메일을 가져옴
        sProfile = intent.getStringExtra("profile");
       // mDbOpenHelper = new DbOpenHelper(this);
       // mDbOpenHelper.open();
       // Cursor cursor= mDbOpenHelper.selectColumns();
       //if(cursor != null){
       // image=cursor.getString(4);
     //   Glide.with(this).load(image).into(t1);








        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("메인 ");


        //프로필 이동버튼
        Button profile = findViewById(R.id.Button_Profile);

        profile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mypage.class
                );
                intent.putExtra("Name", Nickname);
                intent.putExtra("Email", Email);
                intent.putExtra("profile",sProfile);
                startActivity(intent);
            }
        });
        //캘린더 이동 버튼
        Button calender = findViewById(R.id.Button_Calender);
        calender.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calender.class);
                startActivity(intent);
            }
        });

//팝업창 테스트
        /*
        new AlertDialog.Builder(this) // TestActivity 부분에는 현재 Activity의 이름 입력.
                .setMessage("AlertDialog 테스트")     // 제목 부분 (직접 작성)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {      // 버튼1 (직접 작성)
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show(); // 실행할 코드
                        Intent pro =new Intent(MainActivity.this,Mypage.class);
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

         */
    }

    //file 생성
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  File createImageFile() throws IOException{
        String filename ="JPEG_" + ".jpg";
        File imagefile =null;
        File storageDir =new File(Environment.getExternalStorageDirectory() + "/Pictures");

        if (!storageDir.exists()){
            storageDir.mkdirs();
        }
        imagefile =new File(storageDir, filename);
        mCurrentPhotoPath =imagefile.getAbsolutePath();

        return  imagefile;
    }
    //앨범에서 불러오기
    private void getAlbum() {
        Intent getintent = new Intent(Intent.ACTION_PICK);
        getintent.setType("image/*");
        getintent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(getintent, REQUEST_TAKE_ALBUM);
    }
    //앨범에 저장
    private  void galleyAddPic(){
        Intent m=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f=new File(mCurrentPhotoPath);
        Uri curi=Uri.fromFile(f);
        m.setData(curi);
        sendBroadcast(m);
        Toast.makeText(this, "앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();;
    }
    // crop화
    public  void cropImage(){
        Intent c=new Intent("com.android.camera.action.CROP");
        c.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        c.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        c.setDataAndType(PhotoURI,"image/*");
        c.putExtra("aspectX",1);
        c.putExtra("aspectY",1);
        c.putExtra("outputX",1000);
        c.putExtra("outputY",1000);
        c.putExtra("scale",true);
        c.putExtra("output",albumURI);
        startActivityForResult(c,REQUEST_IMAGE_CROP);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {

            case  REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK){
                    if (data.getData() != null){
                        try {
                            File ablumFile = null;
                            ablumFile =createImageFile();
                            PhotoURI= data.getData();
                            albumURI= Uri.fromFile(ablumFile);
                            cropImage();
                        } catch (IOException e) {
                            Log.e("TAKE_ALBUM_",e.toString());

                        }
                    }
                }
                break;
            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK){
                    galleyAddPic();
                    t1.setImageURI(PhotoURI);
                }
                break;

        }




    }



}
