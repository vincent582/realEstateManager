package com.openclassrooms.realestatemanager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    private Context appContext;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if(connectivityManager != null){
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        Boolean isActiveNetwork = activeNetworkInfo.isConnected();
        assertEquals(isActiveNetwork,Utils.isInternetAvailable(appContext));
    }

}
