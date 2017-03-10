package com.eric.permissionplayground;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity {
    private static final int Permission_RequestCode = 2;
    private PermissionGroupAdapter permissionGroupAdapter;
    private PermissionManager permissionManager;
    private HashMap<String, PermissionItem> permissionsMap = new HashMap<String, PermissionItem>();
    private boolean processing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionManager = new PermissionManager(this);

        permissionGroupAdapter = new PermissionGroupAdapter(this);

        InitData();

        ExpandableListView listView = (ExpandableListView)findViewById(R.id.permissionListView);
        listView.setAdapter(permissionGroupAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(processing) return true;
                PermissionItem permissionItem = (PermissionItem)permissionGroupAdapter.getChild(groupPosition, childPosition);
                if(permissionItem != null && !permissionItem.isGranted()) {
                    permissionManager.requestPermission(permissionItem.getName(), Permission_RequestCode);
                    processing = true;
                }
                return true;
            }
        });
    }

    private void refreshPermissionStatus(PermissionGroup group) {
        PermissionItem permissionItem = null;
        for(int i = 0; i < group.size(); ++i) {
            permissionItem = group.get(i);
            if(permissionItem != null) {
                permissionItem.setGranted(permissionManager.hasPermission(permissionItem.getName()));
            }
        }
    }
    private void addPermissionInGroup(PermissionGroup permissionGroup, String permission, int apiLevel) {
        if(Build.VERSION.SDK_INT < apiLevel)
            return;

        PermissionItem permissionItem = permissionGroup.add(permission, apiLevel);
        permissionsMap.put(permissionItem.getName(), permissionItem);
    }

    private void addPermissionInGroup(PermissionGroup permissionGroup, String permission) {
        this.addPermissionInGroup(permissionGroup, permission, 1);
    }

    private void InitData() {
        PermissionGroup permissionGroup = null;

        //CALENDAR
        permissionGroup = new PermissionGroup(Manifest.permission_group.CALENDAR);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_CALENDAR);

        addPermissionInGroup(permissionGroup, Manifest.permission.WRITE_CALENDAR);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //CAMERA
        permissionGroup = new PermissionGroup(Manifest.permission_group.CAMERA);

        addPermissionInGroup(permissionGroup, Manifest.permission.CAMERA);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //CONTACTS
        permissionGroup = new PermissionGroup(Manifest.permission_group.CONTACTS);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_CONTACTS);

        addPermissionInGroup(permissionGroup, Manifest.permission.WRITE_CONTACTS);

        addPermissionInGroup(permissionGroup, Manifest.permission.GET_ACCOUNTS);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //LOCATION
        permissionGroup = new PermissionGroup(Manifest.permission_group.LOCATION);

        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_FINE_LOCATION);

        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //MICROPHONE
        permissionGroup = new PermissionGroup(Manifest.permission_group.MICROPHONE);

        addPermissionInGroup(permissionGroup, Manifest.permission.RECORD_AUDIO);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //PHONE
        permissionGroup = new PermissionGroup(Manifest.permission_group.PHONE);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_PHONE_STATE);

        addPermissionInGroup(permissionGroup, Manifest.permission.CALL_PHONE);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_CALL_LOG, 16);

        addPermissionInGroup(permissionGroup, Manifest.permission.WRITE_CALL_LOG, 16);

        addPermissionInGroup(permissionGroup, Manifest.permission.ADD_VOICEMAIL, 14);

        addPermissionInGroup(permissionGroup, Manifest.permission.USE_SIP, 9);

        addPermissionInGroup(permissionGroup, Manifest.permission.PROCESS_OUTGOING_CALLS);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //SENSORS
        permissionGroup = new PermissionGroup(Manifest.permission_group.SENSORS);

        addPermissionInGroup(permissionGroup, Manifest.permission.BODY_SENSORS, 20);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //SMS
        permissionGroup = new PermissionGroup(Manifest.permission_group.SMS);

        addPermissionInGroup(permissionGroup, Manifest.permission.SEND_SMS);

        addPermissionInGroup(permissionGroup, Manifest.permission.RECEIVE_SMS);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_SMS);

        addPermissionInGroup(permissionGroup, Manifest.permission.RECEIVE_WAP_PUSH);

        addPermissionInGroup(permissionGroup, Manifest.permission.RECEIVE_MMS);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //STORAGE
        permissionGroup = new PermissionGroup(Manifest.permission_group.STORAGE);

        addPermissionInGroup(permissionGroup, Manifest.permission.READ_EXTERNAL_STORAGE, 16);

        addPermissionInGroup(permissionGroup, Manifest.permission.WRITE_EXTERNAL_STORAGE, 4);

        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

        //Normal permission
        permissionGroup = new PermissionGroup(this.getResources().getString(R.string.permission_group_others));

        addPermissionInGroup(permissionGroup, Manifest.permission.INTERNET);
        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_NETWORK_STATE);
        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_NOTIFICATION_POLICY, 23);
        addPermissionInGroup(permissionGroup, Manifest.permission.ACCESS_WIFI_STATE);
        addPermissionInGroup(permissionGroup, Manifest.permission.CHANGE_NETWORK_STATE);
        addPermissionInGroup(permissionGroup, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE, 4);
        addPermissionInGroup(permissionGroup, Manifest.permission.CHANGE_WIFI_STATE);

        addPermissionInGroup(permissionGroup, Manifest.permission.BLUETOOTH);
        addPermissionInGroup(permissionGroup, Manifest.permission.BLUETOOTH_ADMIN);

        addPermissionInGroup(permissionGroup, Manifest.permission.BROADCAST_STICKY);

        addPermissionInGroup(permissionGroup, Manifest.permission.DISABLE_KEYGUARD);

        addPermissionInGroup(permissionGroup, Manifest.permission.EXPAND_STATUS_BAR);
        addPermissionInGroup(permissionGroup, Manifest.permission.GET_PACKAGE_SIZE);

        addPermissionInGroup(permissionGroup, Manifest.permission.INSTALL_SHORTCUT, 19);
        addPermissionInGroup(permissionGroup, Manifest.permission.UNINSTALL_SHORTCUT, 19);

        addPermissionInGroup(permissionGroup, Manifest.permission.KILL_BACKGROUND_PROCESSES, 8);
        addPermissionInGroup(permissionGroup, Manifest.permission.MODIFY_AUDIO_SETTINGS);

        addPermissionInGroup(permissionGroup, Manifest.permission.NFC, 9);
        addPermissionInGroup(permissionGroup, Manifest.permission.READ_SYNC_SETTINGS);
        addPermissionInGroup(permissionGroup, Manifest.permission.READ_SYNC_STATS);
        addPermissionInGroup(permissionGroup, Manifest.permission.RECEIVE_BOOT_COMPLETED);

        addPermissionInGroup(permissionGroup, Manifest.permission.REORDER_TASKS);
        addPermissionInGroup(permissionGroup, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, 23);
        addPermissionInGroup(permissionGroup, Manifest.permission.REQUEST_INSTALL_PACKAGES, 23);
        addPermissionInGroup(permissionGroup, Manifest.permission.SET_ALARM, 9);

        addPermissionInGroup(permissionGroup, Manifest.permission.SET_TIME_ZONE);
        addPermissionInGroup(permissionGroup, Manifest.permission.SET_WALLPAPER);
        addPermissionInGroup(permissionGroup, Manifest.permission.SET_WALLPAPER_HINTS);
        addPermissionInGroup(permissionGroup, Manifest.permission.TRANSMIT_IR, 19);

        addPermissionInGroup(permissionGroup, Manifest.permission.USE_FINGERPRINT, 23);
        addPermissionInGroup(permissionGroup, Manifest.permission.VIBRATE);
        addPermissionInGroup(permissionGroup, Manifest.permission.WAKE_LOCK);
        addPermissionInGroup(permissionGroup, Manifest.permission.WRITE_SYNC_SETTINGS);

        /*Invalid permissions
        addPermissionInGroup(permissionGroup, Manifest.permission.BATTERY_STATS);
        addPermissionInGroup(permissionGroup, Manifest.permission.CHANGE_CONFIGURATION);
        addPermissionInGroup(permissionGroup, Manifest.permission.GET_ACCOUNTS_PRIVILEGED, 23);
        */
        permissionGroupAdapter.addPermissionGroup(permissionGroup);
        refreshPermissionStatus(permissionGroup);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        if(requestCode == Permission_RequestCode) {
            PermissionItem permissionItem = permissionsMap.get(permissions[0]);
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refreshPermissionStatus(permissionItem.getPermissionGroup());
                permissionGroupAdapter.notifyDataSetChanged();
            }
            else {
                if(!permissionManager.shouldShowRequestPermissionRationale(permissionItem.getName())) {
                    String askPromting = String.format(this.getResources().getString(R.string.permission_ask), permissionItem.getShortName());
                    Toast.makeText(getApplicationContext(), askPromting, Toast.LENGTH_SHORT).show();
                }
            }

            processing = false;
        }
    }
}
