package com.chyb.snake.entities.outside;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;

public class StopBlock implements OutsideEntity {

    private final Direction direction;
    private final GameMap gameMap;
    private final Metronome metronome;
    private final Player player;
    private Vector2D position;
    private State currentState;
    private Vector2D previousPosition;

    private enum State{
        MOVING, JUST_STOPPED, STOPPED
    }

    public StopBlock(Vector2D position, Direction direction, GameMap gameMap, Metronome metronome, Player player){
        this.position = position;
        this.direction = direction;
        this.gameMap = gameMap;
        this.metronome = metronome;
        this.player = player;
        previousPosition = position;
        currentState = State.MOVING;
    }

    @Override
    public void update(boolean tick) {
        if(tick){
            switch(currentState){
                case MOVING:
                    previousPosition = position;
                    Vector2D nextPosition = position.add(direction.toUnitVector);
                    if(gameMap.isOccupied(nextPosition)) currentState = State.JUST_STOPPED;
                    else position = nextPosition;
                    break;
                case JUST_STOPPED:
                    currentState = State.STOPPED;
                    break;
            }
            if(player.getPosition().equals(position)) player.hit();
        }
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion blockReg = AssetHandler.getBlockRegion();
        TextureRegion effectRegion = AssetHandler.getColorRects()[0];
        Vector2D shift= previousPosition.subtract(position).signum();
        if(!previousPosition.subtract(shift).equals(position)) shift = shift.opposite();


        batch.draw(blockReg,(position.x+shift.x*(metronome.getTickPercentage())*1.9f)*GameMap.TILESIZE,
                (position.y+shift.y*(metronome.getTickPercentage())*1.9f)*GameMap.TILESIZE, blockReg.getRegionWidth()/2,
                blockReg.getRegionHeight()/2,blockReg.getRegionWidth(),blockReg.getRegionHeight(),
                1+metronome.getTickPercentage(),1+metronome.getTickPercentage(),0);

        if(currentState == State.JUST_STOPPED){
            batch.setColor(1,1,1,metronome.getTickPercentage());
            batch.draw(effectRegion,position.x*GameMap.TILESIZE,position.y*GameMap.TILESIZE);
            batch.setColor(1,1,1,1);
        }

    }
}
