package com.cdut.sign.adapter;

import java.util.List;

import com.cdut.sign.R;
import com.cdut.sign.util.RecordGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordExpandableListAdapter extends BaseExpandableListAdapter {

    class Item {
        TextView groupDate;
    }

    class Child {
        TextView signCourse;
        ImageView signImage;
        ImageView signOutImage;
        TextView signTime;
        TextView signOutTime;
    }

    private Context context;
    private LayoutInflater itemInflater;
    private LayoutInflater childInflater;
    private List<RecordGroup> group;

    public RecordExpandableListAdapter(Context context, List<RecordGroup> group) {
        this.context = context;
        this.group = group;

        itemInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = childInflater.inflate(R.layout.child_sign_record, null);
            Child child = new Child();
            child.signCourse = convertView.findViewById(R.id.sign_course);
            child.signImage = convertView.findViewById(R.id.sign_image);
            child.signTime = convertView.findViewById(R.id.sign_time);
            child.signOutImage = convertView.findViewById(R.id.sign_out_image);
            child.signOutTime = convertView.findViewById(R.id.sign_out_time);

            child.signCourse.setText(group.get(groupPosition).getRecordChildList().get(childPosition).getSignCourse());
            child.signImage.setImageResource(group.get(groupPosition).getRecordChildList().get(childPosition).getSignImage());
            child.signTime.setText(group.get(groupPosition).getRecordChildList().get(childPosition).getSignTime());
            child.signOutImage.setImageResource(group.get(groupPosition).getRecordChildList().get(childPosition).getSignOutImage());
            child.signOutTime.setText(group.get(groupPosition).getRecordChildList().get(childPosition).getSignOutTime());
            convertView.setTag(child);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.get(groupPosition).getChildSize();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = itemInflater.inflate(R.layout.item_sign_record, null);
            Item item = new Item();
            item.groupDate = convertView.findViewById(R.id.group_date);
            item.groupDate.setText(group.get(groupPosition).getItemInfor().getGroupDate());
            convertView.setTag(item);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
