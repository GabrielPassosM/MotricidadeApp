package paredes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import helpers.GameInfo;

public class Paredes {

    private World world;
    private Body body1, body2, body3;
    private Sprite parede1, parede2, sensor;
    private Random randomHole = new Random();

    public Boolean scorePassado;

    private String ultimoBuraco;

    private String direcao;

    private OrthographicCamera mainCamera;

    public Paredes(World world, String ultimoBuraco, String direcao) {
        this.world = world;
        this.scorePassado = false;
        this.ultimoBuraco = ultimoBuraco;
        this.direcao = direcao;
        createParedes();
    }

    void createParedes(){
        parede1 = new Sprite(new Texture("parede_skin2.png"));
        parede2 = new Sprite(new Texture("parede_skin2.png"));

        float x;
        if (direcao == "direita"){
            x = GameInfo.WIDTH + GameInfo.DISTANCIA_ENTRE_PAREDES;
        } else {
            x = 0f - GameInfo.DISTANCIA_ENTRE_PAREDES;
        }
        float buraco = gerarBuracoAleatorio() / 2f;
        float centroTela = (GameInfo.HEIGHT / 2f) + (parede1.getHeight() / 2f);
        float y = centroTela + buraco;
        float yParede2 = centroTela - buraco - parede2.getHeight();

        parede1.setPosition(x, y);
        parede2.setPosition(x, yParede2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        bodyDef.position.set(parede1.getX() / GameInfo.PPM, parede1.getY() / GameInfo.PPM);
        body1 = world.createBody(bodyDef);
        body1.setFixedRotation(false);
        bodyDef.position.set(parede2.getX() / GameInfo.PPM, parede2.getY() / GameInfo.PPM);
        body2 = world.createBody(bodyDef);
        body2.setFixedRotation(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((parede1.getWidth() / 2f) / GameInfo.PPM, (parede1.getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_PAREDE;

        Fixture fixture1 = body1.createFixture(fixtureDef);
        fixture1.setUserData("Parede");
        Fixture fixture2 = body2.createFixture(fixtureDef);
        fixture2.setUserData("Parede");

        // parede sensor
        float proporcaoBuraco = buraco / 5;
        sensor = new Sprite(new Texture("sensor.png"));
        sensor.setPosition(parede1.getX(), parede2.getY() + (parede2.getHeight() / 2) + proporcaoBuraco);

        bodyDef.position.set(sensor.getX() / GameInfo.PPM, sensor.getY() / GameInfo.PPM);
        body3 = world.createBody(bodyDef);
        body3.setFixedRotation(false);

        shape.setAsBox((sensor.getWidth() / 2f) / GameInfo.PPM, 8 / GameInfo.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_SCORE;
        fixtureDef.isSensor = true;
        Fixture fixture3 = body3.createFixture(fixtureDef);
        fixture3.setUserData(this);

        shape.dispose();
    }

    public void drawParedes(SpriteBatch batch) {
        batch.draw(parede1, parede1.getX() - parede1.getWidth() / 2f, parede1.getY() - parede1.getHeight() / 2f);
        batch.draw(parede2, parede2.getX() - parede2.getWidth() / 2f, parede2.getY() - parede2.getHeight() / 2f);
        batch.draw(sensor, sensor.getX() - sensor.getWidth() / 2f, sensor.getY() - sensor.getHeight() / 2f);
    }

    public void updateParedes() {
        parede1.setPosition(body1.getPosition().x * GameInfo.PPM, body1.getPosition().y * GameInfo.PPM);
        parede2.setPosition(body2.getPosition().x * GameInfo.PPM, body2.getPosition().y * GameInfo.PPM);
        sensor.setPosition(body3.getPosition().x * GameInfo.PPM, body3.getPosition().y * GameInfo.PPM);
    }

    public void moveParedes(String direcao){
        if (direcao == "direita") {
            body1.setLinearVelocity(-2f, 0);
            body2.setLinearVelocity(-2f, 0);
            body3.setLinearVelocity(-2f, 0);

            if (parede1.getX() + (GameInfo.WIDTH / 2f) + 200 < mainCamera.position.x){
                body1.setActive(false);
                body2.setActive(false);
                body3.setActive(false);
            }
        } else {
            body1.setLinearVelocity(2f, 0);
            body2.setLinearVelocity(2f, 0);
            body3.setLinearVelocity(2f, 0);

            if (parede1.getX() - (GameInfo.WIDTH / 2f) - 200 > mainCamera.position.x){
                body1.setActive(false);
                body2.setActive(false);
                body3.setActive(false);
            }
        }

    }

    public void stopParedes(){
        body1.setLinearVelocity(0, 0);
        body2.setLinearVelocity(0, 0);
        body3.setLinearVelocity(0, 0);
    }

    public void setMainCamera(OrthographicCamera mainCamera){
        this.mainCamera = mainCamera;
    }

    float gerarBuracoAleatorio(){
        float max, min;

        if (this.ultimoBuraco == "pequeno") {
            // gera um grande
            max = GameInfo.HEIGHT / 2f + 650f;
            min = GameInfo.HEIGHT / 2f + 350f;
        } else {
            // gera um pequeno
            max = GameInfo.HEIGHT / 2f - 600f;
            min = GameInfo.HEIGHT / 2f - 750f;
        }

        float tamanho_buraco = randomHole.nextFloat() * (max - min) + min;

        if (tamanho_buraco < 340) {
            tamanho_buraco = 340;
        }

        return tamanho_buraco;
    }

    public void disposeAll(){
        parede1.getTexture().dispose();
        parede2.getTexture().dispose();
        sensor.getTexture().dispose();
    }
}
