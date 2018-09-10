package com.example.a1027.contactsapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public class PhoneUtil {
    private Context _this;
    private Activity act;
    private String phoneNum;
    //Context, Activity는 생성자
    //String phoneNum -getter,setter

    //contructor 해야한다!
    public PhoneUtil(Context _this, Activity act) {
        this._this = _this;
        this.act = act;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void dial(){
        _this.startActivity(
                new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum))
        );
    }
    public void call(){

        //if문 - google APIs 에서 복사
        if (ActivityCompat.checkSelfPermission(_this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.CALL_PHONE},
                    2);
        }else{
            _this.startActivity(
                    new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum))
            );
        }
    }

}
