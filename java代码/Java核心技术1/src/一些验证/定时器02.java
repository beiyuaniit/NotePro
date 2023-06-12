package 一些验证;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Scanner;

/**
 * @author: beiyuan
 * @className: 定时器02
 * @date: 2022/3/11  18:43
 */
public class 定时器02 {
    public static void main(String[] args) {
        var listener=new TimerPrint();
        new Timer(1000,listener).start();

        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);



    }

}

class TimerPrint implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("the time is "+ Instant.ofEpochMilli(e.getWhen()));
    }
}
