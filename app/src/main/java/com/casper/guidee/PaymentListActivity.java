package com.casper.guidee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

//상세내역 액티비티
public class PaymentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentlist);

        final Button userbtn = (Button) findViewById(R.id.selectUser);
        final Button guidebtn = (Button) findViewById(R.id.selectGuide);

        // ========== 일정 레이아웃 동적 추가 ==========
        for(int i=0; i<10; i++) {
            AddPlanToList n_layout = new AddPlanToList(getApplicationContext());

            LinearLayout con = (LinearLayout)findViewById(R.id.ListLayout);
            con.addView(n_layout);
            n_layout.setId(i);
            Log.i("newView", String.valueOf(n_layout.getId()));
            findViewById(n_layout.getId()).setOnClickListener(vClickListener);
        }

        // ========== 여행자, 가이드 버튼 상호작용 ==========

        userbtn.setBackgroundColor(Color.TRANSPARENT);
        guidebtn.setBackgroundColor(Color.LTGRAY);

        userbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                userbtn.setBackgroundColor(Color.TRANSPARENT);
                guidebtn.setBackgroundColor(Color.LTGRAY);
            }
        });

        guidebtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                guidebtn.setBackgroundColor(Color.TRANSPARENT);
                userbtn.setBackgroundColor(Color.LTGRAY);
            }
        });

        // ========== 날짜 항목 고르기 ==========

        //Spinner객체 생성
        final Spinner spinner_field = (Spinner) findViewById(R.id.spinner_time_field);

        //1번에서 생성한 field.xml의 item을 String 배열로 가져오기
        String[] str = getResources().getStringArray(R.array.spinnerArray);

        //2번에서 생성한 spinner_item.xml과 str을 인자로 어댑터 생성.
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.time_list,str);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_field.setAdapter(adapter);

        //spinner 이벤트 리스너
        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_field.getSelectedItemPosition() > 0){

                    //선택된 항목
                    Log.v("알림",spinner_field.getSelectedItem().toString()+ "is selected");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
    //뷰 클릭리스너
    View.OnClickListener vClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

                //결제내역에서 선택한 상품화면으로 이동
                Intent intent = new Intent(PaymentListActivity.this, DetailedProductInfoActivity.class);
                //(액티비티전환)
                startActivity(intent);


        }
    };

}
