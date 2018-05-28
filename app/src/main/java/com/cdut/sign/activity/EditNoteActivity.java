package com.cdut.sign.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.cdut.sign.R;

public class EditNoteActivity extends Activity {

    private EditText contentText;
    private EditText titleText;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        intent = getIntent();
        titleText = findViewById(R.id.note_title);
        contentText = findViewById(R.id.note_content);

        if (!intent.getBooleanExtra("isNew", true)) {
            titleText.setText(intent.getCharSequenceExtra("title"));
            contentText.setText(intent.getCharSequenceExtra("content"));
        }
    }

    @Override
    public void onBackPressed() {
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        Calendar calendar = Calendar.getInstance();
        String time = (calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE));
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("time", time);
        if (intent.getBooleanExtra("isNew", true)) {
            setResult(1, intent);
        } else {
            setResult(2, intent);
        }
        super.onBackPressed();
    }
}
