package com.example.fragmenttest2.asynchronous;

import android.widget.TextView;

public class CallBacks {
    TextView tv;
    String str;
    public CallBacks(TextView tv, String str) {
        this.tv = tv;
        this.str = str;
    }

    public void setStr() {
        tv.setText(str);
    }
}
