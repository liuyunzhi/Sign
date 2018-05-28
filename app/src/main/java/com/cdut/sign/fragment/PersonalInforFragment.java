package com.cdut.sign.fragment;

import java.util.Map;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
//import com.cdut.sign.net.SendPost;
import com.cdut.sign.activity.AdActivity;
import com.cdut.sign.activity.LoginActivity;
import com.cdut.sign.net.SendPost;
import com.cdut.sign.util.Json;
import com.cdut.sign.util.MassageDailog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONException;

public class PersonalInforFragment extends Fragment {

    private Button modifyPasswordButton;
    private Button logoutButton;
    private ImageView imageHead;
    private TextView name;
    private TextView studentID;
    private TextView college;
    private TextView faculty;
    private TextView gender;
    private TextView IDCard;
    private TextView phone;
    private TableLayout modifyPassword;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String oldPassword;
    private String newPassword;
    private Handler handler;
    private Map<String, String> personInfoMap;
    private SignApplication signApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        dialogBuilder = new AlertDialog.Builder(getActivity());
        handler = new Handler();

        modifyPasswordButton = view.findViewById(R.id.button_person_info);
        logoutButton = view.findViewById(R.id.logout_button);
        imageHead = view.findViewById(R.id.image_head_person_info);
        name = view.findViewById(R.id.name_person_info);
        studentID = view.findViewById(R.id.student_id);
        college = view.findViewById(R.id.college_person_info);
        faculty = view.findViewById(R.id.faculty_person_info);
        gender = view.findViewById(R.id.gender);
        IDCard = view.findViewById(R.id.id_card);
        phone = view.findViewById(R.id.phone);

        signApplication = (SignApplication) getActivity().getApplication();
        personInfoMap = signApplication.getPersonInfoMap();

        name.setText(personInfoMap.get("name"));
        studentID.setText(personInfoMap.get("studentId"));
        gender.setText(personInfoMap.get("gender"));
        IDCard.setText(personInfoMap.get("personId"));
        college.setText(personInfoMap.get("college"));
        faculty.setText(personInfoMap.get("faculty"));
        phone.setText(personInfoMap.get("phone"));

        // 为“修改密码”按钮绑定事件监听器
        modifyPasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPassword = (TableLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_modify_password, null);
                dialogBuilder.setTitle("修改密码");
                dialogBuilder.setView(modifyPassword);
                dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText oldPasswordText = modifyPassword.findViewById(R.id.old_password);
                        EditText newPasswordText = modifyPassword.findViewById(R.id.new_password);
                        EditText confirmPasswordText = modifyPassword.findViewById(R.id.confirm_password);
                        if (newPasswordText.getText().toString().equals(confirmPasswordText.getText().toString())) {
                            oldPassword = oldPasswordText.getText().toString();
                            newPassword = newPasswordText.getText().toString();
                            new modifyPasswordThread().start();
                        } else {
                            Toast.makeText(getContext(), "新密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                            newPasswordText.setText("");
                            confirmPasswordText.setText("");
                        }
                    }
                });
                dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.create();
                dialog = dialogBuilder.show();
            }
        });

        // 为“修改密码”按钮绑定事件监听器
        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定要退出登录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        return view;
    }

    /**
     * 修改密码请求线程
     */
    private class modifyPasswordThread extends Thread {

        @Override
        public void run() {
            // 请求当前课程
            String params = "params={\"account\":\"" + personInfoMap.get("studentId") + "\",\"old\":\"" + oldPassword + "\",\"new\":\"" + newPassword + "\"}";
            String jsonString = SendPost.executeHttpPost(signApplication.getApiUrl(), "modifyPasword", params);
            try {
                Json response = new Json(jsonString);
                if (response.get("code").equals("000000")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MassageDailog.showDialog(getContext(), "密码修改成功");
                            dialog.cancel();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MassageDailog.showDialog(getContext(), "密码修改失败");
                            dialog.cancel();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
