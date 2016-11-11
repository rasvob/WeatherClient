package com.diamonddesign.rasvo.weatherclient.colors;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.diamonddesign.rasvo.weatherclient.R;

import java.util.Random;

/**
 * Created by rasvo on 11.11.2016.
 */

public class MaterialColorProvider {
    private Context context;
    private Random random;
    private TypedArray colorArray;
    private int[] colors;

    public MaterialColorProvider(Context context) {
        this.context = context;
        random = new Random();
        colorArray = context.getResources().obtainTypedArray(R.array.color_array);
        colors = new int[colorArray.length()];

        for (int i = 0; i < colorArray.length(); i++) {
            colors[i] = colorArray.getColor(i, 0);
        }

        colorArray.recycle();
    }

    public int getRandomColor() {
        int idx = random.nextInt(colors.length);
        return colors[idx];
    }

    public int getColorForPosition(int pos) {
        return colors[pos % colors.length];
    }
}
