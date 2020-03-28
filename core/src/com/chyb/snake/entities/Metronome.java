package com.chyb.snake.entities;

public class Metronome {
    private float tickTime;
    private float time;
    public Metronome(){
        reset();
    }

    public void reset() {
        tickTime = 0.25f;
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
    public float getTickPercentage(){
        return Math.max((tickTime/3 - time) / tickTime,0);
    }
    public float getTrueTickPercentage(){
        return Math.max((tickTime-time) / tickTime,0);
    }
    public static float tickFunction(float time, float maxTime){
        return Math.max((maxTime - time) / maxTime,0);
    }
}
