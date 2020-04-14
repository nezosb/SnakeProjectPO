package com.chyb.snake.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.utils.AssetHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ScoreList {

    public enum ScoreType{
        Grow(50),
        PlaneSkip(150),
        Block(50),
        Train(100);

        public final int value;
        ScoreType(int value){
            this.value = value;
        }
    }

    private ArrayList<ScoreType> typeList = new ArrayList<ScoreType>();
    private ArrayList<Float> timeList = new ArrayList<Float>();


    public int addAndExtract(ScoreType scoreType){
        typeList.add(scoreType);
        timeList.add(0f);
        return scoreType.value;
    }
    public void update(float delta){

        for(int i = 0; i < typeList.size(); i++){
            float time = timeList.get(i);
            time += delta;
            if(time>=2){
                if(i==0) {
                    if(time>2.2f) {
                        timeList.remove(i);
                        typeList.remove(i);
                        i--;
                        continue;
                    }
                    timeList.set(i,time);
                }else{
                    timeList.set(i,2f);
                }
            }else{
                timeList.set(i,time);
            }
        }

     }
    public void draw(){
        Batch batch = AssetHandler.getBatch();
        batch.setColor(Color.GRAY);
        float startY = 200;
        for(int i = 0; i < typeList.size(); i++){
            float time = timeList.get(i);

            if(i==0 && time>2){
                float toFinishPercent = (time-2)*5;
                startY = 200 + 16*toFinishPercent;

                batch.setColor(Color.GRAY.r,Color.GRAY.b,Color.GRAY.b,1-toFinishPercent);
                MyFontB.draw(batch, "+ "+typeList.get(i).toString().toLowerCase()+" "+typeList.get(i).value,
                        25, startY-i*16,1);
                batch.setColor(Color.GRAY);
            } else {
                MyFontB.draw(batch, "+ "+typeList.get(i).toString().toLowerCase()+" "+typeList.get(i).value,
                        25, startY-i*16,1);
            }
        }
        batch.setColor(Color.WHITE);
    }
    public void reset() {
        typeList.clear();
        timeList.clear();
    }
}
