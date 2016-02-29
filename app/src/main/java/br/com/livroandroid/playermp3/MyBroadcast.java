package br.com.livroandroid.playermp3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rodrigo on 04/01/2016.
 */
public class MyBroadcast extends BroadcastReceiver {
    private InterfaceMp3 interfaceMp3;
    private boolean headsetConnected = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Log.d("TAG", "Headset is unplugged");
                    if (headsetConnected && interfaceMp3.isPlaying()) {
                        interfaceMp3.stop();
                    }
                    headsetConnected = false;
                    break;
                case 1:
                    Log.d("TAG", "Headset is plugged");
                    headsetConnected = true;
                    break;
                default:
                    Log.d("TAG", "I have no idea what the headset state is");
            }
        }
    }
}
