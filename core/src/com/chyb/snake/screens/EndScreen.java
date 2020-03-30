package com.chyb.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.Startup;
import com.chyb.snake.ui.MyFontB;
import com.chyb.snake.utils.AssetHandler;

public class EndScreen extends ExtendedScreen {

    private final Startup startup;
    Startup backScreen;
    private int score;
    private float time = 0;
    private boolean readyToSwitch;
    OrthographicCamera camera;

    public EndScreen(Startup startup, int score){
        this.score = score;
        this.startup = startup;
        readyToSwitch = false;
        camera = new OrthographicCamera(Startup.SCR_WIDTH, Startup.SCR_HEIGHT);
        camera.translate(Startup.SCR_WIDTH/2,Startup.SCR_HEIGHT/2);
        camera.update();
    }

    private void update(float delta) {
        time += delta;
        if(time > 2f) readyToSwitch = true;

        if(readyToSwitch && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            startup.setTitleScreen();
        }
    }


    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Batch batch = AssetHandler.getBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        String title = "final score";
        float size = MyFontB.getBoundsX(title);
        MyFontB.draw(batch,title, Startup.SCR_WIDTH/2 - size/2 - 32,Startup.SCR_HEIGHT/2+16);

        int dispScore = score;
        int x = Startup.SCR_WIDTH/2 + 60;
        for(int i = 0; i < 10;i++){
            if(dispScore == 0) batch.setColor(Color.DARK_GRAY);
            MyFontB.draw(batch,dispScore % 10 + "", x - 10*i,Startup.SCR_HEIGHT/2);
            dispScore /= 10;
        }
        batch.setColor(Color.WHITE);

        if(readyToSwitch){
            String nStr = "continue?";
            float offsetX = MyFontB.getBoundsX(nStr)/2;
            MyFontB.draw(batch, nStr, Startup.SCR_WIDTH/2 - offsetX,Startup.SCR_HEIGHT/2-64);
        }

        batch.end();
    }
}
