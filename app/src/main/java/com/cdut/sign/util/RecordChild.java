package com.cdut.sign.util;

public class RecordChild {

    private int signImage;
    private int signOutImage;
    private String signCourse;
    private String signTime;
    private String signOutTime;

    public RecordChild() {
    }

    public int getSignImage() {
        return signImage;
    }

    public void setsignImage(int signImage) {
        this.signImage = signImage;
    }

    public int getSignOutImage() {
        return signOutImage;
    }

    public void setsignOutImage(int signOutImage) {
        this.signOutImage = signOutImage;
    }

    public String getSignCourse() {
        return signCourse;
    }

    public void setSignCourse(String signCourse) {
        this.signCourse = signCourse;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }
}
