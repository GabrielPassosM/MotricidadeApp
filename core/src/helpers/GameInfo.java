package helpers;

import com.badlogic.gdx.Gdx;


public class GameInfo {
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
//    static float density = Gdx.graphics.getDensity();
//    public static final int WIDTH = (int) (Gdx.graphics.getWidth() / density);
//    public static final int HEIGHT = (int) (Gdx.graphics.getHeight() / density);
    public static final float PPM = 100f;
    public static final int DISTANCIA_ENTRE_PAREDES = 200;
    public static final short CATEGORIA_BOLA = 2;
    public static final short CATEGORIA_PAREDE = 4;
    public static final short CATEGORIA_SCORE = 6;
    public static final short CATEGORIA_QUADRADO = 8;
    public static final short CATEGORIA_PLATAFORMA = 16;
    public static final float yPlat1 = HEIGHT / 2f + 350f;
    public static final float yPlat2 = HEIGHT / 2f + 75f;
    public static final float yPlat3 = HEIGHT / 2f - 200f;
    public static final float yPlat4 = HEIGHT / 2f - 475f;
    public static final float yPlat5 = HEIGHT / 2f - 750f;

    public static String prefixoCaminho() {
        // 1657 tava sendo usado para pequenos
        // 2281 tava sendo usado para medios

        // cel antigo luiz: height = 2076 | width = 1080
        // cel novo luiz: height = 1573 | width = 810
        // cel vi: height = 2181 | width = 1080
//        int resolucao = WIDTH * HEIGHT;

        if (HEIGHT < 2080) {
            return "Pequenos/";
        } else if (HEIGHT < 2281) {
            return "Pequenos/";
        } else {
            return "Pequenos/";
        }
    }
}
