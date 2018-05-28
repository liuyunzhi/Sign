package com.cdut.sign.util;

import java.util.List;

public class RecordGroup {

	private RecordItem recordItem;
    private List<RecordChild> recordChildList;
  
    public RecordGroup(RecordItem recordItem, List<RecordChild> recordChildList) {
        this.recordItem = recordItem;
        this.recordChildList = recordChildList;
    }   
       
    public void add(RecordChild recordChild){
        recordChildList.add(recordChild);
    }   
       
    public void remove(RecordChild recordChild){
        recordChildList.remove(recordChild);
    }   
       
    public void remove(int index){   
        recordChildList.remove(index);
    }   
       
    public int getChildSize(){   
        return recordChildList.size();
    }   
    
    public List<RecordChild> getRecordChildList() {
        return recordChildList;
    }
       
    public RecordChild getChild(int index){
        return recordChildList.get(index);
    }   
  
    public void setRecordChildList(List<RecordChild> recordChildList) {
        this.recordChildList = recordChildList;
    } 
    
    public RecordItem getItemInfor() {
        return recordItem;
    }   
  
    public void setItemInfor(RecordItem recordItem) {
        this.recordItem = recordItem;
    }   
}
