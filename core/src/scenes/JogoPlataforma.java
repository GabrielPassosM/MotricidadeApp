package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gestos.GestosQuadrado;
import helpers.GameInfo;
import hud.UIHud;
import plataformas.Plataformas;
import quadrado.Quadrado;

public class JogoPlataforma implements Screen, ContactListener {

    private GameMain game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private World world;
    private Quadrado quadrado;
    private Plataformas plataformas;
    private Sprite bg, backHomeButton;
    private GestosQuadrado ouvinteGestos;
//    private OrthographicCamera debugCamera;
//    private Box2DDebugRenderer debugRenderer;
    private Sound scoreSound, colisionSound;
    private UIHud hud;

    public JogoPlataforma(GameMain game, int scoreAtual) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        mainCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

//        debugCamera = new OrthographicCamera();
//        debugCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
//        debugCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
//
//        debugRenderer = new Box2DDebugRenderer();

        hud = new UIHud(game, true, scoreAtual);

        criarBackground();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);

        quadrado = new Quadrado(world, GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f + 600f);
        plataformas = new Plataformas(world);
        plataformas.setMainCamera(mainCamera);

        backHomeButton = new Sprite(new Texture(
                GameInfo.prefixoCaminho() + "Buttons/x_btn.png"));
        backHomeButton.setPosition(GameInfo.WIDTH - 150, GameInfo.HEIGHT - 200);

        ouvinteGestos = new GestosQuadrado(quadrado, backHomeButton, game, hud);

        scoreSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Score.mp3"));
        colisionSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Dead.mp3"));
    }

    void criarBackground(){
        bg = new Sprite(new Texture("Backgrounds/bg_plataforma.png"));
        bg.setPosition(0, 0);
    }

    void desenharBackgroud(SpriteBatch batch) {
        batch.draw(bg, 0, 0);
    }

    void drawBackButton(SpriteBatch batch){
        batch.draw(backHomeButton, backHomeButton.getX(), backHomeButton.getY());
    }

    void verificarColisao(){
        float baseQuadrado = quadrado.getY() - (quadrado.getHeight() / 2f);
        float topoQuadrado = baseQuadrado + 150f;
        float esquerdaQuadrado = quadrado.getX() - (quadrado.getWidth() / 2f);
        float direitaQuadrado = esquerdaQuadrado + 150f;

        if (baseQuadrado <= GameInfo.yPlat1 + 25f && topoQuadrado >= GameInfo.yPlat1) {
            if (esquerdaQuadrado <= plataformas.platX1.get(0) || direitaQuadrado > plataformas.platX1.get(1)){
                acoesColisao();
            }
        }

        if (baseQuadrado <= GameInfo.yPlat2 + 25f && topoQuadrado >= GameInfo.yPlat2) {
            if (esquerdaQuadrado <= plataformas.platX2.get(0) || direitaQuadrado > plataformas.platX2.get(1)){
                acoesColisao();
            }
        }

        if (baseQuadrado <= GameInfo.yPlat3 + 25f && topoQuadrado >= GameInfo.yPlat3) {
            if (esquerdaQuadrado <= plataformas.platX3.get(0) || direitaQuadrado > plataformas.platX3.get(1)){
                acoesColisao();
            }
        }

        if (baseQuadrado <= GameInfo.yPlat4 + 25f && topoQuadrado >= GameInfo.yPlat4) {
            if (esquerdaQuadrado <= plataformas.platX4.get(0) || direitaQuadrado > plataformas.platX4.get(1)){
                acoesColisao();
            }
        }

        if (baseQuadrado <= GameInfo.yPlat5 + 25f && topoQuadrado >= GameInfo.yPlat5) {
            if (esquerdaQuadrado <= plataformas.platX5.get(0) || direitaQuadrado > plataformas.platX5.get(1)){
                acoesColisao();
            }
        }

        if (baseQuadrado < GameInfo.yPlat5 - 50f){
            acoesPonto();
        }
    }

    void acoesColisao(){
        colisionSound.play();

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
        quadrado.mudarPosicao(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f + 600f);
    }

    void acoesPonto(){
        scoreSound.play();
        int scoreAtual = hud.getScore();
        scoreAtual++;

        game.setScreen(new JogoPlataforma(game, scoreAtual));
    }

//    void acoesPonto(){
//        scoreSound.play();
//        int scoreAtual = hud.getScore();
//        scoreAtual++;
//
//        Preferences prefs = Gdx.app.getPreferences("Data");
//        int highscore = prefs.getInteger("ScorePlataforma");
//
//        if(highscore < scoreAtual){
//            prefs.putInteger("ScorePlataforma", scoreAtual);
//            prefs.flush();
//        }
//
//        game.setScreen(new JogoPlataforma(game, scoreAtual));
//    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        game.getBatch().begin();

        desenharBackgroud(game.getBatch());
        plataformas.desenharPlataformas(game.getBatch());
        quadrado.desenharQuadrado(game.getBatch());
        drawBackButton(game.getBatch());

        game.getBatch().end();

//        debugRenderer.render(world, debugCamera.combined);

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();

        quadrado.updateQuadrado();

        verificarColisao();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
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
        bg.getTexture().dispose();
        plataformas.disposeAll();
        quadrado.getTexture().dispose();
        world.dispose();
        colisionSound.dispose();
        scoreSound.dispose();
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
