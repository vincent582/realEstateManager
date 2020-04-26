package com.openclassrooms.realestatemanager.UI.Activities;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity {

    public static String PREFERENCES_NAME = "com.openclassrooms.realestatemanager.prefereneces";
    protected boolean twoPanes;

    /**
     * Get the result of permission with EasyPermissions dependency.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
