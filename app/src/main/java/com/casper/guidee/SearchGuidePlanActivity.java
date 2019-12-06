package com.casper.guidee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SearchGuidePlanActivity extends AppCompatActivity {
    private List<String> list;          // 데이터를 넣은 리스트변수
    EditText editSearch;
    Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_guide_plan);
        editSearch = (EditText) findViewById(R.id.searchText);
        search_btn = (Button) findViewById(R.id.search_button);

        search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SearchGuidePlanActivity.this, GuidePlanListActivity.class);
                intent.putExtra("search_text",editSearch.getText().toString());
                startActivity(intent);
            }
        });
    }

}