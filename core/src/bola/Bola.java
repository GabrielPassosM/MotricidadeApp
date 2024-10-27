package bola;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Bola extends Sprite {
    private World world;
    private Body body;
    private CircleShape shape;

    private boolean isAlive;

    public Bola (World world, float x, float y) {
        super(new Texture("bolinha_skin2.png"));
        this.world = world;
        setPosition(x, y);
        setOrigin(0.5f, 0.5f);
        this.shape = new CircleShape();
        this.body = createBody();
    }

    Body createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(false);
        this.shape.setRadius((getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_BOLA;
        fixtureDef.filter.maskBits = GameInfo.CATEGORIA_PAREDE | GameInfo.CATEGORIA_SCORE;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bola");

        body.setActive(false);

        return body;

    }

    public void activateBola(){
        isAlive = true;
        body.setActive(true);
    }

    public void mudarTamanho(float posicaoDedo) {
        this.body.destroyFixture(this.body.getFixtureList().first());

        this.setSize(posicaoDedo, posicaoDedo);
        this.shape.setRadius((getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bola");
    }

    public void drawBola(SpriteBatch batch) {
        this.setPosition(getX() - (this.getWidth() / 2f), getY() - (this.getHeight() / 2f));
        this.draw(batch);
    }

    public void updateBola() {
        setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getAlive() {
        return isAlive;
    }
}
