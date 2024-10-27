package helpers;

public class OptionsManager {
    private static OptionsManager ourInstance = new OptionsManager();

    private String mao = "mao_esquerda";

    private OptionsManager(){
    }

    public void outraMao(){
        if (mao == "mao_esquerda"){
            mao = "mao_direita";
        } else {
            mao = "mao_esquerda";
        }
    }

    public String getMao(){
        return mao;
    }

    public static OptionsManager getInstance() { return ourInstance; }
}
