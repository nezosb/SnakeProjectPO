package com.chyb.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.Startup;
import com.chyb.snake.utils.AssetHandler;

public abstract class ExtendedScreen implements Screen {
    private boolean locked = false;
    private float switchTime = 0;
    private final float switchTimeMax = 0.3f;
    protected Startup startup;
    protected ExtendedScreen(Startup startup){
        this.startup = startup;
    }

    public boolean ready(){ return !locked && switchTime>switchTimeMax; }
    public void lock(){ locked = true; switchTime = 0; }
    public void unlock(){ locked = false; switchTime = 0; }
    abstract void update(float delta);
    abstract void draw();

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
    public void render(float delta){
        //delta/=5;
        if(!ready()) switchTime += delta;
        if(ready()) update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw();
        if(!ready()) shadowScreen();
        if(switchTime>switchTimeMax && locked) startup.setNext();
    }
    private void shadowScreen(){
        Batch batch = AssetHandler.getDefaultBatch();
        batch.begin();
        if(locked) batch.setColor(1,1,1,Math.min(switchTime*10,1));
        else batch.setColor(1,1,1,Math.max(1 - switchTime*10,0));
        batch.draw(AssetHandler.getColorRects()[2],0,0,640,360);
        batch.setColor(1,1,1,1);
        batch.end();
    }
    @Override
    public void dispose() {
        AssetHandler.dispose();
    }
}
