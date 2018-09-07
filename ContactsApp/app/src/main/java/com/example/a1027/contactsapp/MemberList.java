package com.example.a1027.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.a1027.contactsapp.Main.*;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        Context this_ = MemberList.this;
        ItemList query = new ItemList(this_);
        ListView memberList = findViewById(R.id.memberList);

         /*
        ArrayList<Member> list = (ArrayList<Member>) new ListService(){

            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform();
        */
        //memberList.setAdapter(new MemberAdapter(this_,list)); 함수넣기전

        memberList.setAdapter(new MemberAdapter(this_,(ArrayList<Member>) new ListService(){

            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform()));

        memberList.setOnItemClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Intent intent = new Intent(this_,MemberDetail.class);
                    Member m = (Member) memberList.getItemAtPosition(i);
                    intent.putExtra("seq",m.seq+"");
                    startActivity(intent);

                }
        );


    }

    //추상팩토리
    private class ListQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public ListQuery(Context this_) {
            super(this_);
            helper = new Main.SQLiteHelper(this_);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemList extends ListQuery{
        public ItemList(Context this_) {
            super(this_);
        }
        public ArrayList<Member> execute(){
            ArrayList<Member> list = new ArrayList<>();
            //Cursor는 ResultSet과 같다
            Cursor c = this.getDatabase().rawQuery(
                    " SELECT * FROM MEMBER ",null
            );
            Member m =null;
            if(c != null){
                while(c.moveToNext()){
                    m = new Member();
                    m.seq = Integer.parseInt(c.getString(c.getColumnIndex(MEMSEQ)));
                    m.name = c.getString(c.getColumnIndex(MEMNAME));
                    m.pw = c.getString(c.getColumnIndex(MEMPW));
                    m.email = c.getString(c.getColumnIndex(MEMEMAIL));
                    m.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                    m.addr = c.getString(c.getColumnIndex(MEMADDR));
                    m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    list.add(m);

                }
                Log.d("등록된 회원 수가",list.size()+"");
            }else{
                Log.d("등록된 회원이","없습니다");
            }
            return list;
        }

    }
    private class MemberAdapter extends BaseAdapter{
        ArrayList<Member> list;
        LayoutInflater inflater;

        public MemberAdapter(Context this_, ArrayList<Member> list) {
            this.list = list;
            this.inflater=LayoutInflater.from(this_);

        }
        private int[] photos ={
                R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3,
                R.drawable.profile_4,
                R.drawable.profile_5
        };

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.member_item,null);
                holder = new ViewHolder();
                holder.profile = v.findViewById(R.id.profile);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);

            }else{
                holder = (ViewHolder) v.getTag();
            }
            holder.profile.setImageResource(photos[i]);
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;

    }

}
