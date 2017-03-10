package com.eric.permissionplayground;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Eric on 2017/3/9.
 */

public class PermissionManager {
    private Activity activity;

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public boolean hasPermission(String permission) {
        return (ContextCompat.checkSelfPermission(this.activity, permission) == PackageManager.PERMISSION_GRANTED);
    }

    public boolean shouldShowRequestPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission);
    }

    public void requestPermission(String permission, int requestCode) {
        requestPermissions(new String[]{permission}, requestCode);
    }

    public void requestPermissions(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this.activity, permissions, requestCode);
    }
}
