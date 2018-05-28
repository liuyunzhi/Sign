package com.cdut.sign.adapter;


import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.util.Note;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {

    private Context context;

    public NoteListAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return SignApplication.getNoteList().size();
    }

    @Override
    public Object getItem(int position) {
        return SignApplication.getNoteList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_note, null);
        //获取item对应的数据对象
        Note note = SignApplication.getNoteList().get(position);
        //初始化view
        TextView title = view.findViewById(R.id.item_note_title);
        TextView time = view.findViewById(R.id.item_note_time);
        //绑定数据到view
        title.setText(note.getTitle());
        time.setText(note.getTime());

        return view;
    }

}