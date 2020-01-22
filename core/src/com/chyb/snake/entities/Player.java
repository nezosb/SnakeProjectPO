package com.chyb.snake.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;

import java.util.LinkedList;

public class Player implements MapEntity {
    private final GameMap gMap;
    private Direction direction;
    private Vector2D position;
    private boolean isDead;
    private LinkedList<Ghost> ghostTail;
    public Player(GameMap gMap){
        this.gMap = gMap;
        ghostTail = new LinkedList<Ghost>();
        reset();
    }
    public void reset(){
        isDead = false;
        position = new Vector2D(16,16);
        direction = Direction.L;
        ghostTail.clear();
    }
    public void update(boolean tick){
        readInput();
        if(tick) {
            Vector2D oldPosition = position;
            Vector2D newPosition = position;
            newPosition = newPosition.add(direction.toUnitVector);
            newPosition = newPosition.mod(gMap.getSize());
            position = newPosition;



            boolean toAddTail = false;
            if(gMap.findAndRemoveFruit(newPosition)){
                toAddTail = true;
            }
            if(gMap.isSpaceHarmful(newPosition)) isDead = true;
            gMap.EntityMoved(oldPosition, newPosition);

            if(toAddTail){
                addTail(oldPosition);
            }else if (!ghostTail.isEmpty()){
                Ghost ghost = ghostTail.getLast();
                ghostTail.removeLast();
                Vector2D tempPosition = ghost.getPosition();
                ghost.setPosition(oldPosition);
                ghostTail.addFirst(ghost);
                gMap.EntityMoved(tempPosition, ghost.getPosition());
            }
        }
    }
    public void addTail(Vector2D position){
        Ghost ghost = new Ghost(position);
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
    @Override
    public Vector2D getPosition() {
        return position;
    }
    public void draw(Batch batch){
        batch.begin();
        TextureRegion playerRegion = AssetHandler.getPlayerRegion();
        batch.draw(playerRegion,position.x*GameMap.TILESIZE, position.y*GameMap.TILESIZE,8,8,
                GameMap.TILESIZE,GameMap.TILESIZE,1,1, direction.rotation);
        batch.end();

    }

    @Override
    public boolean isHarmful() {
        return false;
    }
    public boolean isDead(){
        return isDead;
    }

}
