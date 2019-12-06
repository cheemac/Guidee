package com.casper.guidee;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakePlanDetailInfoToArray {
    private String TAG = "phptest";

    public ArrayList toArray(String jsonString){
        ArrayList<PlanDetailInfo> mArrayList = new ArrayList<>();

        String TAG_JSON="plan_detail_info";
        String TAG_PLAN_DETAIL_NUMBER = "plan_detail_number";
        String TAG_PLAN_NUMBER = "plan_number";
        String TAG_PLAN_DAY ="plan_day";
        String TAG_PLAN_FIRST_TIME ="plan_first_time";
        String TAG_PLAN_LAST_TIME ="plan_last_time";
        String TAG_PLAN_DETAIL_PLACE ="plan_detail_place";
        String TAG_PLAN_DETAIL_INFO ="plan_detail_info";
        String TAG_PLAN_TITLE ="plan_title";


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String planDetailNumber = item.getString(TAG_PLAN_DETAIL_NUMBER);
                String planNumber = item.getString(TAG_PLAN_NUMBER);
                String PlanDay = item.getString(TAG_PLAN_DAY);
                String planFirstTime = item.getString(TAG_PLAN_FIRST_TIME);
                String planLastTime = item.getString(TAG_PLAN_LAST_TIME);
                String planDetailPlace = item.getString(TAG_PLAN_DETAIL_PLACE);
                String planDetailInfo = item.getString(TAG_PLAN_DETAIL_INFO);
                String planTitle = item.getString(TAG_PLAN_TITLE);

                PlanDetailInfo DetailInfo = new PlanDetailInfo();

                DetailInfo.setPlanDetailNumber(planDetailNumber);
                DetailInfo.setPlanNumber(planNumber);
                DetailInfo.setPlanDay(PlanDay);
                DetailInfo.setPlanFirstTime(planFirstTime);
                DetailInfo.setPlanLastTime(planLastTime);
                DetailInfo.setPlanDetailPlace(planDetailPlace);
                DetailInfo.setPlanDetailInfo(planDetailInfo);
                DetailInfo.setPlanTitle(planTitle);

                mArrayList.add(DetailInfo);
            }

            return mArrayList;

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);

            return null;
        }

    }
}
