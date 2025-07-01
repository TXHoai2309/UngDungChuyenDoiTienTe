package com.example.ungdungchuyendoitiente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper_DangKy extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="dangky.db";

    //DATABASE_VERSION = 1 là phiên bản của cơ sở dữ liệu SQLite mà ứng dụng đang sử dụng.
    //Mỗi khi bạn thay đổi cấu trúc của cơ sở dữ liệu (ví dụ: thêm bảng, thay đổi cột, thay đổi kiểu dữ liệu, v.v.),
    //bạn cần tăng phiên bản cơ sở dữ liệu.
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tblDangKy";
    private static final String COL_ID= "Id";

    private static final String COL_NAME = "name";

    private static final String COL_PSW = "password";

    private static final String COL_EMAIL ="email";

    private static  final String COL_SDT = "sdt";

    public DataBaseHelper_DangKy(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("+
                COL_ID + "TEXT PRIMARY KEY  ,"+
                COL_NAME + "TEXT,"+
                COL_PSW + "TEXT,"+
                COL_EMAIL +"TEXT,"+
                COL_SDT + "TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //CRUD
    public long addUserDangKy(ThongTinDangKy thongTinDangKy){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, thongTinDangKy.getUid());
        values.put(COL_NAME,thongTinDangKy.getHoTen());
        values.put(COL_PSW,thongTinDangKy.getPsw());
        values.put(COL_EMAIL,thongTinDangKy.getEmail());
        values.put(COL_SDT,thongTinDangKy.getSdt());
        return db.insert(TABLE_NAME,null,values);
    }

    public List<ThongTinDangKy> getAllThongTin(){
        List<ThongTinDangKy>  thongTinDangKyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.query(TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                ThongTinDangKy thongTinDangKy = new ThongTinDangKy(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_PSW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_SDT))
                );
                thongTinDangKyList.add(thongTinDangKy);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  thongTinDangKyList;
    }

    public int updateThongTinDangKy(ThongTinDangKy thongTinDangKy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PSW,thongTinDangKy.getPsw());
        return  db.update(TABLE_NAME,values,COL_ID + "=?",new String[]{String.valueOf(thongTinDangKy.getUid())});
    }

    public boolean checkUser(String userID, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=? AND " + COL_PSW + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{userID,password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;

    }
}
