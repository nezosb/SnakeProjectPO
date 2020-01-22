package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion fruitRegion = AssetHandler.getFruitRegion();
        batch.draw(fruitRegion ,position.x* GameMap.TILESIZE, position.y*GameMap.TILESIZE,
                GameMap.TILESIZE,GameMap.TILESIZE);

    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
