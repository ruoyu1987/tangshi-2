package cn.edu.jssvc.tangshi.function;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "sql.db";

    public Context contextId;

    public static final String LISHIJILU = "create table lishi("
            + "id Integer primary key autoincrement,"
            + "poetrieid text,"
            + "poetname text,"
            + "title text,"
            + "time text)";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        contextId = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LISHIJILU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
