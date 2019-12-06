package com.casper.guidee;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakePlanAllInfoToArray {
    private String TAG = "phptest";

    public ArrayList toArray(String jsonString){
        ArrayList<PlanAllInfo> mArrayList = new ArrayList<>();

        String TAG_JSON="plan_all_info";
        String TAG_PLAN_NUMBER = "plan_number";
        String TAG_USER_NUMBER = "user_number";
        String TAG_PLAN_BASE_PRICE ="plan_base_price";
        String TAG_PLAN_ADD_PRICE ="plan_add_price";
        String TAG_PLAN_FIRST_DAY ="plan_first_day";
        String TAG_PLAN_LAST_DAY ="plan_last_day";
        String TAG_PLAN_MAXIMUM_PERSON ="plan_maximum_person";
        String TAG_PLAN_RECOMMENDED_PERSON ="plan_recommended_person";
        String TAG_PAYMENT_STATUS ="payment_status";
        String TAG_DEMAND_INFO ="demand_info";
        String TAG_PLAN_NAME ="plan_name";


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String planNumber = item.getString(TAG_PLAN_NUMBER);
                String userNumber = item.getString(TAG_USER_NUMBER);
                String planBasePrice = item.getString(TAG_PLAN_BASE_PRICE);
                String planAddPrice = item.getString(TAG_PLAN_ADD_PRICE);
                String planFirstDay = item.getString(TAG_PLAN_FIRST_DAY);
                String planLastDay = item.getString(TAG_PLAN_LAST_DAY);
                String planMaximumPerson = item.getString(TAG_PLAN_MAXIMUM_PERSON);
                String planRecommendedPerson = item.getString(TAG_PLAN_RECOMMENDED_PERSON);
                String paymentStatus = item.getString(TAG_PAYMENT_STATUS);
                String demandInfo = item.getString(TAG_DEMAND_INFO);
                String planName = item.getString(TAG_PLAN_NAME);

                PlanAllInfo AllInfo = new PlanAllInfo();

                AllInfo.setPlanNumber(planNumber);
                AllInfo.setUserNumber(userNumber);
                AllInfo.setPlanBasePrice(planBasePrice);
                AllInfo.setPlanAddPrice(planAddPrice);
                AllInfo.setPlanFirstDay(planFirstDay);
                AllInfo.setPlanLastDay(planLastDay);
                AllInfo.setPlanMaximumPerson(planMaximumPerson);
                AllInfo.setPlanRecommendedPerson(planRecommendedPerson);
                AllInfo.setPayment_status(paymentStatus);
                AllInfo.setDemandInfo(demandInfo);
                AllInfo.setPlanName(planName);

                mArrayList.add(AllInfo);
            }

            return mArrayList;

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);

            return null;
        }

    }
}
