package com.fx.blablabuzzer.Services;

import android.app.IntentService;
import android.content.Intent;

import com.fx.blablabuzzer.BusEvents.AuthenticationConfirmedEvent;
import com.fx.blablabuzzer.BusEvents.BuzzEvent;
import com.fx.blablabuzzer.BusEvents.NewPlayerEvent;
import com.fx.blablabuzzer.BusEvents.PlayerLeaveEvent;
import com.fx.blablabuzzer.BusEvents.QuitEvent;
import com.fx.blablabuzzer.BusEvents.StartGameEvent;
import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Managers.BusManager;
import com.fx.blablabuzzer.Objects.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by FX on 23/01/15.
 */
public class SocketService extends IntentService {

    public static volatile boolean shouldContinue = true;
    private static final int BUFFER_SIZE = 8192;

    public SocketService(){
        super("BlaBlaBuzzerSocketService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Socket socket = null;
        InputStream inputStream;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(Constants.PORT));

            while (shouldContinue){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                socket = serverSocket.accept();
                int bytesRead;
                String response = "";
                inputStream = socket.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

                if (null != response){
                    if (response.startsWith(Constants.NEW_PLAYER_PREFIX)){
                        String playerName = response.substring(Constants.NEW_PLAYER_PREFIX.length());
                        Player player = new Player();
                        player.setName(playerName);
                        player.setIpAddress(socket.getInetAddress());
                        BusManager.getInstance().getBus().post(new NewPlayerEvent(player));
                    } else if (Constants.AUTHENTICATION_OK.equals(response)){
                        BusManager.getInstance().getBus().post(new AuthenticationConfirmedEvent(true));
                    } else if (Constants.START_GAME.equals(response)){
                        BusManager.getInstance().getBus().post(new StartGameEvent(true));
                    } else if (response.startsWith(Constants.BUZZ)){
                        String playerName = response.substring(Constants.BUZZ.length());
                        BusManager.getInstance().getBus().post(new BuzzEvent(playerName));
                    } else if (Constants.QUIT_GAME.equals(response)){
                        BusManager.getInstance().getBus().post(new QuitEvent(true));
                        break;
                    } else if (response.startsWith(Constants.LEAVE)){
                        String playerName = response.substring(Constants.LEAVE.length());
                        BusManager.getInstance().getBus().post(new PlayerLeaveEvent(playerName));
                    }
                }

                byteArrayOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != serverSocket){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
