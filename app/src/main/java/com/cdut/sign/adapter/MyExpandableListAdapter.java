package com.cdut.sign.adapter;

import java.util.List;

import com.cdut.sign.R;
import com.cdut.sign.util.ChildInfor;
import com.cdut.sign.util.GroupInfor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	
	class Item 
	{   
        ImageView groupImage;   
        TextView groupDate;   
    }
	
	class Child 
	{   
        ImageView signImage;
        ImageView signOutImage;
        TextView signDay; 
        TextView signOutDay; 
        TextView signTime; 
        TextView signOutTime;
    }
	
	private Context context;
	private LayoutInflater itemInflater;
	private LayoutInflater childInflater;
	private List<GroupInfor> group;

	public MyExpandableListAdapter(Context context, List<GroupInfor> group)
	{
		this.context = context;
		this.group = group;
		
		itemInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		childInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) 
	{
		return group.get(groupPosition).getChild(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) 
	{
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) 
	{
		Child child = null;  
        if (convertView == null) 
        {  
            convertView = childInflater.inflate(R.layout.child_attendance_record, null);
            child = new Child();  
            child.signImage = (ImageView) convertView.findViewById(R.id.sign_image);
            child.signOutImage = (ImageView) convertView.findViewById(R.id.sign_out_image);
            child.signDay = (TextView) convertView.findViewById(R.id.sign_day);
            child.signOutDay = (TextView) convertView.findViewById(R.id.sign_out_day);
            child.signTime = (TextView) convertView.findViewById(R.id.sign_time);
            child.signOutTime = (TextView) convertView.findViewById(R.id.sign_out_time);
            convertView.setTag(child);  
        } 
        else 
        {  
        	child = (Child) convertView.getTag();  
        }  
        GroupInfor infor = this.group.get(groupPosition);
        if (infor != null) 
        {  
        }  
        return convertView;  
	}

	@Override
	public int getChildrenCount(int groupPosition) 
	{
		return group.get(groupPosition).getChildSize();
	}

	@Override
	public Object getGroup(int groupPosition) 
	{
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() 
	{
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) 
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
	{
		Item item = null;
		
        if (convertView == null) 
        {   
        	convertView = itemInflater.inflate(R.layout.item_attendance_record, null);   
            item = new Item();   
            item.groupImage = (ImageView) convertView.findViewById(R.id.group_image);   
            item.groupDate = (TextView) convertView.findViewById(R.id.group_date);
            convertView.setTag(item);
        } 
        else 
        {   
        	item = (Item) convertView.getTag();   
        }   
        GroupInfor infor = this.group.get(groupPosition);   
        if (infor != null) 
        {   
        	//item.groupDate.setText(infor.getItemInfor().getGroupDate());   
            //Drawable draw = this.context.getResources().getDrawable(infor.getItemInfor().getGroupImage());   
            //item.groupImage.setImageDrawable(draw);   
        }
        
        return convertView;
	}

	@Override
	public boolean hasStableIds() 
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) 
	{
		return false;
	}

}
