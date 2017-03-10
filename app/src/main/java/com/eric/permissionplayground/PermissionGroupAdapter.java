package com.eric.permissionplayground;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eric on 2017/3/9.
 */

public class PermissionGroupAdapter extends BaseExpandableListAdapter {
    private LayoutInflater layoutInflater = null;
    private ArrayList<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>(15);

    public PermissionGroupAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addPermissionGroup(PermissionGroup group) {
        if(permissionGroups.contains(group))
            return;

        permissionGroups.add(group);
    }
    @Override
    public int getGroupCount() {
        return permissionGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition <0 || groupPosition >= permissionGroups.size())
            return 0;

        PermissionGroup group = permissionGroups.get(groupPosition);
        if(group == null) return 0;

        return group.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupPosition <0 || groupPosition >= permissionGroups.size())
            return null;

        return permissionGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition <0 || groupPosition >= permissionGroups.size())
            return null;

        PermissionGroup group = permissionGroups.get(groupPosition);
        if(group == null) return null;

        return group.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PermissionGroupViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new PermissionGroupViewHolder();
            convertView = layoutInflater.inflate(R.layout.permission_group, null);
            viewHolder.name = (TextView)convertView.findViewById(R.id.permission_group_name);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PermissionGroupViewHolder)convertView.getTag();
        }
        PermissionGroup group = this.permissionGroups.get(groupPosition);
        viewHolder.name.setText(group.getShortName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PermissionViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new PermissionViewHolder();
            convertView = this.layoutInflater.inflate(R.layout.permission_item, null);

            viewHolder.name = (TextView)convertView.findViewById(R.id.permission_name);
            viewHolder.status = (TextView)convertView.findViewById(R.id.permission_status);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PermissionViewHolder)convertView.getTag();
        }

        PermissionGroup group = this.permissionGroups.get(groupPosition);
        PermissionItem permission = group.get(childPosition);
        viewHolder.name.setText(permission.getShortName());
        if(permission.isGranted()) {
            int color = Color.rgb(100, 200, 100);
            viewHolder.name.setTextColor(color);
            viewHolder.status.setText(R.string.permission_status_granted);
            viewHolder.status.setTextColor(color);
        }
        else {
            viewHolder.name.setTextColor(Color.GRAY);
            viewHolder.status.setText(R.string.permission_status_denied);
            viewHolder.status.setTextColor(Color.GRAY);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public final static class PermissionViewHolder{
        public TextView name;
        public TextView status;
    }

    public final static class PermissionGroupViewHolder{
        public TextView name;
    }
}
