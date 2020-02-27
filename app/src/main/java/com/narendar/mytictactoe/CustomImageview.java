package com.narendar.mytictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/*
This class is inflate the custome image for tic marks by user or System
getView from Adapter would use this class to return the view(i.e ImageView)
 */
public class CustomImageview extends ImageView {

    public CustomImageview(Context context) {
        super(context);
    }

    public CustomImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
