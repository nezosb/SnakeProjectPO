package com.chyb.snake.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;

public class AssetHandler {
    private static ArrayList<Disposable> disposables;
    private static ShapeRenderer shapeRenderer;
    private static SpriteBatch batch;
    private static SpriteBatch defaultBatch;
    private static TextureRegion fruitRegion;
    private static TextureRegion ghostRegion;
    private static TextureRegion tileRegion;
    private static TextureRegion playerRegion;
    private static TextureRegion[] menuOptionsRegions;
    private static TextureRegion cursorRegion;
    private static TextureRegion[] colorRects;
    private static Texture myFontBTexture;
    private static TextureRegion blockRegion;
    private static TextureRegion shipRegion;
    private static FrameBuffer miniFBO;
    private static TextureRegion menuMovingSliderHead;
    private static TextureRegion menuMovingSlider;
    private static Texture gameMap;
    private static TextureRegion[] trainRegions;


    public static void loadAll(){
        disposables = new ArrayList<Disposable>();
        shapeRenderer = new ShapeRenderer();
        disposables.add(shapeRenderer);
        batch = new SpriteBatch();
        defaultBatch = new SpriteBatch();
        disposables.add(batch);
        disposables.add(defaultBatch);

        OrthographicCamera defcam = new OrthographicCamera(640,360);
        defcam.translate(320,180);
        defcam.update();
        defaultBatch.setProjectionMatrix(defcam.combined);

        Texture menuTexture = loadTexture("menu.gif");
        menuMovingSlider = new TextureRegion(menuTexture, 0,0,16,16);
        menuMovingSliderHead = new TextureRegion(menuTexture,0,0,16,16);
        

        Texture texture = loadTexture("SpriteProj.gif");
        gameMap = loadTexture("game_map.gif");
        fruitRegion = new TextureRegion(texture, 0, 0, 16,16);
        ghostRegion = new TextureRegion(texture,16,0,16,16);
        tileRegion = new TextureRegion(texture,32,0,16,16);
        playerRegion = new TextureRegion(texture,48,0,16,16);
        cursorRegion = new TextureRegion(texture,0,16,64,16);
        shipRegion = new TextureRegion(texture,16*5,16,16,16);
        colorRects = new TextureRegion[3];
        colorRects[0] = new TextureRegion(texture, 16*6, 0, 16,16);
        colorRects[1] = new TextureRegion(texture,16*7, 0, 16,16);
        colorRects[2] = new TextureRegion(texture,16*8, 0, 16,16);
        blockRegion = new TextureRegion(texture, 16*6,16,16,16);
        trainRegions = new TextureRegion[3];
        trainRegions[0] = new TextureRegion(texture, 16*7,16,16,16);
        trainRegions[1] = new TextureRegion(texture, 16*8,16,16,16);
        trainRegions[2] = new TextureRegion(texture, 16*8,16*2,16,16);


        miniFBO = new FrameBuffer(Pixmap.Format.RGBA8888,100,100,false);

        OrthographicCamera cam = new OrthographicCamera(100,100);
        cam.translate(50,50);
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);

        myFontBTexture = loadTexture("MyFontB.gif");

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
        miniFBO.dispose();
    }

    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
    public static SpriteBatch getBatch() {
        return batch;
    }
    public static SpriteBatch getDefaultBatch() {
        return defaultBatch;
    }

    public static TextureRegion getMenuMovingSliderRegion(){
        return menuMovingSlider;
    }
    public static TextureRegion getMenuMovingSliderHeadRegion(){
        return menuMovingSliderHead;
    }

    public static TextureRegion getGhostRegion(){
        return ghostRegion;
    }
    public static TextureRegion getFruitRegion(){
        return fruitRegion;
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
    public static TextureRegion[] getColorRects() { return colorRects; }
    public static TextureRegion[] getTrainRegions(){ return trainRegions; }

    public static Texture getMyFontB() {
        return myFontBTexture;
    }

    public static TextureRegion getBlockRegion() {
        return blockRegion;
    }
    public static TextureRegion getShipRegion() {
        return shipRegion;
    }
    public static TextureRegion getMenuMovingSliderHead(){
        return menuMovingSliderHead;
    }
    public static TextureRegion getMenuMovingSlider(){
        return menuMovingSlider;
    }

    public static FrameBuffer getMiniFBO() {
        return miniFBO;
    }

    public static Texture getMapTexture(){
        return gameMap;
    }
}
