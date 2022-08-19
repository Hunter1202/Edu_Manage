package qldt.controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import quanlydaotao.model.SinhVien;
import quanlydaotao.service.SinhVienService;
import quanlydaotao.service.SinhVienServiceImpl;

public class SinhVienController {
    private JButton btnSubmit;
    private JTextField jtfMaSinhVien;
    private JTextField jtfHoTen;
    private JDateChooser jdcNgaySinh;
    private JTextField jtfSoDienThoai;
    private JRadioButton jrdGioiTinhNam;
    private JRadioButton jrdGioiTinhNu;
    private JTextArea jtaDiaChi;
    private JCheckBox jcbKichHoat;
    private JLabel jlbMsg;

    private SinhVien sinhVien = null;

    private SinhVienService sinhVienService = null;

    public SinhVienController(JButton btnSubmit, JTextField jtfMaSinhVien, JTextField jtfHoTen, JDateChooser jdcNgaySinh, JTextField jtfSoDienThoai, JRadioButton jrdGioiTinhNam, JRadioButton jrdGioiTinhNu, JTextArea jtaDiaChi, JCheckBox jcbKichHoat) {
        this.btnSubmit = btnSubmit;
        this.jtfMaSinhVien = jtfMaSinhVien;
        this.jtfHoTen = jtfHoTen;
        this.jdcNgaySinh = jdcNgaySinh;
        this.jtfSoDienThoai = jtfSoDienThoai;
        this.jrdGioiTinhNam = jrdGioiTinhNam;
        this.jrdGioiTinhNu = jrdGioiTinhNu;
        this.jtaDiaChi = jtaDiaChi;
        this.jcbKichHoat = jcbKichHoat;
        this.jlbMsg = jlbMsg;
    }

    public void setView(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        // set data
        jtfMaSinhVien.setText("#" + sinhVien.getMa_sinh_vien());
        jtfHoTen.setText(sinhVien.getHo_ten());
        jdcNgaySinh.setDate(sinhVien.getNgay_sinh());
        if (sinhVien.isGioi_tinh()) {
            jrdGioiTinhNam.setSelected(true);
        } else {
            jrdGioiTinhNu.setSelected(true);
        }
        jtfSoDienThoai.setText(sinhVien.getSo_dien_thoai());
        jtaDiaChi.setText(sinhVien.getDia_chi());
        jcbKichHoat.setSelected(sinhVien.isTinh_trang());
        // set event
        setEvent();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        sinhVien.setHo_ten(jtfHoTen.getText().trim());
                        sinhVien.setNgay_sinh(covertDateToDateSql(jdcNgaySinh.getDate()));
                        sinhVien.setSo_dien_thoai(jtfSoDienThoai.getText());
                        sinhVien.setDia_chi(jtaDiaChi.getText());
                        sinhVien.setGioi_tinh(jrdGioiTinhNam.isSelected());
                        sinhVien.setTinh_trang(jcbKichHoat.isSelected());
                        if (showDialog()) {
                            int lastId = sinhVienService.createOrUpdate(sinhVien);
                            if (lastId != 0) {
                                sinhVien.setMa_sinh_vien(lastId);
                                jtfMaSinhVien.setText("#" + sinhVien.getMa_sinh_vien());
                                jlbMsg.setText("Xử lý cập nhật dữ liệu thành công!");
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
        });
    }

    private boolean checkNotNull() {
        return jtfHoTen.getText() != null && !jtfHoTen.getText().equalsIgnoreCase("");
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn cập nhật dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
    
    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }
    

}
