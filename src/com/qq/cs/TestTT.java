package com.qq.cs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestTT {
    public static void main(String[] args) throws IOException {
        //读取图片
        BufferedImage bi1 = ImageIO.read(new File("/Users/baige/IT/java/QQ/Server_data/User Avatar/默认头像.jpg"));

        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        // 构造一个类型为预定义图像类型之一的 BufferedImage。
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB代表图像类型
        //Double 类以 double 精度定义椭圆
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                .getHeight());
        /**
         *此 Graphics2D 类扩展 Graphics 类，以提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制。
         * 它是用于在 Java(tm) 平台上呈现二维形状、文本和图像的基础类。
         * createGraphics() 创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
         */
        Graphics2D g2 = bi2.createGraphics();

       // g2.setBackground(Color.black);这行垃圾没有用

        //为 Graphics2D 上下文设置 Paint 属性。也就是圆形以外的颜色
        g2.setPaint(new Color(0,0,0));

        //使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
        g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
        //剪切成椭圆形
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        g2.dispose();

        try {
            //使用支持给定格式的任意 ImageWriter 将一个图像写入 File。如果已经有一个 File 存在，则丢弃其内容。
            ImageIO.write(bi2, "jpg", new File("/Users/baige/IT/java/QQ/Server_data/User Avatar/321.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}