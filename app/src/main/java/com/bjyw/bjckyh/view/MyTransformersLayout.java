package com.bjyw.bjckyh.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zaaach.transformerslayout.TransformersLayout;
import com.zaaach.transformerslayout.holder.TransformersHolderCreator;

import java.util.List;

public class MyTransformersLayout extends TransformersLayout {

    public MyTransformersLayout(Context context) {
        super(context);
    }

    public MyTransformersLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTransformersLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTransformersLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void load(@NonNull List data, TransformersHolderCreator creator) {
        super.load(data, creator);
    }
}
