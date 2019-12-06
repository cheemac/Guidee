package com.casper.guidee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


@lombok.extern.java.Log
public class ApiWebwindow extends AppCompatActivity {
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅
    private String next_redirect_app_url;
    public static AppCompatActivity apiActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_webwindow);

        apiActivity = ApiWebwindow.this;

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("next_redirect_app_url");
        next_redirect_app_url = data;

        mWebView = (WebView) findViewById(R.id.apiwindow);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false;

                try {
                    Intent apiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(apiIntent);
                    return true;
                } catch (Exception e) {
                    return true;
                }
            }
            //url이 바뀔때마다
            public void onPageFinished(WebView view, String url) {
                Uri uri = Uri.parse(getNowURL());
                url= uri.toString();
                log.info("afterFinished loading URL: " +url);
                url = getNowURL();
                log.info("beforeFinished loading URL: " +url);
                if(url.contains("kakaoPaySuccess")){
                    sucessedPaymet();
                }
                else if(url.contains("kakaoPayCancel")){
                    ceanceldPaymet();
                }
                else if(url.contains("kakaoPaySuccessFail")){
                    failedPaymet();
                }

            }

        });// 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        mWebView.loadUrl(next_redirect_app_url);




    }
    //현재 접속한 url값 반환
    public String getNowURL(){
        return this.mWebView.getUrl().toString();
    }
    //결제 성공한 url인경우
    public void sucessedPaymet(){
        // 타이머를 끈다
        // 상품상태를 바꾼다(결제완료)
        //결제정보를 DB로 넘겨준다.
        try{
            finish();
        Intent mainIntent = new Intent(ApiWebwindow.this, MainActivity.class);
        //(공지명,공지내용)

        //(액티비티전환)
        startActivity(mainIntent);
    } catch (Exception e) {
        log.info(e.toString());
    }

    }

    //결제 취소한 url인경우
    public void ceanceldPaymet(){
        // 타이머를 끈다
        // 상품상태를 바꾼다(결제가능)
        finish();

    }
    //결제 실패한 url인경우
    public void failedPaymet(){
        //타이머 그대로진행
        //상품상태 그대로
        //취소화면 보여주고 다시 원본화면으로
        finish();

    }




}
