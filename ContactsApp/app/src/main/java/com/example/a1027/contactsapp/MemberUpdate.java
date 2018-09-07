package com.example.a1027.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.a1027.contactsapp.Main.*;

public class MemberUpdate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        Context this_ = MemberUpdate.this;
        String[] spec = getIntent().getStringExtra("spec").split(",");
        ImageView profile =  findViewById(R.id.profile);
        profile.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(
            this.getPackageName()+":drawable/"+spec[4],null,null)
                ,this_.getTheme()));
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        EditText address = findViewById(R.id.address);
        name.setText(spec[1]);
        phone.setText(spec[3]);
        email.setText(spec[5]);
        address.setText(spec[6]);
            /*        m.seq
                +","+  m.name
                +","+ m.pw
                +","+ m.phone
                +","+  m.photo
                +","+ m.email
                +","+ m.addr*/
        findViewById(R.id.confirmBtn).setOnClickListener(
                (View v)->{
                    ItemUpdate query = new ItemUpdate(this_);
                    Log.d("========================","전==================");
                    query.m.seq=Integer.parseInt(spec[0]);
                    Log.d("========================","전==================");
                    query.m.name=(name.getText().toString().equals(""))?
                            spec[1]:name.getText().toString();
                    //query.m.pw=(pw.getText().toString().equals(""))?spec[2]:pw.getText().toString();
                    query.m.email=(email.getText().toString().equals(""))?
                            spec[5]:email.getText().toString();
                    //query.m.photo=(photo.getText().toString().equals(""))?spec[4]:photo.getText().toString();
                    query.m.addr=(address.getText().toString().equals(""))?
                            spec[6]:address.getText().toString();
                    query.m.phone=(phone.getText().toString().equals(""))?
                            spec[3]:phone.getText().toString();
                    Log.d("=====쿼리 query.m.name::", query.m.name);
                    Log.d("=====쿼리 query.m.email::", query.m.email);
                    Log.d("=====쿼리 query.m.addr::", query.m.addr);
                    Log.d("=====쿼리 query.m.phone::", query.m.phone);

                    new Main.StatusService(){
                        @Override
                        public void perform() {
                            query.execute();
                        }
                    }.perform();
                    Intent moveToDetail = new Intent(this_,MemberDetail.class);
                    Log.d("=====쿼리 spec[0]::", spec[0]);
                    moveToDetail.putExtra("seq",spec[0]);
                    startActivity(moveToDetail);
                }
        );
        findViewById(R.id.cancelBtn).setOnClickListener(
                (View v)->{
                    Intent moveDetail = new Intent(this_,MemberDetail.class);
                    moveDetail.putExtra("seq",spec[0]);
                    startActivity(moveDetail);
                }
        );
    }
    private class UpdateQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public UpdateQuery(Context this_) {
            super(this_);
            helper = new Main.SQLiteHelper(this_);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemUpdate extends UpdateQuery{
        //String seq, email,name, phone, address, profile;
        Main.Member m ;
        public ItemUpdate(Context this_) {
            super(this_);
            m=new Main.Member();
        }
        public void execute(){
            Log.d("===================================sql : ",String.format(
                    " UPDATE %s SET " +
                            " %s = '%s' " +
                            " , %s = '%s' " +
                            " , %s = '%s' " +
                            " , %s = '%s' " +
                            " WHERE %s LIKE '%s' ",
                    MEMTAB,
                    MEMNAME,m.name,MEMPHONE,m.phone,
                    MEMEMAIL,m.email,MEMADDR,m.addr,
                    MEMSEQ,m.seq
            ));
            getDatabase().execSQL(String.format(
                    " UPDATE %s SET " +
                        " %s = '%s' " +
                        " , %s = '%s' " +
                        " , %s = '%s' " +
                        " , %s = '%s' " +
                        " WHERE %s LIKE '%s' ",
                    MEMTAB,
                    MEMNAME,m.name,MEMPHONE,m.phone,
                    MEMEMAIL,m.email,MEMADDR,m.addr,
                    MEMSEQ,m.seq
                    ));
        }
    }
}
