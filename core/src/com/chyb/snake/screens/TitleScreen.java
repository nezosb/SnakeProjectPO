package com.chyb.snake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.Startup;
import com.chyb.snake.ui.GameStateMenuSelection;
import com.chyb.snake.utils.AssetHandler;

public class TitleScreen implements Screen {
    private final Game game;
    private final GameStateMenuSelection menu;
    private SpriteBatch batch;

    public TitleScreen(Startup game){
        this.game = game;
        this.menu = new GameStateMenuSelection(game);
    }
    @Override
    public void show() {
        batch = AssetHandler.getBatch();
        OrthographicCamera camera = new OrthographicCamera(640,360);
        camera.translate(320,180);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {

        menu.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        menu.draw(batch);
        batch.end();
    }

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
