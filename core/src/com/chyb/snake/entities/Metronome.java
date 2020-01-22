package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chyb.snake.utils.AssetHandler;

public class Metronome {
    private float tickTime;
    private float time;
    public Metronome(){
        reset();
    }

    public void reset() {
        tickTime = 0.2f;
        time = 0;
    }

    public boolean updateAndTick(float delta){
        time += delta;
        if(time > tickTime){
            time -= tickTime;
            return true;
        }
        return false;
    }

}
