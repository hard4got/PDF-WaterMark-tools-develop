/*
 * Create By heTao on 2021/11/03
 * Licensed under the MIT license
 *  */
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import com.sun.javaws.progress.Progress;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;


public class PdfWater {
    //PDF文件名
    private static String fileName;
    //文件输入路径
    private static String inputFile;
    //文件输出路径
    private static String outputFile;
    //水印名称
    private static String waterMarkName;
    //是否添加水印
    private static Boolean isMarked = false;
    //水印位置X
    private static int waterMarkPosi_X;
    //水印位置Y
    private static int waterMarkPosi_Y;
    //水印旋转角度
    private static int waterMarkRota;
    //水印文字大小
    private static int waterMarkSize;
    //进度条计数
    private static int e;

    public static void main(String[] args) {

        PdfWater pdfWater = new PdfWater();
        pdfWater.initUI();

    }

    public static void  initUI(){
        // 3.在initUI方法中，实例化JFrame窗体容器组件类的对象。
        final JFrame frame = new JFrame();
        // 4.设置窗体容器组件的属性值：标题、大小、显示位置、关闭操作、禁止调整组件大小、布局、可见。
        // 设置窗体的标题属性值
        frame.setTitle("PDF加文字水印");
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
        final JButton waterMarkBtn = new JButton("加水印");
        final JButton backBtn = new JButton("返回");
        waterMarkBtn.setEnabled(false);
        //水印文字提示
        JLabel label01 = new JLabel();
        label01.setText("请输入水印文字:");
        label01.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextField textField = new JTextField(10);
        textField.setFont(new Font(null, Font.PLAIN, 15));
        textField.setText("S S L");
        //水印位置提示
        JLabel label02 = new JLabel();
        label02.setText("请指定水印位置:");
        label02.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextField textField02 = new JTextField(5);
        textField02.setFont(new Font(null, Font.PLAIN, 15));
        textField02.setText("350");

        final JTextField textField03 = new JTextField(5);
        textField03.setFont(new Font(null, Font.PLAIN, 15));
        textField03.setText("350");
        //水印旋转角度提示
        JLabel label03 = new JLabel();
        label03.setText("请指定水印旋转角度:");
        label03.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextField textField04 = new JTextField(10);
        textField04.setFont(new Font(null, Font.PLAIN, 15));
        textField04.setText("55");
        //水印大小提示
        JLabel label04 = new JLabel();
        label04.setText("请指定水印文字大小:");
        label04.setFont(new Font(null, Font.PLAIN, 15));

        final JTextField textField05 = new JTextField(5);
        textField05.setFont(new Font(null, Font.PLAIN, 15));
        textField05.setText("150");

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(frame, msgTextArea,openBtn,waterMarkBtn);
            }
        });

        waterMarkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //执行添加水印方法
                waterMark(PdfWater.inputFile,PdfWater.outputFile,waterMarkName,textField,textField02,textField03,textField04,textField05);
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
                    //设置按钮是否可点击
                    openBtn.setEnabled(true);
                    waterMarkBtn.setEnabled(false);
                    msgTextArea.setText("");
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
        panel.add(textField);
        panel.add(label02);
        panel.add(textField02);
        panel.add(textField03);
        panel.add(label03);
        panel.add(textField04);
        panel.add(label04);
        panel.add(textField05);
        panel.add(waterMarkBtn);
        panel.add(backBtn);

        // 设置窗体为可见，这个是必须写的，且必须在最后，否则会看不到一些组件
        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    public static void showFileOpenDialog(Component parent, JTextArea msgTextArea,JButton openBtn,JButton waterMarkBtn) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);
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
            PdfWater.inputFile = file.getAbsolutePath();
            System.out.println("输入文件路径："+PdfWater.inputFile);
            fileName = file.getName();
            System.out.println("文件名："+PdfWater.fileName);
            outputFile = file.getParent()+"/加水印"+fileName;
            System.out.println("输出文件路径："+outputFile);
        }
    }

    public static int getProgressBarValue(){
        return e;
    }


    public static void waterMark(String inputFile,String outputFile, String waterMarkName,JTextField textField,JTextField textField02,JTextField textField03,JTextField textField04,JTextField textField05) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            PdfGState gs = new PdfGState();
            //改透明度
            gs.setFillOpacity(0.5f);
            gs.setStrokeOpacity(0.4f);
            double total = reader.getNumberOfPages();
            JLabel label = new JLabel();
            label.setText(waterMarkName);
            PdfContentByte under;

            waterMarkName = textField.getText();
            System.out.println("获取水印名称："+waterMarkName);
            waterMarkPosi_X = Integer.parseInt(textField02.getText());
            System.out.println("获取水印位置X："+waterMarkPosi_X);
            waterMarkPosi_Y = Integer.parseInt(textField03.getText());
            System.out.println("获取水印位置Y："+waterMarkPosi_Y);
            waterMarkRota = Integer.parseInt(textField04.getText());
            System.out.println("获取水印旋转角度："+waterMarkRota);
            waterMarkSize = Integer.parseInt(textField05.getText());
            System.out.println("获取水印文字大小："+waterMarkSize);
            // 添加一个水印
            //调用进度条方法
            ProgressBar03.Progress03();
            //调用进度条线程
            ProgressBar03.MyThread myThread = new ProgressBar03.MyThread();

            for (int i = 1; i <= total; i++) {
                System.out.println("这是total"+total);
                //设置进度条计数
                double d = i/(total)*100;
                System.out.println("这是d："+d);
                 e = (int) d;
                System.out.println("这是e:"+e);
                //执行线程，更新进度条计数
                myThread.run();

                // 在内容上方加水印
                under = stamper.getOverContent(i);
                //在内容下方加水印
                //under = stamper.getUnderContent(i);
                gs.setFillOpacity(0.5f);
                under.setGState(gs);
                under.beginText();
                //改变颜色
                under.setColorFill(BaseColor.LIGHT_GRAY);
                //改水印文字大小
                under.setFontAndSize(base, waterMarkSize);
                under.setTextMatrix(70, 200);
                //后3个参数，x坐标，y坐标，角度

                System.out.println("正在添加第"+i+"页.....");

                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, waterMarkPosi_X, waterMarkPosi_Y, waterMarkRota);

                under.endText();
            }
            stamper.close();
            reader.close();
            PdfWater.isMarked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}