package com.casper.guidee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GuidePlanListActivity extends AppCompatActivity {

    Intent intent;
    String search_text;
    String parameters;
    ListView listView;
    String url = "http://guidee.casper.or.kr/load_searchfile.php";
    Intent intent2;
    String result;
    Context context = this;
    private SearchAdapter adapter;

    ArrayList<PlanAllInfo> mArrayList;
    MakePlanAllInfoToArray makePlanAllInfoToArray;
    NetworkTask networkTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_plan_list);
        intent = getIntent();
        search_text = intent.getExtras().getString("search_text");
        listView = (ListView) findViewById(R.id.result_view);
        parameters = "plan_name="+search_text;
        mArrayList = new ArrayList<>();
        makePlanAllInfoToArray = new MakePlanAllInfoToArray();
        try{
            result = new NetworkTask(url, parameters ,context).execute().get();
            mArrayList =  makePlanAllInfoToArray.toArray(result);
            adapter = new SearchAdapter(mArrayList,this);
            listView.setAdapter(adapter);
            //누르면 연결
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id){
                    intent2 = new Intent(GuidePlanListActivity.this, GuidePlanActivity.class);
                    intent2.putExtra("search_result",mArrayList.get(position));
                    startActivity(intent2);
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
