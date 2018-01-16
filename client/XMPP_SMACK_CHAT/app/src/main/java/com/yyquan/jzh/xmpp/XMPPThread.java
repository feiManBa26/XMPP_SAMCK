package com.yyquan.jzh.xmpp;

import android.text.TextUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.net.InetAddress;

/**
 * File: XMPPThread.java
 * Author: ejiang
 * Version: V100R001C01
 * Create: 2018-01-15 17:07
 */

public class XMPPThread extends Thread {
    private final String serverAddress = "192.168.0.5"; // Your server address or IP
    public static final String serverName = "growing.com"; //xmpp name or your server name
    private XMPPTCPConnectionConfiguration mConfiguration;
    private XMPPTCPConnection mConnection;
    private ConnectionListener mListener;
    private String userName = "";
    private String password = "";
    private XMPPThreadListener mXMPPThreadListener;


    public XMPPTCPConnection getConnection() {
        return mConnection;
    }

    public XMPPThread(ConnectionListener listener, String userName, String password, XMPPThreadListener XMPPThreadListener) {
        mListener = listener;
        this.userName = userName;
        this.password = password;
        mXMPPThreadListener = XMPPThreadListener;
    }

    @Override
    public void run() {
        super.run();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password))
            throw new NullPointerException("用户名称&密码不能为空！！！");
        try {
            mConfiguration = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(userName + "@growing.com", password)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setHostAddress(InetAddress.getByName(serverAddress))
                    .setXmppDomain(serverName)
                    .setPort(5222)
                    .build();
            mConnection = new XMPPTCPConnection(mConfiguration);
            if (mListener != null) {
                mConnection.addConnectionListener(mListener);
            }
            mConnection.connect();
            if (mConnection.isConnected()) {
                System.out.println("老子连接上啦啦啦");
            }
            authenticated();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录认证
     */
    public void authenticated() throws InterruptedException,
            IOException, SmackException, XMPPException {
        if (mConnection != null) {
            if (mConnection.isConnected()) {
                SASLMechanism mechanism = new SASLDigestMD5Mechanism();
                SASLAuthentication.registerSASLMechanism(mechanism);
                SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
                SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
                mConnection.login(userName + "@growing.com", password);
                if (mConnection.isAuthenticated()) {
                    System.out.println("老子上线啦啦啦啦");
                }
            }
        }
    }

    /**
     * 下线
     */
    public void isAuthenticated() {

    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (mConnection != null && mConnection.isConnected()) { //登录状态
            mConnection.disconnect();
            if (!mConnection.isConnected()) {
                mConnection = null;
                System.out.println("老子断开连接啦");
            }
            //关闭线程
            if (mXMPPThreadListener != null) {
                mXMPPThreadListener.stopThread();
            }
        }
    }

    /**
     * 添加分组
     *
     * @param
     * @param groupName
     * @return
     */
    public boolean addGroup(String groupName) {
        try {
//            Roster roster = mConnection.get();
//            roster.createGroup(groupName);
            return true;
        } catch (Exception e) {
//            SLog.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }
}
