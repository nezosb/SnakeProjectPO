package com.chyb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chyb.snake.screens.GameScreen;
import com.chyb.snake.screens.TitleScreen;
import com.chyb.snake.utils.AssetHandler;

public class Startup extends Game {
	private Texture img;
	private OrthographicCamera camera;

	@Override
	public void create () {
		AssetHandler.loadAll();
		this.setScreen(new TitleScreen(this));
	}
	public void setGame(){
		this.setScreen(new GameScreen());
	}

	@Override
	public void dispose () {
		AssetHandler.dispose();
	}
}
