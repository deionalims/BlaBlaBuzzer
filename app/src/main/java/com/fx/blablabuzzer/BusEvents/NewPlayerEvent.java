package com.fx.blablabuzzer.BusEvents;

import com.fx.blablabuzzer.Objects.Player;

import java.io.Serializable;

/**
 * Created by FX on 24/01/15.
 */
public class NewPlayerEvent implements Serializable {

    private Player player;

    public NewPlayerEvent(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
