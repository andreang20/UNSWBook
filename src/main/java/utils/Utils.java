package utils;

import dao.Log;
import dao.LogDao;
import db.IDbManager;

import java.sql.Timestamp;

public class Utils {
    private IDbManager dbm;

    public Utils(IDbManager dbm) {
        this.dbm = dbm;
    }

    public void  logActionNow(String username, String action) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LogDao logDao = new LogDao(dbm);

        logDao.addLog(new Log(username, action, ts));
    }
}
