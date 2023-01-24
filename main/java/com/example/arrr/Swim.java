package com.example.arrr;

import static com.example.arrr.GameView.screenRatioX;
import static com.example.arrr.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
///model gracza
public class Swim {
    int toShoot = 0, shootCounter = 1;
    boolean isGoingup = false;
    int x, y, width, height, swimCounter;
    Bitmap swim1, bullet1, bullet2, bullet3, bullet4, bullet5;
    private final GameView gameView;
    Swim(GameView gameView, int screenY, Resources res)
    {
        ///wgranie tła
        this.gameView = gameView;
        swim1 = BitmapFactory.decodeResource(res, R.drawable.swim1);

        width = swim1.getWidth();
        height = swim1.getHeight();

        width /= 4;
        height /=4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        swim1 = Bitmap.createScaledBitmap(swim1, width, height, false);

        ///wgranie animacji strzelania
        bullet1 = BitmapFactory.decodeResource(res, R.drawable.bullet1);
        bullet2 = BitmapFactory.decodeResource(res, R.drawable.bullet2);
        bullet3 = BitmapFactory.decodeResource(res, R.drawable.bullet3);
        bullet4 = BitmapFactory.decodeResource(res, R.drawable.bullet4);
        bullet5 = BitmapFactory.decodeResource(res, R.drawable.bullet5);

        bullet1 = Bitmap.createScaledBitmap(bullet1, width, height, false);
        bullet2 = Bitmap.createScaledBitmap(bullet2, width, height, false);
        bullet3 = Bitmap.createScaledBitmap(bullet3, width, height, false);
        bullet4 = Bitmap.createScaledBitmap(bullet4, width, height, false);
        bullet5 = Bitmap.createScaledBitmap(bullet5, width, height, false);

        y = screenY /2;
        x = (int) (64 * screenRatioX);


    }
    ///poruszanie sie
    Bitmap getSwim (){
        if(toShoot != 0)
        {
            if(shootCounter == 1) {
                shootCounter++;
                return bullet1;
            }
            if(shootCounter == 2) {
                shootCounter++;
                return bullet2;
            }
            if(shootCounter == 3) {
                shootCounter++;
                return bullet3;
            }
            if(shootCounter == 4) {
                shootCounter++;
                return bullet4;
            }
            shootCounter = 1;
            toShoot--;
            gameView.newBullet();
            return bullet5;
            }

            if (swimCounter == 0)
            {
            swimCounter++;
            return  swim1;
            }
        swimCounter--;
        return swim1;

    }
    Rect getCollisionShape()
    {
        return new Rect(x, y, x+width, y+height);
    }

}
