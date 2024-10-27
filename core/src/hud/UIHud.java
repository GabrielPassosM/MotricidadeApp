package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import scenes.Gameplay;
import scenes.MainMenu;

public class UIHud {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel;

    private ImageButton retryBtn, quitBtn, backdropBtns;

    private int score;

    public UIHud(GameMain game, boolean eh_plataforma, int scoreAtual){
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        createLabel(eh_plataforma, scoreAtual);
    }

    void createLabel(boolean eh_plataforma, int scoreAtual){
        float posicaoX, posicaoY;
        int fontSize;

        if (eh_plataforma){
            fontSize = 150;
            posicaoY = 250;
            this.score = scoreAtual;
        } else {
            fontSize = 200;
            posicaoY = 300;
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/score_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;

        BitmapFont font = generator.generateFont(parameter);

        scoreLabel = new Label(String.valueOf(score), new Label.LabelStyle(font, Color.WHITE));
        if (eh_plataforma){
            posicaoX = 80f;
        } else {
            posicaoX = GameInfo.WIDTH / 2f - scoreLabel.getWidth() / 2;
        }
        scoreLabel.setPosition(posicaoX, GameInfo.HEIGHT - posicaoY);
        stage.addActor(scoreLabel);
    }

    public void createButtons(){
        String prefixo = GameInfo.prefixoCaminho();
        backdropBtns = new ImageButton(new SpriteDrawable( new Sprite(new Texture(prefixo + "fundo_btns2.png"))));
        retryBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/Retry.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/Quit.png"))));

        backdropBtns.setPosition((GameInfo.WIDTH / 2f) - (backdropBtns.getWidth() / 2f), (GameInfo.HEIGHT / 2f) - (backdropBtns.getHeight() / 2f));

        retryBtn.setPosition(GameInfo.WIDTH / 2f - (retryBtn.getWidth() / 2f) - 200f, (GameInfo.HEIGHT / 2f) - retryBtn.getHeight() - 100f);
        quitBtn.setPosition(GameInfo.WIDTH / 2f - (quitBtn.getWidth() / 2f) + 200f, (GameInfo.HEIGHT / 2f) - quitBtn.getHeight() - 100f);

        retryBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Gameplay(game));
                stage.dispose();
            }
        });
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                stage.dispose();
            }
        });

        stage.addActor(backdropBtns);
        stage.addActor(retryBtn);
        stage.addActor(quitBtn);
    }

    public void incrementScore(){
        score++;
        scoreLabel.setText(String.valueOf(score));
    }

    public void showScoreBolinha(){
        // tela gameover bolinha
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.setPosition(GameInfo.WIDTH / 2f + scoreLabel.getWidth() / 2,
                GameInfo.HEIGHT / 2f);
        stage.addActor(scoreLabel);
    }

    public void showScore(){
        // para manter o score na tela
        scoreLabel.setText(String.valueOf(score));
        stage.addActor(scoreLabel);
    }

    public int getScore() {
        return this.score;
    }

    public void zerarScore(){
        this.score = 0;
        showScore();
    }

    public Stage getStage(){
        return this.stage;
    }
}
