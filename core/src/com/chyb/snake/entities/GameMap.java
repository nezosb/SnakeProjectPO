package com.chyb.snake.entities;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Vector2D;

import java.util.Random;

public class GameMap {
    private static Random random = new Random();
    public static final int WIDTH = 18;
    public static final int HEIGHT = 18;
    public static final int TILESIZE = 16;
    private int fruitAmount = 0;
    private MapEntity mapOccupied[][];
    public GameMap(){
        mapOccupied = new MapEntity[WIDTH][HEIGHT];
        fruitAmount = 0;
    }
    public void EntityMoved(Vector2D oldPosition, Vector2D newPosition){
        MapEntity entity = mapOccupied[oldPosition.x][oldPosition.y];
        mapOccupied[newPosition.x][newPosition.y] = entity;
        mapOccupied[oldPosition.x][oldPosition.y] = null;
    }
    public boolean isOccupied(int x, int y){
        return mapOccupied[x][y] == null;
    }
    public boolean isOccupied(Vector2D position){
        return mapOccupied[position.x][position.y] != null;
    }
    public Vector2D getSize(){
        return new Vector2D(WIDTH, HEIGHT);
    }
    public MapEntity getGameEntity(int x, int y){
        return mapOccupied[x][y];
    }
    public void replaceFruitRandomly(){
        if(fruitAmount==0){
            Vector2D position = new Vector2D(random.nextInt(WIDTH), random.nextInt(HEIGHT));
            if(!isOccupied(position)){
                mapOccupied[position.x][position.y] = new Fruit(position);
                fruitAmount ++;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        gridDraw(batch);

        for(int i =0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(mapOccupied[i][j] != null){
                    mapOccupied[i][j].draw(batch);
                }
            }
        }
    }

    private void gridDraw(SpriteBatch batch) {
        batch.begin();
        TextureRegion tileRegion = AssetHandler.getTileRegion();
        for(int i =0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                batch.draw(tileRegion,i*TILESIZE,j*TILESIZE);
            }
        }
        batch.end();
    }

    public boolean findAndRemoveFruit(Vector2D position) {
        if(mapOccupied[position.x][position.y] instanceof Fruit){
            mapOccupied[position.x][position.y] = null;
            fruitAmount--;
            return true;
        }
        return false;
    }
    public int getWidth(){
        return WIDTH*TILESIZE;
    }
    public int getHeight(){
        return HEIGHT*TILESIZE;
    }

    public void addGhost(Ghost ghost, Vector2D position) {
        mapOccupied[position.x][position.y] = ghost;
    }
    public boolean isSpaceHarmful(Vector2D position){
        if(mapOccupied[position.x][position.y] == null) return false;
        return mapOccupied[position.x][position.y].isHarmful();

    }
    public void reset() {
        for(int i = 0; i<WIDTH; i++){
            for(int j =0; j<HEIGHT;j++){
                mapOccupied[i][j] = null;
            }
        }
        fruitAmount = 0;
    }
}
