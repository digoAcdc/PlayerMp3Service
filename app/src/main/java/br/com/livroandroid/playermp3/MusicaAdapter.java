package br.com.livroandroid.playermp3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rodrigo on 28/12/2015.
 */
public class MusicaAdapter extends RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder> {
    private Context context;
    private List<Musica> lstMusicas;
    private final MusicaOnClickListener onClickListener;


    public MusicaAdapter(Context context, List<Musica> lstMusicas, MusicaOnClickListener onClickListener) {
        this.context = context;
        this.lstMusicas = lstMusicas;
        this.onClickListener = onClickListener;
    }

    public interface MusicaOnClickListener
    {
        public void OnClickmusica(View view, int index );
    }

    @Override
    public MusicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_musica, parent, false);
        MusicaViewHolder holder = new MusicaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MusicaViewHolder holder, final int position) {
        Musica m = lstMusicas.get(position);
        holder.tvDescMusica.setText(m.getDescricaoMusica());
        holder.tvNomeMusica.setText(m.getNomeMusica());

        if(onClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickmusica(holder.view, position);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstMusicas.size();
    }

    public static class MusicaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeMusica;
        TextView tvDescMusica;
        /*  ImageView ivJogo;*/
        ImageView ivOpcoes;
        ProgressBar pbJogo;
        private View view;


        public MusicaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvNomeMusica = (TextView) view.findViewById(R.id.tvNomeMusica);
            this.tvDescMusica = (TextView) view.findViewById(R.id.tvDescMuscia);

        }
    }
}
