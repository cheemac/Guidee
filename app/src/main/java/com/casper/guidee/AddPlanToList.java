package com.casper.guidee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class AddPlanToList extends LinearLayout{

    public AddPlanToList(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public AddPlanToList(Context context) {
        super(context);

        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_add_payment_plan,this,true);

    }



}
