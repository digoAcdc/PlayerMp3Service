package br.com.livroandroid.playermp3;

/**
 * Created by rodrigo on 28/12/2015.
 */
public class Musica {
    private String NomeMusica;
    private String DescricaoMusica;
    private String CaminhoMusica;

    public String getCaminhoMusica() {
        return CaminhoMusica;
    }

    public void setCaminhoMusica(String caminhoMusica) {
        CaminhoMusica = caminhoMusica;
    }

    public String getNomeMusica() {
        return NomeMusica;
    }

    public void setNomeMusica(String nomeMusica) {
        NomeMusica = nomeMusica;
    }

    public String getDescricaoMusica() {
        return DescricaoMusica;
    }

    public void setDescricaoMusica(String descricaoMusica) {
        DescricaoMusica = descricaoMusica;
    }
}
