package com.chyb.snake.entities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.entities.outside.OutsideEntity;
import com.chyb.snake.entities.outside.SkipShip;
import com.chyb.snake.ui.ScoreList;
import com.chyb.snake.ui.ScoreTracker;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.utils.Direction;
import com.chyb.snake.utils.Vector2D;
import com.chyb.snake.vfx.VfxHandler;
import java.util.LinkedList;
import java.util.Random;

public class GameMap {
    private static Random random = new Random();
    public static final int WIDTH = 17;
    public static final int HEIGHT = 17;
    public static final int TILESIZE = 16;
    private final ScoreTracker scoreTracker;
    private final LinkedList<OutsideEntity> outsideEntitiesOverPlayer;
    private LinkedList<OutsideEntity> outsideEntitiesUnderPlayer;

    private int fruitAmount = 0;
    private MapEntity[][] mapOccupied;

    private Player player;
    private Metronome metronome;

    public GameMap(Metronome metronome, ScoreTracker scoreTracker){
        this.metronome = metronome;
        this.scoreTracker = scoreTracker;
        mapOccupied = new MapEntity[WIDTH][HEIGHT];
        outsideEntitiesUnderPlayer = new LinkedList<OutsideEntity>();
        outsideEntitiesOverPlayer = new LinkedList<OutsideEntity>();
        fruitAmount = 0;
    }

    public void EntityMoved(Vector2D oldPosition, Vector2D newPosition){
        MapEntity entity = mapOccupied[oldPosition.x][oldPosition.y];
        if(entity instanceof Fruit) System.out.print(false);
        mapOccupied[newPosition.x][newPosition.y] = entity;
        mapOccupied[oldPosition.x][oldPosition.y] = null;
    }
    public boolean isOccupied(int x, int y){
        if(x<0 || x>=WIDTH || y<0 || y>=HEIGHT) return false;
        return mapOccupied[x][y] != null;
    }
    public boolean isOccupied(Vector2D position){
        return isOccupied(position.x,position.y);
    }
    public Vector2D getSize(){
        return new Vector2D(WIDTH, HEIGHT);
    }
    public MapEntity getMapEntity(int x, int y){
        return mapOccupied[x][y];
    }
    public void replaceFruitRandomly(){
        if(fruitAmount==0){
            Vector2D position = new Vector2D(random.nextInt(WIDTH), random.nextInt(HEIGHT));
            if(!isOccupied(position)){
                mapOccupied[position.x][position.y] = new Fruit(position);
                System.out.println(mapOccupied[position.x][position.y].getPosition());
                fruitAmount ++;
            }
        }
    }
    public void updateEntities(boolean tick){
        for(OutsideEntity entity : outsideEntitiesUnderPlayer){
            entity.update(tick);
        }
        for(OutsideEntity entity : outsideEntitiesOverPlayer){
            entity.update(tick);
        }
    }
    public void draw() {
        Batch batch = AssetHandler.getBatch();
        gridDraw(batch);

        for(int i =0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (mapOccupied[i][j] != null) {
                   mapOccupied[i][j].draw();
                }
            }
        }
    }

    private void gridDraw(Batch batch) {
        Texture gridMapTexture = AssetHandler.getMapTexture();
        batch.draw(gridMapTexture,-1,-1);
    }

    public boolean findAndRemoveFruit(Vector2D position) {
        if(mapOccupied[position.x][position.y] != null && mapOccupied[position.x][position.y].isEatable()){
            mapOccupied[position.x][position.y] = null;
            fruitAmount--;
            return true;
        }
        return false;
    }
    public void addGhost(Ghost ghost, Vector2D position) {
        mapOccupied[position.x][position.y] = ghost;
    }
    //TODO
    public void addSkipShip(Vector2D position, Direction direction, Player player, VfxHandler vfxHandler){
        outsideEntitiesOverPlayer.addFirst(new SkipShip(position,direction,player,this, metronome,vfxHandler));
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
        outsideEntitiesOverPlayer.clear();
        outsideEntitiesUnderPlayer.clear();
    }
    public int getWidth(){
        return WIDTH*TILESIZE;
    }
    public int getHeight(){
        return HEIGHT*TILESIZE;
    }

    public boolean isOccupiedByHarmful(Vector2D target) {
        if(mapOccupied[target.x][target.y] == null) return false;
        return mapOccupied[target.x][target.y].isHarmful();
    }

    public void drawUnderOutsideEntities() {
        for(OutsideEntity entity : outsideEntitiesUnderPlayer){
            entity.draw();
        }
    }
    public void drawUpperOutsideEntities(){
        for(OutsideEntity entity : outsideEntitiesOverPlayer){
            entity.draw();
        }
    }
    public void addScore(ScoreList.ScoreType score){
        scoreTracker.addScore(score);
    }

    public MapEntity getMapEntity(Vector2D newPosition) {
        return mapOccupied[newPosition.x][newPosition.y];
    }

    public void placeMapEntity(Vector2D position, MapEntity entity) {
        mapOccupied[position.x][position.y] = entity;
    }

    public void addOutsideEntityUnderPlayer(OutsideEntity entity) {
        outsideEntitiesUnderPlayer.addFirst(entity);
    }
    public void addOutsideEntityOverPlayer(OutsideEntity entity) { outsideEntitiesOverPlayer.addFirst(entity); }
}
