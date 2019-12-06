package com.casper.guidee;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<Void, Void, String> {
    Context mainActivity;

    private String serverURL;
    private String postParameters;
    String result;
    private String TAG = "phptest";

    ProgressDialog progressDialog;
    RequestHttpConnection requestHttpConnection;


    public NetworkTask(String url, String parameters, Context context){
        this.serverURL = url;
        this.postParameters = parameters;
        this.mainActivity = context;
        progressDialog = new ProgressDialog(context);
    }



    @Override
    protected void onPreExecute() {
        //super.onPreExecute();

        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setCancelable(true);
        //progressDialog.setMessage("now loading");
        progressDialog = ProgressDialog.show(mainActivity, "title", "message", false);
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();

        Log.d(TAG, "response - ");
    }


    @Override
    protected String doInBackground(Void... params) {
        //SystemClock.sleep(2000);

        requestHttpConnection = new RequestHttpConnection();
        result = requestHttpConnection.request(serverURL, postParameters);

        return result;
    }

}
