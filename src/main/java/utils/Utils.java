package utils;

import dao.Log;
import dao.LogDao;
import dao.Notification;
import dao.NotificationDao;
import db.IDbManager;

import java.sql.Timestamp;

public class Utils {
    private IDbManager dbm;
    public static final int INVALID = -1;

    public Utils(IDbManager dbm) {
        this.dbm = dbm;
    }

    public void  logActionNow(String username, String action) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LogDao logDao = new LogDao(dbm);

        logDao.addLog(new Log(username, action, ts));
    }

    public void makeNotification(String username, String action) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        NotificationDao notificationDao = new NotificationDao(dbm);
        notificationDao.addNotification(new Notification(INVALID, username, action,false, ts));
    }
}
