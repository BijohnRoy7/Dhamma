package invenz.example.bijohn.dhamma.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import invenz.example.bijohn.dhamma.models.Event;

public class MySharedPreferences {

    public SharedPreferences sharedPreferences;

    public void setMySharedPreferences(Context context, String strKey, String strValue) {

        sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(strKey, strValue);
        editor.apply();
        editor.commit();
    }


    public String getMySharedPreferences(String key) {

        return sharedPreferences.getString(key, null);

    }


}
