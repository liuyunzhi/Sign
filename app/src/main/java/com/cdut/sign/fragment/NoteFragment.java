package com.cdut.sign.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.activity.EditNoteActivity;
import com.cdut.sign.adapter.NoteListAdapter;
import com.cdut.sign.util.Note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class NoteFragment extends Fragment {

    private ListView listView;
    private Button button;
    private NoteListAdapter adapter;
    private Intent intent;
    private int itemPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignApplication.setNoteList(getList());
        adapter = new NoteListAdapter(getActivity());
        intent = new Intent(getActivity(), EditNoteActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        listView = view.findViewById(R.id.note_list_view);
        button = view.findViewById(R.id.add_note_button);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                List<Note> list = SignApplication.getNoteList();
                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("content", list.get(position).getContent());
                intent.putExtra("isNew", false);
                startActivityForResult(intent, 2);
            }
        });

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
        String resultTitle;
        String resultContent;
        String resultTime;
        //新建日志
        if (requestCode == 1 && resultCode == 1) {
            Bundle data = intent.getExtras();
            resultTitle = data.getString("title");
            resultContent = data.getString("content");
            resultTime = data.getString("time");
            //上传服务器

            //显示
            Note note = new Note(resultTitle, resultContent, resultTime);
            SignApplication.getNoteList().add(note);
            adapter.notifyDataSetChanged();
        }
        //修改日志
        if (requestCode == 2 && resultCode == 2) {
            Bundle data = intent.getExtras();
            resultTitle = data.getString("title");
            resultContent = data.getString("content");
            resultTime = data.getString("time");
            //上传服务器

            //显示
            SignApplication.getNoteList().get(itemPosition).setTitle(resultTitle);
            SignApplication.getNoteList().get(itemPosition).setContent(resultContent);
            SignApplication.getNoteList().get(itemPosition).setTime(resultTime);
            adapter.notifyDataSetChanged();
        }
    }

    private List<Note> getList() {
        List<Note> list = new ArrayList<Note>();
        //仅供测试
        Note test = new Note();
        test.setTitle("笔记1");
        test.setContent("Android是以Linux为内核的手机操作系统，其为Google公司的产品。");
        test.setTime("2018/5/15");
        list.add(test);
        Note test1 = new Note();
        test1.setTitle("笔记2");
        test1.setContent("Android一词最早出现于法国作家利尔亚当（Auguste Villiers de l'Isle-Adam）在1886年发表的科幻小说《未来夏娃》（L'ève future）中。他将外表像人的机器起名为Android。");
        test1.setTime("2018/5/15");
        list.add(test1);

        return list;
    }
}
