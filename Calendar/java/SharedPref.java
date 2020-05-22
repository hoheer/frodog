

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref
{
    private SharedPreferences mySharedPref;
    private Context context;


    public SharedPref(Context context)
    {
        mySharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }
    public void setNightModeState(boolean state)
    {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }

    public Boolean loadNightModeState()
    {
        return mySharedPref.getBoolean("NightMode",false);
    }

    public void setPurchasedTheme(boolean flag)
    {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("Purchased",flag);
        editor.apply();
    }

    public Boolean loadPuchasedTheme()
    {
        return mySharedPref.getBoolean("Purchased",false);
    }

    public void setAlarm(boolean flag)
    {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("AlarmOn",flag);
        editor.apply();
    }

    public Boolean loadAlarm()
    {
        return mySharedPref.getBoolean("AlarmOn",false);
    }

    public void setFontBold(boolean flag)
    {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("Font",flag);
        editor.apply();
    }

    public Boolean loadFont()
    {
        return mySharedPref.getBoolean("Font",false);
    }

}
