package com.example.arrr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
///tryp pelnoekranowy, zapisywanie wyniku (rekordu)
public class MainActivity extends AppCompatActivity {

//Walter Jędraszek Student Elekroniki na Politechnice Gdańskiej :)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); /// wyświetlanie na pełnym ekranie

        setContentView(R.layout.activity_main);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
        ///zapisywanie wyniku gracza
        TextView highScoreTxt = findViewById(R.id.highScore);

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("High Score: " + prefs.getInt("highscore", 0));
    }
}