package com.example.NailedOwner.AddTiming;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaysModelData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("weekly_day")
    @Expose
    private String weeklyDay;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWeeklyDay() {
        return weeklyDay;
    }

    public void setWeeklyDay(String weeklyDay) {
        this.weeklyDay = weeklyDay;
    }

}
