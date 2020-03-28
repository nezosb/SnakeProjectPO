package com.chyb.snake.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.chyb.snake.utils.AssetHandler;

public class MyFontB {
	public static void draw(Batch batch,String s,float x, float y){
		float posX=x-3;
		float posY=y-1;
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c==' '){
				posX+=9;
				continue;
			}
			if(c>='0'&&c<='9'){
				int poss=(c-'0')*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,poss,16,16, 16);
				posX+=10;
			}else{
				int poss=(c-'a')*16;
				if(c=='\'') poss=26*16;
				if(c=='+') poss = 29*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,poss,0,16, 16);
				if(c=='i')posX+=5;
				else if(c=='t')posX+=9;
				else if(c=='w'||c=='m')posX+=10;
				else posX+=9;
			}
		}
	}
	public static void draw(Batch batch,String s,int x, int y){
		float posX=x-3;
		float posY=y-1;
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c==' '){
				posX+=9;
				continue;
			}
			if(c>='0'&&c<='9'){
				int poss=(c-'0')*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,poss,16,16, 16);
				posX+=10;
			}else{
				int poss=(c-'a')*16;
				if(c=='\'') poss = 26*16;
				if(c=='+') poss = 29*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,poss,0,16, 16);
				if(c=='i') posX+=5;
				else if(c=='w'||c=='m') posX+=10;
				else posX+=9;
			}
		}
	}
	public static float getBoundsX(String s) {
		float posX=0;
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c==' '){
				posX+=9;
				continue;
			}else if(c>='0'&&c<='9'){
				posX+=10;
			}
			else if(c=='i')posX+=5;
			else if(c=='w'||c=='m')posX+=10;
			else posX+=9;
		}
		return posX;
	}
	public static void draw(Batch batch, String s, float x, float y, int i) {
		float posX = x-3;
		float posY = y-1;
		for(int j=0; j < s.length(); j++){
			char c=s.charAt(j);
			if(c==' '){
				posX+=9*i;
				continue;
			}
			if(c>='0'&&c<='9'){
				int poss=(c-'0')*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,0,0,16*i,16*i,
						1,1,0,poss,16,16, 16, false, false);
				posX+=10*i;
			}else{
				int poss = (c-'a') * 16;
				if(c=='\'')poss = 26*16;
				if(c=='.')poss = 28*16;
				if(c=='+')poss = 29*16;
				batch.draw(AssetHandler.getMyFontB(), posX, posY,0,0,16*i,16*i,
						1,1,0, poss,0,16, 16, false, false);
				if(c=='i')posX+=5*i;
				else if(c=='w'||c=='m')posX+=10*i;
				else posX+=9*i;
			}
		}
	}

}

