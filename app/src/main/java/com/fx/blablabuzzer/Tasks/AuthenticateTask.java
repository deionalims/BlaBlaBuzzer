package com.fx.blablabuzzer.Tasks;

import com.fx.blablabuzzer.Constants;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by FX on 23/01/15.
 */
public class AuthenticateTask extends SpiceRequest<Void> {

    private String name;

    public AuthenticateTask(String name) {
        super(Void.class);
        this.name = name;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {

        Socket socket = new Socket(Constants.HOST_IP_ADDRESS, Constants.PORT);
        OutputStream outputStream;

        try {
            outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(name);
            printStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }
}
