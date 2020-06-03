package com.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class test {
    public static void main(String[] args) {
        try {
            File file = new File("img/icon.png");
            Image img = ImageIO.read(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
