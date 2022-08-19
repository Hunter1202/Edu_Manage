package quanlydaotao.service;

import qldt.dao.SinhVienDAO;
import qldt.dao.SinhVienDAOImpl;
import quanlydaotao.model.SinhVien;
import java.util.List;

public class SinhVienServiceImpl implements SinhVienService {

    private SinhVienDAO sinhVienDAO = null;

    public SinhVienServiceImpl() {
        this.sinhVienDAO = new SinhVienDAOImpl();
    }

    @Override
    public List<SinhVien> getList() {
        return sinhVienDAO.getList();
    }

    @Override
    public int createOrUpdate(SinhVien sinhVien) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
