package com.chyb.snake.entities.outside;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;

public class EaterBlock implements OutsideEntity {
    private final GameMap map;
    private Vector2D position;
    private Direction direction;
    private boolean defeated;

    public EaterBlock(GameMap map,Vector2D position, Direction direction){
        this.map = map;
        this.position = position;
        this.direction = direction;
    }

    @Override
    public void update(boolean tick){
        if(tick) {
            Vector2D target = position.add(direction.toUnitVector);
            if (position.x == -10) defeated = true;
        }
    }
    @Override
    public Vector2D getPosition() {
        return this.position;
    }
    @Override
    public void draw() {
        TextureRegion region = AssetHandler.getBlockRegion();
        Batch batch = AssetHandler.getBatch();

        batch.draw(region,getPosition().x*GameMap.TILESIZE, getPosition().y*GameMap.TILESIZE);
        batch.draw(region,(getPosition().x+1)*GameMap.TILESIZE, getPosition().y*GameMap.TILESIZE);
        batch.draw(region,getPosition().x*GameMap.TILESIZE, (getPosition().y+1)*GameMap.TILESIZE);
        batch.draw(region,(getPosition().x+1)*GameMap.TILESIZE, (getPosition().y+1)*GameMap.TILESIZE);
    }
    @Override
    public boolean toRemove() {
        return false;
    }

}
