package gestos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helpers.GameInfo;
import hud.UIHud;
import quadrado.Quadrado;
import scenes.MainMenu;

public class GestosQuadrado extends ApplicationAdapter implements GestureDetector.GestureListener {

    private Quadrado quadrado;
    private Sprite backHomeButton;
    private GameMain game;
    private UIHud hud;

    public GestosQuadrado(Quadrado quadrado, Sprite backHomeButton, GameMain game, UIHud hud){
        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
        this.quadrado = quadrado;
        this.backHomeButton = backHomeButton;
        this.hud = hud;
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
                int scoreAtual = hud.getScore();
                scoreAtual++;

                List<Integer> scores = new ArrayList<>();

                Preferences prefs = Gdx.app.getPreferences("Data");
                scores.add(prefs.getInteger("ScorePlataforma1"));
                scores.add(prefs.getInteger("ScorePlataforma2"));
                scores.add(prefs.getInteger("ScorePlataforma3"));
                scores.add(prefs.getInteger("ScorePlataforma4"));
                scores.add(prefs.getInteger("ScorePlataforma5"));

                if (!scores.contains(scoreAtual)) {
                    scores.add(scoreAtual);
                }

                Collections.sort(scores, Collections.reverseOrder());

                while (scores.size() < 5) {
                    scores.add(0);
                }
                scores = scores.subList(0, 5);

                for (int i = 0; i < scores.size(); i++) {
                    prefs.putInteger("ScorePlataforma" + (i + 1), scores.get(i));
                }

                prefs.flush();

                hud.zerarScore();
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
        if (!quadrado.getAlive()){
            return false;
        }

        y = GameInfo.HEIGHT - y;
        if (y >= (quadrado.getY() - 200)) {
            quadrado.mudarPosicao(x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        quadrado.mudarPosicao(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f + 600f);
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
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
