package com.example.saidu.genreplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class splashscreen extends AppCompatActivity {
    ImageView ivsplash;
    TextView tvsplash;
    DatabaseHelper mDatabaseHelper;
    public static final int MY_PERMISSION_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //ivsplash = (ImageView) findViewById(R.id.ivsplash);//imageView
       // tvsplash = (TextView) findViewById(R.id.tvsplash);//textView

        //Mysql db
        mDatabaseHelper=new DatabaseHelper(this);


    //Read Storage Permission
        if(ContextCompat.checkSelfPermission(splashscreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }
        else
        {
            dostuff();

        }



        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        //tvsplash.startAnimation(myanim);
       // ivsplash.startAnimation(myanim);


    }
    public void dostuff()
    {

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
       // tvsplash.startAnimation(myanim);
       // ivsplash.startAnimation(myanim);
        ContentResolver contentResolver=getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=contentResolver.query(songUri,null,null,null,null);

        if(songCursor !=null && songCursor.moveToFirst())
        {
            int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songPath2=songCursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            //  Toast.makeText(getApplicationContext(),songpath2,Toast.LENGTH_SHORT).show();

            do{
                try {
                    String currentTitle = songCursor.getString(songTitle);
                    String currentArtist = songCursor.getString(songArtist);
                    //String Path=songCursor.getString(songPath);
                    String Path2 = songCursor.getString(songPath2);
                    boolean insertdata = mDatabaseHelper.adddata(currentTitle, Path2, currentArtist);
                    if (insertdata) {
                       // Toast.makeText(getApplicationContext(), "vetrii", Toast.LENGTH_SHORT).show();
                        Log.d("HG", "success");
                    } else {
                        Log.d("HG", "fail");
                    }

                }
                catch (Exception e)
                {

                }
            }while(songCursor.moveToNext());
            final Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            finish();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST:{

                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this,"Permission Granted!",Toast.LENGTH_SHORT).show();
                        dostuff();
                    }
                } else {
                    Toast.makeText(this,"No Permission Granted!",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;

            }

        }
    }

}