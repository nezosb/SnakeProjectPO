package com.chyb.snake.vfx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chyb.snake.utils.AssetHandler;

public class SkipTrailVFX extends Vfx {
    public SkipTrailVFX(int x, int y){
        super(x,y);
        timer = 0.5f;
    }

    @Override
    void draw() {
        Batch batch = AssetHandler.getBatch();
        TextureRegion region = AssetHandler.getColorRects()[0];
        batch.setColor(1,1,1,timer*2);
        batch.draw(region,x,y,region.getRegionWidth()/2,region.getRegionHeight()/2,
                region.getRegionWidth(),region.getRegionHeight(),1,1f/4,0);
        batch.setColor(1,1,1,1);
    }
}
