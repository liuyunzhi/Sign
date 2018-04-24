package com.cdut.sign.util;

import java.util.List;

public class GroupInfor {

	private ItemInfor item;   
    private List<ChildInfor> child;   
  
    public GroupInfor(ItemInfor item, List<ChildInfor> child) {   
  
        this.item = item;   
        this.child = child;   
    }   
       
    public void add(ChildInfor infor){   
        child.add(infor);   
    }   
       
    public void remove(ChildInfor infor){   
        child.remove(infor);   
    }   
       
    public void remove(int index){   
        child.remove(index);   
    }   
       
    public int getChildSize(){   
        return child.size();   
    }   
    
    public List<ChildInfor> getChild() {   
        return child;   
    }
       
    public ChildInfor getChild(int index){   
        return child.get(index);   
    }   
  
    public void setChild(List<ChildInfor> child) {   
        this.child = child;   
    } 
    
    public ItemInfor getItemInfor() {   
        return item;   
    }   
  
    public void setItemInfor(ItemInfor item) {   
        this.item = item;   
    }   
}
