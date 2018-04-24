package com.cdut.sign.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cdut.sign.R;

public class SuccessActivity extends Activity {
	
	private TextView date,time,position;
	private Calendar calendar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		
		date = (TextView) findViewById(R.id.date_success);
		time = (TextView) findViewById(R.id.time_success);
		position = (TextView) findViewById(R.id.position_success);
		calendar = Calendar.getInstance();
		
		date.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE));
		time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
		Intent intent = getIntent();
		String city = intent.getStringExtra("city");
		String district = intent.getStringExtra("dtc");
		if (city != null && !city.equals("") && district != null && !district.equals(""))
		{
			position.setText(city + "  " + district);
		}
	}
}
