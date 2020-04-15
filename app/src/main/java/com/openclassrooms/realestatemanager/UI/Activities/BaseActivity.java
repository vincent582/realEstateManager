package com.openclassrooms.realestatemanager.UI.Activities;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

import pub.devrel.easypermissions.EasyPermissions;

import static com.openclassrooms.realestatemanager.UI.Fragment.Profile.ProfileFragment.CURRENT_USER_ID;

public class BaseActivity extends AppCompatActivity {

    protected long currentUserId;
    protected boolean twoPanes;

    public boolean isCurrentUser(){
        if (currentUserId != 0) return true;
        return false;
    }

    /**
     * Showing the right menu for Main Activity depends if User connected
     * and the twoPanes mode
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCurrentUser()) {
            if (twoPanes) {
                getMenuInflater().inflate(R.menu.activity_main_menu_two_panes_toolbar, menu);
            } else {
                getMenuInflater().inflate(R.menu.main_activity_menu_toolbar, menu);
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUserId = getPreferences(MODE_PRIVATE).getLong(CURRENT_USER_ID,0);
        invalidateOptionsMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
