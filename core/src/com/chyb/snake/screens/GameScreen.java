package com.chyb.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.Startup;
import com.chyb.snake.entities.outside.EntitySpawner;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.ui.MyFontB;
import com.chyb.snake.ui.ScoreTracker;
import com.chyb.snake.ui.StatisticsTracker;
import com.chyb.snake.utils.AssetHandler;
import com.chyb.snake.vfx.VfxHandler;

public class GameScreen extends ExtendedScreen {

    private final Player player;
    private final Metronome metronome;
    private final GameMap gameMap;
    private final EntitySpawner entitySpawner;
    private final ScoreTracker scoreTracker;
    private final OrthographicCamera hudCam;
    private final OrthographicCamera gameCam;
    private final OrthographicCamera hudCam2;
    private final StatisticsTracker statisticsTracker;
    private final VfxHandler vfxHandler;
    private State currentState;
    private float stateTime;

    private enum State{
        START,PLAY,END
    }

    public GameScreen(Startup startup){
        super(startup);
        metronome = new Metronome();
        vfxHandler = new VfxHandler();
        scoreTracker = new ScoreTracker();
        gameMap = new GameMap(metronome,scoreTracker);
        player = new Player(gameMap,metronome,scoreTracker,vfxHandler);
        entitySpawner = new EntitySpawner(gameMap, player, vfxHandler,metronome);
        statisticsTracker = new StatisticsTracker(player);

        gameCam = new OrthographicCamera(640,360);
        gameCam.position.x = gameMap.getWidth()/2f;
        gameCam.position.y = gameMap.getHeight()/2f;
        gameCam.update();
        hudCam = new OrthographicCamera(640,360);
        hudCam.position.x = -gameMap.getWidth()/2f;
        hudCam.position.y = gameMap.getHeight()/2f;
        hudCam.update();

        hudCam2 = new OrthographicCamera(640,360);
        hudCam2.position.x = 320;
        hudCam2.position.y = gameMap.getHeight()/2f;
        hudCam2.update();
        reset();
    }
    public void reset(){
        metronome.reset();
        gameMap.reset();
        player.reset();
        scoreTracker.reset();
        entitySpawner.reset();
        currentState = State.START;
        stateTime = 2;
    }
    protected void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if(player.isDead()) startup.setExtendedScreen(new EndScreen(startup,scoreTracker.getScore()));

        switch(currentState){
            case START:
                stateTime -= delta;
                if(stateTime<0){
                    currentState = State.PLAY;
                }
                break;
            case PLAY:
                boolean ticked = metronome.updateAndTick(delta);
                player.update(ticked,delta);
                gameMap.updateEntities(ticked);
                scoreTracker.update(delta);
                gameMap.replaceFruitRandomly();
                entitySpawner.update(delta);
                vfxHandler.update(delta);
                break;
            case END:
                break;
        }

    }
    protected void draw() {
        Batch batch = AssetHandler.getBatch();

        batch.setProjectionMatrix(hudCam.combined);
        batch.begin();
        scoreTracker.draw(batch);
        batch.end();

        batch.setProjectionMatrix(hudCam2.combined);
        batch.begin();
        statisticsTracker.draw();
        batch.end();

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        gameMap.draw();
        gameMap.drawUnderOutsideEntities();
        if(currentState!=State.START) player.draw();
        gameMap.drawUpperOutsideEntities();
        vfxHandler.draw();

        switch(currentState){
            case START:
                batch.setColor(0,0,0,0.5f);
                batch.draw(AssetHandler.getColorRects()[0],-1,-1,
                        GameMap.TILESIZE*GameMap.WIDTH+2,GameMap.TILESIZE*GameMap.HEIGHT+2);

                int digit = Math.min((int) (stateTime*2),3);
                float offsetY = Math.max(0.6f, (stateTime*2 -digit)*(stateTime*2 - digit))-0.6f;
                String s = digit + "";
                float offset = 8*4-2;
                if(s.equals("1")){
                    offset = 8*4+9;
                }else if(s.equals("0")){
                    s = "go";
                    offset = 8*6 - 2;
                    offsetY -= 0.1;
                }
                batch.setColor(255f/255,170f/255,0,1);
                MyFontB.draw(batch, s, GameMap.WIDTH * GameMap.TILESIZE/2f-offset,
                        GameMap.HEIGHT*GameMap.TILESIZE/2f-32 + 25*offsetY,4);
                batch.setColor(1,1,1,1);
                break;
            case PLAY:
                break;
            case END:
                break;
        }
        batch.end();

    }

}
