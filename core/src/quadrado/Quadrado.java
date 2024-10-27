package quadrado;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Quadrado extends Sprite {
    private World world;
    private Body body;
    private PolygonShape shape;
    private boolean isAlive;

    public Quadrado (World world, float x, float y){
        super(new Texture("quadrado.png"));
        this.world = world;
        this.isAlive = true;
        setPosition(x, y);
        setOrigin(0.5f, 0.5f);
        this.shape = new PolygonShape();
        this.body = criarBody();
    }

    Body criarBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        this.shape.setAsBox((getWidth() / 2f) / GameInfo.PPM, (getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_QUADRADO;
        fixtureDef.filter.maskBits = GameInfo.CATEGORIA_PLATAFORMA;

        Fixture fixture1 = body.createFixture(fixtureDef);
        fixture1.setUserData("Quadrado");

        return body;
    }

    public void desenharQuadrado(SpriteBatch batch){
        this.setPosition(getX() - (this.getWidth() / 2f), getY() - (this.getHeight() / 2f));
        this.draw(batch);
    }

    public void updateQuadrado(){
        setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }

    public void mudarPosicao(float x, float y){
        this.body.destroyFixture(this.body.getFixtureList().first());

        this.setPosition(x, y);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        this.body = world.createBody(bodyDef);
        this.body.setFixedRotation(false);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this.shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_QUADRADO;
        fixtureDef.filter.maskBits = GameInfo.CATEGORIA_PLATAFORMA;
        Fixture fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData("Quadrado");
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean getAlive(){
        return isAlive;
    }
}
