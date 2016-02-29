package br.com.livroandroid.playermp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListaMusicaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Musica> lstMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_musica);

        recyclerView = (RecyclerView) findViewById(R.id.rvMuscias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        lstMusica = retornaListaDeMusicas();

        recyclerView.setAdapter(new MusicaAdapter(this, lstMusica, onClickMusica()));
    }

    private MusicaAdapter.MusicaOnClickListener onClickMusica() {
        return new MusicaAdapter.MusicaOnClickListener() {
            @Override
            public void OnClickmusica(View view, int index) {
                lstMusica = retornaListaDeMusicas();
                Musica m = new Musica();
                m = lstMusica.get(index);

                Intent i = new Intent(ListaMusicaActivity.this, MainActivity.class);

                Bundle params = new Bundle();
                params.putString("MUSICA", m.getNomeMusica());
                params.putString("CAMINHO", m.getCaminhoMusica());
                i.putExtras(params);

                startActivity(i);
            }
        };
    }

    public List<Musica> retornaListaDeMusicas() {
        lstMusica = new ArrayList<>();
        String caminho = "/storage/emulated/0/música";
        File file = new File(caminho);
        File fList[] = file.listFiles();


        for (File item : fList) {
            String subDire = item.getPath();
            File f = new File(subDire);
            Musica m = new Musica();

            m.setNomeMusica(f.getName());
            m.setCaminhoMusica(f.getPath());

            lstMusica.add(m);

        }

        return lstMusica;

    }


   /* private MusicaAdapter.MusicaOnClickListener onClickJogo() {
        return new MusicaAdapter().JogoOnClickListener() {

            @Override
            public void OnClickJogo(View view, int index) {
                *//*JogosDAO dao = new JogosDAO(ListaJogosActivity.this);
                List<Jogo> lista = new ArrayList<>();
                lista = dao.getJogos();
                Jogo jogo = new Jogo();
                jogo = lista.get(index);
                Toast.makeText(getBaseContext(), "Jogo: " + jogo.getTitulo(), Toast.LENGTH_LONG).show();*//*

                showFilterPopup(view, index);
            }
        };
    }*/
}
