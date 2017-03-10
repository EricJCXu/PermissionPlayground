package com.eric.permissionplayground;

/**
 * Created by Eric on 2017/3/9.
 */

public class PermissionItem {
    private PermissionGroup permissionGroup;
    private String name;
    private int apiLevel = 1;
    private boolean granted = false;

    public PermissionItem(PermissionGroup group, String name, boolean granted, int level) {
        this.permissionGroup = group;
        this.name = name;
        this.apiLevel = level;
        this.granted = granted;
    }

    public PermissionItem(PermissionGroup group, String name, boolean granted) {
        this(group, name, granted, 1);
    }

    public PermissionItem(PermissionGroup group, String name, int level) {
        this(group, name, false, level);
    }

    public PermissionItem(PermissionGroup group, String name) {
        this(group, name, false, 1);
    }

    public PermissionItem(String name, boolean granted) {
        this(null, name, granted, 1);
    }

    public PermissionItem(String name, int level) {
        this(null, name, false, 1);
    }

    public PermissionItem(String name) {
        this(name, false);
    }

    public String getShortName() {
        if(this.name == null) return null;
        int pos = this.name.lastIndexOf('.');
        if(pos < 0)
            return this.name;

        return this.name.substring(pos + 1);
    }

    public String getName() {
        return this.name;
    }

    public int getApiLevel() {
        return this.apiLevel;
    }

    public boolean isGranted() {
        return this.granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public PermissionGroup getPermissionGroup() {
        return this.permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup group) {
        this.permissionGroup = group;
    }
}
