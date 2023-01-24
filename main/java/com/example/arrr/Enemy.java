package com.example.arrr;

import static com.example.arrr.GameView.screenRatioX;
import static com.example.arrr.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.print.PrintAttributes;
///wgranie jak i rozklad przeciwnikow
public class Enemy {
    public int speed=20;
    public boolean wasShot = true;

    int x=0, y, width, height, enemyCounter=1 , licznik=0;
    Bitmap enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8, enemy9, enemy10;
    Enemy (Resources res)
    {
        ///deklaracja przeciwnikow
       enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemy1);
       enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy2);
       enemy3 = BitmapFactory.decodeResource(res, R.drawable.enemy3);
       enemy4 = BitmapFactory.decodeResource(res, R.drawable.enemy4);
       enemy5 = BitmapFactory.decodeResource(res, R.drawable.enemy5);
       enemy6 = BitmapFactory.decodeResource(res, R.drawable.enemy6);
       enemy7 = BitmapFactory.decodeResource(res, R.drawable.enemy7);
       enemy8 = BitmapFactory.decodeResource(res, R.drawable.enemy8);
       enemy9 = BitmapFactory.decodeResource(res, R.drawable.enemy9);
       enemy10 = BitmapFactory.decodeResource(res, R.drawable.enemy10);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false);
        enemy4 = Bitmap.createScaledBitmap(enemy4, width, height, false);
        enemy5 = Bitmap.createScaledBitmap(enemy5, width, height, false);
        enemy6 = Bitmap.createScaledBitmap(enemy6, width, height, false);
        enemy7 = Bitmap.createScaledBitmap(enemy7, width, height, false);
        enemy8 = Bitmap.createScaledBitmap(enemy8, width, height, false);
        enemy9 = Bitmap.createScaledBitmap(enemy9, width, height, false);
        enemy10 = Bitmap.createScaledBitmap(enemy10, width, height, false);

        y = -height;
    }
///generowanie przeciwników
    Bitmap getEnemy () {
            if (enemyCounter == 1) {
                enemyCounter++;
                licznik++;
                return enemy1;
            }
            if (enemyCounter == 2) {
                enemyCounter++;
                licznik++;
                return enemy2;
            }

            if (enemyCounter == 3) {
                enemyCounter++;
                licznik++;
                return enemy3;
            }

            if (enemyCounter == 4) {
                enemyCounter++;
                licznik++;
                return enemy4;
            }
            if (enemyCounter == 5) {
                enemyCounter++;
                return enemy5;
            }

            if (enemyCounter == 6) {
                enemyCounter++;
                return enemy6;
            }

            if (enemyCounter == 7) {
                enemyCounter++;
                return enemy7;
            }
            if (enemyCounter == 8) {
                enemyCounter++;
                return enemy8;
            }
            if (enemyCounter == 9) {
                enemyCounter++;
                return enemy9;
            }

            enemyCounter = 1;
            return enemy10;
    }
///obramówka w okół modelu aby zderzenia były możliwe
    Rect getCollisionShape()
    {
        return new Rect(x, y, x+width, y+height);
    }

}
