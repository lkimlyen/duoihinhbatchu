package com.example.yenyen.duoihinhbatchudemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by yenyen on 6/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.yenyen.duoihinhbatchudemo/databases/";
    private static String DB_NAME = "duoihinhbatchu.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cauhoi";
    private static final String KEY_ID = "_id";
    private static final String KEY_IMAGEPATH = "imagePath";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SHORTANSWER = "shortAnswer";
    private static final String KEY_FULLANSWER = "fullAnswer";
    private static final String KEY_STATUS = "status";

    private SQLiteDatabase myDataBase;
    Context myContext;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();

    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.i(TAG, "database chưa tồn tại" );
        }
        if (checkDB != null)
            checkDB.close();
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {

        //mo db trong thu muc assets nhu mot input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //duong dan den db se tao
        String outFileName = DB_PATH + DB_NAME;

        //mo db giong nhu mot output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //truyen du lieu tu inputfile sang outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Dong luon
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void copyDatabaseFromAsset() throws IOException {
        boolean dbExist = checkDataBase(); //kiem tra db

        if (dbExist) {
            //khong lam gi ca, database da co roi
            Toast.makeText(myContext, "Đã có", Toast.LENGTH_SHORT).show();
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();//chep du lieu
                Toast.makeText(myContext, "Thành công", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(myContext, "Không thành công", Toast.LENGTH_SHORT).show();
                throw new Error("Error copying database");

            }
        }
    }
    public ArrayList<CauHoi> getQuestion()
    {
        String limit = "1";
        ArrayList<CauHoi> ds=new ArrayList<CauHoi>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor contro=db.query(TABLE_NAME,
                null,//cac cot can lay
                "status = ?", //menh de where
               new String[]{"0"},//doi so menh de where,
                null,//group by
                null,//having
                null,//order by - xap thu tu theo
                limit//limit - gioi hang so luong can lay
        );
        if(contro.moveToFirst()) {
            do {
                CauHoi x = new CauHoi();
                x.id = Integer.parseInt(contro.getString(0));
                x.imagePath = contro.getString(1);
                x.description = contro.getString(2);
                x.shortAnswer = contro.getString(3);
                x.fullAnswer = contro.getString(4);
                x.status = Integer.parseInt(contro.getString(5));
                ds.add(x);
            } while (contro.moveToNext());
        }
        return ds;
    }
    public void updateStatus(CauHoi ch) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("status",ch.status);
        db.update(TABLE_NAME,values,"_id=?",new String[]{ch.id+""});

    }
}
