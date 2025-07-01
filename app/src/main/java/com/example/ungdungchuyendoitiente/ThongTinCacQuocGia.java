package com.example.ungdungchuyendoitiente;

public class ThongTinCacQuocGia {
    private String maTienTe;
    private String tenQuocGia;
    private int hinhQuocGia;

    public ThongTinCacQuocGia(String maTienTe, String tenQuocGia, int hinhQuocGia) {
        this.maTienTe = maTienTe;
        this.tenQuocGia = tenQuocGia;
        this.hinhQuocGia = hinhQuocGia;
    }
    public String getMaTienTe() {
        return maTienTe;
    }
    public String getTenQuocGia() {
        return tenQuocGia;
    }
    public int getHinhQuocGia() {
        return hinhQuocGia;
    }
}
