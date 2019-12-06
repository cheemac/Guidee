package com.casper.guidee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeActivity extends Activity {

    TextView noticeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice);

        //UI 객체생성
        noticeText = (TextView)findViewById(R.id.NoticeText);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        noticeText.setText(data);

        findViewById(R.id.YesButton).setOnClickListener(mClickListener);
        findViewById(R.id.NoButton).setOnClickListener(mClickListener);

    }

    //<!-- 버튼리스너 -->
    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                //<!-- 예버튼 -->
                case R.id.YesButton:
                    Log.i("pay","click paybutton");
                    try {


                        //타이머 시작을 알림
                        Intent intent = new Intent();
                        intent.putExtra("result", "RESULT_OK");
                        setResult(RESULT_OK, intent);

                        //API호출을 통한 API화면으로 진입 및 액티비티(팝업) 닫기
                         onDestroy();
                         finish();
                    }
                    catch (Exception e) {
                        Toast.makeText(NoticeActivity.this, "has error",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                //<!-- 예버튼 -->

                //<!-- 아니오버튼 -->
                case R.id.NoButton:

                    try {
                        //액티비티(팝업) 닫기
                        onDestroy();
                        finish();
                        /*닫고 이전 화면으로 돌아가기*/

                    } catch (Exception e) {
                        Toast.makeText(NoticeActivity.this, "has error",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                //<!-- 아니오버튼 -->
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    //바깥레이어 클릭시 안닫히게
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    //안드로이드 백버튼 막기
    @Override
    public void onBackPressed() {

        return;
    }





}
