package com.example.ungdungchuyendoitiente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThongTinCacQuocGiaADapter extends RecyclerView.Adapter<ThongTinCacQuocGiaADapter.ThongTinCacQuocGiaViewHolder> {
    private List<ThongTinCacQuocGia> thongTinCacQuocGiaList;
    private Context context;

    // Thêm constructor này
    public ThongTinCacQuocGiaADapter(List<ThongTinCacQuocGia> thongTinCacQuocGiaList,Context context) {
        this.context = context;
        this.thongTinCacQuocGiaList = thongTinCacQuocGiaList;
    }

    @Override
    public ThongTinCacQuocGiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chon_don_vi_chuyen_doi, parent, false);
        return new ThongTinCacQuocGiaViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ThongTinCacQuocGiaViewHolder holder, int position) {
        ThongTinCacQuocGia thongTin = thongTinCacQuocGiaList.get(position);
        holder.tenQuocGia.setText(thongTin.getTenQuocGia());
        holder.maTienTe.setText(thongTin.getMaTienTe());
        holder.hinhQuocGia.setImageResource(thongTin.getHinhQuocGia());

    }
    @Override
    public int getItemCount() {
        return thongTinCacQuocGiaList.size();
    }
    public static class ThongTinCacQuocGiaViewHolder extends RecyclerView.ViewHolder {
        TextView maTienTe, tenQuocGia;
        ImageView hinhQuocGia;

        public ThongTinCacQuocGiaViewHolder(View itemView) {
            super(itemView);
            tenQuocGia = itemView.findViewById(R.id.txtTenQuocGia);
            maTienTe = itemView.findViewById(R.id.txtMaTienTe);
            hinhQuocGia = itemView.findViewById(R.id.imgAnhNuoc);
        }
    }
}
