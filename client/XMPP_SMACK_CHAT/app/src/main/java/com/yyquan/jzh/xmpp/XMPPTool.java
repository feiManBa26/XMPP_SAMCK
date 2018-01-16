package com.yyquan.jzh.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

public class XMPPTool implements XMPPThreadListener {

    private String TAG = XMPPTool.class.getName();
    private final String serverAddress = "192.168.0.5"; // Your server address or IP
    public static final String serverName = "growing.com"; //xmpp name or your server name
    private XMPPTCPConnectionConfiguration config;
    private static XMPPTool instance;
    public XMPPTCPConnection connection;
    private XMPPThread mXMPPThread;

    public static XMPPTool getInstance() {
        synchronized (XMPPTool.class){
            if (null == instance) {
                instance = new XMPPTool();
            }
        }
        return instance;
    }

    /**
     * 连接
     *
     * @param username
     * @param password
     */
    public void connect(final String username, final String password) {
        mXMPPThread = new XMPPThread(new XMPPManagerListener(), username, password, this);
        mXMPPThread.start();
    }

    /**
     * 连接
     *
     * @param username
     * @param password
     * @param xmppConnectionListener
     */
    public void connect(final String username, final String password, XMPPConnectionListener xmppConnectionListener) {
        mXMPPThread = new XMPPThread(xmppConnectionListener, username, password, this);
        mXMPPThread.start();
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (mXMPPThread != null) {
            mXMPPThread.disConnect();
        }
    }

    /**
     * 登录
     */
    public void authenticated() throws InterruptedException, XMPPException, SmackException, IOException {
        if (mXMPPThread != null) {
            mXMPPThread.authenticated();
        }
    }


    @Override
    public void stopThread() {
        if (mXMPPThread != null && mXMPPThread.isAlive()) { //判断线程状态--是否在活动状态
            mXMPPThread.interrupt(); //终止线程
            mXMPPThread = null; //滞空
        }
    }

    public XMPPTCPConnection getXMPPTcpCon() {
        return mXMPPThread != null ? mXMPPThread.getConnection() : null;
    }

