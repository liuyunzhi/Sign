package com.cdut.sign.fragment;

import java.util.Map;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.activity.LocationMapActivity;
//import com.cdut.sign.net.SendPost;
import com.cdut.sign.util.MassageDailog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInforFragment extends Fragment {

	private Button button;
	private Button locationMap;
	private ImageView imageHead;
	private TextView name;
	private TextView studentID;
	private TextView department;
	private TextView sex;
	private TextView IDCard;
	private TextView phone;
	private TextView position;
	private TextView content;
	private TableLayout modifyPassword;
	AlertDialog.Builder dialog;
	private Map<String, String> map;
	private SignApplication signApplication;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_personal_infor, container, false);
		dialog = new AlertDialog.Builder(getActivity());
		
		button = (Button) view.findViewById(R.id.button_personal_infor);
		locationMap = (Button) view.findViewById(R.id.location_map);
		imageHead = (ImageView) view.findViewById(R.id.image_head_personal_infor);
		name = (TextView) view.findViewById(R.id.name_personal_infor);
		studentID = (TextView) view.findViewById(R.id.student_id);
		department = (TextView) view.findViewById(R.id.department_personal_infor);
		sex = (TextView) view.findViewById(R.id.sex);
		IDCard = (TextView) view.findViewById(R.id.id_card);
		phone = (TextView) view.findViewById(R.id.phone);
		position = (TextView) view.findViewById(R.id.position);
		content = (TextView) view.findViewById(R.id.content);
		
		signApplication = (SignApplication)getActivity().getApplication();
		map = signApplication.getPersonInfoMap();
		
//		imageHead.setImageDrawable(map.get("img"));
//		name.setText(map.get("name"));
//		studentID.setText(map.get("uId"));
//		department.setText(map.get("company"));
//		sex.setText(map.get("sex"));
//		IDCard.setText(map.get("idNumber"));
//		phone.setText(map.get("mobile"));
//		position.setText(map.get("address"));
//		content.setText(map.get("content"));
		
		// 为“地图选点”按钮绑定事件监听器
		locationMap.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//跳转到地图界面
				Intent intent = new Intent();
				intent.putExtra("address", position.getText());
				intent.setClass(getActivity(), LocationMapActivity.class);
				startActivityForResult(intent, 0);
			}
		});
				
		// 为“修改密码”按钮绑定事件监听器
		button.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				modifyPassword = (TableLayout)getActivity().getLayoutInflater().inflate(R.layout.dialog_modify_password, null);
				dialog.setTitle("修改密码");
				dialog.setView(modifyPassword);
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						EditText oldPassword = (EditText) modifyPassword.findViewById(R.id.old_password);
						EditText newPassword = (EditText) modifyPassword.findViewById(R.id.new_password);
						EditText confirmPassword = (EditText) modifyPassword.findViewById(R.id.confirm_password);
						if (newPassword.getText().toString().equals(confirmPassword.getText().toString()))
						{
//							//访问服务器
//				        	Map<String, String> returnInfor = WebServicePost.executeHttpPost(oldPassword.getText().toString(), newPassword.getText().toString());
//				        	if (returnInfor.get("resCode") == 100)
//				        	{
//				        		MassageDailog.showDialog(getContext(), "密码修改成功");
//								dialog.dismiss();
//				        	}
//				        	else
//				        	{
//				        		MassageDailog.showDialog(getContext(), returnInfor.get("resMsg"));
//				        	}
							//仅供测试
							if (true)
				        	{
				        		MassageDailog.showDialog(getContext(), "密码修改成功");
								dialog.cancel();
				        	}
						}
						else
						{
							Toast.makeText(getContext(), "新密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
							newPassword.setText("");
							confirmPassword.setText("");
						}
					}
				});
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.dismiss();
					}
				});
				dialog.create();
				dialog.show();
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0 && resultCode == 0)
		{
			Bundle data = intent.getExtras();
			String resultAddress = data.getString("address");
			//上传服务器
			
			//显示
			position.setText(resultAddress);
		}
	}
}
