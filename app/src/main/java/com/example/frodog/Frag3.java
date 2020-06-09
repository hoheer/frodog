package com.example.frodog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class Frag3 extends PreferenceFragmentCompat
{
    private MemoDBHelper dbHelper;
    private View view;
    private Preference resetPreference;
    private AlertDialog.Builder builder;
    private SharedPref sharedPref;
    private SwitchPreferenceCompat alarmSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.settings_preference);


        dbHelper = MemoDBHelper.getInstance(getActivity());
        resetPreference = (Preference) findPreference("reset");
        sharedPref = new SharedPref(getActivity());
        alarmSwitch = (SwitchPreferenceCompat) findPreference("alarm_setting");


        // 액션바 이름
        androidx.appcompat.app.ActionBar actionBar = ((Calender) getActivity()).getSupportActionBar();
        actionBar.setTitle("설정");

        // 스위치 상태, 다이얼로그
        builder = new AlertDialog.Builder(getActivity());

        alarmSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if (alarmSwitch.isChecked())
                {
                    sharedPref.setAlarm(false);
                    alarmSwitch.setChecked(false);
                    ((Calender)Calender.mContext).hide();
                }
                else
                {
                    sharedPref.setAlarm(true);
                    alarmSwitch.setChecked(true);
                    ((Calender)Calender.mContext).show();
                }
                return false;
            }
        });





        // 일정 초기화
        resetPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {


                builder.setTitle("초기화");
                builder.setMessage("모든 일정을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        dbHelper.DropTable(db);
                        Toast.makeText(getActivity(), "모든 일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        ((Calender) Calender.mContext).show();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();

                return false;
            }
        });



    }
}
