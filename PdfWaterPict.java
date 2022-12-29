/*
 * Create By heTao on 2021/11/03
 * Licensed under the MIT license
 *  */
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class PdfWaterPict {
    //PDF文件名
    private static String fileName;
    //文件输入路径
    private static String inputFile;
    //文件输出路径
    private static String outputFile;
    //水印图片
    private static  String iconFile;
    //是否添加水印
    private static Boolean isMarked = false;
    //水印位置X
    private static int waterMarkPosi_X;
    //水印位置Y
    private static int waterMarkPosi_Y;
    //水印图片大小W
    private static int waterMarkSize_W;
    //水印图片大小H
    private static int waterMarkSize_H;
    //进度条计数
    private static int e;

    public static void main(String[] args) {

        PdfWaterPict PdfWaterPict = new PdfWaterPict();
        PdfWaterPict.initUI();

    }

    public static void  initUI(){
        // 3.在initUI方法中，实例化JFrame窗体容器组件类的对象。
        final JFrame frame = new JFrame();
        // 4.设置窗体容器组件的属性值：标题、大小、显示位置、关闭操作、禁止调整组件大小、布局、可见。
        // 设置窗体的标题属性值
        frame.setTitle("PDF加图片水印");
        // 设置窗体的大小属性值，单位是像素
        frame.setSize(380,320);
        // 设置窗体显示在屏幕的中央
        frame.setLocationRelativeTo(null);
        // 设置窗体的关闭操作，3表示关闭窗体退出程序。
        frame.setDefaultCloseOperation(3);
        // 设置禁止调整窗体的大小
        frame.setResizable(false);
        // 实例化BorderLayout边框 布局类的对象
        BorderLayout bl = new BorderLayout();
        // JFrame窗体容器组件默认的布局方式就是BorderLayout边框布局
        frame.setLayout(bl);
        // 创建内容面板，指定使用 流式布局
        JPanel panel = new JPanel(new FlowLayout());
        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(3, 20);
        msgTextArea.setLineWrap(true);
        msgTextArea.setEditable(false);

        final JButton openBtn = new JButton("打开");
        final JButton openBtn02 = new JButton("打开");
        final JButton waterMarkBtn = new JButton("加水印");
        final JButton backBtn = new JButton("返回");
        waterMarkBtn.setEnabled(false);
        //水印文字提示
        JLabel label01 = new JLabel();
        label01.setText("请打开水印图片:");
        label01.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextArea msgTextArea02 = new JTextArea(2, 10);
        msgTextArea02.setLineWrap(true);
        msgTextArea02.setEditable(false);

        //水印位置提示
        JLabel label02 = new JLabel();
        label02.setText("请指定水印位置:");
        label02.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextField textField02 = new JTextField(5);
        textField02.setFont(new Font(null, Font.PLAIN, 15));
        textField02.setText("300");

        final JTextField textField03 = new JTextField(5);
        textField03.setFont(new Font(null, Font.PLAIN, 15));
        textField03.setText("300");

        //水印大小提示
        JLabel label04 = new JLabel();
        label04.setText("请指定水印大小:");
        label04.setFont(new Font(null, Font.PLAIN, 15));

        final JTextField textField05 = new JTextField(5);
        textField05.setFont(new Font(null, Font.PLAIN, 15));
        textField05.setText("150");

        final JTextField textField06 = new JTextField(5);
        textField06.setFont(new Font(null, Font.PLAIN, 15));
        textField06.setText("150");

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(frame, msgTextArea,openBtn,waterMarkBtn);
            }
        });

        openBtn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(frame, msgTextArea02);
            }
        });

        waterMarkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //执行加水印方法
                waterMark(inputFile,outputFile,iconFile,textField02,textField03,textField05,textField06);
                System.out.println("水印是否添加成功:"+isMarked);
                if (isMarked){
                    JOptionPane.showMessageDialog(
                            frame,
                            "水印添加成功！",
                            "消息标题",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //关闭进度条窗口
                    ProgressBar03.closeWindows();
                    //将打开按钮设置成可点击
                    openBtn.setEnabled(true);
                    //将加水印按钮设置成不可点击
                    waterMarkBtn.setEnabled(false);
                    //清空文本框内容
                    msgTextArea.setText("");
                    msgTextArea02.setText("");
                    //初始化标记
                    isMarked = false;
                }else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "水印添加失败！",
                            "消息标题",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //关闭进度条窗口
                    ProgressBar03.closeWindows();
                    openBtn.setEnabled(true);
                    waterMarkBtn.setEnabled(false);
                    msgTextArea.setText("");
                    msgTextArea02.setText("");

                }

            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                // JButton btn = (JButton) e.getSource();
                System.out.println("返回");
                frame.dispose();
                Menu.menu();

            }
        });

        panel.add(msgTextArea);
        panel.add(openBtn);
        panel.add(label01);
        panel.add(msgTextArea02);
        panel.add(openBtn02);
        panel.add(label02);
        panel.add(textField02);
        panel.add(textField03);
