package com.narendar.mytictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    private GridView mGridView;
    private Button mStart;
    private MyGridAdapter myGridAdapter;
    private TicTacToeGame mTicTacToeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intialization
        mStart      = findViewById(R.id.btn_start);
        mGridView   = findViewById(R.id.grid_view);
        myGridAdapter = new MyGridAdapter(this, new int[3][3]);

        mGridView.setAdapter(myGridAdapter);
        mGridView.setOnItemClickListener(this);

        mTicTacToeGame = new TicTacToeGame(TicTacToeGame.SYSTEM_GAME);

        mStart.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_start:
                Log.i(TAG,"start new game ");
                mTicTacToeGame = new TicTacToeGame(TicTacToeGame.SYSTEM_GAME);
                myGridAdapter.update(mTicTacToeGame.getmBoard());
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick()");
        if (mTicTacToeGame != null && !mTicTacToeGame.isOver() && mTicTacToeGame.getBoardItem(TicTacToeGame.posToPoint(position)) == 0) {
            mTicTacToeGame.play(TicTacToeGame.posToPoint(position));
            myGridAdapter.update(mTicTacToeGame.getmBoard());
            if (mTicTacToeGame.isOver()) {
                mStart.setVisibility(View.VISIBLE);
                if (mTicTacToeGame.isUserWon()) {
                    Log.i(TAG,"You have Lost the game");
                    Toast.makeText(MainActivity.this,"YOU LOST",Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG,"Game Draw");
                    Toast.makeText(MainActivity.this,"GAME DRAW",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
