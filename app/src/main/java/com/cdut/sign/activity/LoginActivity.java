package com.cdut.sign.activity;

import java.util.HashMap;
import java.util.Map;

import com.cdut.sign.App;
import com.cdut.sign.R;
//import com.cdut.sign.net.SendPost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	// 返回主线程更新数据
    private static Handler handler = new Handler();
    
	private Button login_button;
	private EditText login_count;
	private TextView login_password;
	private CheckBox remember_me;
	private CheckBox auto_login;
	//保存配置信息
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	//创建等待框
    private ProgressDialog dialog;
	//保存全局数据
    private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 记住我和自动登录的配置信息
		preferences = getSharedPreferences("Sign",MODE_PRIVATE);
		//全局数据
		app = (App)getApplication();
		
		if (preferences.getBoolean("auto_login", false)) 
		{
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		} 
		else 
		{
			setContentView(R.layout.activity_login);
			initView();
		}
	}

	//初始化姐界面试图
	private void initView()
	{
		login_button = (Button) findViewById(R.id.button_login);
		login_count = (EditText) findViewById(R.id.login_phone);
		login_password = (TextView) findViewById(R.id.login_password);
		remember_me = (CheckBox) findViewById(R.id.remenber_me);
		auto_login = (CheckBox) findViewById(R.id.auto_login);
		editor = preferences.edit();

		// 为“自动登录”绑定事件监听器(与“记住我”联动)
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked)
				{
					remember_me.setChecked(true);
				}
			}
		});

		// 为“登录”按钮绑定事件监听器
		login_button.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				String account = login_count.getText().toString();
				String password = login_password.getText().toString();
				boolean isRemenberMe = remember_me.isChecked();
				boolean isAutoLogin = auto_login.isChecked();

				//判断帐号是否为空
				if (account.trim().equals(""))
				{
					Toast.makeText(LoginActivity.this, "请输入帐号", Toast.LENGTH_SHORT).show();
					return;
				}
				//判断密码是否为空
				if (password.trim().equals(""))
				{
					Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
					return;
				}
				//是否“记住我”
				if (isRemenberMe)
				{
					editor.putString("account", account);
					editor.putString("Password", password);
					editor.commit();
				}
				//是否“自动登陆”
				if (isAutoLogin)
				{
					editor.putBoolean("auto_login", true);
					editor.commit();
				}
				// 提示框
	            dialog = new ProgressDialog(LoginActivity.this);
	            dialog.setTitle("提示");
	            dialog.setMessage("正在登陆，请稍后...");
	            dialog.setCancelable(false);
	            dialog.show();
				// 创建子线程，分别进行Get和Post传输
	            new Thread(new HttpClientThread()).start();
			}
		});
	}

	// 子线程接收数据，主线程修改数据
    public class HttpClientThread implements Runnable 
    {
        @Override
        public void run() 
        {
			// 返回的数据
            Map<String, String> returnInfor = new HashMap<>();
			// 访问服务器
//        	returnInfor = SendPost.executeHttpPost(login_count.getText().toString(), login_password.getText().toString());
			// 保存全局数据
            app.setPersonalInforMap(returnInfor);
			// 启动主页
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			// 更新UI组件（销毁对话框）
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }
}
