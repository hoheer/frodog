package com.example.frodog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Pro extends AppCompatActivity implements View.OnClickListener {
    ImageView profile_image;
    String Nickname;
    String Email;
    DatePickerDialog datePicker;
    EditText editText ;
    Adapter sAdapter;
    CheckBox boy;
    CheckBox girl;
    CheckBox nothing;
    String kind1;


    private static final int REQUEST_TAKE_ALBUM = 1111;
    private static final int REQUEST_IMAGE_CROP = 2222;
    private String mCurrentPhotoPath;
    private  Uri PhotoURI, albumURI;


    //데이터베이스 함수
    Button Save;
    EditText pet_name ,kind_of_dog;

    long nowIndex;
    String ID;
    String name;
    String kind;
    String gender = "";
    String sort = "userid";
    String image;
    String Profile;
    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("나의 반려 동물 등록");


        Intent intent = getIntent();
       // TextView nickname = findViewById(R.id.Sign_Nickname);
        // Nickname =intent.getStringExtra("name"); //카카오 api를 통해 사용자의 이름을 가져옴
       // Nickname = intent.getExtras().getString("Name");
       // nickname.setText(Nickname);
        // Email =intent.getStringExtra("email"); // 카카오 api를 통해 사용자의 이메일을 가져옴
      //  Email = intent.getExtras().getString("Email");
       // TextView email = findViewById(R.id.Sign_Email);
       // email.setText(Email);

      //  Button Logout = findViewById(R.id.logout); //로그아웃
      //  Button Sign_out = findViewById(R.id.Button_Sign_Out); // 회원탈퇴
      //  Button Map = findViewById(R.id.Button_Map);//지도
        //저장
        Save=findViewById(R.id.Save_Button);
        Save.setOnClickListener(this);
        //품종
        kind_of_dog=findViewById(R.id.Dog_check);
        kind_of_dog.setOnClickListener(this);
        //이름
        pet_name=findViewById(R.id.Pet_name);
        pet_name.setOnClickListener(this);
        //체크박스
        girl =findViewById(R.id.girl);
        girl.setOnClickListener(this);
    //체크박스
        boy= findViewById(R.id.boy);
        boy.setOnClickListener(this);
    //체크박스
        nothing=findViewById(R.id.nothing);
        nothing.setOnClickListener(this);



        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
       // ListView listView =findViewById(R.id.db_list_view);
       // listView.setAdapter(arrayAdapter);
      //  listView.setOnItemClickListener(onClickListener);
       // listView.setOnItemLongClickListener(longClickListener);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();



        //강아지 종류 선택 spinner 사용
        //강아지 종류 values/kind.xml에 위치
       /* final Spinner spinner = (Spinner) findViewById(R.id.Dog_check); //spinner 를사용하여 목록을 불러오고 선택하는 방식
        sAdapter = ArrayAdapter.createFromResource(this, R.array.kind, android.R.layout.simple_spinner_dropdown_item);//spinner의 밑으로 스크로를 내리를 레이아웃을 가져와서 목록을 띄우게함
        spinner.setAdapter((SpinnerAdapter) sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템을 선택했을때 추가적인 행동 밑에 주석은 선택시 아이템에 대한 toast 메세지를 생성
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind1=spinner.getSelectedItem().toString();

                // Toast.makeText(Pro.this,
                //       (CharSequence) sAdapter.getItem(position), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        */

        //생일 선택
        editText = (EditText) findViewById(R.id.Dog_Birthday_Text); //날짜를 입력받는 edittext를 불러옴
        editText.setInputType(InputType.TYPE_NULL); //초기 입력값은 Null로 설정
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();    //클릭 리스너를 이용해, 캘런더를 불러오고 거기에서 년,월,일을 가져온다
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //datapickdialog를 불러와서 입력을 받고 그것을 보여주게 한다.
                datePicker = new DatePickerDialog(Pro.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
                    }
                }, year, month, day); //선택시 년 월 일로 표시후 그걸 입력
                datePicker.show();


            }
        });


        //저장후 엑티비티
