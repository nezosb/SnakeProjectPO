package com.chyb.snake.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.ui.ScoreTracker;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;
import com.chyb.snake.vfx.VfxHandler;

import java.util.LinkedList;

public class Player implements MapEntity {
    private final GameMap gMap;
    private final ScoreTracker scoreTracker;
    private final VfxHandler vfxHandler;
    private Direction direction;
    private Vector2D position;
    private Vector2D previousPosition;
    private LinkedList<Ghost> ghostTail;
    private Metronome metronome;
    private State currentState;
    private float dyingTime;
    private float diedVelocityY;
    private float diedPositionY;
    private enum State{
        PLAY,DYING,DEAD
    }
    public Player(GameMap gMap, Metronome metronome, ScoreTracker scoreTracker, VfxHandler vfxHandler){
        this.scoreTracker = scoreTracker;
        this.gMap = gMap;
        this.metronome = metronome;
        this.vfxHandler = vfxHandler;
        ghostTail = new LinkedList<Ghost>();
        reset();
    }
    public void reset(){
        currentState = State.PLAY;
        position = new Vector2D(8,8);
        previousPosition = position.copy();
        direction = Direction.L;
        gMap.placeMapEntity(position,new SpotTaker(position));
        ghostTail.clear();
        dyingTime = 0;
    }
    public void update(boolean tick, float delta){
        readInput();
        Vector2D oldPosition = position;
        Vector2D newPosition = position.add(direction.toUnitVector);
        newPosition = newPosition.mod(gMap.getSize());
        if(newPosition.equals(previousPosition)){
            direction = direction.flip();
            newPosition = position.add(direction.toUnitVector);
            newPosition = newPosition.mod(gMap.getSize());
        }
        if(currentState==State.DYING){
            dyingTime += delta;
            diedVelocityY -= 600*delta;
            diedPositionY += diedVelocityY*delta;
        }
        if(tick && currentState == State.DYING){

            for(int i = 0; i < ghostTail.size(); i++) {
                Ghost ghost = ghostTail.get(i);
                ghost.setPosition(ghost.getPosition());
            }
        }
        if(dyingTime>5f) currentState = State.DEAD;
        if(tick && currentState == State.PLAY) {

            previousPosition = position;

            boolean toAddTail = false;
            if(gMap.findAndRemoveFruit(newPosition)){
                vfxHandler.addFruitEaten(newPosition.x* GameMap.TILESIZE, newPosition.y*GameMap.TILESIZE);
                System.out.print(true);
                toAddTail = true;
            }
            if(gMap.isSpaceHarmful(newPosition)) {
                hit();
                return;
            }
            gMap.EntityMoved(oldPosition, newPosition);
            position = newPosition;

            if(toAddTail){
                addTail(oldPosition);
            }else if(!ghostTail.isEmpty()){
                Vector2D lastPosition = oldPosition;
                for(int i = 0; i < ghostTail.size(); i++){
                    Ghost ghost = ghostTail.get(i);
                    Vector2D swapPosition = ghost.getPosition();
                    ghost.setPosition(lastPosition);
                    gMap.EntityMoved(swapPosition,lastPosition);
                    lastPosition = swapPosition;
                }
            }
        }
    }
    public void addTail(Vector2D position){
        scoreTracker.addMultiplier();
        Vector2D ghostPreviousPosition = position;
        if(!ghostTail.isEmpty()) ghostPreviousPosition = ghostTail.getFirst().getPosition();
        Ghost ghost = new Ghost(ghostPreviousPosition, metronome);
        ghost.setPosition(position);
        ghostTail.addFirst(ghost);
        gMap.addGhost(ghost, ghost.getPosition());
    }

    private void readInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            direction = Direction.L;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            direction = Direction.R;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            direction = Direction.U;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            direction = Direction.D;
        }
    }

    public void draw(){
        Batch batch = AssetHandler.getBatch();
        TextureRegion playerRegion = AssetHandler.getPlayerRegion();
        Vector2D shift = previousPosition.subtract(position).signum();
        if(!previousPosition.subtract(shift).equals(position)) shift = shift.opposite();
        switch(currentState){
            case PLAY:
                batch.draw(playerRegion,(position.x+shift.x*(metronome.getTickPercentage()*1.95f))*GameMap.TILESIZE,
                        (position.y+shift.y*(metronome.getTickPercentage()*1.95f))*GameMap.TILESIZE,
                        playerRegion.getRegionWidth()/2,playerRegion.getRegionHeight()/2,
                        GameMap.TILESIZE,GameMap.TILESIZE,
                        1,1-(metronome.getTickPercentage())*1.5f, direction.rotation);
                break;
            case DYING:
                batch.draw(playerRegion,(position.x+shift.x*0.5f)*GameMap.TILESIZE,
                        (position.y+shift.y*0.5f)*GameMap.TILESIZE + diedPositionY,
                        playerRegion.getRegionWidth()/2,playerRegion.getRegionHeight()/2,
                        GameMap.TILESIZE,GameMap.TILESIZE,
                        1+dyingTime/2,1+dyingTime/2,direction.rotation+dyingTime*600);
                break;
            case DEAD:

                break;
        }


    }
    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }

    @Override
    public boolean isEatable() {
        return false;
    }

    public boolean isDead(){
        return currentState == State.DEAD;
    }

    public void hit() {
        if(currentState==State.PLAY){
            currentState = State.DYING;
            diedVelocityY = 150;
            diedPositionY = 0;
        }
    }

    public int getSize() {
        return ghostTail.size();
    }
}
