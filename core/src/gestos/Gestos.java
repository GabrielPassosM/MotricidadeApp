package gestos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;

import bola.Bola;
import helpers.GameInfo;
import scenes.MainMenu;

public class Gestos extends ApplicationAdapter implements GestureDetector.GestureListener {

    private Bola bola;
    private Sprite backHomeButton;
    private GameMain game;

    public Gestos(Bola bola, Sprite backHomeButton, GameMain game) {
        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
        this.bola = bola;
        this.backHomeButton = backHomeButton;
        this.game = game;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        y = GameInfo.HEIGHT - y;
        if (x >= backHomeButton.getX() && x <= backHomeButton.getX() + backHomeButton.getWidth()) {
            if (y >= backHomeButton.getY() && y <= backHomeButton.getY() + backHomeButton.getHeight()){
                game.setScreen(new MainMenu(game));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
//        float touchX = Gdx.input.getX() + bola.getWidth();
//        float touchX = bola.getX();
//        float touchY = Gdx.input.getY();
//        if (bola.getBoundingRectangle().contains(touchX, touchY)) {
        if(bola.getAlive() && distance >= 280){
            bola.mudarTamanho(distance);
            return true;
        }
//        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
