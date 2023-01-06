# PDF处理-工具开发

使用Java图形化开发

开发环境  IDEA+JDK1.8

借助了itextpdf和pdfbox插件（只是进行简单的封装....）


Menu.java 程序入口

![图片](https://user-images.githubusercontent.com/48041910/211006483-d4a68953-adf3-4676-bc13-ec104e67c101.png)

PdfWater.java  添加文字水印

![图片](https://user-images.githubusercontent.com/48041910/211006775-cdc47f01-f4c4-4d0f-94a2-7539189731c5.png)


PdfWaterPict.java  添加图片水印

![截屏2023-01-06 14 49 38](https://user-images.githubusercontent.com/48041910/211006871-6857865b-7bcc-4539-9944-8c9c79034ebf.png)

ProgerssBar03.java   进度条

![截屏2023-01-06 14 54 52](https://user-images.githubusercontent.com/48041910/211007659-62785fe5-39f6-42ef-a5bb-82bfe68411af.png)


PdfToImage.java pdf转png(2022/12/30 新增功能)

页数自动获取，也可以手动指定

![截屏2023-01-06 14 51 04](https://user-images.githubusercontent.com/48041910/211007109-64d7bba4-8c7c-4614-b8a0-a473bad9f78d.png)

ImageToPdf.java png转pdf(2023/01/06 新增功能)

可以选择多个png文件

![截屏2023-01-06 14 57 33](https://user-images.githubusercontent.com/48041910/211008416-de12be56-41a4-4f5c-84df-fcc0cebd4450.png)

![截屏2023-01-06 14 58 41](https://user-images.githubusercontent.com/48041910/211008536-d93cb519-bdd0-49fe-bc10-1799c06f5edb.png)

![截屏2023-01-06 14 59 32](https://user-images.githubusercontent.com/48041910/211008731-d246e851-dcf7-4218-81a8-df281192feff.png)



pom.xml 依赖包的导入

（以下说明为旧版本，新版本还想增加一些功能再封装....）

WaterPdf_v1.0.jar为IDEA导出的jar包

已使用exe4j将IDEA导出的jar文件转换成exe可执行文件

Water.zip解压后Water文件夹里有JRE运行环境，若打开exe可执行文件出现运行环境错误，可使用DirectRepair工具进行修复

Water.zip文件分享
链接: https://pan.baidu.com/s/1-ZYfnl8Emk2CNmDQGOB15Q 提取码: rmnu

WaterPdf_v1.0.jar文件分享
链接: https://pan.baidu.com/s/1aAPJHzuSzrwAMCjU1I6lbA 提取码: hdf8


DirectRepair工具分享
链接: https://pan.baidu.com/s/1EPB-GNJypvzYNibWrl-hBg 提取码: ihkw





