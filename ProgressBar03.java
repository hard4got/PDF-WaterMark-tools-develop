/*
 * Create By heTao on 2021/11/03
 * Licensed under the MIT license
 *  */
import javax.swing.*;
import java.awt.*;

public  class ProgressBar03 extends JFrame{
   static JProgressBar progressBar;
    static JFrame jf;
    //进度条计数
    private static int e;
    //判断是否选择添加图片水印

    public void ProgressBar03(){
        jf = new JFrame("进度提示");
        jf.setSize(250, 100);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        //进度提示
        JLabel label01 = new JLabel();
        label01.setText("完成进度:");
        label01.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体
        // 创建一个进度条
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);//信息被显示
        panel.add(progressBar);

        jf.setContentPane(panel);
        jf.setVisible(true);
        MyThread progress = new MyThread();//线程对象
        progress.start();//启动线程

    }

    static class MyThread extends Thread{//自定义线程，实现进度的不断变化
        public void pdfFlag(int flag){
            if (1 == flag){
                e = PdfWater.getProgressBarValue();
            }
            if (2 == flag){
                e = PdfWaterPict.getProgressBarValue();
            }
            if (3 == flag){
                e = PdfToImage.getProgressBarValue();
            }
        }
        @Override
        public void run() {
            //判断是否是添加图片类型，得到的计数不同

            System.out.println("这是从PdfWater中获取到e的值:"+e);
            /*
            *  如果只使用 progressBar.setValue(e); 方法，进度条没有执行过程，该操作会被阻塞
            * */
            Dimension d = progressBar.getSize();
            Rectangle rect = new Rectangle(0, 0, d.width, d.height);
            progressBar.setValue(e);
            progressBar.paintImmediately(rect);

        }
    }
    public static void main(String[] args) {
        ProgressBar03 frame = new ProgressBar03();
        frame.ProgressBar03();
    }
    public static void Progress03() {
        ProgressBar03 frame = new ProgressBar03();
        frame.ProgressBar03();
    }
    //关闭进度条调用方法
    public static void closeWindows(){
        jf.dispose();
    }
}
