package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KeyboardObserver extends Thread {
    public static void main(String[] args) {
        try {
            // retrieve image
            BufferedImage[] temp = split(4, 4, 64, 64, ImageIO.read(new File("sources/images/missiles/explosion.png")));
            for (int i = 0; i < temp.length; i++) {
                int name = i + 13;
                String direction = String.format("sources/images/missiles/new/%d.png", name);
                File outputfile = new File(direction);
                ImageIO.write(temp[i], "png", outputfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage[] split(int rows, int cols, int width, int height, BufferedImage shit) {
        BufferedImage[] sprites = new BufferedImage[rows * cols];
        for (int i = 0; i < rows; i++) {
            if (i >= 5) {
                cols = 3 + i;
                width = 477 / (3 + i);
            }
            for (int j = 0; j < cols; j++) {
                sprites[(i * cols) + j] = shit.getSubimage(j * width, i * height, width, height);
            }
        }
        return sprites;
    }
}

