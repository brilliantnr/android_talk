package com.example.a1027.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.a1027.contactsapp.Main.MEMPW;
import static com.example.a1027.contactsapp.Main.MEMSEQ;
import static com.example.a1027.contactsapp.Main.MEMTAB;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Context this_ = Login.this;


        findViewById(R.id.loginBtn).setOnClickListener(
                (View v)->{
                    ItemExist query = new ItemExist(this_);
                    EditText x = findViewById(R.id.inputID);
                    EditText y = findViewById(R.id.inputPW);
                    query.id=x.getText().toString();
                    query.pw=y.getText().toString();

                    //핵심★★★★★
                    new Main.StatusService(){
                        @Override
                        public void perform() {
                            if(query.excute()){
                                Toast.makeText(this_,"로그인성공",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this_,MemberList.class));

                            }else{
                                Toast.makeText(this_,"로그인실패",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this_,Login.class));
                            }
                        }
                    }.perform();

                }
        );
    }
    //로그인쿼리
    private class LoginQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context this_) {
            super(this_);
            helper = new Main.SQLiteHelper(this_);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemExist extends LoginQuery{
        String id, pw;
        public ItemExist(Context this_) {
            super(this_);
        }
        public boolean excute(){
            return super.getDatabase().rawQuery(String.format(
                    " SELECT * FROM %s "
                    + "WHERE %s LIKE '%s' "
                    + "AND %s LIKE '%s' ",
                    MEMTAB, MEMSEQ, id, MEMPW, pw
            ),null).moveToNext();
        }
    }
}
