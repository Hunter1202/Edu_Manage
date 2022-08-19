package quanlydaotao.main;

import quanlydaotao.view.DangNhapJDialog;
import quanlydaotao.view.MainJFrame;
import quanlydaotao.view.MainJFrame;


public class Main {

    public static void main(String[] args) {
        
        //new MainJFrame().setVisible(true);
        DangNhapJDialog dialog = new DangNhapJDialog(null, true);
        dialog.setTitle("Đăng nhập hệ thống");
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
}
