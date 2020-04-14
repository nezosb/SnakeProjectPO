package com.chyb.snake.entities.outside;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Vector2D;

public class Train implements OutsideEntity{

    private final Metronome metronome;
    private final Vector2D trackPosition;
    private final int length;
    private final boolean isHorizontal;
    private final Player player;

    private int enteringTimer;
    private GameMap gameMap;
    private boolean toRemove;
    private Vector2D previousPosition;
    private boolean goForward;
    private int trainOffset;
    private boolean defeatedPlayer;

    public Train(Vector2D trackPosition, int length, boolean isHorizontal, GameMap gameMap, Metronome metronome, Player player){
        this.player = player;
        this.metronome = metronome;
        this.trackPosition = trackPosition;
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.gameMap = gameMap;
        trainOffset = 0;
        goForward = true;
        previousPosition = getPosition();
        toRemove = false;
        enteringTimer = 4;
        defeatedPlayer = false;
    }

    @Override
    public void update(boolean tick) {
        if(tick && enteringTimer == 0){
            previousPosition = getPosition().mod(gameMap.getSize());
            if(goForward) trainOffset++;
            else trainOffset--;

            if(trainOffset == length-1) goForward = !goForward;
            if(trainOffset == 0) goForward = !goForward;

            if(player.getPosition().equals(getPosition().mod(gameMap.getSize()))){
                player.hit();
                defeatedPlayer = true;
            }
            if(player.getPreviousPosition().equals(getPosition()) && player.getPosition().equals(previousPosition)){
                player.hit();
                defeatedPlayer = true;
            }
            if(!defeatedPlayer && gameMap.isOccupiedByGhost(getPosition().mod(gameMap.getSize()))) toRemove = true;
        }else if(tick){
            enteringTimer--;
        }

    }

    @Override
    public Vector2D getPosition() {
        if(isHorizontal){
            return new Vector2D(trackPosition.x + trainOffset, trackPosition.y);
        }else{
            return new Vector2D(trackPosition.x, trackPosition.y + trainOffset);
        }
    }

    @Override
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion junction = AssetHandler.getTrainRegions()[0];
        TextureRegion connection = AssetHandler.getTrainRegions()[1];
        TextureRegion train = AssetHandler.getTrainRegions()[2];

        //draw tracks
        for(int i = 0; i < length; i++) {
            if (isHorizontal) {
                batch.draw(junction,(trackPosition.x+i) % GameMap.WIDTH * GameMap.TILESIZE,
                        trackPosition.y % GameMap.HEIGHT * GameMap.TILESIZE);
                if(i!=length-1)
                    batch.draw(connection,(trackPosition.x+i+0.5f) % GameMap.WIDTH * GameMap.TILESIZE,
                            trackPosition.y % GameMap.HEIGHT * GameMap.TILESIZE);
            } else {
                batch.draw(junction,trackPosition.x % GameMap.WIDTH * GameMap.TILESIZE,
                        (trackPosition.y+i) % GameMap.HEIGHT * GameMap.TILESIZE);
                if(i!=length-1)
                    batch.draw(junction,trackPosition.x % GameMap.WIDTH * GameMap.TILESIZE,
                            (trackPosition.y+i+0.5f) % GameMap.HEIGHT * GameMap.TILESIZE);
            }
        }

        Vector2D position = getPosition();
        //draw train
        Vector2D shift= previousPosition.subtract(position).signum();
        position = position.mod(gameMap.getSize());

        if(!previousPosition.subtract(shift).equals(position)) shift = shift.opposite();
        if(enteringTimer==0)
        batch.draw(train,(position.x+shift.x*(metronome.getTickPercentage())*1.9f)*GameMap.TILESIZE,
                (position.y+shift.y*(metronome.getTickPercentage())*1.9f)*GameMap.TILESIZE, train.getRegionWidth()/2,
                train.getRegionHeight()/2,train.getRegionWidth(),train.getRegionHeight(),
                1+metronome.getTickPercentage(),1+metronome.getTickPercentage(),0);
    }

    @Override
    public boolean toRemove() {
        return toRemove;
    }
}