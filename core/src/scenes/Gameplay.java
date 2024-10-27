package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bola.Bola;
import gestos.Gestos;
import helpers.GameInfo;
import helpers.OptionsManager;
import hud.UIHud;
import paredes.Paredes;

public class Gameplay implements Screen, ContactListener {

    private GameMain game;

    private World world;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
//    private OrthographicCamera debugCamera;
//    private Box2DDebugRenderer debugRenderer;
    private Array<Sprite> backgrounds = new Array<Sprite>();
    private Bola bola;

    private UIHud hud;

    private boolean firstTouch;

    private Array<Paredes> paredesArray = new Array<Paredes>();

    private Sound scoreSound, colisionSound;
    private Gestos ouvinteGestos;

    private String ultimoBuraco;

    private String direcao;

    private Sprite backHomeButton, dicaInicio;

    public Gameplay (GameMain game) {
        this.game = game;
        ultimoBuraco = "pequeno";
        if (OptionsManager.getInstance().getMao() == "mao_esquerda"){
            direcao = "direita";
        } else {
            direcao = "esquerda";
        }

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        mainCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

//        debugCamera = new OrthographicCamera();
//        debugCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
//        debugCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

//        debugRenderer = new Box2DDebugRenderer();

        hud = new UIHud(game, false, 0);

        createBackgrounds();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);

        float xBola;
        if (direcao == "direita"){
            xBola = GameInfo.WIDTH / 2f - 80;
        } else {
            xBola = GameInfo.WIDTH / 2f + 80;
        }
        bola = new Bola(world, xBola, GameInfo.HEIGHT / 2f);

        String prefixo = GameInfo.prefixoCaminho();
        backHomeButton = new Sprite(new Texture(prefixo + "Buttons/x_btn.png"));
        backHomeButton.setPosition(GameInfo.WIDTH - 200, GameInfo.HEIGHT - 200);

        dicaInicio = new Sprite(new Texture(prefixo + "iniciar_bolinha.png"));
        dicaInicio.setPosition(
                (GameInfo.WIDTH / 2f) - (dicaInicio.getWidth() / 2f),
                bola.getY() - bola.getHeight() - 50
        );

        ouvinteGestos = new Gestos(bola, backHomeButton, game);

        scoreSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Score.mp3"));
        colisionSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Dead.mp3"));
    }

    void checkForFirstTouch(){
        if(!firstTouch){
            if(Gdx.input.justTouched()){
                firstTouch = true;
                bola.activateBola();
                createAllParedes();
            }
        }
    }

    void update(float dt){

        checkForFirstTouch();

        if(bola.getAlive()){
            moveBackgrounds();
            updateParedes();
            moveParedes();
        }
    }

    void createAllParedes(){
        RunnableAction run = new RunnableAction();
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                createParedes();
            }
        });

        // velocidade que cria as paredes
        SequenceAction sa = new SequenceAction();
        sa.addAction(Actions.delay(5f));
        sa.addAction(run);

        hud.getStage().addAction(Actions.forever(sa));
    }

    void createBackgrounds() {
        for (int i = 0; i < 3; i++) {
            Sprite bg = new Sprite(new Texture("Backgrounds/bg_bolinha2.png"));
            bg.setPosition(i * bg.getWidth(), 0);
            backgrounds.add(bg);
        }
    }

    void drawBackgrounds(SpriteBatch batch) {
        for (Sprite s : backgrounds) {
            batch.draw(s, s.getX(), s.getY());
        }
    }

    void drawBackButton(SpriteBatch batch){
        batch.draw(backHomeButton, backHomeButton.getX(), backHomeButton.getY());
    }

    void drawDicaInicio(SpriteBatch batch, boolean jogoComecou){
        if(!jogoComecou){
            batch.draw(dicaInicio, dicaInicio.getX(), dicaInicio.getY());
        }
    }

    void moveBackgrounds() {
        float newX;
        for (Sprite bg : backgrounds) {
            if (direcao == "direita"){
                newX = bg.getX() - 2f;
                bg.setPosition(newX, bg.getY());
                if (bg.getX() + GameInfo.WIDTH + (bg.getWidth() / 2f) < mainCamera.position.x) {
                    float newerX = bg.getX() + bg.getWidth() * backgrounds.size;
                    bg.setPosition(newerX, bg.getY());
                }
            } else {
                newX = bg.getX() + 2f;
                bg.setPosition(newX, bg.getY());
                if (bg.getX() - GameInfo.WIDTH - (bg.getWidth() - bg.getWidth() - 50) > mainCamera.position.x) {
                    float newerX = bg.getX() - bg.getWidth() * backgrounds.size;
                    bg.setPosition(newerX, bg.getY());
                }
            }
        }
    }

    void createParedes(){
        Paredes p = new Paredes(world, ultimoBuraco, direcao);
        if (ultimoBuraco == "pequeno") {
            ultimoBuraco = "grande";
        } else {
            ultimoBuraco = "pequeno";
        }
        p.setMainCamera(mainCamera);
        paredesArray.add(p);
    }

    void drawParedes(SpriteBatch batch){
        for (Paredes parede : paredesArray){
            parede.drawParedes(batch);
        }
    }

    void updateParedes(){
        for (Paredes parede : paredesArray){
            parede.updateParedes();
        }
    }

    void moveParedes(){
        for (Paredes parede : paredesArray){
            parede.moveParedes(direcao);
        }
    }

    public void bolaColidiu(){
        bola.setAlive(false);
        stopParedes();

        hud.getStage().clear();

        backHomeButton.setPosition(-10000, -10000);

        int scoreAtual = hud.getScore();

        List<Integer> scores = new ArrayList<>();

        Preferences prefs = Gdx.app.getPreferences("Data");
        scores.add(prefs.getInteger("ScoreBolinha1"));
        scores.add(prefs.getInteger("ScoreBolinha2"));
        scores.add(prefs.getInteger("ScoreBolinha3"));
        scores.add(prefs.getInteger("ScoreBolinha4"));
        scores.add(prefs.getInteger("ScoreBolinha5"));

        if (!scores.contains(scoreAtual)) {
            scores.add(scoreAtual);
        }

        Collections.sort(scores, Collections.reverseOrder());

        while (scores.size() < 5) {
            scores.add(0);
        }
        scores = scores.subList(0, 5);

        for (int i = 0; i < scores.size(); i++) {
            prefs.putInteger("ScoreBolinha" + (i + 1), scores.get(i));
        }

        prefs.flush();

        hud.createButtons();
        hud.showScoreBolinha();
        Gdx.input.setInputProcessor(hud.getStage());
    }

    void stopParedes(){
        for(Paredes parede: paredesArray){
            parede.stopParedes();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(1, 0, 0, 1);
        game.getBatch().begin();

        drawBackgrounds(game.getBatch());
        bola.drawBola(game.getBatch());

        drawParedes(game.getBatch());
        drawBackButton(game.getBatch());
        drawDicaInicio(game.getBatch(), bola.getAlive());

        game.getBatch().end();

//        debugRenderer.render(world, debugCamera.combined);

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();

        bola.updateBola();

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
        for(Sprite bg : backgrounds){
            bg.getTexture().dispose();
        }

        for(Paredes parede : paredesArray){
            parede.disposeAll();
        }

        world.dispose();
        scoreSound.dispose();
        colisionSound.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture body1, body2;

        if(contact.getFixtureA().getUserData() == "Bola") {
            body1 = contact.getFixtureA();
            body2 = contact.getFixtureB();
        } else {
            body1 = contact.getFixtureB();
            body2 = contact.getFixtureA();
        }

        if(body1.getUserData() == "Bola" && body2.getUserData() == "Parede"){
            if(bola.getAlive()){
                colisionSound.play();
                bolaColidiu();
            }
        }

        if(body1.getUserData() == "Bola" && body2.getUserData() instanceof Paredes && bola.getAlive()){
            Paredes paredeScore = (Paredes) body2.getUserData();
            if (!paredeScore.scorePassado) {
                scoreSound.play();
                hud.incrementScore();
                paredeScore.scorePassado = true;
            }
        }

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
