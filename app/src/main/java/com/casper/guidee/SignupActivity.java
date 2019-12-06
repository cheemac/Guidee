package com.casper.guidee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    // 입력 회원 정보
    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;
    @BindView(R.id.genderSpinner)
    Spinner genderData;
    @BindView(R.id.nationSpinner)
    Spinner nationData;
    @BindView(R.id.datePicker)
    DatePicker datePicker;

    // 현재 날짜 값을 기본값으로
    Date setDate = new Date(System.currentTimeMillis());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        // 생년월일 받기
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setDate = new Date(year-1900, monthOfYear, dayOfMonth);
            }
        });

        // 가입하기 버튼 클릭
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        // 로그인하기 버튼 클릭
        // 화면 전환
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 회원가입 동작
    // 버튼 클릭 시 동작
    public void signup() {
        Log.d(TAG, "Signup");

        // 회원 정보 유효성 검사
        if (!validate()) {
            onSignupFailed();
            return;
        }

        // 유효할 경우 회원정보 등록 시작
        signupButton.setEnabled(false);

        // 로딩
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // 입력 정보 불러오기
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String gender = genderData.getSelectedItem().toString();
        String nation = nationData.getSelectedItem().toString();
        String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(setDate);

        // TODO: 회원 DB와 연동 및 데이터 저장
        InsertData task = new InsertData();
        task.execute("http://guidee.casper.or.kr/reg2.php", email, password, name, gender, nation, date_text);

        // 종료
        // 로그인 액티비티에 결과 넘겨줌
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Signup Success", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
        // OK 신호 로그인 액티비티로 전송
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    // 회원정보 유효성 검사
    public boolean validate() {
        boolean valid = true;

        // 입력값 가져오기
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        // 이름 유효성
        // 공란 혹은 길이제한
        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        // 이메일 유효성
        // 이메일 형식 혹은 공란
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        // 비밀번호 유효성
        // 비밀번호 형식 혹은 공란
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        // 비밀번호 재입력
        // 공란, 형식, 비밀번호와 동일여부
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

    // 데이터 통신
    class InsertData extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        // DB 데이터 전송
        @Override
        protected String doInBackground(String... params) {
            String user_id = (String)params[1];
            String user_ps = (String)params[2];
            String user_name = (String)params[3];
            String user_sex = (String)params[4];
            String user_nation = (String)params[5];
            String user_birthday = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "user_id=" + user_id + "&user_ps=" + user_ps + "&user_name=" + user_name + "&user_sex=" + user_sex + "&user_nation=" + user_nation + "&user_birthday=" + user_birthday;

            Log.d("url Param = ", serverURL);
            Log.d("result = ", postParameters);

            try {
                // 서버 연결
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.connect();

                // 파라미터 값 전달
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // 응답 확인
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, " POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                // 수신되는 데이터 저장
                // sb에 저장
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                Log.d("Last Result = ", sb.toString());
                return sb.toString();

            }
            catch (Exception e) {
                Log.d("Exception occur = ",  e.toString());
                return new String("Error: " + e.getMessage());
            }
        }
    }
}