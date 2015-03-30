package com.github.axet.lookup;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import com.github.axet.desktop.Desktop;
import com.github.axet.desktop.DesktopFolders;

public class Capture {

    //
    // crop
    //

    static public BufferedImage crop(BufferedImage image, int boundsSize) {
        Rectangle r = new Rectangle(image.getWidth(), image.getHeight());
        r.x += boundsSize;
        r.y += boundsSize;
        r.width -= boundsSize * 2;
        r.height -= boundsSize * 2;
        return crop(image, r);
    }

    static public BufferedImage crop(BufferedImage image, Rectangle r) {
        return crop(image, r.x, r.y, r.x + r.width, r.y + r.height);
    }

    static public BufferedImage crop(BufferedImage src, int x1, int y1, int x2, int y2) {
        BufferedImage dest = new BufferedImage(x2 - x1, y2 - y1, src.getType());
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, (int) dest.getWidth(), (int) dest.getHeight(), x1, y1, x1 + dest.getWidth(),
                y1 + dest.getHeight(), null);
        g.dispose();

        return dest;
    }



    //
    // load / save
    //

    static public void writeDesktop(BufferedImage img) {
        writeDesktop(img, Long.toString(System.currentTimeMillis()) + ".png");
    }

    static public void writeDesktop(BufferedImage img, String file) {
        DesktopFolders d = Desktop.getDesktopFolders();
        File f = FileUtils.getFile(d.getDesktop(), file);
        write(img, f);
    }

    static public void write(BufferedImage img, File f) {
        try {
            ImageIO.write(img, "PNG", f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




//    static public BufferedImage load(Class<?> c, String path) {
//        return load(c.getResourceAsStream(path));
//    }
    //fixed --->

    static public BufferedImage load(Class<?> c, URL url) throws IOException {
        return load(url.openStream());
    }

    static public BufferedImage load(InputStream path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(path);
            return img;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
