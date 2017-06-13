/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.davemorrissey.labs.subscaleview.sample.extension.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Pair;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.sample.MainActivity;
import com.davemorrissey.labs.subscaleview.sample.R.drawable;

import java.util.ArrayList;
import java.util.List;


public class PinView extends SubsamplingScaleImageView {

    private Context context;
    private PointF sPin;
    //private Bitmap pin;
    ArrayList<Pair<PointF, String>> mapPins;
    String tag = getClass().getSimpleName();

    public PinView(Context context) {
        this(context, null);
        this.context = context;
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initialise();
    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
//        initialise();
//        invalidate();
    }
    public void setPins(ArrayList<Pair<PointF, String>>  mapPins) {
        this.mapPins = mapPins;
        initialise();
        invalidate();
    }

    public PointF getPin() {
        return sPin;
    }

    private void initialise() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float density = getResources().getDisplayMetrics().densityDpi;
        if (mapPins == null || mapPins.size()==0){ return;}

//        int specialPt = 0;
//        if (mapPins.size()>0){
//            if(mapPins.get(0).equals(0,0)){
//                //shows the Avg
//                specialPt = 1;
//                mapPins.remove(0);
//            } else if (mapPins.get(0).equals(999,999)){
//                //shows the center
//                specialPt = 2;
//                mapPins.remove(0);
//            }
//        }
        for (int i = 0; i < mapPins.size(); i++) {
//            if (mapPins.size() > 0 && i == 0){
//                specialPt = 2;
//            }
            float w =0,h=0;
            PointF mPin = mapPins.get(i).first;
            String pinLabel = mapPins.get(i).second;
            Bitmap bmpPin = null;
            switch (pinLabel){
                case "CenterBig":
                    bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.red_curse_big5);
                    w = (density / 200f) * bmpPin.getWidth();
                    h = (density / 200f) * bmpPin.getHeight();
                    break;
                case "CenterSmall":
                    bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.red_curse_big5);
                    w = (density / 300f) * bmpPin.getWidth();
                    h = (density / 300f) * bmpPin.getHeight();
                    break;
                case "newHit":
                    bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.blue_cirle_small);
                    w = (density / 420f) * bmpPin.getWidth();
                    h = (density / 420f) * bmpPin.getHeight();
                    break;
                case "oldHit":
                    bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.red_cirle_small);
                    w = (density / 420f) * bmpPin.getWidth();
                    h = (density / 420f) * bmpPin.getHeight();
                    break;
            }
//            if (specialPt == 1) {
//                //Shows only the center
//                bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.red_curse_big5);
//                w = (density / 200f) * bmpPin.getWidth();
//                h = (density / 200f) * bmpPin.getHeight();
//            } else if (specialPt == 2 && i==0){
//                //show all hits+ center
//                bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.red_curse_big5);
//                w = (density / 300f) * bmpPin.getWidth();
//                h = (density / 300f) * bmpPin.getHeight();
//            }   else {
//                bmpPin = BitmapFactory.decodeResource(this.getResources(), drawable.blue_cirle_small);
//                w = (density / 420f) * bmpPin.getWidth();
//                h = (density / 420f) * bmpPin.getHeight();
//            }

            bmpPin = Bitmap.createScaledBitmap(bmpPin, (int) w, (int) h, true);

            PointF vPin = sourceToViewCoord(mPin);
            float vX = vPin.x - (bmpPin.getWidth() / 2);
            float vY = vPin.y - (bmpPin.getHeight() /2);
            canvas.drawBitmap(bmpPin, vX, vY, paint);



        }

    }


}
