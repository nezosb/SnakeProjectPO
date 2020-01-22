package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Vector2D;

public class Fruit implements MapEntity {
    public Vector2D position;
    public Fruit(Vector2D position){
        this.position = position.copy();
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw(Batch batch) {
        ShapeRenderer sr = AssetHandler.getShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setColor(Color.CYAN);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(position.x* GameMap.TILESIZE, position.y*GameMap.TILESIZE,GameMap.TILESIZE,GameMap.TILESIZE);
        sr.end();
        sr.setColor(Color.WHITE);
    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
