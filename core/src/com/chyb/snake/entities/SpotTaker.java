package com.chyb.snake.entities;

import com.chyb.snake.utils.Vector2D;

public class SpotTaker implements MapEntity{
    Vector2D position;
    public SpotTaker(Vector2D position){
        this.position = position;
    }
    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void draw() {}

    @Override
    public boolean isHarmful() {
        return false;
    }
    @Override
    public boolean isEatable() {
        return false;
    }
}
