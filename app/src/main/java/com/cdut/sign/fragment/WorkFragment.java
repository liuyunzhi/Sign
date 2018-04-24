package com.cdut.sign.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cdut.sign.App;
import com.cdut.sign.R;
import com.cdut.sign.activity.EditWorkActivity;
import com.cdut.sign.adapter.WorkListAdapter;
import com.cdut.sign.util.WorkInfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class WorkFragment extends Fragment {
	
	private ListView listView;
	private Button button;
	private WorkListAdapter adapter;
	private Intent intent;
	private int itemPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.setWorkList(getList());
		adapter = new WorkListAdapter(getActivity());
		intent = new Intent(getActivity(), EditWorkActivity.class);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_work, container, false);
		listView = (ListView) view.findViewById(R.id.word_list_view);
		button = (Button) view.findViewById(R.id.word_button);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				itemPosition = position;
				List<WorkInfor> list = App.getWorkList();
				intent.putExtra("content", list.get(position).getContent());
				intent.putExtra("isNew", false);
				startActivityForResult(intent, 2);
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.putExtra("content", "");
				intent.putExtra("isNew", true);
				startActivityForResult(intent, 1);
			}
		});
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		//新建日志
		if (requestCode == 1 && resultCode == 1)
		{
			Bundle data = intent.getExtras();
			String resultContent = data.getString("content");
			String resultTime = data.getString("time");
			//上传服务器
			
			//显示
			WorkInfor workInfor = new WorkInfor();
			workInfor.setContent(resultContent);
	        workInfor.setTime(resultTime);
	        App.getWorkList().add(workInfor);
	        adapter.notifyDataSetChanged();
		}
		//修改日志
		if (requestCode == 2 && resultCode == 2)
		{
			Bundle data = intent.getExtras();
			String resultContent = data.getString("content");
			String resultTime = data.getString("time");
			//上传服务器
			
			//显示
	        App.getWorkList().get(itemPosition).setContent(resultContent);
	        App.getWorkList().get(itemPosition).setTime(resultTime);
	        adapter.notifyDataSetChanged();
		}
	}

	private List<WorkInfor> getList() {
		List<WorkInfor> list = new ArrayList<WorkInfor>();
		//仅供测试
		WorkInfor test = new WorkInfor();
        test.setContent("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
        test.setTime("2017/8/9");
        list.add(test);
        WorkInfor test1 = new WorkInfor();
        test1.setContent("效果好不好？效果好不好？效果好不好？效果好不好？效果好不好？效果好不好？效果好不好？效果好不好？");
        test1.setTime("2017/8/10");
        list.add(test1);
        
		return list;
	}
}
