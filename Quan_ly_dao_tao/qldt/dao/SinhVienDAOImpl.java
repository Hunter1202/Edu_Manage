package qldt.dao;

import quanlydaotao.model.SinhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAOImpl implements SinhVienDAO {

    @Override
    public List<SinhVien> getList() {
        Connection cons = DBConnect.getConnection();
        String sql = "SELECT * FROM sinh_vien";
        List<SinhVien> list = new ArrayList<>();
        try {
            PreparedStatement ps = (PreparedStatement) cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setMa_sinh_vien(rs.getInt("ma_sinh_vien"));
                sinhVien.setHo_ten(rs.getString("ho_ten"));
                sinhVien.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                sinhVien.setDia_chi(rs.getString("dia_chi"));
                sinhVien.setNgay_sinh(rs.getDate("ngay_sinh"));
                sinhVien.setGioi_tinh(rs.getBoolean("gioi_tinh"));
                sinhVien.setTinh_trang(rs.getBoolean("tinh_trang"));
                list.add(sinhVien);
            }
            ps.close();
            cons.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }   
    public static void main(String[] args){
        SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
        System.out.println(sinhVienDAO.getList());
    }

    @Override
    public int createOrUpdate(SinhVien sinhVien) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
