package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Vector2D;

public class Ghost implements MapEntity
{
    private Vector2D position;

    public Ghost(Vector2D position){
        this.position = position.copy();
    }
    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        TextureRegion ghostReg = AssetHandler.getGhostRegion();
        batch.draw(ghostReg,position.x*GameMap.TILESIZE,position.y*GameMap.TILESIZE);
        batch.end();
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
