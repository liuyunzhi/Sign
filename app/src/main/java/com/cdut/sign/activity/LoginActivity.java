package com.cdut.sign.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Toast;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.net.SendPost;
import com.cdut.sign.util.Json;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    //返回主线程,更新UI
    private Handler handler;
    //UI组件
    private Button login_button;
    private EditText login_account;
    private EditText login_password;
    private CheckBox remember_me;
    private CheckBox auto_login;
    private ProgressDialog dialog;
    //持久化登录配置信息
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //服务器
    private String account;
    private String password;
    private Json response;
    //全局数据
    private SignApplication signApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        //读取记住我和自动登录配置信息
        preferences = getSharedPreferences("Sign", MODE_PRIVATE);
        //全局数据
        signApplication = (SignApplication) getApplication();

        if (preferences.getBoolean("autoLogin", false)) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        } else {
            setContentView(R.layout.activity_login);
            initView();
        }
    }

    /**
     * 初始化UI界面
     */
    private void initView() {
        login_button = findViewById(R.id.button_login);
        login_account = findViewById(R.id.login_account);
        login_password = findViewById(R.id.login_password);
        remember_me = findViewById(R.id.remenber_me);
        auto_login = findViewById(R.id.auto_login);
        editor = preferences.edit();

        if (preferences.getBoolean("rememberMe", false)) {
            login_account.setText(preferences.getString("account",""));
            login_password.setText(preferences.getString("password",""));
            remember_me.setChecked(true);
            auto_login.setChecked(false);
        } else {
            remember_me.setChecked(false);
            auto_login.setChecked(false);
        }

        // 为“自动登录”绑定事件监听器(与“记住我”联动)
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    remember_me.setChecked(true);
                }
            }
        });

        // 为“登录”按钮绑定事件监听器
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                account = login_account.getText().toString();
                password = login_password.getText().toString();
                boolean isRememberMe = remember_me.isChecked();
                boolean isAutoLogin = auto_login.isChecked();

                //判断帐号是否为空
                if (account.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入帐号", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码是否为空
                if (password.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //是否“记住我”
                if (isRememberMe) {
                    editor.putBoolean("rememberMe", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.commit();
                }
                //是否“自动登陆”
                if (isAutoLogin) {
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                }
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();;
                new LoginValidateThread().start();
            }
        });
    }

    /**
     * 登录验证子进程
     */
    private class LoginValidateThread extends Thread {

        @Override
        public void run() {
            // 访问服务器
            String params = "params={\"account\":\""+ account +"\",\"password\":\""+ password +"\"}";
            String jsonString = SendPost.executeHttpPost(signApplication.getApiUrl(), "login", params);
            try {
                response = new Json(jsonString);
                if (response.get("code").equals("000000")) {
                    Json data = new Json(response.get("data"));
                    Map<String, String> personInfoMap = new HashMap<>();
                    personInfoMap.put("studentId", data.get("student_id"));
                    personInfoMap.put("personId", data.get("person_id"));
                    personInfoMap.put("name", data.get("name"));
                    personInfoMap.put("gender", data.get("gender"));
                    personInfoMap.put("college", data.get("college"));
                    personInfoMap.put("faculty", data.get("faculty"));
                    personInfoMap.put("phone", data.get("phone"));
                    // 保存全局数据
                    signApplication.setPersonInfoMap(personInfoMap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 启动主页
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                            LoginActivity.this.finish();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        private String message = response.get("message");

                        @Override
                        public void run() {
                            dialog.dismiss();
                            final AlertDialog alert = new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("提示")
                                    .setMessage(message)
                                    .create();
                            alert.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    alert.dismiss();
                                }
                            }, 2000);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
