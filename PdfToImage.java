import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PdfToImage {
    /**
     *使用pdfbox转换全部的pdf
     *自由确定起始页和终止页
     * @param fileAddress 文件地址
     * @param fileName pdf文件名
     * @param indexOfStart 开始页  开始转换的页码，从0开始
     * @param indexOfEnd 结束页  停止转换的页码，-1为全部
     * @param type 图片类型
     */
    //PDF文件地址
    private static String fileAddress;
    //PDF文件名
    private static String fileName;
    //图片类型
    private static String type;
    //进度条计数
    private static int e;
    //起始页码
    private static int indexOfStart;
    //结束页码
    private static double indexOfEnd;
    //是否产生图片
    private static Boolean isImage = false;

    public static void main(String[] args) throws Exception {
//        String fileAddress = "/Users/wangjihua/Desktop/pdfbox";
//        String fileName = "liuxuexuzhi";
//        String type = "png";
//        pdf2png(fileAddress, fileName, type);
        PdfToImage pdfToImage = new PdfToImage();
        pdfToImage.initUI();
    }

    public static void  initUI(){
        // 3.在initUI方法中，实例化JFrame窗体容器组件类的对象。
        final JFrame frame = new JFrame();
        // 4.设置窗体容器组件的属性值：标题、大小、显示位置、关闭操作、禁止调整组件大小、布局、可见。
        // 设置窗体的标题属性值
        frame.setTitle("PDF转图片");
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
        final JButton waterMarkBtn = new JButton("转图片");
        final JButton backBtn = new JButton("返回");
        waterMarkBtn.setEnabled(false);

        //水印位置提示
        JLabel label02 = new JLabel();
        label02.setText("请指定转换页数:");
        label02.setFont(new Font(null, Font.PLAIN, 15));  // 设置字体，null 表示使用默认字体

        final JTextField textField02 = new JTextField(5);
        textField02.setFont(new Font(null, Font.PLAIN, 15));
        textField02.setText("");

        final JTextField textField03 = new JTextField(5);
        textField03.setFont(new Font(null, Font.PLAIN, 15));
        textField03.setText("");

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showFileOpenDialog(frame, msgTextArea,openBtn,waterMarkBtn);
                    textField02.setText(String.valueOf(indexOfStart));

                    textField03.setText(String.valueOf((int)indexOfEnd));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        waterMarkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //转换之前获取指定的起始页和结束页
                String indexOfStart1 = textField02.getText();
                String indexOfEnd1 = textField03.getText();
                int indexOfStart = Integer.parseInt(indexOfStart1);
                double indexOfEnd = Double.parseDouble(indexOfEnd1);
                //执行添加水印方法
                pdf2png(PdfToImage.fileAddress, PdfToImage.fileName, PdfToImage.type, indexOfStart, indexOfEnd);
                System.out.println("pdf是否转为图片:"+isImage);
                if (isImage){
                    JOptionPane.showMessageDialog(
                            frame,
                            "pdf转图片成功！",
                            "消息标题",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //关闭进度条窗口
                    ProgressBar03.closeWindows();
                    //设置按钮是否可点击
                    openBtn.setEnabled(true);
                    waterMarkBtn.setEnabled(false);
                    msgTextArea.setText("");
                    textField02.setText("");
                    textField03.setText("");
                    isImage = false;

                }else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "pdf转图片失败！",
                            "消息标题",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //关闭进度条窗口
                    ProgressBar03.closeWindows();
                    openBtn.setEnabled(true);
                    waterMarkBtn.setEnabled(false);
                    msgTextArea.setText("");
                    textField02.setText("");
                    textField03.setText("");
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
        panel.add(label02);
        panel.add(textField02);
        panel.add(textField03);
        panel.add(waterMarkBtn);
        panel.add(backBtn);

        // 设置窗体为可见，这个是必须写的，且必须在最后，否则会看不到一些组件
        frame.setContentPane(panel);
        frame.setVisible(true);

    }
    public static void showFileOpenDialog(Component parent, JTextArea msgTextArea,JButton openBtn,JButton waterMarkBtn) throws IOException {
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

            PDDocument doc = PDDocument.load(file);

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            //设置转换的起始页和结束页（默认全部页数）
            PdfToImage.indexOfStart = 1;
            PdfToImage.indexOfEnd = doc.getNumberOfPages();


            msgTextArea.append("打开文件: " + file.getAbsolutePath());
            PdfToImage.fileAddress = file.getAbsolutePath();
            System.out.println("输入文件路径："+PdfToImage.fileAddress);
            fileName = file.getName();
            System.out.println("文件名："+PdfToImage.fileName);
            type = "png";

        }
    }
    //返回进度条计数
    public static int getProgressBarValue(){
        return e;
    }

    public static void pdf2png(String fileAddress, String fileName, String type, int indexOfStart, double indexOfEnd) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress);
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
//            double total = doc.getNumberOfPages();
            //获取pdf文件的目录
            String outputFile = file.getParent();
            //获取pdf文件的文件名（去掉后缀）
            String caselsh = fileName.substring(0, fileName.lastIndexOf("."));
            //调用进度条方法
            ProgressBar03.Progress03();
            //调用进度条线程
            ProgressBar03.MyThread myThread = new ProgressBar03.MyThread();
            for (int i = indexOfStart - 1; i < indexOfEnd; i++) {
                System.out.println("这是total"+indexOfEnd);
                //设置进度条计数
                double d = i/(indexOfEnd)*100;
                System.out.println("这是d："+d);
                e = (int) d;
                System.out.println("这是e:"+e);
                //执行线程，更新进度条计数
                myThread.pdfFlag(3);
                myThread.run();
//                myThread.pdfTyoe(e);
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(outputFile + "/" + caselsh + "_" + (i + 1) + "." + type));
            }
            PdfToImage.isImage = true;
            System.out.println("处理完毕!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  



//    public static void pdf2png(String fileAddress,String fileName,int indexOfStart,int indexOfEnd,String type) {
//            // 将pdf装图片 并且自定义图片得格式大小
//            File file = new File(fileAddress+"\\"+fileName+".pdf");
//            try {
//            PDDocument doc = PDDocument.load(file);
//            PDFRenderer renderer = new PDFRenderer(doc);
//            int pageCount = doc.getNumberOfPages();
//            for (int i = indexOfStart; i < indexOfEnd; i++) {
//            BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
//            // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
//            ImageIO.write(image, type, new File(fileAddress+"\\"+fileName+"_"+(i+1)+"."+type));
//            }
//            } catch (IOException e) {
//            e.printStackTrace();
//            }
//            }
}