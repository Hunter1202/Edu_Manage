package quanlydaotao.service;

import qldt.dao.TaiKhoanDAO;
import qldt.dao.TaiKhoanDAOImpl;
import quanlydaotao.model.TaiKhoan;

public class TaiKhoanServiceImpl implements TaiKhoanService {

    private TaiKhoanDAO taiKhoanDAO = null;

    public TaiKhoanServiceImpl() {
        taiKhoanDAO = new TaiKhoanDAOImpl();
    }

    @Override
    public TaiKhoan login(String tenDangNhap, String matKhau) {
        return taiKhoanDAO.login(tenDangNhap, matKhau);
    }    
}
