package com.chyb.snake.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.Startup;
import com.chyb.snake.screens.GameScreen;
import com.chyb.snake.utils.AssetHandler;


public class GameStateMenuSelection {
    private final Startup game;
    private int currentOption;
    private final int x = 320-32, y = 180-24, length = 3;
    private boolean downPressed, upPressed;

    public GameStateMenuSelection(Startup game){
        reset();
        this.game = game;
        downPressed = false;
        upPressed = false;
    }

    public void draw(Batch batch){
        for(int i = 0; i < length; i++){
            if(currentOption == i) batch.setColor(Color.LIGHT_GRAY);
            else batch.setColor(Color.WHITE);
            TextureRegion toDraw = AssetHandler.getMenuOptionsRegs()[i];
            batch.draw(toDraw,x,y+(2-i)*16);
        }
        batch.setColor(Color.WHITE);
        batch.draw(AssetHandler.getCursorRegion(),x,y+(2-currentOption)*16);

    }
    public void update() {
        readInput();
    }
    public void readInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(!upPressed) currentOption --;
            currentOption = (currentOption + length) % length;
            upPressed = true;
        } else upPressed = false;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if(!downPressed) currentOption ++;
            currentOption = currentOption % length;
            downPressed = true;
        } else downPressed = false;
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            enterPressed();
        }
    }

    private void enterPressed() {
        switch(currentOption) {
            case 0:
                game.setGame();
                break;
            case 1:
                if(!Gdx.graphics.isFullscreen()){
                    //DisplayMode dm = new DisplayMode();
                    //Gdx.graphics.setFullscreenMode(dm);
                }else{

                }
                //TODO
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
    }

    private void reset() {
        currentOption = 0;
    }

}
