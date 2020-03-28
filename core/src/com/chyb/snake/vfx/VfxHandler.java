package com.chyb.snake.vfx;

import java.util.ArrayList;

public class VfxHandler {
    private ArrayList<Vfx> vfxPool;

    public VfxHandler(){
        vfxPool = new ArrayList<Vfx>();
    }
    public void update(float delta){
        for(Vfx vfx : vfxPool){
            vfx.update(delta);
        }
        for(int i = 0; i < vfxPool.size(); i++){
            if(vfxPool.get(i).toRemove()){
                vfxPool.remove(i);
                i--;
            }
        }
    }
    public void draw(){
        for(Vfx vfx : vfxPool){
            vfx.draw();
        }
    }
    public void reset(){
        vfxPool.clear();
    }
    public void addTrail(int x, int y){
        vfxPool.add(new SkipTrailVFX(x,y));
    }
    public void addFruitEaten(int x, int y){
        vfxPool.add(new FruitEatenVfx(x,y));
    }
}
