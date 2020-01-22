package com.chyb.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.AssetHandler;

public class GameScreen implements Screen {
    private final Player player;
    private final Metronome metronome;
    private final SpriteBatch batch;
    private final GameMap gameMap;
    private OrthographicCamera gameCam;
    private OrthographicCamera hudCam;

    public GameScreen(){
        gameMap = new GameMap(); 
        player = new Player(gameMap);
        metronome = new Metronome();
        batch = new SpriteBatch();
        gameCam = new OrthographicCamera(640,360);
        hudCam = new OrthographicCamera(640,360);
        gameCam.translate(gameMap.getHeight()/2f,gameMap.getWidth()/2f);
        gameCam.update();
    }
    private void reset(){
        metronome.reset();
        player.reset();
        gameMap.reset();
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        draw();
    }
    private void update(float delta) {
        if(player.isDead()) reset();

        boolean ticked = metronome.updateAndTick(delta);
        player.update(ticked);
        gameMap.replaceFruitRandomly();
    }
    private void draw() {

        batch.setProjectionMatrix(gameCam.combined);
        gameMap.draw(batch);
        player.draw(batch);
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        AssetHandler.dispose();
    }
}