package com.chyb.snake.vfx;

import com.chyb.snake.utils.Vector2D;

public abstract class Vfx {
    protected float timer;
    protected float x,y;

    public Vfx(float x, float y) {
        timer = 0;
        this.x = x;
        this.y = y;
    }

    public void update(float delta){
        timer -= delta;
    }
    abstract void draw();
    public boolean toRemove(){
        return timer <= 0;
    }
}
