package com.example.frodog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        Log.e("디버깅", "알람실행" );
//        ((Calender)Calender.mContext).show();
    }
//        Date date = new Date();
//        mTime = mFormat.format(date);
//
//        mDialogContent = new StringBuffer();
//
//        dbHelper = MemoDBHelper.getInstance(this);
//        String[] params = {mTime};
//        Cursor cursor = (Cursor) dbHelper.getReadableDatabase().query(MemoContract.MemoEntry.TABLE_NAME, null, "date=?", params, null, null, null);
//        cursor.moveToFirst();
//
//
//        if (cursor != null && cursor.getCount() != 0)
//        {
//            if(cursor.getCount()==1)
//            {
//                mDialogContent.append(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
//            }
//            else
//            {
//                mDialogContent.append(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
//                mDialogContent.append(" 외 ");
//                mDialogContent.append(cursor.getCount()-1);
//                mDialogContent.append("개의 메모가 있습니다.");
//            }
//
//        }
//        else
//        {
//            mDialogContent.append("오늘의 일정이 없습니다.");
//        }
//        NotificationCompat.Builder builder =new NotificationCompat.Builder(this,"default");
//
//        builder.setSmallIcon(R.mipmap.ic_launcher_calendar);
//        builder.setContentTitle("오늘의 일정");
//        builder.setContentText(mDialogContent.toString());
//
//        Intent intent = new Intent(this,Calender.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentIntent(pendingIntent);
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
//                R.mipmap.ic_launcher_calendar);
//        builder.setLargeIcon(largeIcon);
//
//        builder.setAutoCancel(false);
//
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
//        }
//
//        manager.notify(1,builder.build());
//    }
}
