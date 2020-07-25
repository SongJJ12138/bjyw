package com.bjyw.bjckyh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bjyw.bjckyh.R;
import com.bjyw.bjckyh.utils.TakePhoto;


public class onePicSelectDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String fileName;
    private SelectPicListener selectPicListener;

    public onePicSelectDialog(Context context,String fileName ,SelectPicListener listener) {
        super(context, R.style.CommontMyDialog);
        this.context=context;
        this.fileName=fileName;
        this.selectPicListener=listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ontpicselect);
        init();
    }

    private void init() {
        TextView tv_takephoto=findViewById(R.id.dialog_takePic);
        tv_takephoto.setOnClickListener(this);
        TextView tv_selectphoto=findViewById(R.id.dialog_selectPic);
        tv_selectphoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_takePic) {
            Uri myuri= TakePhoto.getOutputMediaFileUri(context,fileName);
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, myuri);
            openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            selectPicListener.onChooseTake(openCameraIntent);
            dismiss();
        } else if (i == R.id.dialog_selectPic) {
            Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            selectPicListener.onChooseSelect(albumIntent);
            dismiss();
        }
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
    public interface SelectPicListener{
        void onChooseTake(Intent intent);
        void onChooseSelect(Intent intent);
    }
}
