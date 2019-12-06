package com.casper.guidee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {

    Intent intent;

    PlanDetailInfo detailInfo;

    TextView planFirstTime;
    TextView planLastTime;
    TextView planDetailPlace;
    TextView planDetailInfo;
    TextView planTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        intent = getIntent();
        detailInfo = (PlanDetailInfo) getIntent().getParcelableExtra("PlanDetailInfo");

        planFirstTime = findViewById(R.id.plan_first_time);
        planLastTime = findViewById(R.id.plan_last_time);
        planDetailPlace = findViewById(R.id.plan_detail_place);
        planDetailInfo = findViewById(R.id.plan_detail_info);
        planTitle = findViewById(R.id.plan_title);

        planFirstTime.setText(detailInfo.getPlanFirstTime());
        planLastTime.setText(detailInfo.getPlanLastTime());
        planDetailPlace.setText(detailInfo.getPlanDetailPlace());
        planDetailInfo.setText(detailInfo.getPlanDetailInfo());
        planTitle.setText(detailInfo.getPlanTitle());
    }

}
