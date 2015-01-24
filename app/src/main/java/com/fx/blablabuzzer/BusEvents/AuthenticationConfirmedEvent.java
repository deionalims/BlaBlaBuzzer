package com.fx.blablabuzzer.BusEvents;

/**
 * Created by FX on 24/01/15.
 */
public class AuthenticationConfirmedEvent {

    private boolean confirmed;

    public AuthenticationConfirmedEvent(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
