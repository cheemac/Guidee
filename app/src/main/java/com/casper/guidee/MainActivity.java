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
