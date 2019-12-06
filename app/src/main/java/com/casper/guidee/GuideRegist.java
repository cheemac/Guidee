package com.casper.guidee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class GuideRegist extends AppCompatActivity {

    @BindView(R.id.regist_job)
    EditText jobText;
    @BindView(R.id.regist_info)
    EditText infoText;

    @BindView(R.id.btn_confirmReg)
    Button guidRegButton;
    @BindView(R.id.btn_cancelReg)
    Button regCancleButton;


    SharedPreferences log = null;
    String id = null;
    String ps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideregist);
        ButterKnife.bind(this);

        // 회원 정보 불러오기
        // 로그인 된 정보
        log = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        id = log.getString("email", null);
        ps = log.getString("password", null);

        // 등록 버튼
        guidRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideReg();
            }
        });

        // 취소 버튼
        regCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onGuidRegSuccess() {
        Toast.makeText(getBaseContext(), "Regist Success", Toast.LENGTH_LONG).show();
        guidRegButton.setEnabled(true);
        finish();
    }

    public void onGuidRegFailed() {
        Toast.makeText(getBaseContext(), "Regist failed", Toast.LENGTH_LONG).show();
        guidRegButton.setEnabled(true);
    }

    public  void guideReg() {
        if(!guideValidate())
        {
            onGuidRegFailed();
            return;
        }

        guidRegButton.setEnabled(false);

        // 로딩
        final ProgressDialog progressDialog = new ProgressDialog(GuideRegist.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Guide Registing...");
        progressDialog.show();

        String guideJob = jobText.getText().toString();
        String guideInfo = infoText.getText().toString();

        // 정보 전송
        InsertGuideDate task = new InsertGuideDate();
        //task.execute("http://guidee.casper.or.kr/guideReg2.php", id, guideJob, guideInfo);

        String guideRegResult = null;

        try {
            guideRegResult = task.execute("http://guidee.casper.or.kr/guideReg2.php", id, guideJob, guideInfo).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("GUIDE REG = ", guideRegResult);

        // 종료
        String finalGuideRegResult = guideRegResult;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(finalGuideRegResult.contains("1062")){
                            Toast.makeText(getBaseContext(), "Already Registed", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            onGuidRegSuccess();
                        }

                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean guideValidate(){
        boolean valid = true;

        if(jobText.getText().toString().isEmpty()){
            jobText.setError("Enter Your Job");
            valid = false;
        }else{
            jobText.setError(null);
        }

        if(infoText.getText().toString().isEmpty()){
            infoText.setError("Enter Your Info At Least 10 characters.");
            valid = false;
        }else{
            infoText.setError(null);
        }


        return valid;
    }

    class InsertGuideDate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String user_id = (String)params[1];
            String guide_job = (String)params[2];
            String guide_info = (String)params[3];

            String serverURL = (String)params[0];

            String postParameters = "user_id=" + user_id + "&guide_job=" + guide_job + "&guide_info=" + guide_info;

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
