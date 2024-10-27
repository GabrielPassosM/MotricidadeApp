package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import helpers.OptionsManager;
import scenes.Gameplay;
import scenes.JogoPlataforma;
import scenes.TelaRanking;
import scenes.TutoriaisJogos;

public class MainMenuButtons {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton bolinhaBtn, rankingBtn, plataformaBtn, titulo, changeMaoBtn, infoBolinha, infoPlataforma, infoMao;

    public MainMenuButtons(GameMain game){
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        createAndPositionButtons();

        stage.addActor(bolinhaBtn);
        stage.addActor(plataformaBtn);
        stage.addActor(rankingBtn);
        stage.addActor(titulo);
        stage.addActor(infoBolinha);
        stage.addActor(infoPlataforma);

        changeMao();

    }

    void createAndPositionButtons(){
        String prefixo = GameInfo.prefixoCaminho();
        bolinhaBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/botao_bolinha2.png"))));
        plataformaBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/botao_quadrado2.png"))));
        rankingBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/ranking2.png"))));
        infoBolinha = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/info_icon.png"))));
        infoPlataforma = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "Buttons/info_icon.png"))));

        bolinhaBtn.setPosition(
                GameInfo.WIDTH / 2f - bolinhaBtn.getWidth() - 50,
                GameInfo.HEIGHT / 2f - (bolinhaBtn.getHeight() / 2))
        ;
        infoBolinha.setPosition(
                bolinhaBtn.getX() + bolinhaBtn.getWidth() - (infoBolinha.getWidth() / 2f),
                bolinhaBtn.getY() + 100f
        );
        plataformaBtn.setPosition(
                GameInfo.WIDTH / 2f + 50,
                GameInfo.HEIGHT / 2f - (plataformaBtn.getHeight() / 2)
        );
        infoPlataforma.setPosition(
                plataformaBtn.getX() + plataformaBtn.getWidth() - (infoPlataforma.getWidth() / 2f),
                plataformaBtn.getY() + 100f
        );
        rankingBtn.setPosition((GameInfo.WIDTH / 2f) - (rankingBtn.getWidth() / 2f),
                bolinhaBtn.getY() - rankingBtn.getHeight() - 50f);

        bolinhaBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Gameplay(game));
                stage.dispose();
            }
        });

        infoBolinha.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TutoriaisJogos(game, true));
                stage.dispose();
            }
        });

        plataformaBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new JogoPlataforma(game, 0));
                stage.dispose();
            }
        });

        infoPlataforma.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TutoriaisJogos(game, false));
                stage.dispose();
            }
        });

        rankingBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                showHighscore();
                game.setScreen(new TelaRanking(game, true));
            }
        });

        // TITULO
        titulo = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "motrijogos.png"))));
        titulo.setPosition((GameInfo.WIDTH / 2f) - (titulo.getWidth() / 2f), GameInfo.HEIGHT - titulo.getHeight() - (GameInfo.HEIGHT / 12f));
    }

    void changeMao(){
        if (changeMaoBtn != null){
            changeMaoBtn.remove();
        }

        String prefixo = GameInfo.prefixoCaminho();
        changeMaoBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
                prefixo + "Buttons/" + OptionsManager.getInstance().getMao() + ".png"
        ))));

        changeMaoBtn.setPosition(GameInfo.WIDTH / 2f - changeMaoBtn.getWidth() - 100,
                (changeMaoBtn.getHeight() / 2f) - 50
        );

        changeMaoBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                OptionsManager.getInstance().outraMao();
                changeMao();
            }
        });

        infoMao = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "info_mao.png"))));
        infoMao.setPosition(changeMaoBtn.getX() + changeMaoBtn.getWidth(),
                changeMaoBtn.getY() + 20);

        stage.addActor(changeMaoBtn);
        stage.addActor(infoMao);
    }

    public Stage getStage(){
        return this.stage;
    }
}
