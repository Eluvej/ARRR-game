package com.example.arrr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/// glowna czesc programu, wyswietlanie na ekranie
public class GameView extends SurfaceView implements Runnable {

    private Thread thread; //deklaracja wątku
    private boolean isPlaying, isGameOver = false;
    private int screenX;
    private int screenY;
    private int score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Random random;
    private Enemy[] enemys;
    private List<Bullet> bullets;
    private Swim swim;
    private Background background1, background2;
    private SharedPreferences prefs;
    private GameActivity activity;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background2.x = screenX; /// ustawienie drugiego tla poza ekranem

        swim = new Swim(this, screenY, getResources());

        bullets = new ArrayList<>();

        paint = new Paint();

        ///wyświetlanie scor pod czas gry
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        ///generowanie liczby przeciwników
        enemys = new Enemy[10];
                for(int j = 0; j <10; j++) {
                    Enemy enemy = new Enemy(getResources());
                    enemys[j] = enemy;
                }
            random = new Random();
    }

    @Override
    public void run() {
        while(isPlaying)
        {
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        ///poruszanie sie tla
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;
        ///jeli backgorund1 wyjdzie za ekran to zmiana jego polozenia na prawo przed ekranem, analogicznie z background2
        if(background1.x + background1.background.getWidth() < 0 )
        {
            background1.x = screenX;
        }
        if(background2.x + background2.background.getWidth() < 0 )
        {
            background2.x = screenX;
        }
        if(swim.isGoingup)
        {
            swim.y -= 30 * screenRatioY;
        }
        else
            swim.y += 30 * screenRatioY;

        ///warunki sprawdzające abyśmy nie wykroczyli za ekran
        if(swim.y < 0 )
        {
            swim.y = 0;
        }
        if(swim.y > screenY - swim.height)
        {
            swim.y = screenY - swim.height;
        }

        List<Bullet> trash = new ArrayList<>();

        ///zestrzelenie przciwnika
        for (Bullet bullet :bullets)
        {
            if(bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50 * screenRatioX;

            for(Enemy enemy : enemys)
            {
                if(Rect.intersects(enemy.getCollisionShape(), bullet.getCollisionShape()))
                {
                    score++;
                    enemy.x = -500;
                    bullet.x = screenX + 500;
                    enemy.wasShot = true;
                }
            }
        }



///usuanie pociskow
        for (Bullet bullet: trash)
            bullets.remove(bullet);
///pojawianie się przeciwników
        for (Enemy enemy : enemys)
        {
            enemy.x -= enemy.speed;

            if(enemy.x + enemy.width < 0)
            {
                if(!enemy.wasShot)
                {
                    isGameOver = true;
                    return;
                }

                int bound = (int)(30 * screenRatioX);
        ///zwiekszanie predkosci przeciwnikow (stopniowanie trudnosci)
                if(score<10) {
                    enemy.speed = random.nextInt(bound);
                }
                if((score<20)&&(score>=10)) {
                    enemy.speed = random.nextInt(bound)+5;
                }
                if((score>=20)&&(score<30)) {
                    enemy.speed = random.nextInt(bound)+10;
                }
                if((score>=30)&&(score<50)) {
                    enemy.speed = random.nextInt(bound)+15;
                }
                if((score>=50)&&(score<100)) {
                    enemy.speed = random.nextInt(bound)+20;
                }
                if(score>=100) {
                    enemy.speed = random.nextInt(bound)+25;
                }

                if(enemy.speed < 10 * screenRatioX) {
                    enemy.speed = (int) (10 * screenRatioX);
                }

                enemy.x = screenX;
                enemy.y = random.nextInt(screenY - enemy.height);

                enemy.wasShot = false;
            }

            if(Rect.intersects(enemy.getCollisionShape(), swim.getCollisionShape()))
            {
                isGameOver = true;
                return;
            }

        }
    }

    private void draw(){
        if (getHolder().getSurface().isValid())
        {
            ///rysowanie tla na ekranie
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            ///przeciwnik staje się widzialny dla gracza
            for(Enemy enemy : enemys)
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);

            ///wyświetlanie scor
            canvas.drawText(score + "", screenX / 2f, 164, paint);

            ///czynnosci ktore dzieja sie po przegranej gracza
            if(isGameOver)
            {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore(); ///wynik gracza
                waitBeforeExiting(); ///pauza przed ekranem pczatkowym
                return;
            }

            canvas.drawBitmap(swim.getSwim(), swim.x, swim.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }
///odczekanie sekundy przed wyswietleniem menu poczatkowego
    private void waitBeforeExiting() {
        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    ///sprawdzanie obecnego wyniku gracza z jego rekordem
    private void saveIfHighScore() {
        if(prefs.getInt("highscore", 0) < score)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }
///generowanie mniej wiecej 60 klatek na sekunde
    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(  this);
        thread.start();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    ///Poruszanie się gracza (góra, dół)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ///poruszanie sie gracza na dotyk

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2)
                {
                    swim.isGoingup = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                swim.isGoingup = false;
                if(event.getX() > screenX/2)
                {
                    swim.toShoot++;
                }
                break;
        }
        return true;
    }
    public void newBullet(){
        Bullet bullet = new Bullet(getResources());
        bullet.x = swim.x +swim.width;
        bullet.y = swim.y + (swim.height / 2);
        bullets.add(bullet);
    }
}
