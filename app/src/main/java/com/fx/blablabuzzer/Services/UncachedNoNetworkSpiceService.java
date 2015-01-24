package com.fx.blablabuzzer.Services;

import android.content.Context;

import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.networkstate.DefaultNetworkStateChecker;
import com.octo.android.robospice.networkstate.NetworkStateChecker;

/**
 * Created by FX on 24/01/15.
 */
public class UncachedNoNetworkSpiceService extends UncachedSpiceService {

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return new CustomNetworkStateChecker();
    }

    private class CustomNetworkStateChecker extends DefaultNetworkStateChecker {
        @Override
        public boolean isNetworkAvailable(Context context) {
            return true;
        }
    }

}
