package com.cdut.sign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cdut.sign.R;

import java.util.Calendar;

public class FailActivity extends Activity {

    private TextView description;
    private TextView date;
    private TextView time;
    private TextView position;
    private Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        description = findViewById(R.id.note_fail);
        date = findViewById(R.id.date_fail);
        time = findViewById(R.id.time_fail);
        position = findViewById(R.id.position_fail);
        calendar = Calendar.getInstance();

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");
        String city = intent.getStringExtra("city");
        String district = intent.getStringExtra("dtc");
        if (note != null && !note.equals("")) {
            description.setText(note);
        }
        date.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE));
        time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        if (city != null && !city.equals("") && district != null && !district.equals("")) {
            position.setText(city + "  " + district);
        }
    }
}