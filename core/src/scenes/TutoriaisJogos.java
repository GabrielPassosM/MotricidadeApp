package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;

public class TutoriaisJogos implements Screen {
    private GameMain game;
    private Viewport gameViewport;
    private OrthographicCamera mainCamera;
    private Texture bg;
    private Stage stage;
    private ImageButton voltarBtn, objetivo, objetivo_corpo, mecanica, mecanica_corpo;

    public TutoriaisJogos(GameMain game, Boolean ehBolinha){
        this.game = game;

        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

        bg = new Texture("Backgrounds/bg_info.png");

        stage = new Stage(gameViewport, game.getBatch());

        criarEPosicionarObjetos(ehBolinha);

        stage.addActor(voltarBtn);
        stage.addActor(objetivo);
        stage.addActor(objetivo_corpo);
        stage.addActor(mecanica);
        stage.addActor(mecanica_corpo);

        Gdx.input.setInputProcessor(stage);
    }

    void criarEPosicionarObjetos(boolean ehBolinha){
        String caminhoCorpoObjetivo, caminhoCorpoMecanica;
        if (ehBolinha) {
            caminhoCorpoObjetivo = "InfoAssets/objetivo_corpo.png";
            caminhoCorpoMecanica = "InfoAssets/mecanica_corpo.png";
        } else {
            caminhoCorpoObjetivo = "InfoAssets/objetivo_corpo_plataforma.png";
            caminhoCorpoMecanica = "InfoAssets/mecanica_corpo_plataforma.png";
        }

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

        objetivo = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "InfoAssets/objetivo_titulo.png"))));
        objetivo.setPosition((GameInfo.WIDTH / 2f) - (objetivo.getWidth() / 2f), voltarBtn.getY() - 150);

        objetivo_corpo = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + caminhoCorpoObjetivo))));
        objetivo_corpo.setPosition((GameInfo.WIDTH / 2f) - (objetivo_corpo.getWidth() / 2f),
                objetivo.getY() - objetivo_corpo.getHeight() - 50);

        mecanica = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + "InfoAssets/mecanica_titulo.png"))));
        mecanica.setPosition((GameInfo.WIDTH / 2f) - (mecanica.getWidth() / 2f), objetivo_corpo.getY() - 150);

        mecanica_corpo = new ImageButton(new SpriteDrawable(new Sprite(new Texture(prefixo + caminhoCorpoMecanica))));
        mecanica_corpo.setPosition((GameInfo.WIDTH / 2f) - (mecanica_corpo.getWidth() / 2f),
                mecanica.getY() - mecanica_corpo.getHeight() - 50);
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
        bg.dispose();
    }
}
