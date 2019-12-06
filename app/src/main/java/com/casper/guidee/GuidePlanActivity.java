package com.casper.guidee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuidePlanActivity extends AppCompatActivity {

    Button planDetailInfoButton;
    Button payButton;
    Button showReview;
    TextView planBasePrice;
    TextView planAddPrice;
    TextView planFirstDay;
    TextView planLastDay;
    TextView planName;
    TextView planRecommendedPerson;
    TextView planMaximumPerson;
    TextView demandInfo;

    PlanAllInfo planAllInfo;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_plan);

        intent = getIntent();
        planAllInfo = (PlanAllInfo)getIntent().getParcelableExtra("search_result");

        //ayButton = findViewById(R.id.plan_first_time);
       // showReview = findViewById(R.id.plan_last_time);
        planBasePrice = findViewById(R.id.plan_base_price);
        planAddPrice = findViewById(R.id.plan_add_price);
        planFirstDay = findViewById(R.id.plan_first_day);
        planLastDay = findViewById(R.id.plan_last_day);
        planName = findViewById(R.id.plan_name);
        planRecommendedPerson = findViewById(R.id.plan_recommended_person);
        planMaximumPerson = findViewById(R.id.plan_maximum_person);
        demandInfo = findViewById(R.id.demand_info);
        planDetailInfoButton = (Button)findViewById(R.id.plan_detail_info_button);

        planBasePrice.setText(planAllInfo.getPlanBasePrice());
        planAddPrice.setText(planAllInfo.getPlanAddPrice());
        planFirstDay.setText(planAllInfo.getPlanFirstDay());
        planLastDay.setText(planAllInfo.getPlanLastDay());
        planName.setText(planAllInfo.getPlanName());
        planRecommendedPerson.setText(planAllInfo.getPlanRecommendedPerson());
        planMaximumPerson.setText(planAllInfo.getPlanMaximumPerson());
        demandInfo.setText(planAllInfo.getDemandInfo());



        planDetailInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuidePlanActivity.this, ScheduleListActivity.class);
                intent.putExtra("plan_number", planAllInfo.getPlanNumber());
                startActivity(intent);
            }
        });
    }
}
