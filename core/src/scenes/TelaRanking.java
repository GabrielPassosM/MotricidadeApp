package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;

public class TelaRanking implements Screen {
    private GameMain game;
    private Viewport gameViewport;
    private OrthographicCamera mainCamera;
    private Texture bg;
    private Stage stage;
    private ImageButton voltarBtn, titulo, trocaBtn;
    private Label scoreLabel1, scoreLabel2, scoreLabel3, scoreLabel4, scoreLabel5;

    public TelaRanking(GameMain game, Boolean ehBolinha) {
        this.game = game;

        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

        bg = new Texture("Backgrounds/bg_info.png");

        stage = new Stage(gameViewport, game.getBatch());

        criarEPosicionarObjetos(ehBolinha);

        stage.addActor(voltarBtn);
        stage.addActor(titulo);
        stage.addActor(trocaBtn);

        Gdx.input.setInputProcessor(stage);
    }

    void criarEPosicionarObjetos(boolean ehBolinha){
        String prefixo = GameInfo.prefixoCaminho();

        voltarBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/voltar_botao.png"))));
        voltarBtn.setPosition(30, GameInfo.HEIGHT - voltarBtn.getHeight() - 50);
        voltarBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                stage.dispose();
            }
        });

        titulo = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "RankingAssets/titulo_ranking.png"))));
        titulo.setPosition((GameInfo.WIDTH / 2f) - (titulo.getWidth() / 2f), voltarBtn.getY() - titulo.getHeight() - 80);
        if (ehBolinha){
            trocaBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "RankingAssets/troca_ranking.png"))));
            trocaBtn.setPosition((GameInfo.WIDTH / 2f) - (trocaBtn.getWidth() / 2f), titulo.getY() - trocaBtn.getHeight() - 80);
            trocaBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new TelaRanking(game, false));
                    stage.dispose();
                }
            });
        } else {
            trocaBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "RankingAssets/ranking_quadrado.png"))));
            trocaBtn.setPosition((GameInfo.WIDTH / 2f) - (trocaBtn.getWidth() / 2f), titulo.getY() - trocaBtn.getHeight() - 80);
            trocaBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new TelaRanking(game, true));
                    stage.dispose();
                }
            });
        }

        showHighscore(ehBolinha);
    }

    void showHighscore(boolean ehBolinha){

        if(scoreLabel1 != null){
            return;
        }

        int tamanhoFont;
        if (GameInfo.prefixoCaminho().equals("Pequenos/")){
            tamanhoFont = 140;
        } else{
            tamanhoFont = 150;
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/score_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = tamanhoFont;
        BitmapFont font = generator.generateFont(parameter);

        Preferences prefs = Gdx.app.getPreferences("Data");

        if (ehBolinha){
            scoreLabel1 = new Label("1-  " + String.valueOf(prefs.getInteger("ScoreBolinha1")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel1.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel1.getWidth() / 2f),
                    trocaBtn.getY() - scoreLabel1.getHeight() - 50);
            scoreLabel2 = new Label("2-  " + String.valueOf(prefs.getInteger("ScoreBolinha2")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel2.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel2.getWidth() / 2f),
                    scoreLabel1.getY() - scoreLabel2.getHeight() - 50);
            scoreLabel3 = new Label("3-  " + String.valueOf(prefs.getInteger("ScoreBolinha3")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel3.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel3.getWidth() / 2f),
                    scoreLabel2.getY() - scoreLabel3.getHeight() - 50);
            scoreLabel4 = new Label("4-  " + String.valueOf(prefs.getInteger("ScoreBolinha4")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel4.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel4.getWidth() / 2f),
                    scoreLabel3.getY() - scoreLabel4.getHeight() - 50);
            scoreLabel5 = new Label("5-  " + String.valueOf(prefs.getInteger("ScoreBolinha5")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel5.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel5.getWidth() / 2f),
                    scoreLabel4.getY() - scoreLabel5.getHeight() - 50);
        } else {
            scoreLabel1 = new Label("1-  " + String.valueOf(prefs.getInteger("ScorePlataforma1")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel1.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel1.getWidth() / 2f),
                    trocaBtn.getY() - scoreLabel1.getHeight() - 50);
            scoreLabel2 = new Label("2-  " + String.valueOf(prefs.getInteger("ScorePlataforma2")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel2.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel2.getWidth() / 2f),
                    scoreLabel1.getY() - scoreLabel2.getHeight() - 50);
            scoreLabel3 = new Label("3-  " + String.valueOf(prefs.getInteger("ScorePlataforma3")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel3.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel3.getWidth() / 2f),
                    scoreLabel2.getY() - scoreLabel3.getHeight() - 50);
            scoreLabel4 = new Label("4-  " + String.valueOf(prefs.getInteger("ScorePlataforma4")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel4.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel4.getWidth() / 2f),
                    scoreLabel3.getY() - scoreLabel4.getHeight() - 50);
            scoreLabel5 = new Label("5-  " + String.valueOf(prefs.getInteger("ScorePlataforma5")), new Label.LabelStyle(font, Color.BLACK));
            scoreLabel5.setPosition((GameInfo.WIDTH / 2f) - (scoreLabel5.getWidth() / 2f),
                    scoreLabel4.getY() - scoreLabel5.getHeight() - 50);
        }

        stage.addActor(scoreLabel1);
        stage.addActor(scoreLabel2);
        stage.addActor(scoreLabel3);
        stage.addActor(scoreLabel4);
        stage.addActor(scoreLabel5);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        game.getBatch().begin();

        game.getBatch().draw(bg, 0, 0);

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}