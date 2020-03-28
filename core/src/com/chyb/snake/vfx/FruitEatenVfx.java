package com.chyb.snake.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.chyb.snake.utils.AssetHandler;

public class FruitEatenVfx extends Vfx{
    private static final float maxTime = 0.4f;

    public FruitEatenVfx(float x,float y){
        super(x,y);
        timer = 0.4f;
    }
    @Override
    void draw() {
        Batch batch = AssetHandler.getBatch();
        ShapeRenderer sr = AssetHandler.getShapeRenderer();
        FrameBuffer fbo = AssetHandler.getMiniFBO();
        Matrix4 pm = batch.getProjectionMatrix();
        batch.end();

        fbo.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.CYAN);
        sr.circle(50,50, (int) (10 -(maxTime-timer)*(-maxTime+timer)*120));
        sr.setColor(1,1,1,0);
        sr.circle(50,50, (int) (8 - (maxTime-timer)*(-maxTime+timer)*120));
        sr.setColor(1,1,1,1);

        sr.end();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        fbo.end();
        batch.begin();
        fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.setColor(1,1,1,Math.min(timer*5,0.6f));
        batch.draw(fbo.getColorBufferTexture(),x-42,y-42);
        batch.setColor(1,1,1,1);
    }
}
