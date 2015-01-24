package com.fx.blablabuzzer.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fx.blablabuzzer.Managers.BusManager;
import com.fx.blablabuzzer.Services.SocketService;
import com.fx.blablabuzzer.Services.UncachedNoNetworkSpiceService;
import com.octo.android.robospice.SpiceManager;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by FX on 23/01/15.
 */
public class BaseActivity extends Activity {

    protected SpiceManager spiceManager = new SpiceManager(UncachedNoNetworkSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!spiceManager.isStarted()){
            spiceManager.start(this);
        }

        Intent intent = new Intent(this, SocketService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        if (spiceManager.isStarted()){
            spiceManager.shouldStop();
        }
        BusManager.getInstance().getBus().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!spiceManager.isStarted()){
            spiceManager.start(this);
        }
        BusManager.getInstance().getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        if (spiceManager.isStarted()){
            spiceManager.shouldStop();
        }
        super.onDestroy();
    }

    protected void showAlert(String message){
        showCrouton(message, Style.ALERT);
    }

    protected void showInfo(String message){
        showCrouton(message, Style.INFO);
    }

    protected void showConfirm(String message){
        showCrouton(message, Style.CONFIRM);
    }

    private void showCrouton(String message, Style style){
        Crouton.clearCroutonsForActivity(this);
        Crouton.makeText(this, message, style, null)
                .setConfiguration(Configuration.DEFAULT)
                .show();
    }

}
