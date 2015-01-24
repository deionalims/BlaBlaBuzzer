package com.fx.blablabuzzer.UI.Activities.Master;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fx.blablabuzzer.BusEvents.BuzzEvent;
import com.fx.blablabuzzer.BusEvents.NewPlayerEvent;
import com.fx.blablabuzzer.BusEvents.PlayerLeaveEvent;
import com.fx.blablabuzzer.BusEvents.QuitEvent;
import com.fx.blablabuzzer.Managers.MasterManager;
import com.fx.blablabuzzer.Objects.Player;
import com.fx.blablabuzzer.R;
import com.fx.blablabuzzer.Services.SocketService;
import com.fx.blablabuzzer.UI.Activities.BaseActivity;
import com.fx.blablabuzzer.UI.Activities.MainActivity;
import com.fx.blablabuzzer.UI.Views.NewPlayerView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by FX on 23/01/15.
 */
public class MasterActivity extends BaseActivity {

    private static final int MINIMUM_PLAYERS = 2;

    @InjectView(R.id.players_layout) LinearLayout playersLayout;
    @InjectView(R.id.quit_button) Button quitButton;
    @InjectView(R.id.reset_button) Button resetButton;
    @InjectView(R.id.start_button) Button startButton;
    @InjectView(R.id.waiting_for_players_textView) View waitingForPlayersView;

    private List<Player> players;
    private MasterManager masterManager;
    private boolean gameIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        ButterKnife.inject(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameIsRunning = false;
        masterManager = new MasterManager(spiceManager);
        players = new ArrayList<>();
    }

    @Subscribe
    public void onNewPlayerEventReceived(NewPlayerEvent event){
        if (null != event && null != event.getPlayer()){
            for (Player p : players){
                if (p.getName().equals(event.getPlayer().getName())){
                    //TODO send "Name already used"
                    return;
                }
            }

            players.add(event.getPlayer());
            showInfo(String.format(getString(R.string.new_player_added), event.getPlayer().getName()));
            playersLayout.addView(new NewPlayerView(this, event.getPlayer()));
            resetButton.setEnabled(players.size() >= MINIMUM_PLAYERS);
            startButton.setEnabled(players.size() >= MINIMUM_PLAYERS);
            waitingForPlayersView.setVisibility(players.size() > 0 ? View.GONE : View.VISIBLE);
            masterManager.confirmAuthentication(event.getPlayer());
        }
    }

    @OnClick(R.id.reset_button)
    public void resetScoresOnClick(View v){
        for (int i = 0; i < playersLayout.getChildCount(); i++){
            NewPlayerView newPlayerView = (NewPlayerView) playersLayout.getChildAt(i);
            newPlayerView.setScore(0);
        }
    }

    @OnClick(R.id.start_button)
    public void startButtonClick(View v){
        for (int i = 0; i < playersLayout.getChildCount(); i++){
            NewPlayerView newPlayerView = (NewPlayerView) playersLayout.getChildAt(i);
            newPlayerView.resetColorIndicator();
        }
        gameIsRunning = true;
        masterManager.startGame(players);
    }

    @OnClick(R.id.quit_button)
    public void quitButtonOnClick(View v){
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
                        masterManager.quitGame(players);
                    }
                });
        builder.create().show();
    }

    @Subscribe
    public void onBuzzEventReceived(BuzzEvent event){
        if (gameIsRunning && null != event && !TextUtils.isEmpty(event.getPlayerName())){
            gameIsRunning = false;
            for (int i = 0; i < playersLayout.getChildCount(); i++){
                NewPlayerView newPlayerView = (NewPlayerView) playersLayout.getChildAt(i);
                if (event.getPlayerName().equals(newPlayerView.getPlayerName())){
                    newPlayerView.setColorIndicator(true);
                } else {
                    newPlayerView.setColorIndicator(false);
                }
            }
        }
    }

    @Subscribe
    public void playerLeaveEventReceived(PlayerLeaveEvent event){
        if (null != event && !TextUtils.isEmpty(event.getPlayerName())) {
            for (int i = 0; i < playersLayout.getChildCount(); i++){
                NewPlayerView newPlayerView = (NewPlayerView) playersLayout.getChildAt(i);
                Player player = players.get(i);
                if (event.getPlayerName().equals(newPlayerView.getPlayerName())){
                    playersLayout.removeView(newPlayerView);
                }
                if (player.getName().equals(event.getPlayerName())){
                    players.remove(i);
                }
            }

            resetButton.setEnabled(players.size() >= MINIMUM_PLAYERS);
            startButton.setEnabled(players.size() >= MINIMUM_PLAYERS);
            waitingForPlayersView.setVisibility(players.size() > 0 ? View.GONE : View.VISIBLE);
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
        }
    }
}
