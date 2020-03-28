package com.chyb.snake.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.entities.Player;
import com.chyb.snake.utils.AssetHandler;

public class StatisticsTracker {
    private Player player;
    public StatisticsTracker(Player player){
        this.player = player;
    }
    public void draw() {
        Batch batch = AssetHandler.getBatch();

        MyFontB.draw(batch, "size", 30, 270);
        int num = player.getSize();
        for (int i = 2; i >= 0; i--) {
            if(num == 0) batch.setColor(Color.DARK_GRAY);
            MyFontB.draw(batch, num%10 + "", 39+i*10, 270 - 16, 1);
            batch.setColor(1,1,1,1);
            num /= 10;
        }
        MyFontB.draw(batch, "length", 30, 210);
        num = player.getSize();
        for (int i = 2; i >= 0; i--) {
            if(num == 0) batch.setColor(Color.DARK_GRAY);
            MyFontB.draw(batch, num%10 + "", 39+i*10, 210 - 16, 1);
            batch.setColor(1,1,1,1);
            num /= 10;
        }
    }
}
