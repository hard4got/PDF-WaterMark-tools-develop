import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 图片转pdf工具类
 * @author hetao
 * @Date 2023/1/6 下午2:35
 */
public class ImageToPdf {
    /**
     *
     * @param fileAddress 文件地址
     * @param fileName pdf文件名
     */
    //PDF文件地址
    private static String fileAddress;
    //PDF文件名
    private static String fileName;
    private static File[] fileAll;
    //进度条计数
    private static int e;
    //是否产生pdf
    private static Boolean isPdf = false;
    public static void  initUI(){
        // 3.在initUI方法中，实例化JFrame窗体容器组件类的对象。
        final JFrame frame = new JFrame();
        // 4.设置窗体容器组件的属性值：标题、大小、显示位置、关闭操作、禁止调整组件大小、布局、可见。
        // 设置窗体的标题属性值
        frame.setTitle("图片转PDF");
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
        final JTextArea msgTextArea = new JTextArea(5, 30);
        msgTextArea.setLineWrap(true);
        msgTextArea.setEditable(false);

        final JButton openBtn = new JButton("打开");
        final JButton waterMarkBtn = new JButton("转PDF");
        final JButton backBtn = new JButton("返回");
        waterMarkBtn.setEnabled(false);

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showFileOpenDialog(frame, msgTextArea,openBtn,waterMarkBtn);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        waterMarkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //执行图片转pdf方法
                image2pdf(fileAll);
                System.out.println("图片是否转为pdf:"+isPdf);
                if (isPdf){
                    JOptionPane.showMessageDialog(
                            frame,
                            "图片转pdf成功！",
                            "消息标题",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //关闭进度条窗口
//                    ProgressBar03.closeWindows();
                    //设置按钮是否可点击
                    openBtn.setEnabled(true);
                    waterMarkBtn.setEnabled(false);
                    msgTextArea.setText("");
                    isPdf = false;

                }else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "图片转pdf失败！",
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
                System.out.println("返回");
                frame.dispose();
                Menu.menu();

            }
        });

        panel.add(msgTextArea);
        panel.add(openBtn);
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
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG(*.png)",  "png"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            openBtn.setEnabled(false);
            waterMarkBtn.setEnabled(true);
            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            File[] files = fileChooser.getSelectedFiles();
            fileAll = files;
            msgTextArea.append("文件目录: " + files[0].getParent());
            msgTextArea.append("\n");
            msgTextArea.append("已选文件：");
            int flag = 0;
            for (File file : files
            ) {
                flag = flag + 1;
                if (flag > 15){
                    msgTextArea.append("..........");
                    break;
                }else {
                    msgTextArea.append(file.getName() + ",");
                }
                ImageToPdf.fileAddress = file.getAbsolutePath();
                System.out.println("输入文件路径："+ImageToPdf.fileAddress);
                fileName = file.getName();
                System.out.println("文件名："+ImageToPdf.fileName);
            }

        }
    }

    public static File Pdf(ArrayList<String> imageUrllist, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0); //new一个pdf文档
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); //pdf写入
            doc.open();//打开文档
            for (int i = 0; i < imageUrllist.size(); i++) {  //循环图片List，将图片加入到pdf中
                doc.newPage();  //在pdf创建一页
                Image png1 = Image.getInstance(imageUrllist.get(i)); //通过文件路径获取image
                float heigth = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent2(heigth, width);
                png1.setAlignment(Image.MIDDLE);
                png1.scalePercent(percent + 3);// 表示是原来图像的比例;
                doc.add(png1);
            }
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        return mOutputPdfFile; //反回文件输出流
    }


    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    public void imgOfPdf(String filepath, String imgUrl) {
        try {
            ArrayList<String> imageUrllist = new ArrayList<String>(); //图片list集合
            String[] imgUrls = imgUrl.split(",");
            for (int i = 0; i < imgUrls.length; i++) {
                imageUrllist.add(imgUrls[i]);
            }
            String pdfUrl = filepath;  //输出pdf文件路径
            File file = this.Pdf(imageUrllist, pdfUrl);//生成pdf
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void image2pdf(File[] fileAll){
        try{
            //判断文件名格式是否为标准格式
            StringBuffer stringBuffer = new StringBuffer();
            String sameFileName[] = new String[fileAll.length];
            for (int i = 0; i < fileAll.length ; i++) {
                String fileName = fileAll[i].getName();
                String caselsh = fileName.substring(0, fileName.lastIndexOf("."));
                if (caselsh.contains("_")){
                    String caselsh1 = caselsh.substring(caselsh.lastIndexOf("_") + 1, caselsh.length());
                    sameFileName[i] =  caselsh.replaceAll(caselsh1, "");
                    if (!(caselsh1.isEmpty())){
                        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
                        if (pattern.matcher(caselsh1).matches()){
                            stringBuffer.append(1);
                        }else {
                            stringBuffer.append(0);
                        }
                    }else {
                        stringBuffer.append(0);
                    }
                }else {
                    stringBuffer.append(0);
                }
            }
            for (String s:sameFileName
                 ) {
                if (s == null || s.equals("")){
                    stringBuffer.append(0);
                }
            }

            ImageToPdf image = new ImageToPdf();
            if (stringBuffer.toString().contains("0")){
                System.out.println("文件格式不正确，使用通用接口");
                Map<String, String> map = generalSortApi(fileAll);
                assert map != null;
                String outputPath = map.get("outputPath");
                String imgUrlFin = map.get("imgUrl02");
                System.out.println("输出文件路径：" + outputPath);
                //执行图片转pdf方法
                image.imgOfPdf(outputPath, imgUrlFin);
            }else {
                System.out.println("文件格式正确，使用标准接口");
                Map<String, String> map = standardSortApi(fileAll);
                assert map != null;
                String outputPath = map.get("outputPath");
                String imgUrlFin = map.get("imgUrl02");
                System.out.println("输出文件路径：" + outputPath);
                //执行图片转pdf方法
               image.imgOfPdf(outputPath, imgUrlFin);
            }
            ImageToPdf.isPdf = true;
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Map<String, String> standardSortApi(File[] fileAll){
        try {
            String outputPath = fileAll[0].getAbsolutePath().substring(0, fileAll[0].getAbsolutePath().lastIndexOf(".")) + ".pdf";

            //获取文件路径，使用getParent()方法会缺少最后一个特殊字符"/"或"\",因此使用绝对路径剪掉文件名来获取路径
            String fullPath = fileAll[0].getAbsolutePath();
            String fileName0 = fileAll[0].getName();
            String filePath;
            filePath = fullPath.replaceAll(fileName0, "");

            String fileNamePrefix[] = new String[fileAll.length];
            int midCount[] = new int[fileAll.length];
            for (int i = 0; i < fileAll.length ; i++) {
                String fileName = fileAll[i].getName();
                String caselsh = fileName.substring(0, fileName.lastIndexOf("."));
                String caselsh1 = caselsh.substring(caselsh.lastIndexOf("_") + 1, caselsh.length());
                fileNamePrefix[i] = caselsh.substring(0, caselsh.lastIndexOf("_")) + "_";
                midCount[i] = Integer.parseInt(caselsh1);
            }
            Arrays.sort(midCount);
            String[] sortFin = new String[midCount.length];
            for (int i = 0; i < midCount.length ; i++) {
                sortFin[i] = filePath + fileNamePrefix[i] + midCount[i] + ".png";
            }
            StringBuffer stringBuffer02 = new StringBuffer();
            for (String s : sortFin) {
                //用append方法拼接字符串
                stringBuffer02.append(s).append(",");
            }
            //用substring方法截掉最后一个","
            String imgUrl02 = stringBuffer02.substring(0, stringBuffer02.length() - 1);
            Map<String, String> map = new HashMap<String, String>();
            map.put("outputPath", outputPath);
            map.put("imgUrl02", imgUrl02);
           return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Map<String, String> generalSortApi(File[] fileAll){
        try{
            String outputPath = fileAll[0].getAbsolutePath().substring(0, fileAll[0].getAbsolutePath().lastIndexOf(".")) + ".pdf";
            //创建一个StringBuffer对象
            StringBuffer str = new StringBuffer();
            for (File file : fileAll) {
                //用append方法拼接字符串
                str.append(file).append(",");
            }
            //用substring方法截掉最后一个","
            String imgUrl02 = str.substring(0, str.length() - 1);
            Map<String, String> map = new HashMap<String, String>();
            map.put("outputPath", outputPath);
            map.put("imgUrl02", imgUrl02);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //test
    public static void main(String[] args){
        ImageToPdf image = new ImageToPdf();
        image.imgOfPdf("/Users/username/Desktop/pdfbox/test.pdf","/Users/username/Desktop/pdfbox/preview_1.png,/Users/username/Desktop/pdfbox/preview_2.png");
    }
    }

