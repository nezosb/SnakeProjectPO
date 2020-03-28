package com.chyb.snake.entities.outside;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.ui.ScoreList;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;
import com.chyb.snake.vfx.VfxHandler;

public class SkipShip implements OutsideEntity {
    private final Player player;
    private final Metronome metronome;
    private final VfxHandler vfxHandler;
    private Vector2D position;
    private Direction direction;
    private boolean defeated;
    private GameMap gMap;
    private Vector2D previousPosition;
    private boolean skipped;

    public SkipShip(Vector2D position, Direction direction, Player player, GameMap gMap, Metronome metronome, VfxHandler vfxHandler){
        this.position = position;
        this.direction = direction;
        this.player = player;
        this.gMap = gMap;
        this.metronome = metronome;
        this.vfxHandler = vfxHandler;
        previousPosition = position;
    }

    @Override
    public void update(boolean tick){
        if(tick) {
            previousPosition = position;
            Vector2D target = position.add(direction.toUnitVector);
            skipped = false;
            while (target.x >= 0 && target.x < GameMap.WIDTH && gMap.isOccupiedByHarmful(target)){
                if (player.getPosition().equals(target)){
                    player.hit();
                    break;
                }
                skipped = true;
                vfxHandler.addTrail(target.x*GameMap.TILESIZE,target.y*GameMap.TILESIZE);
                target = target.add(direction.toUnitVector);
            }
            if (player.getPosition().equals(target)) player.hit();
            if(skipped) gMap.addScore(ScoreList.ScoreType.Skip);
            position = target;
            if (direction == Direction.L && position.x == -10) defeated = true;
            if (direction == Direction.R && position.x == GameMap.WIDTH+10) defeated = true;
        }
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion region = AssetHandler.getShipRegion();
        Vector2D shift = previousPosition.subtract(position).signum();

        float addedSize = 0;
        if(skipped) {
            batch.setColor(Color.RED);
            addedSize = 0.3f;
        }

        if(previousPosition.equals(position)){
            batch.setColor(1,1,1,1-metronome.getTrueTickPercentage());
            batch.draw(region, position.x*GameMap.TILESIZE, position.y*GameMap.TILESIZE,
                    region.getRegionWidth()/2,region.getRegionHeight()/2,
                    region.getRegionWidth(),region.getRegionHeight(),
                    1+metronome.getTrueTickPercentage()*5,1+metronome.getTrueTickPercentage()*5,
                    direction.rotation);
        } else
        batch.draw(region, (position.x+shift.x*(metronome.getTickPercentage()*1.9f))*GameMap.TILESIZE,
                (position.y+shift.y*(metronome.getTickPercentage()*1.9f))*GameMap.TILESIZE,
                region.getRegionWidth()/2,region.getRegionHeight()/2,
                region.getRegionWidth(),region.getRegionHeight(),
                1+addedSize+metronome.getTickPercentage(),1+addedSize+metronome.getTickPercentage(),direction.rotation);
        batch.setColor(Color.WHITE);
    }
}