/*
        //버튼을 생성하여서 입력받은 정보들을 다음 액티비티로 넘기는 작업
        Button save = findViewById(R.id.Save_Button);
        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Check.class);//넘어가는 액티비티의 class
                intent.putExtra("name", Nickname);
                intent.putExtra("Email", Email);
                intent.putExtra("birthday", editText.getText().toString());
                intent.putExtra("kind", spinner.getSelectedItem().toString()); //유저가 선택한 아이템을 불러와서 string으로 변환하여 넘김
                intent.putExtra("sex", checked(v));
                startActivity(intent);
                finish();

            }
        });

 */
        //프로필 사진

        profile_image=findViewById(R.id.Dog_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();


            }
        });



/*
        //지도로 넘어가는 버튼
        //지도는 에뮬로 돌렸을시 작동이 안댐 폰으로 구동시만 작동함.
        Map.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pro.this, Map.class);
                startActivity(intent);
            }
        });


        //로그아웃
        Logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(Pro.this, Login.class);
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
                new AlertDialog.Builder(Pro.this)
                        .setMessage("탈퇴하시겠습니까")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    //로그인 세션이 닫혀있어 못들어갈 시
                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Pro.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    // 카카오에 가입이 되어 있지 않을시
                                    @Override
                                    public void onNotSignedUp() {
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Pro.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    // 회원 탈퇴 실패시
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {

                                        int result = errorResult.getErrorCode();

                                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                            Toast.makeText(getApplicationContext(), "네트워크 여결이 원활하지 않습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();


                                        } else {
                                            Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하셨습니다 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    //정상적으로 회원탈퇴가 되었을 시
                                    @Override
                                    public void onSuccess(Long result) {
                                        Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Pro.this, Login.class);
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



 */
    }
    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            pet_name.setText(tempData[0].trim());
            pet_name.setText(tempData[1].trim());
            pet_name.setText(tempData[2].trim());
            //pet_name.setText(tempData[3].trim());
            if(tempData[3].trim().equals("수컷")){
                boy.setChecked(true);
                gender = "수컷";
            }
            else if(tempData[3].trim().equals("중성")){
                nothing.setChecked(true);
                gender="중성";
            }
            else{
                girl.setChecked(true);
                gender = "암컷";
            }


            Save.setEnabled(false);

        }
    };
    public void setInsertMode(){
        Save.setText("");
        pet_name.setText("");
        editText.setText("");
        kind_of_dog.setText("");
        boy.setChecked(false);
        girl.setChecked(false);
        nothing.setChecked(false);
        Save.setEnabled(true);
       // btn_Update.setEnabled(false);
    }
    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3];
            AlertDialog.Builder dialog = new AlertDialog.Builder(Pro.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Pro.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mDbOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Pro.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                            setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };
    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
            tempID = setTextLength(tempID,10);
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            tempName = setTextLength(tempName,10);
            String tempAge = iCursor.getString(iCursor.getColumnIndex("age"));
            tempAge = setTextLength(tempAge,10);
            String tempGender = iCursor.getString(iCursor.getColumnIndex("gender"));
            tempGender = setTextLength(tempGender,10);

            String Result = tempID + tempName + tempAge + tempGender;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
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
                            albumURI=Uri.fromFile(ablumFile);
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
                    profile_image.setImageURI(PhotoURI);
                }
                break;

        }


    }
    /*
    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))){
                new AlertDialog.Builder(this).setTitle("알림").setMessage("저장소 권한이 거부되었습니다.").setNeutralButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package: " + getPackageName()));
                        startActivity(intent);
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setCancelable(false).create().show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }
    //카메라 권한
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] < 0) {
                        Toast.makeText(Pro.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                break;
        }
    }

     */


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Save_Button:
                ID=pet_name.getText().toString();
                name=kind_of_dog.getText().toString();
                kind=editText.getText().toString();
               // Profile=profile_image.toString();


                mDbOpenHelper.open();
              mDbOpenHelper.insertColumn(ID, name, kind,gender);
               //mDbOpenHelper.deleteAllColumns();
              //  showDatabase(sort);
               // setInsertMode();
                pet_name.requestFocus();
                pet_name.setCursorVisible(true);
                mDbOpenHelper.close();
                Intent intent =new Intent(Pro.this,Check.class);
                startActivity(intent);
                finish();
                break;


            case R.id.boy:
                girl.setChecked(false);
                nothing.setChecked(false);
                gender = "수컷";
                break;

            case R.id.girl:
                boy.setChecked(false);
                nothing.setChecked(false);
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
