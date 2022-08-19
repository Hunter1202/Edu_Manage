package qldt.dao;

import java.util.List;
import quanlydaotao.model.SinhVien;

public interface SinhVienDAO {
    
    public List<SinhVien> getList(); 
    
    public int createOrUpdate(SinhVien sinhVien);
      
}
