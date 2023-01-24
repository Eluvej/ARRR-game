package com.example.arrr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
///klasa aktywujaca wszystko
public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///dzialanie gry w pelnym oknie (fullscreen)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ///uzyskanie wielkosci ekranu
        Point point =new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

        ///pokazanie widoku gry na ekranie
        setContentView(gameView);
    }
///system pauz
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
///system resume
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}