package com.chyb.snake.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class AssetHandler {
    private static ArrayList<Disposable> disposables;
    private static ShapeRenderer shapeRenderer;
    private static SpriteBatch batch;
    private static TextureRegion fruitRegion;
    private static TextureRegion ghostRegion;
    private static TextureRegion tileRegion;
    private static TextureRegion playerRegion;
    private static TextureRegion[] menuOptionsRegions;
    private static TextureRegion cursorRegion;

    public static void loadAll(){
        disposables = new ArrayList<Disposable>();
        shapeRenderer = new ShapeRenderer();
        disposables.add(shapeRenderer);
        batch = new SpriteBatch();
        disposables.add(batch);
        Texture texture = loadTexture("SpriteProj.gif");
        fruitRegion = new TextureRegion(texture, 0, 0, 16,16);
        ghostRegion = new TextureRegion(texture,16,0,16,16);
        tileRegion = new TextureRegion(texture,32,0,16,16);
        playerRegion = new TextureRegion(texture,48,0,16,16);
        cursorRegion = new TextureRegion(texture,0,16,64,16);

        menuOptionsRegions = new TextureRegion[3];
        for(int i = 0; i < 3; i++){
            menuOptionsRegions[i] = new TextureRegion(texture,0,32+16*i,64 ,16);
        }

    }
    private static Texture loadTexture(String source){
        Texture tex = new Texture(source);
        disposables.add(tex);
        return tex;
    }
    public static void dispose(){
        for(Disposable d : disposables){
            d.dispose();
        }
        disposables.clear();
    }

    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
    public static TextureRegion getGhostRegion(){
        return ghostRegion;
    }
    public static TextureRegion getFruitRegion(){
        return fruitRegion;
    }
    public static TextureRegion getTileRegion(){
        return tileRegion;
    }
    public static TextureRegion getPlayerRegion(){
        return playerRegion;
    }
    public static TextureRegion getCursorRegion(){
        return cursorRegion;
    }
    public static TextureRegion[] getMenuOptionsRegs() {
        return menuOptionsRegions;
    }
}
