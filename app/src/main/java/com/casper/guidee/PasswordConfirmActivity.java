package com.casper.guidee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordConfirmActivity extends AppCompatActivity {

    @BindView(R.id.btn_psConfirm )
    Button confirmBtn;
    @BindView(R.id.btn_cancel)
    Button cancelBtn;
    @BindView(R.id.edit_password)
    EditText passwordText;

    // 회원 정보 불러오기
    // 로그인 된 정보
    SharedPreferences log =null;
    String id = null;
    String ps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordconfirm);
        ButterKnife.bind(this);

        log = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        id =log.getString("email", null);
        ps = log.getString("password", null);
        confirmBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 비밀번호 확인
                if(ps.equals(passwordText.getText().toString())) {
                    // 일치 시 회원 정보 변경하기
                    try {
                        //(현재 액티비티 , 전환액티비비티 클래스)
                        Intent editIntent = new Intent(getApplicationContext(), MemberInfoEdit.class);
                        //(액티비티전환)
                        startActivity(editIntent);
                        finish();
                    } catch (Exception e) {
                        //log.info(e.toString());
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "Password Not Matches", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 취소 돌아가기
                finish();
            }
        });
    }
}
