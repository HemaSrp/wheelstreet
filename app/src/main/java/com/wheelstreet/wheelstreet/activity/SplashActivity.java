package com.wheelstreet.wheelstreet.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.utils.SharedPrefManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    //Sharedpreferncemanager
    private SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        facebookKeyHashes();
         getSupportActionBar().hide();
        sharedPrefManager = new SharedPrefManager(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your activity on first time launch
                if(!sharedPrefManager.getFirstTimeLaunch()) {
                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }else{
                    //On second time launch this screen will be created
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void facebookKeyHashes() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    "com.wheelstreet.wheelstreet",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
