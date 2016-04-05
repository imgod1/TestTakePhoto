package com.example.gaokang.testtakephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int TAKE_PHOTO_FROM_CAMERA = 0x00;
    public static final int TAKE_PHOTO_FROM_ALBUM = 0x01;
    private TextView txt_camera;
    private TextView txt_album;
    private ImageView img_main;
    private Uri imgUri;

    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        txt_camera.setOnClickListener(this);
        txt_album.setOnClickListener(this);
    }

    private void initView() {
        txt_camera = (TextView) findViewById(R.id.txt_camera);
        txt_album = (TextView) findViewById(R.id.txt_album);
        img_main = (ImageView) findViewById(R.id.img_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_camera:
                Intent intent1 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                imgPath = new File(Environment
                        .getExternalStorageDirectory(), "avatar_"
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg").getAbsolutePath();
                imgUri = Uri.fromFile(new File(imgPath));
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent1, TAKE_PHOTO_FROM_CAMERA);
                break;
            case R.id.txt_album:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2, TAKE_PHOTO_FROM_ALBUM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO_FROM_CAMERA:
                if (Activity.RESULT_OK == resultCode) {
                    img_main.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                }
                break;
            case TAKE_PHOTO_FROM_ALBUM:
                if (Activity.RESULT_OK == resultCode) {
                    imgUri = data.getData();
                    img_main.setImageURI(imgUri);
                }
                break;
        }
    }
}
