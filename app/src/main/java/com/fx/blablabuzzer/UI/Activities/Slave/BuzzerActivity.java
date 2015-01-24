package com.fx.blablabuzzer.UI.Activities.Slave;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fx.blablabuzzer.BusEvents.QuitEvent;
import com.fx.blablabuzzer.BusEvents.StartGameEvent;
import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Managers.SlaveManager;
import com.fx.blablabuzzer.R;
import com.fx.blablabuzzer.Services.SocketService;
import com.fx.blablabuzzer.UI.Activities.BaseActivity;
import com.fx.blablabuzzer.UI.Activities.MainActivity;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by FX on 24/01/15.
 */
public class BuzzerActivity extends BaseActivity {

    @InjectView(R.id.waiting_for_game_textView) TextView waitingForGameTextView;
    @InjectView(R.id.buzzer_button) ImageButton buzzerButton;
    @InjectView(R.id.ok_view) View okView;
    @InjectView(R.id.nok_view) View nokView;

    private String playerName;
    private SlaveManager slaveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buzzer);

        ButterKnife.inject(this);

        playerName = getIntent().getStringExtra(Constants.EXTRA_PLAYER_NAME);

        slaveManager = new SlaveManager(spiceManager);
    }

    @Subscribe
    public void startGameEventReceived(StartGameEvent event){
        if (event.isStarted()){
            okView.setVisibility(View.INVISIBLE);
            nokView.setVisibility(View.INVISIBLE);
            waitingForGameTextView.setVisibility(View.INVISIBLE);
            buzzerButton.setVisibility(View.VISIBLE);
            buzzerButton.setEnabled(true);
        }
    }

    @Subscribe
    public void onQuitEventReceived(QuitEvent event){
        if (event.shouldQuit()){
            SocketService.shouldContinue = false;
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            slaveManager.quitGame();
        }
    }

    @OnClick(R.id.buzzer_button)
    public void buzzerOnClick(View v){
        slaveManager.buzz(playerName);
        buzzerButton.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.quit))
                        .setMessage(getString(R.string.are_you_sure_you_wanna_quit))
                        .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                slaveManager.playerLeave(playerName);
                                finish();
                            }
                        });

        builder.create().show();

    }
}
