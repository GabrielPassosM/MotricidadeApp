package plataformas;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import helpers.GameInfo;

public class Plataformas {

    private Sprite plat1, plat2, plat3, plat4, plat5;
    private Body body1, body2, body3, body4, body5;
    private Body body6, body7, body8, body9, body10;
    private OrthographicCamera mainCamera;
    private World world;
    public ArrayList<Float> platX1 = new ArrayList<>();
    public ArrayList<Float> platX2 = new ArrayList<>();
    public ArrayList<Float> platX3 = new ArrayList<>();
    public ArrayList<Float> platX4 = new ArrayList<>();
    public ArrayList<Float> platX5 = new ArrayList<>();

    public Plataformas(World world){
        this.world = world;
        criarTodasPlataformas();
    }

    void criarTodasPlataformas(){
        plat1 = new Sprite(new Texture("plataforma.png"));
        plat2 = new Sprite(new Texture("plataforma.png"));
        plat3 = new Sprite(new Texture("plataforma.png"));
        plat4 = new Sprite(new Texture("plataforma.png"));
        plat5 = new Sprite(new Texture("plataforma.png"));

        int x1 = sortearPosicao(-1);
        int x2 = sortearPosicao(x1);
        int x3 = sortearPosicao(x2);
        int x4 = sortearPosicao(x3);
        int x5 = sortearPosicao(x4);

        plat1.setPosition(-x1, GameInfo.yPlat1);
        plat2.setPosition(-x2, GameInfo.yPlat2);
        plat3.setPosition(-x3, GameInfo.yPlat3);
        plat4.setPosition(-x4, GameInfo.yPlat4);
        plat5.setPosition(-x5, GameInfo.yPlat5);

        this.platX1.add(plat1.getX() + 550f);
        this.platX1.add(platX1.get(0) + 250f);

        this.platX2.add(plat2.getX() + 550f);
        this.platX2.add(platX2.get(0) + 250f);

        this.platX3.add(plat3.getX() + 550f);
        this.platX3.add(platX3.get(0) + 250f);

        this.platX4.add(plat4.getX() + 550f);
        this.platX4.add(platX4.get(0) + 250f);

        this.platX5.add(plat5.getX() + 550f);
        this.platX5.add(platX5.get(0) + 250f);

//        criarBodys();
//        criarFixtures();
    }

    Integer sortearPosicao(Integer ultimaPosicao) {
        ArrayList<Integer> posicoes = new ArrayList<>();
        if (ultimaPosicao == -1) {
            posicoes.add(0);
            posicoes.add(250);
            posicoes.add(500);
        } else if (ultimaPosicao == 0) {
            posicoes.add(250);
            posicoes.add(500);
        } else if (ultimaPosicao == 250) {
            posicoes.add(0);
            posicoes.add(500);
        } else {
            posicoes.add(0);
            posicoes.add(250);
        }

        Random rand = new Random();
        int indiceSorteado = rand.nextInt(posicoes.size());

        return posicoes.get(indiceSorteado);
    }

