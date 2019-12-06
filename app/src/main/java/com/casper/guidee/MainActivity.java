package com.casper.guidee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView txtResult;

    SharedPreferences log = null;
    String id = null;
    String ps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.paymentButton).setOnClickListener(mClickListener);
        findViewById(R.id.paymentlistButton).setOnClickListener(mClickListener);
        findViewById(R.id.login).setOnClickListener(mClickListener);
        findViewById(R.id.editInfo).setOnClickListener(mClickListener);
        findViewById(R.id.logout).setOnClickListener(mClickListener);
        findViewById(R.id.guideReg).setOnClickListener(mClickListener);
        findViewById(R.id.search_guide_plan).setOnClickListener(mClickListener);

        // 회원 정보 불러오기
        // 로그인 된 정보
        log = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        id = log.getString("email", null);
        ps = log.getString("password", null);

        TextView logInfo = (TextView) findViewById(R.id.loginInfo);

        if(id!=null)
        {
            logInfo.setText(id);
        }

    }

    //<!-- 버튼리스너 -->
    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                //<!-- 결제버튼 -->
                case R.id.paymentButton:
                    Log.i("pay","click paybutton");
                    try {
                        //(현재 액티비티 , 전환액티비비티 클래스)
                        intent = new Intent(MainActivity.this, NoticeActivity.class);
                        //(공지명,공지내용)
                        intent.putExtra("data","현재 상품을 구입하겠습니까?");
                        //(액티비티전환)
                        startActivityForResult(intent,1);
                    } catch (Exception e) {
                        Log.e("this erroer",e.toString());
                    }
                    break;
                //<!-- 결제버튼 -->

                //<!-- 결제내역버튼 -->
                case R.id.paymentlistButton:

                    try {
                        //(현재 액티비티 , 전환액티비비티 클래스)
                        intent = new Intent(MainActivity.this, PaymentListActivity.class);

                        //(액티비티전환)
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("this erroer",e.toString());
                    }
                    break;
                //<!-- 결제내역버튼 -->

                //<!-- 로그인버튼 -->
                case R.id.login:
                    try {
                        //(현재 액티비티 , 전환액티비비티 클래스)
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                        //(액티비티전환)
                        startActivity(loginIntent);
                    } catch (Exception e) {

                        //log.info(e.toString());
                    }
                    break;

                case R.id.editInfo:
                    try{
                        if(id != null)
                        {
                            Intent editIntent = new Intent(MainActivity.this, PasswordConfirmActivity.class);

                            //(액티비티전환)
                            startActivity(editIntent);
                        }

                    }catch(Exception e){

                    }
                    break;
                    // 로그아웃, 새로고침
                case R.id.logout:
                    try{
                        if(id != null)
                        {
                            Toast.makeText(getBaseContext(), "Logout", Toast.LENGTH_LONG).show();
                            log.edit().clear().commit();
                            Intent editIntent = new Intent(MainActivity.this, MainActivity.class);
                            finish();
                            startActivity(editIntent);
                        }
                    }catch(Exception e){

                    }
                    break;

                case R.id.guideReg:
                    try{
                        if(id != null)
                        {
                            //Toast.makeText(getBaseContext(), "Logout", Toast.LENGTH_LONG).show();
                            Intent guideRegIntent = new Intent(MainActivity.this, GuideRegist.class);
                            startActivity(guideRegIntent);
                        }
                    }catch(Exception e){

                    }
                    break;

                case R.id.search_guide_plan:
                    try{
                            Intent searchIntent = new Intent(MainActivity.this, SearchGuidePlanActivity.class);
                            startActivity(searchIntent);
                    }catch(Exception e){

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
                    APIThread apiThread = new APIThread();
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
            Intent webIntent = new Intent(MainActivity.this, ApiWebwindow.class);
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
