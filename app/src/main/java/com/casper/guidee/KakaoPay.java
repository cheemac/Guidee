package com.casper.guidee;


import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


@lombok.extern.java.Log


public class KakaoPay {

    private static String HOST = "https://kapi.kakao.com";
    private static String adminKey = "b99a1dfa7fd248b22ca6cae8c446f594";


    private static String cid = "TC0ONETIME";


    private static KakaoPayReadyVO kakaoPayReadyVO;
    private static KakaoPayApprovalVO kakaoPayApprovalVO;


    public KakaoPay(){
        this.kakaoPayReadyVO = new KakaoPayReadyVO();
        this.kakaoPayApprovalVO = new KakaoPayApprovalVO();
    }

    public void kakaoPayReadyByHTTP() {
        //log.info("KakaoPayReadyByHTTP............................................");
       // log.info("-----------------------------");
        try {

            String apiURL = "https://kapi.kakao.com/v1/payment/ready";
           // log.info("카카오페이 클래스시작:현재URL은"+apiURL);
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            //요청 방식 선택
            con.setRequestMethod("POST");
            //서버 response Data JSON 형식 으로 요청
            con.setRequestProperty("Host", "kapi.kakao.com");
            con.setRequestProperty("Authorization", "KakaoAK "+ adminKey);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 파라미터 세팅
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("cid", cid);
            params.put("partner_order_id", "1001");
            params.put("partner_user_id", "gorany");
            params.put("item_name", "조재헌");
            params.put("quantity", "1");
            params.put("total_amount", "150000");
            params.put("tax_free_amount", "1000");
            params.put("approval_url", "https://localhost:8080/kakaoPaySuccess");
            params.put("cancel_url", "https://localhost:8080/kakaoPayCancel");
            params.put("fail_url", "https://localhost:8080/kakaoPaySuccessFail");
            Log.i(this.getClass().getName(),"현재 params는 "+ params.toString());
            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String,Object> param : params.entrySet()) {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes); // POST 호출

            int responseCode = con.getResponseCode();
            BufferedReader br= new BufferedReader(new InputStreamReader(con.getInputStream()));
            //response 출력
            Log.i(this.getClass().getName(),"현재 responseCode는 "+ responseCode);

            //Json 응답 분해
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            Log.i(this.getClass().getName(),"현재 response.toString()는 "+ response.toString());
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject )parser.parse(response.toString());

            kakaoPayReadyVO.setKakaoPayReadyVO(object);

            br.close();
            con.disconnect();

        } catch (IOException e) {
            System.out.println(e);
            Log.i(this.getClass().getName(),"현재 HTTP 문제는 "+ "IO");
        }
        catch(NullPointerException e){
            System.out.println(e);
            Log.i(this.getClass().getName(),"현재 HTTP 문제는 "+ "NULL");
        }
        catch (ParseException e){
            System.out.println(e);
            Log.i(this.getClass().getName(),"현재 HTTP 문제는 "+ "Parse");
        }

    }


    public void kakaoPayInfoByHTTP(String pg_token) {

      //  log.info("KakaoPayInfoByHTTP()............................................");
      //  log.info("-----------------------------");


        try {
            String apiURL = "https://kapi.kakao.com/v1/payment/approve";
         //   log.info("카카오페이 클래스시작:현재URL은" + apiURL);
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //요청 방식 선택
            con.setRequestMethod("POST");
            //서버 response Data JSON 형식 으로 요청
            con.setRequestProperty("Host", "kapi.kakao.com");
            con.setRequestProperty("Authorization", "KakaoAK " + adminKey);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 파라미터 세팅
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("cid", cid);
            params.put("tid", kakaoPayReadyVO.getTid());
            params.put("partner_order_id", "1001");
            params.put("partner_user_id", "gorany");
            params.put("pg_token", pg_token);
            params.put("total_amount", "2100");

            Log.i(this.getClass().getName(),"현재 params는 "+ params.toString());
            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String,Object> param : params.entrySet()) {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes); // POST 호출

            int responseCode = con.getResponseCode();
            BufferedReader br= new BufferedReader(new InputStreamReader(con.getInputStream()));
            //response 출력
            Log.i(this.getClass().getName(),"현재 responseCode는 "+ responseCode);

            //Json 응답 분해
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            Log.i(this.getClass().getName(),"현재 response.toString()는 "+ response.toString());
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject)parser.parse(response.toString());
            JSONObject amount;
            JSONObject card_info;


            kakaoPayApprovalVO.setKakaoPayApprovalVO(object);


            br.close();
            con.disconnect();
        }

        catch (MalformedURLException | ParseException e){ e.printStackTrace(); }
        catch (IOException e){ e.printStackTrace(); }


    }


    public KakaoPayReadyVO getKakaoPayReadyVO(){
        return this.kakaoPayReadyVO;
    }
    public KakaoPayApprovalVO getKakaoPayApprovalVO(){
        return this.kakaoPayApprovalVO;
    }

}


