package com.casper.guidee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.login_email)
    EditText emailText;                  // 이메일 입력
    @BindView(R.id.login_password)
    EditText passwordText;      // 패스워드 입력
    @BindView(R.id.btn_login)
    Button loginButton;              // 로그인 버튼
    @BindView(R.id.link_signup)
    TextView signupLink;           // 회원가입 버튼
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // 로그인 버튼 클릭
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 회원가입 버튼 클릭
        // 회원 가입 액티비티로 전환
        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        // 이메일, 패스워드 유효하지 않으면
        // 로그인 실패 메시지 후 화면으로 복귀
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        // 로딩
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        // TODO: DB와 연동하여 계정 로그인 구현
        InsertLoginData task = new InsertLoginData();
        String loginResult = null;
        try {
            loginResult = task.execute("http://guidee.casper.or.kr/login2.php",email, password).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 결과 완료 및 종료
        String finalLoginResult = loginResult;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(finalLoginResult.equals("LOGIN SUCCESS")) {
                            onLoginSuccess();
                        }
                        else {
                            onLoginFailed();
                        }
                        // onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // TODO: 회원가입 성공 동작
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    // 뒤로가기 버튼 클릭 시 동작
    // 종료
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();

        // 계정 정보 저장
        SharedPreferences accountInfo = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = accountInfo.edit();
        editor.putString("email", emailText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.commit();

        finish();
    }

    // 로그인 실패시 메시지
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    // 이메일 비밀번호 유효성 검사
    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // 이메일 공란 혹은 맞지 않는 형식 구분
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        // 비밀번호 공란 혹은 길이에 따른 구분(임의로 4~10자, 수정 필요)
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    class InsertLoginData extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected String doInBackground(String... params) {
            String user_id = (String) params[1];
            String user_ps = (String) params[2];

            String serverURL = (String) params[0];
            String postParameters = "user_id=" + user_id + "&user_ps=" + user_ps;

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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                // 수신되는 데이터 저장
                // sb에 저장
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                // 로그인 결과에 따른 동작
                // 계정 없음
                if(sb.toString().contains("NO ACCOUNT")) {
                    Log.d("WOW = " , "LOGIN FAILED");
                }
                // 로그인 성공
                else if (sb.toString().equals("LOGIN SUCCESS")) {
                    // TODO: 메인 액티비티 화면 전환
                    // 기능 사용 가능
                    Log.d("WOW = " , "LOGIN PROGRESS");
                }
                // 기타 예외
                else{
                    Log.d("WOW = " , "LOGIN ELSE");
                }

                Log.d("Last Result = ", sb.toString());

                return sb.toString();
            } catch (Exception e) {
                Log.d("Exception occur = ", e.toString());
                return new String("Error: " + e.getMessage());
            }
        }
    }
}