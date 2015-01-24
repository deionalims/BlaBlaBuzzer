package com.fx.blablabuzzer.BusEvents;

/**
 * Created by FX on 24/01/15.
 */
public class StartGameEvent {

    private boolean started;

    public StartGameEvent(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
