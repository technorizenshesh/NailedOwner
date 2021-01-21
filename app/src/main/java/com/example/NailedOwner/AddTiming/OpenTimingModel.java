package com.example.NailedOwner.AddTiming;

public class OpenTimingModel {

    String time;
    String tie_one;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public OpenTimingModel(String time, String tie_one) {
        this.time = time;
        this.tie_one = tie_one;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTie_one() {
        return tie_one;
    }

    public void setTie_one(String tie_one) {
        this.tie_one = tie_one;
    }
}
