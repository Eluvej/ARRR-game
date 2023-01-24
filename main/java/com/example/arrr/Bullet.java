package com.example.arrr;

import static android.view.View.getDefaultSize;
import static com.example.arrr.GameView.screenRatioX;
import static com.example.arrr.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
///delaracja pociskow
public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources res)
    {
        ///deklaracja pociskow
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /=4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }

    Rect getCollisionShape()
    {
        return new Rect(x, y, x+width, y+height);
    }
}
