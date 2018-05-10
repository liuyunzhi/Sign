package com.cdut.sign.adapter;


import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.util.WorkInfor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkListAdapter extends BaseAdapter{
	
	private Context context;
	
	
	public WorkListAdapter(Context context)
	{
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return SignApplication.getWorkList().size();
	}

	@Override
	public Object getItem(int position) {
		return SignApplication.getWorkList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_work, null);
		//获取item对应的数据对象
		WorkInfor student = SignApplication.getWorkList().get(position);
		
		//初始化view
        TextView content=(TextView) view.findViewById(R.id.item_work_content);
        TextView time=(TextView) view.findViewById(R.id.item_work_time);
        //绑定数据到view
        content.setText(student.getContent());
        time.setText(student.getTime());
        
        return view;
	}

}