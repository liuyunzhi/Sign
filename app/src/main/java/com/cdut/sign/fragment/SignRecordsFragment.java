package com.cdut.sign.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.adapter.RecordExpandableListAdapter;
import com.cdut.sign.util.RecordChild;
import com.cdut.sign.util.RecordGroup;
import com.cdut.sign.util.RecordItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class SignRecordsFragment extends Fragment {

    private RecordExpandableListAdapter listAdapter = null;
    private ExpandableListView recordList;
    private List<RecordGroup> group;
    private SignApplication signApplication;
    private Map<String, String> signRecordMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_records, container, false);

        recordList = view.findViewById(R.id.record_list);
        group = new ArrayList<>();
        signApplication = (SignApplication) getActivity().getApplication();
        signRecordMap = signApplication.getSignRecordMap();

        //每天的记录
        RecordItem item = new RecordItem();
        //仅供测试
        item.setGroupDate("2018年6月1日");
        //每节课的考勤记录
        List<RecordChild> childList = new ArrayList<>();
        RecordChild child = new RecordChild();
        //仅供测试
        child.setsignImage(R.drawable.sign_success);
        child.setsignOutImage(R.drawable.sign_fail);
        child.setSignCourse("论文写作");
        child.setSignTime("18:00:00");
        child.setSignOutTime("18:20:00");
        childList.add(child);
        //每月成一组
        RecordGroup recordGroup = new RecordGroup(item, childList);
        group.add(recordGroup);

        listAdapter = new RecordExpandableListAdapter(getActivity(), group);
        recordList.setAdapter(listAdapter);

        return view;
    }
}
