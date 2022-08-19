package quanlydaotao.service;

import quanlydaotao.model.SinhVien;
import java.util.List;

public interface SinhVienService {
    
    public List<SinhVien> getList();

    public int createOrUpdate(SinhVien sinhVien);
        
}
