package com.bjyw.bjckyh.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bjyw.bjckyh.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends Activity implements android.view.GestureDetector.OnGestureListener{
    private List<String> photiList=new ArrayList<>();
    private GestureDetector gestureDetector;
    private ImageView img;
    private int firstPictureCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("photo");
        photiList=bundle.getStringArrayList("photo");
        img = (ImageView) findViewById(R.id.imageView_show);
        gestureDetector = new GestureDetector(this); // 声明检测手势事件
        firstPictureCode=intent.getIntExtra("firstCode",0);// 设置初始背景图片
        String FirstPhoto = photiList.get(firstPictureCode);
        if (!FirstPhoto.equals("")) {
            Glide.with(getApplicationContext())
                    .load("http://img.bjckyh.com/" + FirstPhoto)
                    .into(img);
        } else {
            Toast.makeText(getApplicationContext(), "未找到相关照片资源，请重试！", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 120) { // 从左向右滑动（左进右出）
            firstPictureCode--;
            if (firstPictureCode >= 0) {
                String path=photiList.get(firstPictureCode);
                if (!path.equals("")) {
                    Glide.with(getApplicationContext())
                            .load("http://img.bjckyh.com/"+ path)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(img);
                }
            }else {
                Toast.makeText(getApplicationContext(),"已经没有照片啦!",Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (e2.getX() - e1.getX() < -120) { // 从右向左滑动（右进左出）
            firstPictureCode++;
            if (firstPictureCode < photiList.size()) {
                String path=photiList.get(firstPictureCode);
                if (!path.equals("")) {
                    Glide.with(getApplicationContext())
                            .load("http://img.bjckyh.com/" + path)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(img);
                }
            }else {
                Toast.makeText(getApplicationContext(),"已经没有照片啦!",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event); // 注册手势事件
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}