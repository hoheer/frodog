

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.GregorianCalendar;

public class BootReceiver extends BroadcastReceiver
{
    public AlarmManager alarmManager;


    @Override
    public void onReceive(Context context, Intent intent)
    {

//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
//        {
//            Log.e("디버깅", "부트 리시버 동작" );
//
//            //오후 10시 마다 어떤 행위를 처리하기 위해 Date값 생성
//            GregorianCalendar twopm = new GregorianCalendar();
//
//            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//            twopm.set(GregorianCalendar.HOUR_OF_DAY, 16);
//            twopm.set(GregorianCalendar.MINUTE, 52);
//            twopm.set(GregorianCalendar.SECOND, 0);
//            twopm.set(GregorianCalendar.MILLISECOND, 0);
//            if(twopm.before(new GregorianCalendar()))
//            {
//                twopm.add(GregorianCalendar.DAY_OF_MONTH, 1);
//            }
//            // PendingIntent를 통해서 setRepeating 해주는데 반복시간은 24시간마다 실행한다.
//            PendingIntent alarmReceiver;
//            alarmReceiver = PendingIntent.getBroadcast(context,1, new Intent(context,AlarmReceiver.class),0);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, twopm.getTimeInMillis(), 1000*60*60*6, alarmReceiver);
//            Log.e("디버깅", "알람전달" );
//        }
    }

}
