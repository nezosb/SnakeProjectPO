package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.utils.Vector2D;

public interface MapEntity {
    public Vector2D getPosition();
    public void setPosition(Vector2D position);
    public void draw();
    public boolean isHarmful();

    boolean isEatable();
}
