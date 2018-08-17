package invenz.example.bijohn.dhamma.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import invenz.example.bijohn.dhamma.utils.IsInternetConnected;
import invenz.example.bijohn.dhamma.R;
import invenz.example.bijohn.dhamma.utils.MySharedPreferences;

public class SplashSactivity extends AppCompatActivity {
    private ImageView imageView;
    private Dialog selectLanguageDialog;
    private TextView btSelect;
    private RadioButton rbEnglishLang, rbBanglaLang;
    private String langShared;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sactivity);

        imageView = findViewById(R.id.isSplashS);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        imageView.startAnimation(animation);
        selectLanguageDialog = new Dialog(SplashSactivity.this);

        //SharedPreferences for checking if app is used first time
        SharedPreferences preferences = getSharedPreferences("sf", MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean("firstLogin", true);
        //Toast.makeText(this, ""+isFirst, Toast.LENGTH_SHORT).show();


        /*###################### Is Internet Connected ? ##########################*/
        IsInternetConnected isInternetConnected = new IsInternetConnected();

        if(!isInternetConnected.isConnected(SplashSactivity.this) && isFirst==true )
        {
            buildDialog(SplashSactivity.this).show();
        }

        else {

            //if app is once used second time it won't ask for internet
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstLogin", false);
            editor.commit();

            //splash work
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashSactivity.this, MainActivity.class));
                    finish();
                }
            }, 3000);
        }
    }




    /*################### METHOD FOR SHOWING DIALOG WHEN INTERNET IS NOT CONNECTED ###############################*/
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
