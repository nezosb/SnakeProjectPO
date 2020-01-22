package com.chyb.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.entities.GameMap;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.AssetHandler;

public class GameScreen implements Screen {
    private final Player player;
    private final Metronome metronome;
    private final GameMap gameMap;
    private OrthographicCamera gameCam;

    public GameScreen(){
        gameMap = new GameMap(); 
        player = new Player(gameMap);
        metronome = new Metronome();
        gameCam = new OrthographicCamera(640,360);
        gameCam.position.x = gameMap.getHeight()/2f;
        gameCam.position.y = gameMap.getWidth()/2f;
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
        Batch batch = AssetHandler.getBatch();
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        gameMap.draw();
        player.draw();
        batch.end();
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
        AssetHandler.dispose();
    }
}
