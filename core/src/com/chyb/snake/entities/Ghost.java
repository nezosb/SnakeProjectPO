package com.chyb.snake.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Vector2D;

public class Ghost implements MapEntity
{
    private final Metronome metronome;
    private Vector2D position;
    private Vector2D previousPosition;

    public Ghost(Vector2D position, Metronome metronome){
        this.position = position;
        this.metronome = metronome;
        previousPosition = position;
    }
    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion ghostReg = AssetHandler.getGhostRegion();
        Vector2D shift= previousPosition.subtract(position).signum();
        if(!previousPosition.subtract(shift).equals(position)) shift = shift.opposite();
        batch.draw(ghostReg,(position.x+shift.x*(metronome.getTickPercentage())*2)*GameMap.TILESIZE,
                (position.y+shift.y*(metronome.getTickPercentage())*2)*GameMap.TILESIZE, ghostReg.getRegionWidth()/2,
                ghostReg.getRegionHeight()/2,ghostReg.getRegionWidth(),ghostReg.getRegionHeight(),
                1+metronome.getTickPercentage()/2,1+metronome.getTickPercentage()/2,0);
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

    @Override
    public boolean isEatable() {
        return false;
    }

    public void setPosition(Vector2D position) {
        this.previousPosition = this.position;
        this.position = position;
    }
}
