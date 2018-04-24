package com.cdut.sign.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
    private List<Fragment> list;  
  
    public MyFragmentPagerAdapter(FragmentManager fragment, List<Fragment> list) 
    {  
        super(fragment);  
        this.list = list;  
    }  
  
    @Override  
    public Fragment getItem(int position) 
    {  
        return list.get(position);  
    }  
  
    @Override  
    public int getCount() 
    {  
        return list.size();  
    }  
}  
