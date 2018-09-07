package app.rstone.com.myappbmi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        final Context this_ = BMI.this;
        final EditText height = findViewById(R.id.height);
        final EditText weight = findViewById(R.id.weight);
        final TextView result = findViewById(R.id.res);

        class Calc{
            String result;
            double height, weight;

            public void excute(String res) {
                double bmi = weight/(height*height/10000) ;
                if(bmi<18.5){
                    result = "저체중";
                }else if(bmi>=18.5 && bmi<23){
                    result = "정상";
                }else if(bmi>=23 && bmi<25){
                    result = "비만 전단계";
                }else if(bmi>=25 && bmi<30){
                    result = "1단계 비만";
                }else if(bmi>=30 && bmi<35){
                    result = "2단계 비만";
                }else{
                    result = "3단계 비만";
                }
            }
        }

       findViewById(R.id.btn).setOnClickListener(
                (View v) ->{
                    Calc c = new Calc();
                    c.height=Double.parseDouble(height.getText().toString());
                    c.weight=Double.parseDouble(weight.getText().toString());

                Toast.makeText(this_,"AAA",Toast.LENGTH_LONG).show();
                result.setText("BMI단계는 : "+c.result);
                }
        );




    }
}
