package quanlydaotao.utility;

import quanlydaotao.model.SinhVien;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClassTableModel {

    public DefaultTableModel setTableSinhVien(List<SinhVien> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 7 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        SinhVien sinhVien = null;
        for (int i = 0; i < num; i++) {
            sinhVien = listItem.get(i);
            obj = new Object[columns];
            obj[0] = sinhVien.getMa_sinh_vien();
            obj[1] = (i + 1);
            obj[2] = sinhVien.getHo_ten();
            obj[3] = sinhVien.getNgay_sinh();
            obj[4] = sinhVien.isGioi_tinh() == true ? "Nam" : "Nữ";
            obj[5] = sinhVien.getSo_dien_thoai();
            obj[6] = sinhVien.getDia_chi();
            obj[7] = sinhVien.isTinh_trang();
            dtm.addRow(obj);
        }
        return dtm;
    }

}
