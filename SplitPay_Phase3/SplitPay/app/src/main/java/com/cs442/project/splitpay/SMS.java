package com.cs442.project.splitpay;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

public class SMS implements Runnable {
    String phoneNumber;
    String message;

    public SMS(){

    }

    public SMS(String phoneNumber, String message){
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    @Override
    public void run() {
        sendSMS(phoneNumber, message);
    }

    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
