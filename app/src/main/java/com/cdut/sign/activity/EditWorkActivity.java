package com.cdut.sign.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.cdut.sign.R;

public class EditWorkActivity extends Activity {
	
	private EditText edit;
	private Intent intent;
	private String content;
	private Calendar calendar;
	private String time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_work);
		
		intent = getIntent();
		edit = (EditText) findViewById(R.id.edit_work_edit);
		calendar = Calendar.getInstance();
		
		if (! intent.getCharSequenceExtra("content").equals(""))
		{
			edit.setText(intent.getCharSequenceExtra("content"));
		}
	}
	
	@Override
    public void onBackPressed() {
		content = edit.getText().toString();
		time = (calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE));
		intent.putExtra("content", content);
		intent.putExtra("time", time);
		if (intent.getBooleanExtra("isNew", true))
		{
			setResult(1, intent);
		}
		else
		{
			setResult(2, intent);
		}
		super.onBackPressed();
    }
}
