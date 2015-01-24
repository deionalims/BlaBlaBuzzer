package com.fx.blablabuzzer.Managers;

import com.fx.blablabuzzer.BusEvents.MainThreadBus;
import com.squareup.otto.Bus;

/**
 * Created by FX on 23/01/15.
 */
public class BusManager {

    private static BusManager instance;
    private MainThreadBus bus;


    private BusManager(){
        bus = new MainThreadBus();
    }

    public static BusManager getInstance(){
        if (null == instance){
            instance = new BusManager();
        }

        return instance;
    }

    public Bus getBus() {
        return bus;
    }
}
