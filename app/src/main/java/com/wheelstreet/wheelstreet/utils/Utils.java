package com.wheelstreet.wheelstreet.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wheelstreet.wheelstreet.retrofit.RestClient;
import com.wheelstreet.wheelstreet.retrofit.SOSInterface;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class is the base application of the class.
 */

public class Utils {

    private static Context mContext = null;

    //Base url of the app
    private static final String BASE_URL = " https://api.wheelstreet.com/";

    /**
     * This method is used to create SOSinterface
     *
     * @return client
     */
    public static SOSInterface getSOService() {
        return RestClient.getClient(BASE_URL).create(SOSInterface.class);
    }

    /**
     * Public constructor that takes mContext for later use
     */
    public Utils(Context con) {
        mContext = con;
    }

    /**
     * Encode user email to use it as a Firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail", list and item "owner" value
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    //This is a method to Check if the device internet connection is currently on
    public static boolean isNetworkAvailable(Context loginScreen) {

        ConnectivityManager connectivityManager

                = (ConnectivityManager) loginScreen.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public static String dateFormat(String arrivalTime){
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = originalFormat.parse(arrivalTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }
}