    void criarBodys(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Plataforma 1
        bodyDef.position.set((plat1.getX() + (plat1.getWidth() / 2f) - 900f) / GameInfo.PPM, (plat1.getY() + (plat1.getHeight() / 2f)) / GameInfo.PPM);
        body1 = world.createBody(bodyDef);
        body1.setFixedRotation(false);
        bodyDef.position.set((plat1.getX() + (plat1.getWidth() / 2f) + 200f) / GameInfo.PPM, (plat1.getY() + (plat1.getHeight() / 2f)) / GameInfo.PPM);
        body2 = world.createBody(bodyDef);
        body2.setFixedRotation(false);

        // Plataforma 2
        bodyDef.position.set((plat2.getX() + (plat2.getWidth() / 2f) - 900f) / GameInfo.PPM, (plat2.getY() + (plat2.getHeight() / 2f)) / GameInfo.PPM);
        body3 = world.createBody(bodyDef);
        body3.setFixedRotation(false);
        bodyDef.position.set((plat2.getX() + (plat2.getWidth() / 2f) + 200f) / GameInfo.PPM, (plat2.getY() + (plat2.getHeight() / 2f)) / GameInfo.PPM);
        body4 = world.createBody(bodyDef);
        body4.setFixedRotation(false);

        // Plataforma 3
        bodyDef.position.set((plat3.getX() + (plat3.getWidth() / 2f) - 900f) / GameInfo.PPM, (plat3.getY() + (plat3.getHeight() / 2f)) / GameInfo.PPM);
        body5 = world.createBody(bodyDef);
        body5.setFixedRotation(false);
        bodyDef.position.set((plat3.getX() + (plat3.getWidth() / 2f) + 200f) / GameInfo.PPM, (plat3.getY() + (plat3.getHeight() / 2f)) / GameInfo.PPM);
        body6 = world.createBody(bodyDef);
        body6.setFixedRotation(false);

        // Plataforma 4
        bodyDef.position.set((plat4.getX() + (plat4.getWidth() / 2f) - 900f) / GameInfo.PPM, (plat4.getY() + (plat4.getHeight() / 2f)) / GameInfo.PPM);
        body7 = world.createBody(bodyDef);
        body7.setFixedRotation(false);
        bodyDef.position.set((plat4.getX() + (plat4.getWidth() / 2f) + 200f) / GameInfo.PPM, (plat4.getY() + (plat4.getHeight() / 2f)) / GameInfo.PPM);
        body8 = world.createBody(bodyDef);
        body8.setFixedRotation(false);

        // Plataforma 5
        bodyDef.position.set((plat5.getX() + (plat5.getWidth() / 2f) - 900f) / GameInfo.PPM, (plat5.getY() + (plat5.getHeight() / 2f)) / GameInfo.PPM);
        body9 = world.createBody(bodyDef);
        body9.setFixedRotation(false);
        bodyDef.position.set((plat5.getX() + (plat5.getWidth() / 2f) + 200f) / GameInfo.PPM, (plat5.getY() + (plat5.getHeight() / 2f)) / GameInfo.PPM);
        body10 = world.createBody(bodyDef);
        body10.setFixedRotation(false);
    }

    void criarFixtures(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((plat1.getWidth() / 2f - 1450f) / GameInfo.PPM, (plat1.getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.CATEGORIA_PLATAFORMA;

        Fixture fixture1 = body1.createFixture(fixtureDef);
        fixture1.setUserData("Parede");
        Fixture fixture2 = body2.createFixture(fixtureDef);
        fixture2.setUserData("Parede");
        Fixture fixture3 = body3.createFixture(fixtureDef);
        fixture3.setUserData("Parede");
        Fixture fixture4 = body4.createFixture(fixtureDef);
        fixture4.setUserData("Parede");
        Fixture fixture5 = body5.createFixture(fixtureDef);
        fixture5.setUserData("Parede");
        Fixture fixture6 = body6.createFixture(fixtureDef);
        fixture6.setUserData("Parede");
        Fixture fixture7 = body7.createFixture(fixtureDef);
        fixture7.setUserData("Parede");
        Fixture fixture8 = body8.createFixture(fixtureDef);
        fixture8.setUserData("Parede");
        Fixture fixture9 = body9.createFixture(fixtureDef);
        fixture9.setUserData("Parede");
        Fixture fixture10 = body10.createFixture(fixtureDef);
        fixture10.setUserData("Parede");
    }

    public void desenharPlataformas(SpriteBatch batch){
        batch.draw(plat1, plat1.getX(), plat1.getY());
        batch.draw(plat2, plat2.getX(), plat2.getY());
        batch.draw(plat3, plat3.getX(), plat3.getY());
        batch.draw(plat4, plat4.getX(), plat4.getY());
        batch.draw(plat5, plat5.getX(), plat5.getY());
    }

    public void setMainCamera(OrthographicCamera mainCamera){
        this.mainCamera = mainCamera;
    }

    public void disposeAll(){
        plat1.getTexture().dispose();
        plat2.getTexture().dispose();
        plat3.getTexture().dispose();
        plat4.getTexture().dispose();
        plat5.getTexture().dispose();
    }
}
