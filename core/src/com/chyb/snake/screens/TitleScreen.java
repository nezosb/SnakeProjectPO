package com.chyb.snake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.Startup;
import com.chyb.snake.entities.Metronome;
import com.chyb.snake.ui.GameStateMenuSelection;
import com.chyb.snake.ui.MyFontB;
import com.chyb.snake.utils.AssetHandler;


public class TitleScreen extends ExtendedScreen {

    private final GameStateMenuSelection menu;
    private SpriteBatch batch;
    private State currentState;
    private float time;
    OrthographicCamera camera;

    public void reset() {
        batch.setProjectionMatrix(camera.combined);
        currentState = State.TITLE;
        time=0;
    }

    private enum State{
        TITLE, TRANSITION, MENU
    }

    public TitleScreen(Startup game){
        super(game);
        this.menu = new GameStateMenuSelection(game);
        currentState = State.TITLE;
        batch = AssetHandler.getBatch();
        camera = new OrthographicCamera(640,360);
        camera.translate(320,180);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    protected void update(float delta){
        time += delta;
        time = time % 2;
        switch(currentState){
            case TITLE:
                if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
                    currentState = State.TRANSITION;
                    time = 0;
                }
                break;
            case MENU:
                menu.update();
                break;
            case TRANSITION:
                if(time>=0.5f) {
                    time = 0;
                    currentState = State.MENU;
                }
                break;
        }

    }
    private void titleDraw(){
        float offset = MyFontB.getBoundsX("arcade tease")/2;
        MyFontB.draw(batch,"arcade tease",Startup.SCR_WIDTH/2-offset*3,Startup.SCR_HEIGHT/2,3);
    }
    protected void draw(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        float offset;
        switch(currentState){
            case TRANSITION:
                titleDraw();
                if(time % 0.1f > 0.05f) batch.setColor(1,1,1,1);
                else batch.setColor(1,1,1,0.1f);

                offset = MyFontB.getBoundsX("press any button")/2;
                MyFontB.draw(batch,"press any button",Startup.SCR_WIDTH/2-offset,Startup.SCR_HEIGHT/4);
                batch.setColor(1,1,1,1);

                lineDraw();
                break;
            case TITLE:
                titleDraw();

                batch.setColor(1,1,1,1-(time-1)*(time-1));
                offset = MyFontB.getBoundsX("press any button")/2;
                MyFontB.draw(batch,"press any button",Startup.SCR_WIDTH/2-offset,Startup.SCR_HEIGHT/4);
                batch.setColor(1,1,1,1);

                lineDraw();
                break;
            case MENU:
                MyFontB.draw(batch,"main menu",64,Startup.SCR_HEIGHT/10*8+24,2);
                float tickVal = Metronome.tickFunction(time % 0.5f,0.25f);
                batch.setColor(1,1,1,1);
                for(int i = -1; i<24;i++){
                    if(i==23)batch.setColor(1,1,1,tickVal);
                    batch.draw(AssetHandler.getMenuMovingSliderRegion(),i*8 -
                            tickVal * 8,Startup.SCR_HEIGHT/10*8);
                }
                batch.setColor(1,1,1,1);
                menu.draw(batch);
                break;
        }
        batch.end();
    }

    private void lineDraw() {
        for(int i =-1; i < Startup.SCR_WIDTH/8 ; i++) {
            batch.draw(AssetHandler.getMenuMovingSliderRegion(), i *8 +
                    Metronome.tickFunction(time % 0.5f,0.25f) * 8, 120);
        }
    }

}
