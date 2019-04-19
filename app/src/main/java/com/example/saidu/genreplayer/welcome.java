package com.example.saidu.genreplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class welcome extends AppCompatActivity {
    LinearLayout l1,l2;
    Button btnsub;
    Animation uptodown,downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //btnsub = (Button)findViewById(R.id.buttonsub);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        Thread timer=new Thread(){

            @Override
            public void run() {
                try
                {
                    sleep(2000);
                }catch (InterruptedException e)
                {

                }
                finally {
                    Intent intent = new Intent(welcome.this, splashscreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();




    }
}
