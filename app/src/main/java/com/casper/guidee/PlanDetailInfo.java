package com.casper.guidee;


import android.os.Parcel;
import android.os.Parcelable;

public class PlanDetailInfo implements Parcelable {
    private String planDetailNumber;
    private String planNumber;
    private String planDay;
    private String planFirstTime;
    private String planLastTime;
    private String planDetailPlace;
    private String planDetailInfo;
    private String planTitle;

    public PlanDetailInfo(Parcel parcel) {
        // must be same order with writeToParcel()
        this.planDetailNumber = parcel.readString();
        this.planNumber = parcel.readString();
        this.planDay = parcel.readString();
        this.planFirstTime = parcel.readString();
        this.planLastTime = parcel.readString();
        this.planDetailPlace = parcel.readString();
        this.planDetailInfo = parcel.readString();
        this.planTitle = parcel.readString();
    }

    // create Parcelable
    public static final Creator<PlanDetailInfo> CREATOR = new Creator<PlanDetailInfo>() {
        @Override
        public PlanDetailInfo createFromParcel(Parcel parcel) {
            return new PlanDetailInfo(parcel);
        }
        @Override
        public PlanDetailInfo[] newArray(int size) {
            return new PlanDetailInfo[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.planDetailNumber);
        dest.writeString(this.planNumber);
        dest.writeString(this.planDay);
        dest.writeString(this.planFirstTime);
        dest.writeString(this.planLastTime);
        dest.writeString(this.planDetailPlace);
        dest.writeString(this.planDetailInfo);
        dest.writeString(this.planTitle);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public PlanDetailInfo(){

    }

    public String getPlanDetailNumber(){
        return planDetailNumber;
    }

    public String getPlanNumber(){
        return planNumber;
    }

    public String getPlanDay(){
        return planDay;
    }

    public String getPlanFirstTime(){
        return planFirstTime;
    }

    public String getPlanLastTime(){
        return planLastTime;
    }

    public String getPlanDetailPlace(){
        return planDetailPlace;
    }

    public String getPlanDetailInfo(){
        return planDetailInfo;
    }

    public String getPlanTitle(){
        return planTitle;
    }


    public void setPlanDetailNumber(String planDetailNumber){
        this.planDetailNumber = planDetailNumber;
    }

    public void setPlanNumber(String planNumber){
        this.planNumber = planNumber;
    }

    public void setPlanDay(String planDay){
        this.planDay = planDay;
    }

    public void setPlanFirstTime(String planFirstTime){
        this. planFirstTime = planFirstTime;
    }

    public void setPlanLastTime(String planLastTime){
        this. planLastTime = planLastTime;
    }

    public void setPlanDetailPlace(String planDetailPlace){
        this. planDetailPlace = planDetailPlace;
    }

    public void setPlanDetailInfo(String planDetailInfo){
        this. planDetailInfo = planDetailInfo;
    }

    public void setPlanTitle(String planTitle){
        this.planTitle = planTitle;
    }
}
