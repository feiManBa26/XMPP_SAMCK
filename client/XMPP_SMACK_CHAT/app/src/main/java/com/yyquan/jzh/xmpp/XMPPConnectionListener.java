package com.yyquan.jzh.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * File: XMPPConnectionListener.java
 * Author: ejiang
 * Version: V100R001C01
 * Create: 2018-01-16 10:40
 */

public class XMPPConnectionListener implements ConnectionListener {
    private static final String TAG = XMPPConnectionListener.class.getName();

    @Override
    public void connected(XMPPConnection xmppConnection) {
        Log.i(TAG, "--connected--");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.i(TAG, "--authenticated--");

    }

    @Override
    public void connectionClosed() {
        Log.i(TAG, "--connectionClosed--");

    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.e(TAG, "--connectionClosedOnError--");
    }

    @Override
    public void reconnectingIn(int i) {
        Log.i(TAG, "--reconnectingIn--");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.i(TAG, "--reconnectionSuccessful--");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.e(TAG, "--reconnectionFailed--");
    }
}
