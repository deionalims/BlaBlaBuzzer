package com.fx.blablabuzzer.Objects;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by FX on 24/01/15.
 */
public class Player implements Serializable {

    private String name;
    private InetAddress ipAddress;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
