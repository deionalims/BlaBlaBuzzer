package com.fx.blablabuzzer.Managers;

import com.fx.blablabuzzer.Objects.Player;
import com.fx.blablabuzzer.Tasks.ConfirmAuthenticationTask;
import com.fx.blablabuzzer.Tasks.QuitTask;
import com.fx.blablabuzzer.Tasks.StartGameTask;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

/**
 * Created by FX on 23/01/15.
 */
public class MasterManager {

    private SpiceManager spiceManager;

    public MasterManager(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }

    public void confirmAuthentication(Player player){
        ConfirmAuthenticationTask task = new ConfirmAuthenticationTask(player);
        spiceManager.execute(task, null);
    }

    public void startGame(List<Player> players){
        StartGameTask task = new StartGameTask(players);
        spiceManager.execute(task, null);
    }

    public void quitGame(List<Player> players){
        QuitTask task = new QuitTask(players);
        spiceManager.execute(task, null);
    }
}
