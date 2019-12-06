package com.casper.guidee;

import android.os.Parcel;
import android.os.Parcelable;


public class PlanAllInfo implements Parcelable{
    private String planNumber;
    private String userNumber;
    private String planBasePrice;
    private String planAddPrice;
    private String planFirstDay;
    private String planLastDay;
    private String planMaximumPerson;
    private String planRecommendedPerson;
    private String demandInfo;
    private String payment_status;
    private String planName;

    public PlanAllInfo(Parcel parcel) {
        // must be same order with writeToParcel()
        this.userNumber = parcel.readString();
        this.planNumber = parcel.readString();
        this.planBasePrice = parcel.readString();
        this.planAddPrice = parcel.readString();
        this.planFirstDay = parcel.readString();
        this.planLastDay = parcel.readString();
        this.planMaximumPerson = parcel.readString();
        this.planRecommendedPerson = parcel.readString();
        this.demandInfo = parcel.readString();
        this.payment_status = parcel.readString();
        this.planName = parcel.readString();
    }

    // create Parcelable
    public static final Creator<PlanAllInfo> CREATOR = new Creator<PlanAllInfo>() {
        @Override
        public PlanAllInfo createFromParcel(Parcel parcel) {
            return new PlanAllInfo(parcel);
        }
        @Override
        public PlanAllInfo[] newArray(int size) {
            return new PlanAllInfo[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userNumber);
        dest.writeString(this.planNumber);
        dest.writeString(this.planBasePrice);
        dest.writeString(this.planAddPrice);
        dest.writeString(this.planFirstDay);
        dest.writeString(this.planLastDay);
        dest.writeString(this.planMaximumPerson);
        dest.writeString(this.planRecommendedPerson);
        dest.writeString(this.demandInfo);
        dest.writeString(this.payment_status);
        dest.writeString(this.planName);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public PlanAllInfo(){

    }
    public String getPlanNumber(){
        return planNumber;
    }

    public String getUserNumber(){
        return userNumber;
    }

    public String getPlanBasePrice(){
        return planBasePrice;
    }

    public String getPlanAddPrice(){
        return planAddPrice;
    }

    public String getPlanFirstDay(){
        return planFirstDay;
    }

    public String getPlanLastDay(){
        return planLastDay;
    }

    public String getPlanMaximumPerson(){
        return planMaximumPerson;
    }

    public String getPlanRecommendedPerson(){
        return planRecommendedPerson;
    }

    public String getDemandInfo(){
        return demandInfo;
    }

    public String getPayment_status(){
        return payment_status;
    }

    public String getPlanName(){
        return planName;
    }

    //set
    public void setPlanNumber(String planNumber){
        this.planNumber = planNumber;
    }

    public void setUserNumber(String userNumber){
        this.userNumber = userNumber;
    }

    public void setPlanFirstDay(String planFirstDay){
        this. planFirstDay = planFirstDay;
    }

    public void setPlanLastDay(String planLastDay){
        this. planLastDay = planLastDay;
    }

    public void setPlanBasePrice(String planBasePrice){
        this. planBasePrice = planBasePrice;
    }

    public void setPlanAddPrice(String planAddPrice){
        this. planAddPrice = planAddPrice;
    }

    public void setPlanMaximumPerson(String planMaximumPerson){
        this. planMaximumPerson = planMaximumPerson;
    }

    public void setPlanRecommendedPerson(String planRecommendedPerson){
        this. planRecommendedPerson = planRecommendedPerson;
    }

    public void setDemandInfo(String demandInfo){
        this.demandInfo = demandInfo;
    }

    public void setPayment_status(String payment_status){
        this.payment_status = payment_status;
    }

    public void setPlanName(String planName){ this.planName = planName; }
}
