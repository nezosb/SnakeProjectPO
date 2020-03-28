package com.chyb.snake.entities.outside;

import com.chyb.snake.utils.Vector2D;

public interface OutsideEntity {

    void update(boolean tick);
    Vector2D getPosition();
    void draw();
}
