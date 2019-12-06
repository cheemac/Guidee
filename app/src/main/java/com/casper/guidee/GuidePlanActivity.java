package com.casper.guidee;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
        planAllInfo = (PlanAllInfo) getIntent().getParcelableExtra("search_result");

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


        findViewById(R.id.plan_detail_info_button).setOnClickListener(mClickListener);
        findViewById(R.id.payment).setOnClickListener(mClickListener);

        planBasePrice.setText(planAllInfo.getPlanBasePrice());
        planAddPrice.setText(planAllInfo.getPlanAddPrice());
        planFirstDay.setText(planAllInfo.getPlanFirstDay());
        planLastDay.setText(planAllInfo.getPlanLastDay());
        planName.setText(planAllInfo.getPlanName());
        planRecommendedPerson.setText(planAllInfo.getPlanRecommendedPerson());
        planMaximumPerson.setText(planAllInfo.getPlanMaximumPerson());
        demandInfo.setText(planAllInfo.getDemandInfo());

    }

        Button.OnClickListener mClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    //<!-- 결제버튼 -->
                    case R.id.plan_detail_info_button:
                        Intent intent = new Intent(GuidePlanActivity.this, ScheduleListActivity.class);
                        intent.putExtra("plan_number", planAllInfo.getPlanNumber());
                        startActivity(intent);
                    break;
                    case R.id.payment:
                        Log.i("pay","click paybutton");
                        try {
                            //(현재 액티비티 , 전환액티비비티 클래스)
                            intent = new Intent(GuidePlanActivity.this, NoticeActivity.class);
                            //(공지명,공지내용)
                            intent.putExtra("data","현재 상품을 구입하겠습니까?");
                            //(액티비티전환)
                            startActivityForResult(intent,1);
                        } catch (Exception e) {
                            Log.e("this erroer",e.toString());
                        }
                        break;
                }
            }
        };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //NoticeActivity
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                //
                if(result.equals("RESULT_OK")){
                    //타이머 쓰레드 시작

                    //api쓰레드 시작
                    GuidePlanActivity.APIThread apiThread = new GuidePlanActivity.APIThread();
                    apiThread.setDaemon(true);
                    apiThread.start();

                }
            }
        }
    }
    /**********************API쓰레드*****************************/
    //getAPIURL
    class APIThread extends Thread {
        Controller controller = new Controller() ;
        String isExpire;   //타이머 상태 표기
        int time = 10;
        Message msg = Message.obtain();
        Message url = Message.obtain();
        CountDownTimer countDownTimer = new CountDownTimer(time*60*1000, 1000) { //제한시간동안 시간간격으로 줄어듦
            //타이머가 종료 될 떄 까지 동작하는 onTick함수
            @Override
            public void onTick(long millisUntilFinished) {

            }
            //타이머가 종료될 때 실행되는 onFinish함수
            @Override
            public void onFinish() {
                Log.d("CountDownTimer","timer is stop!!!");
                isExpire = new String("isExpire");

                msg.obj = (isExpire);
                apiHandler.sendMessage(msg);
            }


        };
        //카카오페이로 요청시도
        public void run() {
            //product는 상품과 상품의 정보를 가짐

            startTimer();

            controller.readyToKakao();
            // controller.approveToKakao();

            String next_redirect_app_url =  controller.getKakaopay().getKakaoPayReadyVO().getNext_redirect_pc_url();
            url.obj = next_redirect_app_url;
            apiHandler.sendMessage(url);

        }


        public void startTimer(){
            Log.d("CountDownTimer","timer is start!!!");
            this.countDownTimer.start();
            isExpire = new String("isNotExpire");
            msg.obj = (isExpire);
            apiHandler.sendMessage(msg);
        }
        public void stopTimer(){
            Log.d("CountDownTimer","timer is stop!!!");
            this.countDownTimer.cancel();
        }

        public String getIsExprie(){
            return  this.isExpire;
        }
    }
    /**********************API쓰레드*****************************/
    Handler apiHandler = new Handler(){
        public void handleMessage(Message msg) {
            String message = (String) msg.obj;
            Intent webIntent = new Intent(GuidePlanActivity.this, ApiWebwindow.class);
            //쓰레드에서 타이머의 시작을 받음boolean문자받기
            ApiWebwindow Activity = (ApiWebwindow)ApiWebwindow.apiActivity;
            if ( message.equals("isNotExpire")) {
                Log.i("상품상태","현재 결제할려는 상품은 결제가 가능합니다!!!");


                //현재 켜진 인텐트전환
                //DB에 해당상품 결제 불가능으로 바꿈
            } else if (message.equals("isExpire")) {
                Log.i("상품상태","현재 결제할려는 상품은 결제가 불가능합니다!!!");

                //현재 켜진 인텐트 끄기
                Activity.finish();
                //DB에 해당상품 결제 가능으로 바꿈
            }
            else {
                Log.i("loadURL","loadURL은 " + message + "입니다!!!");

                webIntent.putExtra("next_redirect_app_url", message);
                //(액티비티전환)
                startActivity(webIntent);
            }
        }
    };
    /**********************API쓰레드*****************************/


}
