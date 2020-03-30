package com.chyb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.screens.ExtendedScreen;
import com.chyb.snake.screens.GameScreen;
import com.chyb.snake.screens.TitleScreen;
import com.chyb.snake.utils.AssetHandler;

public class Startup extends Game {
	public static int SCR_WIDTH = 640;
	public static int SCR_HEIGHT = 360;
	private TitleScreen titleScr;
	private GameScreen gameScr;

	@Override
	public void create () {
		AssetHandler.loadAll();
		titleScr = new TitleScreen(this);
		gameScr = new GameScreen(this);
		this.setScreen(new TitleScreen(this));
	}
	public void setExtendedScreen(ExtendedScreen screen){
		this.setScreen(screen);
	}
	public void setTitleScreen(){
		this.setScreen(titleScr);
	}
	public void setGameScreen(){
		gameScr.reset();
		this.setScreen(gameScr);
	}
	@Override
	public void dispose () {
		AssetHandler.dispose();
	}
}