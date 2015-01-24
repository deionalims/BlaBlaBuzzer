package com.fx.blablabuzzer.Objects;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by FX on 23/01/15.
 */
public class AuthenticateResponse implements Serializable {

    private Socket socket;
    private String message;
    private int resultCode;

}
