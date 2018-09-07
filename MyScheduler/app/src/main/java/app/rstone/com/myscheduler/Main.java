package app.rstone.com.myscheduler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;
        class MyDate{
            //이너클래스 이용
            String year, month, day, hour, minute;
        }
        final TextView today = findViewById(R.id.today);
        final TextView year = findViewById(R.id.year);
        final TextView month = findViewById(R.id.month);
        final TextView day = findViewById(R.id.day);
        final TextView hour = findViewById(R.id.hour);
        final TextView minute = findViewById(R.id.minute);
        final CalendarView calender = findViewById(R.id.calender);
        final TimePicker time = findViewById(R.id.time);
        final Button btnEnd = findViewById(R.id.btnEnd);
        calender.setVisibility(View.VISIBLE);
        time.setVisibility(View.INVISIBLE);

        today.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        final MyDate m = new MyDate();
        calender.setOnDateChangeListener((view, year1, month1, dayOfMonth) -> {
            m.year=year1+"";
            m.month=(month1+1)+"";
            m.day=dayOfMonth+"";
            Toast.makeText(ctx,year1+"/"+(month1+1)+"/"+dayOfMonth,Toast.LENGTH_LONG).show();
        });


        findViewById(R.id.rdoCalendar).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.VISIBLE);
                    time.setVisibility(View.INVISIBLE);
                }
        );
        findViewById(R.id.rdoTime).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);
                }
        );
        findViewById(R.id.btnEnd).setOnClickListener(
                (View v)->{
                    /*Toast.makeText(ctx,"현재시간"+time.getHour(),Toast.LENGTH_LONG).show();
                    Toast.makeText(ctx,year+"/"+month+"/"+day,Toast.LENGTH_LONG).show();*/
                    year.setText(m.year);
                    month.setText(m.month);
                    day.setText(m.day);
                    hour.setText(time.getHour()+"");
                    minute.setText(time.getMinute()+"");
                }
        );
    }
}
