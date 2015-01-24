package com.fx.blablabuzzer.BusEvents;

/**
 * Created by FX on 24/01/15.
 */
public class PlayerLeaveEvent {

    private String playerName;

    public PlayerLeaveEvent(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
