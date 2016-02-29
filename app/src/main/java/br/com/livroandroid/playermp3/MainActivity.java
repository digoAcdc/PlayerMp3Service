package br.com.livroandroid.playermp3;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.NotificationUtil;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "livro";
    // Classe que encapsula o MediaPlayer

    private EditText text;
    private EditText caminhoArquivo;
    private TextView songName;
    MyBroadcast broadcast;

    private InterfaceMp3 interfaceMp3;
    private ServiceConnection conexao = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // (*3*)
            // Recupera a interface para interagir com o servico

            Mp3Service.Mp3ServiceBinder conexao = (Mp3Service.Mp3ServiceBinder) service;
            interfaceMp3 = conexao.getInterface();
            Log.d(TAG, "onServiceConnected, interfaceMp3 conectada: " + interfaceMp3);
        }

        public void onServiceDisconnected(ComponentName className) {
            // (*6*)
            Log.d(TAG, "onServiceDisconnected, liberando recursos.");
            interfaceMp3 = null;
        }
    };


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        text = (EditText) findViewById(R.id.tArquivo);
        caminhoArquivo = (EditText) findViewById(R.id.tArquivo);
        songName = (TextView) findViewById(R.id.songName);

        /*broadcast = new MyBroadcast();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("android.intent.action.HEADSET_PLUG");

        registerReceiver(broadcast, intentFilter);*/

        Intent it = getIntent();
        Bundle params = it.getExtras();
        String musica = "";
        String caminho = "";
        if (params != null) {
            musica = params.getString("MUSICA");
            caminho = params.getString("CAMINHO");

            caminhoArquivo.setText(caminho);
            songName.setText(musica);
        }

            Intent intent = new Intent(this, Mp3Service.class);
            Log.d(TAG, "Iniciando o service");
            // (*1*)
            startService(intent);
            // Faz o bind
            // (*2*)
            boolean b = bindService(intent, conexao, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "Service conectado: " + b);

            // Solicita as permissões
            String[] permissoes = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            PermissionUtils.validate(this, 0, permissoes);
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                alertAndFinish();
                return;
            }
        }
        // Se chegou aqui está OK :-)
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    public void onClickPlay(View view) {
        // (*4*)
        if (interfaceMp3 != null) {
            String mp3 = text.getText().toString();
            Log.d(TAG, "play: " + mp3);
            interfaceMp3.play(mp3);

            broadcast = new MyBroadcast();

            IntentFilter intentFilter = new IntentFilter();

            intentFilter.addAction("android.intent.action.HEADSET_PLUG");

            registerReceiver(broadcast, intentFilter);
        }
    }

    public void onClickPause(View view) {
        // (*4*)
        if (interfaceMp3 != null) {
            Log.d(TAG, "pause");
            interfaceMp3.pause();
        }
    }

    public void onClickStop(View view) {
        // (*4*)
        if (interfaceMp3 != null) {
            Log.d(TAG, "stop");
            interfaceMp3.stop();
        }
    }

    public void onClickAcelera(View view) {
        if (interfaceMp3 != null) {
            Log.d(TAG, "Acelera");
            interfaceMp3.acelara();
        }
    }

    public void onClickRetroceder(View view) {
        if (interfaceMp3 != null) {
            Log.d(TAG, "Retrocede");
            interfaceMp3.retrocede();
        }
    }

    public void onClickMostraPlayList(View view) {
        Intent i = new Intent(this, ListaMusicaActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if (interfaceMp3 != null && interfaceMp3.isPlaying()) {*/
        if (interfaceMp3 != null) {
            // (*5*)
            Log.d(TAG, "Activity destruida. A musica continua.");
            unbindService(conexao);
            // Cria a notificacao para o usuario voltar ao player.
            String mp3 = interfaceMp3.getMp3();

            NotificationUtil.create(this, 1, new Intent(this, MainActivity.class), R.mipmap.ic_launcher, "MP3 Player", mp3);
        } else {
            // (*7*)
            Log.d(TAG, "Activity destruida. Para o servico, pois nao existe musica tocando.");
            unbindService(conexao);
            stopService(new Intent(this, Mp3Service.class));
        }
    }
}
