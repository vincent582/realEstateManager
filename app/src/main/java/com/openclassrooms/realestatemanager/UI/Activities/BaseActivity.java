package com.openclassrooms.realestatemanager.UI.Activities;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import pub.devrel.easypermissions.EasyPermissions;
import static com.openclassrooms.realestatemanager.UI.Fragment.Profile.ProfileFragment.CURRENT_USER_ID;

public class BaseActivity extends AppCompatActivity {

    public static String PREFERENCES_NAME = "com.openclassrooms.realestatemanager.prefereneces";
    protected long currentUserId;
    protected boolean twoPanes;

    /**
     * Check if a currentUser is logged
     * @return
     */
    public boolean isCurrentUser(){
        if (currentUserId != 0) return true;
        return false;
    }

    /**
     * onResume Activity update currentUserId
     * cancel the displayed menu
     */
    @Override
    protected void onResume() {
        super.onResume();
        currentUserId = getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE).getLong(CURRENT_USER_ID,0);
        invalidateOptionsMenu();
    }

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
