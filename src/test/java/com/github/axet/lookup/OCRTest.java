package com.github.axet.lookup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import com.github.axet.lookup.common.ImageBinaryGrey;

import javax.swing.*;

public class OCRTest {
    public static  String str;



    static public void main(String[] args)  {
        tray();
        double k;
        while (true){
            getText();
            System.out.println(str);
            try {
                Thread.sleep(3*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            k = Double.parseDouble(str.substring(0, str.length() - 2));
            if ( k >= 4.8 && k <= 5){
                JOptionPane.showMessageDialog(null, "Download " + str, "Internet: " + "ds.pollub.pl", JOptionPane.INFORMATION_MESSAGE);


            }

        }
    }
    public static void tray(){

        final TrayIcon trayIcon;

        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("ar.gif");



            ActionListener exitListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            };

            final JPopupMenu popup = new JPopupMenu();
            JMenuItem defaultItem = new JMenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, str);

            ActionListener actionListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Download", str,
                            TrayIcon.MessageType.INFO);
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.setLocation(e.getX(), e.getY());
                        popup.setInvoker(popup);
                        popup.setVisible(true);
                    }
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            //  System Tray is not supported
        }
    }
    public static  void getText() {
        OCR l = new OCR(0.70f);

        String url = "http://ds.pollub.pl/wykres/my.png";

        l.loadFont(OCRTest.class, new File("fonts", "font_2"));

        try {
            ImageBinaryGrey i = new ImageBinaryGrey(Capture.load(OCRTest.class, url));
            str = l.recognize(i, 564, 264, 636, 280, l.getSymbols("font_2"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
