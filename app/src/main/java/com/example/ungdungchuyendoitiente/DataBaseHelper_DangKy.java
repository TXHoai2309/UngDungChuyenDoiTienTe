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
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                COL_PSW + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_SDT + " TEXT)";
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

    // Lấy thông tin người dùng theo userId
    public ThongTinDangKy getUserInfo(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Sử dụng rawQuery để thực hiện truy vấn trực tiếp
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";

        // Thực thi câu truy vấn và lấy dữ liệu từ cơ sở dữ liệu
        try (Cursor cursor = db.rawQuery(query, new String[]{userId})) {
            if (cursor != null && cursor.moveToFirst()) {
                // Lấy thông tin người dùng từ cursor mà không cần getColumnIndex()
                String uid = cursor.getString(0);      // Cột 0 là COL_ID
                String name = cursor.getString(1);     // Cột 1 là COL_NAME
                String psw = cursor.getString(2);      // Cột 2 là COL_PSW
                String email = cursor.getString(3);    // Cột 3 là COL_EMAIL
                String sdt = cursor.getString(4);      // Cột 4 là COL_SDT

                // Trả về thông tin người dùng
                return new ThongTinDangKy(uid, name, psw, email, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có vấn đề trong truy vấn
        }

        return null;  // Trả về null nếu không tìm thấy người dùng
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
                        //
                );
                thongTinDangKyList.add(thongTinDangKy);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  thongTinDangKyList;
    }
    // Cập nhật thông tin người dùng (bao gồm mật khẩu)
    public int updateThongTinDangKy(ThongTinDangKy thongTinDangKy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, thongTinDangKy.getHoTen());   // Cập nhật họ tên
        values.put(COL_PSW, thongTinDangKy.getPsw());      // Cập nhật mật khẩu
        values.put(COL_EMAIL, thongTinDangKy.getEmail());  // Cập nhật email
        values.put(COL_SDT, thongTinDangKy.getSdt());      // Cập nhật số điện thoại
        return db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{thongTinDangKy.getUid()});
    }

    public boolean checkUser(String userID, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=? AND " + COL_PSW + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{userID,password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
        //

    }

    // Xóa tài khoản người dùng
    public int deleteUser(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_ID + "=?", new String[]{userId});
    }
}
