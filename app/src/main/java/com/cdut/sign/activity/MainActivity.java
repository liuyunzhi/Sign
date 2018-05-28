package com.cdut.sign.activity;

import java.util.ArrayList;
import java.util.List;

import com.cdut.sign.R;
import com.cdut.sign.adapter.MainFragmentPagerAdapter;
import com.cdut.sign.fragment.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private HomeFragment homeFragment;
    private SignRecordsFragment signRecordsFragment;
    private NoteFragment noteFragment;
    private PersonInfoFragment personInfoFragment;

    private List<Fragment> allFragment;

    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);

        //RadioGroup选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) {
                    case R.id.home_page_button:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.record_button:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.work_button:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.person_info_button:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        viewPager = findViewById(R.id.view_pager);
        allFragment = new ArrayList<>();

        homeFragment = new HomeFragment();
        signRecordsFragment = new SignRecordsFragment();
        noteFragment = new NoteFragment();
        personInfoFragment = new PersonInfoFragment();

        allFragment.add(homeFragment);
        allFragment.add(signRecordsFragment);
        allFragment.add(noteFragment);
        allFragment.add(personInfoFragment);

        //ViewPager设置适配器
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), allFragment));
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
        //防止界面之间来回切换时重新加载
        viewPager.setOffscreenPageLimit(4);
        //ViewPagerҳ页面切换监听
        viewPager.setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        radioGroup.check(R.id.home_page_button);
                        break;
                    case 1:
                        radioGroup.check(R.id.record_button);
                        break;
                    case 2:
                        radioGroup.check(R.id.work_button);
                        break;
                    case 3:
                        radioGroup.check(R.id.person_info_button);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }


}
