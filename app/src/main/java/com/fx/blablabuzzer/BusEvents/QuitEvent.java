package com.fx.blablabuzzer.BusEvents;

/**
 * Created by FX on 24/01/15.
 */
public class QuitEvent {

    private boolean shouldQuit;

    public QuitEvent(boolean shouldQuit) {
        this.shouldQuit = shouldQuit;
    }

    public boolean shouldQuit() {
        return shouldQuit;
    }

    public void setShouldQuit(boolean shouldQuit) {
        this.shouldQuit = shouldQuit;
    }
}
