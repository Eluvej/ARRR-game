package com.example.arrr;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
///wgranie tla
public class Background {
    int x=0, y=0;
    Bitmap background;
    Background (int screenX, int screenY, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.tlo2);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
        
    }
}
