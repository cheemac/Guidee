package com.casper.guidee;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedProductInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_product);

        findViewById(R.id.backButton).setOnClickListener(mClickListener);
    }

    //<!-- 버튼리스너 -->
    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                //<!-- 확인버튼 -->
                case R.id.backButton:
                    Log.i("confrim","click confrimbutton");
                    try {
                        finish();
                    }
                    catch (Exception e) {
                        Toast.makeText(DetailedProductInfoActivity.this, "has error",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                //<!-- 확인버튼 -->


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
