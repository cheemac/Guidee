package com.casper.guidee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MemberInfoEdit extends AppCompatActivity {
    private static final String TAG = "Edit_USER";

    @BindView(R.id.new_password)
    EditText passwordEdit;
    @BindView(R.id.edit_reEnterPassword)
    EditText rePasswordEdit;
    @BindView(R.id.edit_Nation)
    Spinner nationEdit;
    @BindView(R.id.edit_Gender)
    Spinner genderEdit;
    @BindView(R.id.edit_Date)
    DatePicker dateEdit;

    Date setDate = new Date(System.currentTimeMillis());

    @BindView(R.id.btn_memCancel)
    Button cancelBtn;
    @BindView(R.id.btn_editConfirm)
    Button confirmBtn;

    // 회원 정보 불러오기
    // 로그인 된 정보
    SharedPreferences log = null;
    String id = null;
    String ps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memedit);
        ButterKnife.bind(this);

        log = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        id = log.getString("email", null);
        ps = log.getString("password", null);

        dateEdit.init(dateEdit.getYear(), dateEdit.getMonth(), dateEdit.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setDate = new Date(year-1900, monthOfYear, dayOfMonth);
            }
        });

        // 취소 버튼
        // 액티비티 종료, 이전 액티비티로 전환
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 적용 버튼
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInfo();
            }
        });
    }

    // 변경 정보 적용
    // DB 접근
    public void confirmInfo(){

        // 유효하지 않아 실패
        if(!editValidate()){
            onConfirmFailed();
            // 유효성 없음
            return;
        }

        confirmBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MemberInfoEdit.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Confirming Info...");
        progressDialog.show();

        // 유효한 정보
        // DB 등록 시작
        String password = passwordEdit.getText().toString();
        String reEnterPassword = rePasswordEdit.getText().toString();
        String gender = genderEdit.getSelectedItem().toString();
        String nation = nationEdit.getSelectedItem().toString();
        String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(setDate);

        // TODO : DB의 회원 정보 업데이트
        EditData task = new EditData();
        task.execute("http://guidee.casper.or.kr/editMem2.php",  id, password,  gender, nation, date_text);

        // 종료
        // 로그인 액티비티에 결과 넘겨줌
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onConfirmSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    // 변경 성공
    public void onConfirmSuccess() {
        Toast.makeText(getBaseContext(), "Edit Confirm. Login Again.", Toast.LENGTH_LONG).show();
        // 이전 액티비티로 결과 전송
        log.edit().clear().commit();
        finish();
    }

    // 변경 실패
    public void onConfirmFailed() {
        Toast.makeText(getBaseContext(), "Check Inputs", Toast.LENGTH_LONG).show();
    }

    public boolean editValidate(){
        boolean valid = true;

        // 검증할 수정 데이터
        String password = passwordEdit.getText().toString();
        String reEnterPassword = rePasswordEdit.getText().toString();
      //  String gender = genderEdit.getSelectedItem().toString();
      //  String nation = nationEdit.getSelectedItem().toString();


        // 오류점 설명
        // 비밀번호 조건
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEdit.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEdit.setError(null);
        }

        // 비밀번호 재입력
        // 공란, 형식, 비밀번호와 동일여부
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            rePasswordEdit.setError("Password Do not match");
            valid = false;
        } else {
            rePasswordEdit.setError(null);
        }

        return valid;
    }

    class EditData extends AsyncTask<String, Void, String> {

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
            String user_sex = (String)params[3];
            String user_nation = (String)params[4];
            String user_birthday = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "user_id=" + user_id + "&user_ps=" + user_ps +  "&user_sex=" + user_sex + "&user_nation=" + user_nation + "&user_birthday=" + user_birthday;

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
