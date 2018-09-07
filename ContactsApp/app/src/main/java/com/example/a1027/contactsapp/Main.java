package com.example.a1027.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Context this_=Main.this;
        findViewById(R.id.next_btn).setOnClickListener(
                (View v)->{
                    SQLiteHelper helper=new SQLiteHelper(this_);
                    //이거 옮김
                    startActivity(new Intent(this_,Login.class));
                }
        );
/*        findViewById(R.id.moveLogin).setOnClickListener(
                (View v)->{

                }
        );*/
    }
    //DB 만들기
    //생체정보로 뚫고 온거라 보안 신경안쓰고 static
    static class Member{int seq; String name, pw, email, phone, addr, photo; }

    //인터페이스도 이거로 끝
    static interface StatusService{public void perform();}
    static interface ListService{public List<?> perform();}
    static interface RetrieveService{public Object perform();}
    static String DBNAME = "rstone.db";
    static String MEMTAB = "MEMBER";
    static String MEMSEQ = "SEQ";
    static String MEMNAME = "NAME";
    static String MEMPW = "PW";
    static String MEMEMAIL= "EMAIL";
    static String MEMPHONE = "PHONE";
    static String MEMADDR = "ADDR";
    static String MEMPHOTO= "PHOTO";
    static abstract class QueryFactory{
        Context this_;

        public QueryFactory(Context this_) {
            this.this_ = this_;
        }
        public abstract SQLiteDatabase getDatabase();
        //SQLiteDatabase : 안드로이드에 내장된 DB (only 안드로이드스튜디오에서만 된다. JAVA로만)
    }
    static class SQLiteHelper extends SQLiteOpenHelper{
        //SQLiteOpenHelper <- 내장된

        //[Alt+Insert]-Constructor
        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1);
            this.getWritableDatabase();
            //수정 가능한 DB
        }


        //[Alt+Insert]-Implement Method ->onCreate/onUpgrade
        //쿼리팩토리가 추상이니까 impl 해야하는 것
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    " CREATE TABLE IF NOT EXISTS %s "
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " %s TEXT, %s TEXT, %s TEXT, "
                    + " %s TEXT, %s TEXT, %s TEXT "
                    + " ) ",
                    MEMTAB,MEMSEQ,
                    MEMNAME,MEMPW,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO
            );
            Log.d("실행할 쿼리 :: ",sql);
            db.execSQL(sql);
            Log.d("====================","create 쿼리실행 완료");
            String[] names ={"김태형","박지민","전정국","김남준","김석진"};
            String[] nicks = {"tae","jimin","jk","rm","jin"};

            for (int i=1;i<=5;i++){
                db.execSQL(String.format(
                        " INSERT INTO %s "
                                + " ( "
                                + " %s , %s , %s , "
                                + " %s , %s , %s  "
                                + " ) VALUES ("
                                + " '%s' , '%s' , '%s' , "
                                + " '%s' , '%s' , '%s'  "
                                + ") ",
                        MEMTAB,
                        MEMNAME,MEMPW,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO,
                        names[i-1],"1",nicks[i-1]+"@test.com","010-1234-567"+i,
                        "신촌"+i+"길","profile_"+i

                ));

            }
            Log.d("===================="," insert 쿼리실행 완료");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTAB);
            onCreate(db);
        }
    }

   /*
   //로그인쿼리
    private class LoginQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context this_) {
            super(this_);
            helper = new SQLiteHelper(this_);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
            //Readable = Read Only
        }
    }
*/











}
