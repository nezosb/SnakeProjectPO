package com.chyb.snake.screens;

import com.badlogic.gdx.Screen;
import com.chyb.snake.utils.AssetHandler;

public abstract class ExtendedScreen implements Screen {
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
