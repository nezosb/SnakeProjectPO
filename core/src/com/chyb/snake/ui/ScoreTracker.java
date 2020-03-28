package com.chyb.snake.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.utils.AssetHandler;

public class ScoreTracker {

    private int multiplied;
    private int score;
    private float multipliedTime;
    private final float multipliedTimeMax = 8f;
    private ScoreList scoreList;

    public ScoreTracker(){
        multipliedTime = 0;
        score = 70;
        scoreList = new ScoreList();
    }
    public void update(float delta){
        multipliedTime -= delta;
        if(multipliedTime <= 0){
            multipliedTime = 0;
            multiplied = 0;
        }
        scoreList.update(delta);
    }
    public void draw(Batch batch){

        int dispScore = score;
        MyFontB.draw(batch,"score",20,290);
        for(int i = 0; i < 10;i++){
            if(dispScore == 0) batch.setColor(Color.DARK_GRAY);
            MyFontB.draw(batch,dispScore % 10 + "",119 - 10*i,274);
            dispScore /= 10;
        }
        batch.setColor(Color.WHITE);


        float multiplierX = (float) Math.min(100*(multipliedTimeMax-multipliedTime),20);
        MyFontB.draw(batch,"x",multiplierX,230,2);
        if(multiplied == 0){
            MyFontB.draw(batch," 0",multiplierX,230,2);
        }else{
            int firstVal = (multiplied -1)/10 + 1;
            int secondVal = (multiplied - 1) % 10;
            MyFontB.draw(batch," "+Integer.toString(firstVal), multiplierX,230,2);
            MyFontB.draw(batch,".",multiplierX + 32,230,2);
            MyFontB.draw(batch,Integer.toString(secondVal),multiplierX+44,230,2);
        }

        float barLength = 50f;
        if(multipliedTime <= multipliedTimeMax-1){
            barLength = 50f / (multipliedTimeMax-1)*multipliedTime;
        }

        //Bar
        batch.draw(AssetHandler.getColorRects()[1],multiplierX + 80,240,54,8);
        batch.draw(AssetHandler.getColorRects()[0],multiplierX + 80 + 2,240+2,barLength,4);

        //TODO remove
        MyFontB.draw(batch,""+ Gdx.graphics.getFramesPerSecond(),20,120);
        scoreList.draw();

    }
    public void addScore(ScoreList.ScoreType scoreType){
        int scoreToAdd = scoreList.addAndExtract(scoreType);
        if(multiplied != 0) score += scoreToAdd *(1+ (multiplied-1f)/10f);
    }

    public void addMultiplier(){
        multiplied++;
        multipliedTime = multipliedTimeMax;
        addScore(ScoreList.ScoreType.Fruit);
    }
    public void reset() {
        multiplied = 0;
        scoreList.reset();
        multipliedTime=0;
        score = 0;
    }
}
