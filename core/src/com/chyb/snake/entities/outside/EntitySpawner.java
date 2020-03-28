package com.chyb.snake.entities.outside;

import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;
import com.chyb.snake.vfx.VfxHandler;

import java.util.Random;

public class EntitySpawner {

    private static final Random random = new Random();
    private final GameMap gMap;
    private final Player player;
    private final VfxHandler vfxHandler;
    private final Metronome metronome;
    private float timer;

    public EntitySpawner(GameMap gMap, Player player, VfxHandler vfxHandler, Metronome metronome){
        this.gMap = gMap;
        this.player = player;
        this.vfxHandler = vfxHandler;
        this.metronome = metronome;
        reset();
    }
    public void reset(){
        timer = 0;
    }
    public void update(float delta){
        timer += delta;
        if(timer>5f){
            timer-=5f;
            spawnSkipShip();
            spawnBlock();
        }
    }
    private void spawnSkipShip(){
        int side = random.nextInt(2);
        int xPosition;
        Direction direction;
        if(side==0) {
            direction = Direction.R;
            xPosition = -10;
        }
        else {
            direction = Direction.L;
            xPosition = 27;
        }
        int yPosition = random.nextInt(GameMap.HEIGHT);
        Vector2D position = new Vector2D(xPosition,yPosition);
        gMap.addSkipShip(position, direction, player, vfxHandler);
    }
    private void spawnBlock(){
        int side = random.nextInt(2);
        int xPosition;
        Direction direction;
        if(side==0) {
            direction = Direction.R;
            xPosition = -10;
        }
        else {
            direction = Direction.L;
            xPosition = 27;
        }
        int yPosition = random.nextInt(GameMap.HEIGHT);
        Vector2D position = new Vector2D(xPosition,yPosition);
        gMap.addOutsideEntityUnderPlayer(new StopBlock(position,direction,gMap, metronome, player));
    }
}
