package com.eric.permissionplayground;

import java.util.LinkedList;

/**
 * Created by Eric on 2017/3/9.
 */

public class PermissionGroup extends LinkedList<PermissionItem> {
    private String name;

    public PermissionGroup(String groupName) {
        this.name = groupName;
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

    @Override
    public boolean add(PermissionItem permissionItem) {
        permissionItem.setPermissionGroup(this);
        return super.add(permissionItem);
    }

    public PermissionItem add(String permissionName, boolean granted, int level) {
        PermissionItem permissionItem = new PermissionItem(this, permissionName, granted, level);
        super.add(permissionItem);

        return permissionItem;
    }

    public PermissionItem add(String permissionName, boolean granted) {
        return this.add(permissionName, granted, 1);
    }

    public PermissionItem add(String permissionName, int level) {
        return this.add(permissionName, false, level);
    }

}
