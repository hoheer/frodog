package com.example.frodog;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calender extends AppCompatActivity
{
    public static Context mContext;
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private String mTime;
    private SharedPref sharedPref;
    private MemoDBHelper dbHelper;
    private StringBuffer mDialogContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        sharedPref = new SharedPref(this);
        setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        Date date = new Date();
        mTime = mFormat.format(date);


        mDialogContent = new StringBuffer();

        mContext = this;


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_today:
                        setFrag(0);
                        break;
                    case R.id.action_add:
                        setFrag(1);
                        break;
                    case R.id.action_setting:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        setFrag(0); // 첫 프래그먼트 화면 지정
    }

    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame, frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame, frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.Main_Frame, frag3);
                ft.commit();
                break;

        }
    }



    public void show()
    {
        if(!sharedPref.loadAlarm())
        {
            return;
        }
        mDialogContent.setLength(0);

        dbHelper = MemoDBHelper.getInstance(this);
        String[] params = {mTime};
        Cursor cursor = (Cursor) dbHelper.getReadableDatabase().query(MemoContract.MemoEntry.TABLE_NAME, null, "date=?", params, null, null, null);
        cursor.moveToFirst();


        if (cursor != null && cursor.getCount() != 0)
        {
            if (cursor.getCount() == 1)
            {
                mDialogContent.append(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
            } else
            {
                mDialogContent.append(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
                mDialogContent.append(" 외 ");
                mDialogContent.append(cursor.getCount() - 1);
                mDialogContent.append("개의 일정이 있습니다.");
            }

        } else
        {
            mDialogContent.append("오늘의 일정이 없습니다.");
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher_calendar);
        builder.setContentTitle("오늘의 일정");
        builder.setContentText(mDialogContent.toString());

        Intent intent = new Intent(this, Calender.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher_calendar);
        builder.setLargeIcon(largeIcon);

        builder.setAutoCancel(false);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_LOW));
            manager.getNotificationChannel("default").setVibrationPattern(new long[]{ 0 }); // 진동제거
            manager.getNotificationChannel("default").enableVibration(true);
        }


        manager.notify(1, builder.build());

    }

    public void hide()
    {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.getNotificationChannel("default");
        }

        manager.cancel(1);
    }


}
