/*
* Create By heTao on 2021/11/03
* Licensed under the MIT license
*  */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.menu();
    }

    public static void menu(){
        final JFrame jf = new JFrame("PDF加水印");
        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        // 创建一个按钮
        final JButton btn = new JButton("添加文字水印");
        final JButton btn02 = new JButton("添加图片水印");
        final JButton btn05 = new JButton("pdf转图片");
//        final JButton btn04 = new JButton("松山湖专用");
        final JButton btn03 = new JButton("退出");
        // 添加按钮的点击事件监听器
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                // JButton btn = (JButton) e.getSource();
                System.out.println("添加文字水印");
                jf.dispose();
                PdfWater.initUI();

            }
        });
        btn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                // JButton btn = (JButton) e.getSource();
                System.out.println("添加图片水印");
                jf.dispose();
                PdfWaterPict.initUI();
            }
        });
//        btn04.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 获取到的事件源就是按钮本身
//                // JButton btn = (JButton) e.getSource();
//                System.out.println("松山湖专用");
//                jf.dispose();
//                PdfWaterForSSL.initUI();
//            }
//        });
        btn05.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                // JButton btn = (JButton) e.getSource();
                System.out.println("pdf转图片");
                jf.dispose();
                PdfToImage.initUI();
            }
        });
        btn03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                // JButton btn = (JButton) e.getSource();
                System.out.println("退出");
                System.exit(0);
            }
        });
        panel.add(btn);
        panel.add(btn02);
//        panel.add(btn04);
        panel.add(btn05);
        panel.add(btn03);

        jf.setContentPane(panel);
        jf.setVisible(true);
    }
}
