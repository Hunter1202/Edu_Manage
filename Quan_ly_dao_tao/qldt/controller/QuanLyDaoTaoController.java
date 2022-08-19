package qldt.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlydaotao.model.SinhVien;
import quanlydaotao.service.SinhVienService;
import quanlydaotao.service.SinhVienServiceImpl;
import quanlydaotao.utility.ClassTableModel;
import quanlydaotao.view.SinhVienJFrame;

public class QuanLyDaoTaoController {
    
    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;

    private ClassTableModel classTableModel = null;

    private final String[] COLUMNS = {"Mã học viên", "STT", "Tên học viên", "Ngày sinh",
        "Giới tính", "Số điện thoại", "Địa chỉ", "Trạng thái"};

    private SinhVienService sinhVienService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyDaoTaoController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        
        this.classTableModel = new ClassTableModel();

        this.sinhVienService = new SinhVienServiceImpl();
    }

    

    public void setDataToTable() {
        List<SinhVien> listItem = sinhVienService.getList();
        DefaultTableModel model = classTableModel.setTableSinhVien(listItem, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        //design
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        
        table.addMouseListener(new MouseAdapter()  {
        @Override
        public void mouseClicked(MouseEvent e)  {
            if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int selectedRowIndex = table.getSelectedRow();
                selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex); 
                
                SinhVien sinhVien = new SinhVien();
                sinhVien.setMa_sinh_vien((int) model.getValueAt(selectedRowIndex, 0));
                sinhVien.setHo_ten(model.getValueAt(selectedRowIndex, 2).toString());
                //set ngay sinh:
                sinhVien.setGioi_tinh(model.getValueAt(selectedRowIndex, 4).toString().equalsIgnoreCase("Nam"));
                sinhVien.setSo_dien_thoai(model.getValueAt(selectedRowIndex, 5).toString());
                sinhVien.setDia_chi(model.getValueAt(selectedRowIndex, 6).toString());
                sinhVien.setTinh_trang((boolean) model.getValueAt(selectedRowIndex, 7));
                        
                SinhVienJFrame frame = new SinhVienJFrame(sinhVien);
                frame.setTitle("Thông tin inh viên");
                frame.setResizeable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
            }
        }    
    });           
        
        // design
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }
    
    public void setEvent(){
        btnAdd.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            SinhVienJFrame frame = new SinhVienJFrame(new SinhVien());
            frame.setTitle("Thông tin sinh viên");
            frame.setLocationRelativeTo(null);
            frame.setResizeable(false);
            frame.setVisible(true);
        }
        
        @Override
        public void mouseEntered(MouseEvent e){
            btnAdd.setBackground(new Color(0, 200, 83)); 
        }
        
        @Override
        public void mouseExited(MouseEvent e){
            btnAdd.setBackground(new Color(100, 221, 23)); 
        }
                
    }); 
     
    btnPrint.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
          
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sinh viên");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH SINH VIÊN");

            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Họ và tên");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Ngày sinh");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Giới tính");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Số điện thoại");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Địa chỉ");

            SinhVienService sinhVienService = new SinhVienServiceImpl();

            List<SinhVien> listItem = sinhVienService.getList();
            
            if(listItem != null){
                FileOutputStream fis = null;
                try{
                    int s = listItem.size();
                    for (int i = 0; i < listItem.size(); i++) {
                    SinhVien sinhVien = listItem.get(i);
                    row = spreadsheet.createRow((short) 4 + i);
                    row.setHeight((short) 400);
                    row.createCell(0).setCellValue(i + 1);
                    row.createCell(1).setCellValue(sinhVien.getHo_ten());
                    row.createCell(2).setCellValue(sinhVien.getNgay_sinh().toString());
                    row.createCell(3).setCellValue(sinhVien.isGioi_tinh() ? "Nam" : "Nữ");
                    row.createCell(4).setCellValue(sinhVien.getSo_dien_thoai());
                    row.createCell(5).setCellValue(sinhVien.getDia_chi());
            }
            //save file
            File f = new File("D:/sinh_vien.xlsx");
            fis = new FileOutputStream(f);
            workbook.write(fis);
            fis.close();
        }catch(Exception ex){
            ex.printStackTrace();
            }
        }
    }
        @Override
        public void mouseExited(MouseEvent e){
            btnPrint.setBackground(new Color(100, 221, 23)); 
        }
        @Override
        public void mouseEntered(MouseEvent e){
            btnPrint.setBackground(new Color(0, 200, 83)); 
        }
                
    });       
    }
}

