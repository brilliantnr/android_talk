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
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.a1027.contactsapp.Main.*;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        Context this_ = MemberDetail.this;
        //Intent intent = this.getIntent();
        String seq = this.getIntent().getExtras().getString("seq");
        Log.d("넘어온 seq 값 :: ",seq);
        /* String seq2 = intent.getStringExtra("seq");
        Log.d("넘어온 seq2 값 :: ",seq2);*/
        ItemExist query = new ItemExist(this_);
        query.seq = seq;

        //서비스인터페이스 - 쿼리실행하기
        Main.Member m = (Main.Member) new RetrieveService() {
            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();
        Log.d("검색된 이름 :: ", m.name);
        ImageView profile = findViewById(R.id.profile);
        profile.setImageDrawable(getResources()
            .getDrawable(getResources()
                .getIdentifier(
                this.getPackageName()+":drawable/"+m.photo,null,null
                    ),this_.getTheme()));
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        TextView phone = findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
        TextView addr = findViewById(R.id.addr);
        addr.setText(m.addr);

        Intent intent = new Intent(this_,MemberUpdate.class);
        //from A to B 로 생각
        findViewById(R.id.updateBtn).setOnClickListener(
                (View v)->{
                    intent.putExtra("spec",
                            m.seq
                            +","+  m.name
                            +","+ m.pw
                            +","+ m.phone
                            +","+  m.photo
                            +","+ m.email
                            +","+ m.addr
                               );
                    startActivity(intent);
                }
        );
        findViewById(R.id.callBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.dialBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.smsBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.emailBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.albumBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.movieBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.mapBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.musicBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.listBtn).setOnClickListener(
                (View v)->{}
        );



    }
    private class DetailQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public DetailQuery(Context this_) {
            super(this_);
            helper = new Main.SQLiteHelper(this_);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemExist extends  DetailQuery{
        String seq;
        public ItemExist(Context this_) {
            super(this_);
        }
        public  Main.Member execute(){
            Main.Member m = null;
            Cursor c = this.getDatabase().rawQuery(String.format(
                    " SELECT * FROM %s WHERE %s LIKE '%s' ",MEMTAB,MEMSEQ,seq
                    ),null
            );
            if(c!=null){
                if(c.moveToNext()){
                    m = new Main.Member();
                    m.seq = Integer.parseInt(c.getString(c.getColumnIndex(MEMSEQ)));
                    m.name=c.getString(c.getColumnIndex(MEMNAME));
                    m.pw=c.getString(c.getColumnIndex(MEMPW));
                    m.email=c.getString(c.getColumnIndex(MEMEMAIL));
                    m.photo=c.getString(c.getColumnIndex(MEMPHOTO));
                    m.addr=c.getString(c.getColumnIndex(MEMADDR));
                    m.phone=c.getString(c.getColumnIndex(MEMPHONE));
                }
                Log.d("찾는 회원은 :: ",m+"");

            }else{
                Log.d("찾는 회원이 ","없습니다");
            }
            return m;
        }
    }
}
