package com.narendar.mytictactoe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.content.ContextCompat;

/*
    Adapter class to fill the data in grid layout .
 */
public class MyGridAdapter extends BaseAdapter {


    private int[][] mGrid;
    private Context mContext;

    public MyGridAdapter(Context context, int[][] board) {
        mGrid = board;
        mContext = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Integer getItem(int i) {
        TicMark mark = TicTacToeGame.posToPoint(i);
        return mGrid[mark.a][mark.b];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawable userTick      = ContextCompat.getDrawable(mContext, R.drawable.usertick);
        Drawable systemTick    = ContextCompat.getDrawable(mContext, R.drawable.systemtick);
        CustomImageview imageView = new CustomImageview(mContext);
        imageView.setBackgroundColor(Color.WHITE);

        //To set the System & User tic with corresponding image mark. & return it.
        if (getItem(position) == TicTacToeGame.USER_GAME) {
            imageView.setImageDrawable(systemTick);
        } else if (getItem(position) == TicTacToeGame.SYSTEM_GAME) {
            imageView.setImageDrawable(userTick);
        }
        return imageView;
    }

    public void update(int[][] board) {
        mGrid = board;
        notifyDataSetChanged();
    }
}