//        panel.add(label03);
//        panel.add(textField04);
        panel.add(label04);
        panel.add(textField05);
        panel.add(textField06);
        panel.add(waterMarkBtn);
        panel.add(backBtn);

        // 设置窗体为可见，这个是必须写的，且必须在最后，否则会看不到一些组件
        frame.setContentPane(panel);
        frame.setVisible(true);

    }
    //打开PDF文件
    public static void showFileOpenDialog(Component parent, JTextArea msgTextArea,JButton openBtn,JButton waterMarkBtn) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF(*.pdf)",  "pdf"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            openBtn.setEnabled(false);
            waterMarkBtn.setEnabled(true);
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();
            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            msgTextArea.append("打开文件: " + file.getAbsolutePath());
            PdfWaterPict.inputFile = file.getAbsolutePath();
            System.out.println("输入文件路径："+PdfWaterPict.inputFile);
            fileName = file.getName();
            System.out.println("文件名："+PdfWaterPict.fileName);
            outputFile = file.getParent()+"/加水印"+fileName;
            System.out.println("输出文件路径："+outputFile);
        }
    }
    //打开水印图片
    public static void showFileOpenDialog(Component parent,JTextArea msgTextArea02){
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("图片(*.png, *.jpg)",  "png","jpg"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();
            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            msgTextArea02.append("打开文件: " + file.getAbsolutePath());
            PdfWaterPict.iconFile = file.getAbsolutePath();
            System.out.println("输入水印图片路径："+PdfWaterPict.iconFile);

        }
    }
    //返回进度条计数
    public static int getProgressBarValue(){
        return e;
    }
    //加完水印后将输入文件和图片水印设置为空
    public static void setNull(){
        iconFile = null;
        inputFile = null;
    }
    //添加水印方法
    public static void waterMark(String inputFile, String outputFile, String iconFile, JTextField textField02, JTextField textField03, JTextField textField05, JTextField textField06){
       try {
           //实例化PdfDocument类的对象，并加载测试文档
           PdfDocument doc = new PdfDocument();
           doc.loadFromFile(inputFile);
           //获取PDF总页数
           double pdfCount = doc.getPages().getCount();

           waterMarkPosi_X = Integer.parseInt(textField02.getText());
           System.out.println("获取水印位置X："+waterMarkPosi_X);
           waterMarkPosi_Y = Integer.parseInt(textField03.getText());
           System.out.println("获取水印位置Y："+waterMarkPosi_Y);
           waterMarkSize_W = Integer.parseInt(textField05.getText());
           System.out.println("获取图片水印大小W："+waterMarkSize_W);
           waterMarkSize_H = Integer.parseInt(textField06.getText());
           System.out.println("获取图片水印大小H："+waterMarkSize_H);
           System.out.println("PDF总页数："+pdfCount);
           System.out.println("正在加水印........");

           //调用进度条方法
           ProgressBar03.Progress03();
           //调用进度条线程
           ProgressBar03.MyThread myThread = new ProgressBar03.MyThread();

           for (int i = 0;i <= pdfCount-1; i++){
               System.out.println("这是total"+pdfCount);
               //设置进度条计数
               double d = i/pdfCount*100;
               System.out.println("这是d："+d);
               e = (int) d;
               System.out.println("这是e:"+e);
               //执行线程，更新进度条计数
               myThread.pdfFlag(2);
               myThread.run();

               System.out.println("正在添加第"+(i+1)+"页.....");
               PdfPageBase page = doc.getPages().get(i);
               //加载图片，设置为背景水印
               page.setBackgroundImage(iconFile);
               //指定水印在文档中的位置及图片大小
               Rectangle2D.Float rect = new Rectangle2D.Float();
               rect.setRect(waterMarkPosi_X, waterMarkPosi_Y, waterMarkSize_W, waterMarkSize_H);
               page.setBackgroundRegion(rect);
           }
           //保存文档
           doc.saveToFile(outputFile);
           doc.close();
           PdfWaterPict.isMarked = true;
           System.out.println("处理完毕!!!!");
           setNull();
           System.out.println("初始化inputFile:"+PdfWaterPict.inputFile);
           System.out.println("初始化iconFile:"+PdfWaterPict.iconFile);
       }catch (Exception e){
           e.printStackTrace();
       }

    }
}
