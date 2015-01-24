package com.fx.blablabuzzer.Managers;

import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Tasks.AuthenticateTask;
import com.fx.blablabuzzer.Tasks.BuzzTask;
import com.fx.blablabuzzer.Tasks.PlayerLeaveTask;
import com.fx.blablabuzzer.Tasks.QuitTask;
import com.octo.android.robospice.SpiceManager;

import java.net.Socket;

/**
 * Created by FX on 23/01/15.
 */
public class SlaveManager {

    private SpiceManager spiceManager;
    private Socket socket;

    public SlaveManager(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }

    public void authenticate(String name){
        AuthenticateTask task = new AuthenticateTask(Constants.NEW_PLAYER_PREFIX + name);
        spiceManager.execute(task, null);
    }

    public void buzz(String playerName){
        BuzzTask task = new BuzzTask(playerName);
        spiceManager.execute(task, null);
    }

    public void playerLeave(String playerName){
        PlayerLeaveTask task = new PlayerLeaveTask(playerName);
        spiceManager.execute(task, null);
    }

    public void quitGame(){
        QuitTask task = new QuitTask(null);
        spiceManager.execute(task, null);
    }
}
