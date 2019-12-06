package com.casper.guidee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScheduleListActivity extends AppCompatActivity {
    String url = "http://guidee.casper.or.kr/load_schedule.php";
    String result;
    String param;
    String plan_number;

    ArrayList<PlanDetailInfo> mArrayList;
    MakePlanDetailInfoToArray makePlanDetailInfoToArray;
    NetworkTask networkTask;

    Context context;
    ListView listView ;
    ListViewAdapter adapter;
    Button planDay;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        context = this;

        mArrayList = new ArrayList<>();
        makePlanDetailInfoToArray = new MakePlanDetailInfoToArray();
        adapter = new ListViewAdapter() ;

        planDay = (Button)findViewById(R.id.button);

        intent = getIntent();
        plan_number = intent.getExtras().getString("plan_number");
        param = "plan_number=" + plan_number;

        try {
            result = new NetworkTask(url, param, context).execute().get();

            mArrayList =  makePlanDetailInfoToArray.toArray(result);

            // 리스트뷰 참조 및 Adapter달기
            listView = (ListView) findViewById(R.id.listview1);
            listView.setAdapter(adapter);

            adapter.additem(mArrayList);

        }catch (Exception e) {
            e.printStackTrace();
            planDay.setText(result);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position이 클릭된 위치입니다.
                // 컬렉션에서 적절하게 꺼내서 사용하시면 됩니다.
                //Toast.makeText(getApplicationContext(), itemList.get(position).getSomethingColumn(), Toast.LENGTH_LONG).show();
                Intent nextIntent = new Intent(ScheduleListActivity.this, ScheduleActivity.class);
                // 추가된 부분
                nextIntent.putExtra("PlanDetailInfo",  mArrayList.get(position));
                startActivity(nextIntent);
            }
        });


    }
}
