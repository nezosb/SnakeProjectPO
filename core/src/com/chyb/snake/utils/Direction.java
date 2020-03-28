package com.chyb.snake.utils;

public enum Direction {
    L(new Vector2D(-1,0), 180),R(new Vector2D(1,0),0),
    U(new Vector2D(0,1),90),D(new Vector2D(0,-1),270);
    public final Vector2D toUnitVector;
    public final int rotation;

    Direction(Vector2D position, int rotation){
        this.toUnitVector = position;
        this.rotation = rotation;
    }

    public Direction flip() {
        switch(this){
            case L:
                return R;
            case R:
                return L;
            case U:
                return D;
            case D:
                return U;
        }
        return null;
    }
}