    private class XMPPManagerListener implements ConnectionListener {

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

//
//    /**
//     * 修改密码
//     *
//     * @param pwd
//     * @return
//     */
//    public boolean changePassword(String pwd) {
//        try {
//            con.getAccountManager().changePassword(pwd);
//            return true;
//        } catch (XMPPException e) {
//            SLog.e(tag, Log.getStackTraceString(e));
//        }
//        return false;
//    }
//
//
//    /**
//     * 设置状态
//     *
//     * @param state
//     */
//    public void setPresence(int state) {
//        Presence presence;
//        switch (state) {
//            //0.在线 1.Q我吧 2.忙碌 3.勿扰 4.离开 5.隐身 6.离线
//            case 0:
//                presence = new Presence(Presence.Type.available);
//                con.sendPacket(presence);
//                SLog.e(tag, "设置在线");
//                break;
//            case 1:
//                presence = new Presence(Presence.Type.available);
//                presence.setMode(Presence.Mode.chat);
//                con.sendPacket(presence);
//                SLog.e(tag, "Q我吧");
//                SLog.e(tag, presence.toXML());
//                break;
//            case 2:
//                presence = new Presence(Presence.Type.available);
//                presence.setMode(Presence.Mode.dnd);
//                con.sendPacket(presence);
//                SLog.e(tag, "忙碌");
//                SLog.e(tag, presence.toXML());
//                break;
//            case 3:
//                presence = new Presence(Presence.Type.available);
//                presence.setMode(Presence.Mode.xa);
//                con.sendPacket(presence);
//                SLog.e(tag, "勿扰");
//                SLog.e(tag, presence.toXML());
//                break;
//            case 4:
//                presence = new Presence(Presence.Type.available);
//                presence.setMode(Presence.Mode.away);
//                con.sendPacket(presence);
//                SLog.e(tag, "离开");
//                SLog.e(tag, presence.toXML());
//                break;
//            case 5:
//                Roster roster = con.getRoster();
//                Collection<RosterEntry> entries = roster.getEntries();
//                for (RosterEntry entity : entries) {
//                    presence = new Presence(Presence.Type.unavailable);
//                    presence.setPacketID(Packet.ID_NOT_AVAILABLE);
//                    presence.setFrom(con.getUser());
//                    presence.setTo(entity.getUser());
//                    con.sendPacket(presence);
//                    SLog.e(tag, presence.toXML());
//                }
//                SLog.e(tag, "告知其他用户-隐身");
//
//                break;
////            case 6:
////                presence = new Presence(Presence.Type.unavailable);
////                con.sendPacket(presence);
////                SLog.e(tag, "离线");
////                SLog.e(tag, presence.toXML());
////                break;
////            default:
////                break;
//        }
//    }
//
//    public void setPresence(ImageView iv, ImageView iv_me, Context context, String name) {
//
//        int status = SharedPreferencesUtil.getInt(context, "status", name + "status");
//        switch (status) {
//            //0.在线 1.Q我吧 2.忙碌 3.勿扰 4.离开 5.隐身 6.离线
//            case 0:
//                iv.setImageResource(R.mipmap.status_online);
//                iv_me.setImageResource(R.mipmap.status_online);
//                break;
//            case 1:
//                iv.setImageResource(R.mipmap.status_qme);
//                iv_me.setImageResource(R.mipmap.status_qme);
//                break;
//            case 2:
//                iv.setImageResource(R.mipmap.status_busy);
//                iv_me.setImageResource(R.mipmap.status_busy);
//                break;
//            case 3:
//                iv.setImageResource(R.mipmap.status_shield);
//                iv_me.setImageResource(R.mipmap.status_shield);
//                break;
//            case 4:
//                iv.setImageResource(R.mipmap.status_leave);
//                iv_me.setImageResource(R.mipmap.status_leave);
//                break;
//            case 5:
//                iv.setImageResource(R.mipmap.status_invisible);
//                iv_me.setImageResource(R.mipmap.status_invisible);
//                break;
//
//        }
//    }
//
//    /**
//     * 获取离线消息
//     */
//    private void getMessage() {
//        OfflineMessageManager offlineManager = new OfflineMessageManager(getCon());
//        try {
//            Iterator<org.jivesoftware.smack.packet.Message> it = offlineManager
//                    .getMessages();
//            Log.i("service", offlineManager.supportsFlexibleRetrieval() + "");
//            Log.i("service", "离线消息数量: " + offlineManager.getMessageCount());
//            Map<String, ArrayList<Message>> offlineMsgs = new HashMap<String, ArrayList<Message>>();
//            while (it.hasNext()) {
//                org.jivesoftware.smack.packet.Message message = it.next();
//                Log.i("service", "收到离线消息, Received from 【" + message.getFrom()
//                        + "】 message: " + message.getBody());
//                String fromUser = message.getFrom().split("/")[0];
//                if (offlineMsgs.containsKey(fromUser)) {
//                    offlineMsgs.get(fromUser).add(message);
//                } else {
//                    ArrayList<Message> temp = new ArrayList<Message>();
//                    temp.add(message);
//                    offlineMsgs.put(fromUser, temp);
//                }
//            }
////在这里进行处理离线消息集合......
//            Set<String> keys = offlineMsgs.keySet();
//            Iterator<String> offIt = keys.iterator();
//            while (offIt.hasNext()) {
//                String key = offIt.next();
//                ArrayList<Message> ms = offlineMsgs.get(key);
//
//                for (int i = 0; i < ms.size(); i++) {
//                    Log.i("serviceeeeeeeeeeeee", "收到离线消息, Received from 【" + ms.get(i).getFrom()
//                            + "】 message: " + ms.get(i).getBody());
//                }
//            }
//            offlineManager.deleteMessages();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    /**
//     * 查找用户
//     *
//     * @param
//     * @param userName
//     * @return
//     */
//    public List<XmppUser> searchUsers(String userName) {
//        List<XmppUser> list = new ArrayList<XmppUser>();
//        UserSearchManager userSearchManager = new UserSearchManager(con);
//        try {
//            Form searchForm = userSearchManager.getSearchForm("search."
//                    + con.getServiceName());
//            Form answerForm = searchForm.createAnswerForm();
//            answerForm.setAnswer("Username", true);
//            answerForm.setAnswer("Name", true);
//            answerForm.setAnswer("search", userName);
//            ReportedData data = userSearchManager.getSearchResults(answerForm,
//                    "search." + con.getServiceName());
//            Iterator<ReportedData.Row> rows = data.getRows();
//            while (rows.hasNext()) {
//                XmppUser user = new XmppUser(null, null);
//                ReportedData.Row row = rows.next();
//                user.setUserName(row.getValues("Username").next().toString());
//                user.setName(row.getValues("Name").next().toString());
//                list.add(user);
//            }
//        } catch (XMPPException e) {
//            SLog.e(tag, Log.getStackTraceString(e));
//        }
//        return list;
//    }
//
//    /**
//     * 添加好友
//     *
//     * @param
//     * @param userName
//     * @param name
//     * @param groupName 是否有分组
//     * @return
//     */
//    public boolean addUser(String userName, String name, String groupName) {
//        Roster roster = con.getRoster();
//        try {
//            roster.createEntry(userName, name, null == groupName ? null
//                    : new String[]{groupName});
//            return true;
//        } catch (XMPPException e) {
//            SLog.e(tag, Log.getStackTraceString(e));
//        }
//        return false;
//    }
//
//    /**
//     * 删除好友
//     *
//     * @param userName
//     * @return
//     */
//    public boolean removeUser(String userName) {
//        Roster roster = con.getRoster();
//        try {
//            RosterEntry entry = roster.getEntry(userName);
//            if (null != entry) {
//                roster.removeEntry(entry);
//            }
//            return true;
//        } catch (XMPPException e) {
//            SLog.e(tag, Log.getStackTraceString(e));
//        }
//        return false;
//    }
//
//
//    /**
//     * 添加到分组
//     *
//     * @param
//     * @param userName
//     * @param groupName
//     */
//    public void addUserToGroup(String userName, String groupName) {
//        Roster roster = con.getRoster();
//        RosterGroup group = roster.getGroup(groupName);
//        if (null == group) {
//            group = roster.createGroup(groupName);
//        }
//        RosterEntry entry = roster.getEntry(userName);
//        if (entry != null) {
//            try {
//                group.addEntry(entry);
//            } catch (XMPPException e) {
//                SLog.e(tag, Log.getStackTraceString(e));
//            }
//        }
//
//    }
//
//    /**
//     * 获取所有分组
//     *
//     * @param
//     * @return
//     */
//    public List<RosterGroup> getGroups() {
//        Roster roster = getCon().getRoster();
//        List<RosterGroup> list = new ArrayList<RosterGroup>();
//        list.addAll(roster.getGroups());
//        return list;
//    }
//
//    /**
//     * 获取某一个分组的成员
//     *
//     * @param
//     * @param groupName
//     * @return
//     */
//    public List<RosterEntry> getEntrysByGroup(String groupName) {
//
//        Roster roster = getCon().getRoster();
//        List<RosterEntry> list = new ArrayList<RosterEntry>();
//        RosterGroup group = roster.getGroup(groupName);
//        Collection<RosterEntry> rosterEntiry = group.getEntries();
//        Iterator<RosterEntry> iter = rosterEntiry.iterator();
//        while (iter.hasNext()) {
//            RosterEntry entry = iter.next();
//            SLog.i("xmpptool", entry.getUser() + "\t" + entry.getName() + entry.getType().toString());
//            if (entry.getType().toString().equals("both")) {
//                list.add(entry);
//            }
//
//        }
//        return list;
//
//    }
//
//    /**
//     * 判断是否是好友
//     *
//     * @param
//     * @param user
//     * @return
//     */
//    public boolean isFriendly(String user) {
//
//
//        Roster roster = getCon().getRoster();
//        List<RosterEntry> list = new ArrayList<RosterEntry>();
//        list.addAll(roster.getEntries());
//        for (int i = 0; i < list.size(); i++) {
//            Log.i("xmppttttttttt", list.get(i).getUser().toUpperCase() + "\t" + user);
//            if (list.get(i).getUser().contains(user.toLowerCase())) {
//                if (list.get(i).getType().toString().equals("both")) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return false;
//
//    }
//
////    /**
////     * 注册
////     *
////     * @return 0 服务端无响应 1成功 2已存在 3 失败
////     */
////    public int regist(User user) {
////
////        Registration reg = new Registration();
////        reg.setType(IQ.Type.SET);
////        reg.setTo(con.getServiceName());
////        reg.setUsername(user.getUser());
////        reg.setPassword(user.getPassword());
////        reg.addAttribute("Android", "createUser");
////        reg.addAttribute("name", user.getNickname());
////        PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()));
////        PacketCollector col = con.createPacketCollector(filter);
////        con.sendPacket(reg);
////        IQ result = (IQ) col.nextResult(SmackConfiguration.getPacketReplyTimeout());
////        col.cancel();
////        if (null == result) {
////            SLog.e(tag, "no response from server");
////            return 0;
////        } else if (result.getType() == IQ.Type.RESULT) {
////            SLog.e(tag, result.toString());
////
////            return 1;
////        } else if (result.getType() == IQ.Type.ERROR) {
////            SLog.e(tag, result.toString());
////            if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
////                return 2;
////            } else {
////                return 3;
////            }
////        }
////        return 3;
////    }
//
//
////    /**
////     * 添加分组
////     *
////     * @param
////     * @param groupName
////     * @return
////     */
////    public boolean addGroup(String groupName) {
////        try {
////            Roster roster = getCon().getRoster();
////            roster.createGroup(groupName);
////            return true;
////        } catch (Exception e) {
////            SLog.e(tag, Log.getStackTraceString(e));
////        }
////        return false;
////    }
//
//
//    /**
//     * 断开连接
//     */
//    public static void disConnectServer() {
//        if (null != con && con.isConnected()) {
//
//            new Thread() {
//                public void run() {
//                    con.disconnect();
//                }
//            }.start();
//        }
//
//    }


//    public void configure(ProviderManager pm) {
//
//        try {
//            Class.forName("org.jivesoftware.smack.ReconnectionManager");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Private Data Storage
//        pm.addIQProvider("query", "jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());
//
//        // Time
//        try {
//            pm.addIQProvider("query", "jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
//        } catch (ClassNotFoundException e) {
//            Log.w("TestClient", "Can't load class for org.jivesoftware.smackx.packet.Time");
//        }
//
//        // Roster Exchange
//        pm.addExtensionProvider("x", "jabber:x:roster", new RosterExchangeProvider());
//
//        // Message Events
//        pm.addExtensionProvider("x", "jabber:x:event", new MessageEventProvider());
//
//        // Chat State
//        pm.addExtensionProvider("active", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
//        pm.addExtensionProvider("composing", "http://jabber.org/protocol/chatstates",
//                new ChatStateExtension.Provider());
//        pm.addExtensionProvider("paused", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
//        pm.addExtensionProvider("inactive", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
//        pm.addExtensionProvider("gone", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
//
//        // XHTML
//        pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());
//
//        // Group Chat Invitations
//        pm.addExtensionProvider("x", "jabber:x:conference", new GroupChatInvitation.Provider());
//
//        // Service Discovery # Items
//        pm.addIQProvider("query", "http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());
//
//        // Service Discovery # Info
//        pm.addIQProvider("query", "http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());
//
//        // Data Forms
//        pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
//
//        // MUC User
//        pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user", new MUCUserProvider());
//
//        // MUC Admin
//        pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin", new MUCAdminProvider());
//
//        // MUC Owner
//        pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());
//
//        // Delayed Delivery
//        pm.addExtensionProvider("x", "jabber:x:delay", new DelayInformationProvider());
//
//        // Version
//        try {
//            pm.addIQProvider("query", "jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
//        } catch (ClassNotFoundException e) {
//            // Not sure what's happening here.
//        }
//
//        // VCard
//        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
//
//        // Offline Message Requests
//        pm.addIQProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());
//
//        // Offline Message Indicator
//        pm.addExtensionProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());
//
//        // Last Activity
//        pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
//
//        // User Search
//        pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
//
//        // SharedGroupsInfo
//        pm.addIQProvider("sharedgroup", "http://www.jivesoftware.org/protocol/sharedgroup",
//                new SharedGroupsInfo.Provider());
//
//        // JEP-33: Extended Stanza Addressing
//        pm.addExtensionProvider("addresses", "http://jabber.org/protocol/address", new MultipleAddressesProvider());
//
//        // FileTransfer
//        pm.addIQProvider("si", "http://jabber.org/protocol/si", new StreamInitiationProvider());
//        pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
//        pm.addIQProvider("open", "http://jabber.org/protocol/ibb", new OpenIQProvider());
//        pm.addIQProvider("close", "http://jabber.org/protocol/ibb", new CloseIQProvider());
//        pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb", new DataPacketProvider());
//
//        // Privacy
//        pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
//        pm.addIQProvider("command", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider());
//        pm.addExtensionProvider("malformed-action", "http://jabber.org/protocol/commands",
//                new AdHocCommandDataProvider.MalformedActionError());
//        pm.addExtensionProvider("bad-locale", "http://jabber.org/protocol/commands",
//                new AdHocCommandDataProvider.BadLocaleError());
//        pm.addExtensionProvider("bad-payload", "http://jabber.org/protocol/commands",
//                new AdHocCommandDataProvider.BadPayloadError());
//        pm.addExtensionProvider("bad-sessionid", "http://jabber.org/protocol/commands",
//                new AdHocCommandDataProvider.BadSessionIDError());
//        pm.addExtensionProvider("session-expired", "http://jabber.org/protocol/commands",
//                new AdHocCommandDataProvider.SessionExpiredError());
//    }
}
