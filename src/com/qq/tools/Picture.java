package com.qq.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Picture {



    //garden，把图片切割成圆形，背景色自定义
    public static boolean garden(File file,String jieguo,Color yanshe){
        System.out.println("读取"+file);
        //读取图片
        BufferedImage bi1;
        try {
            bi1 = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        // 构造一个类型为预定义图像类型之一的 BufferedImage。
        int min = Math.min(bi1.getWidth(), bi1.getHeight());


        BufferedImage bi2 = new BufferedImage(min,min,
                BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB代表图像类型
        //Double 类以 double 精度定义椭圆
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, min,min);
        /*
         此 Graphics2D 类扩展 Graphics 类，以提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制。
          它是用于在 Java(tm) 平台上呈现二维形状、文本和图像的基础类。
          createGraphics() 创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
         */
        Graphics2D g2 = bi2.createGraphics();//把Graphics2D拿出来后续好理清思路


        //为 Graphics2D 上下文设置 Paint 属性。也就是圆形以外的颜色
        g2.setPaint(yanshe);

        //使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
        g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
        //剪切成椭圆形
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        g2.dispose();//关闭资源

        try {
            //使用支持给定格式的任意 ImageWriter 将一个图像写入 File。如果已经有一个 File 存在，则丢弃其内容。
            ImageIO.write(bi2, "jpg", new File(file.getParent()+"/"+jieguo+"/"+file.getName()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }







    }





}
