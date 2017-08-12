package com.company;

import com.company.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class View extends JPanel {
    private static final int WIDTH_SCREEN_SIZE = 1280;
    private static final int HEIGHT_SCREEN_SIZE = 720;

    private BufferedImage background;
    private BufferedImage platformHorizontal1;
    private BufferedImage platformVertical1;


    private BufferedImage playerSheet;
    // The above line throws an checked IOException which must be caught.
    BufferedImage[] sprites;

    private Controller controller;

    public View(Controller controller) {
        setFocusable(true);
        this.controller = controller;
        this.addKeyListener(controller);
        this.addMouseListener(controller);
        try {
            loadSources();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void loadSources() throws IOException {
        background = ImageIO.read(new File("sources/images/backgrounds/9.jpg"));
        platformHorizontal1 = ImageIO.read(new File("sources/images/decoration/floor_platform.png"));
        platformVertical1 = ImageIO.read(new File("sources/images/decoration/wall_platform.png"));
        playerSheet = ImageIO.read(new File("sources/images/player/sheet_doom.gif"));
        final int rows = 6;
        int cols = 9;
        int width = 477 / 9;
        int height = 432 / 6;
        sprites = new BufferedImage[rows * cols];
        for (int i = 0; i < rows; i++) {
            if (i >= 5) {
                cols = 3 + i;
                width = 477 / (3 + i);
            }
            for (int j = 0; j < cols; j++) {
//                System.out.println("try " + j * width + " " + i * height + " " + width + " " + height);
                sprites[(i * cols) + j] = playerSheet.getSubimage(j * width, i * height, width, height);
//                System.out.println("good " + j * width + " " + i * height + " " + width + " " + height);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("paint " + new Date());
        Color BG_COLOR = new Color(0xbbada0);
        g.setColor(BG_COLOR);
        g.drawImage(background.getSubimage(0, 100, 1280, 720)
                , 0, 0, this);
        int offx;
        int offy;

        char[][] mapDec = controller.getStage();
        for (int i = 0; i < mapDec.length; i++) {
            for (int j = 0; j < mapDec[0].length; j++) {
                if (mapDec[i][j] == 'l') {
                    offx = offsetCoors(j, (int) (1280 / 12.75)) - 50;
                    offy = offsetCoors(i, 650 / 15) - 30;
                    g.drawImage(platformHorizontal1, offx, offy, 100, 100, this);
                } else if (mapDec[i][j] == 'v') {
                    offx = offsetCoors(j, (int) (1280 / 12.75)) - 50;
                    offy = offsetCoors(i, 650 / 15);
                    g.drawImage(platformVertical1, offx, offy, 100, 50, this);
                }
            }
        }

        offx = offsetCoors((int) controller.getWarrior().getX(), 1280 / 256) - 53 / 2;
        if (controller.getPlayerFrame() < 9) offy = offsetCoors((int) controller.getWarrior().getY(), 650 / 30) + 5;
        else offy = offsetCoors((int) controller.getWarrior().getY(), 650 / 30) - 20 + 5;
        if (controller.getWarrior().getDirection() == -1 || controller.getWarrior().getDirection() == 0) {
//            System.out.println("direction left/stop " + controller.getWarrior().getDirection());
            g.drawImage(sprites[controller.getPlayerFrame()], offx, offy, this);
        } else {
//            System.out.println("direction right " + controller.getWarrior().getDirection());
            g.drawImage(mirror(sprites[controller.getPlayerFrame()]), offx, offy, this);
        }

//        System.out.println((double) offsetCoors((int) controller.getWarrior().getX(), 1280 / 256) / 100
//                + " " + ((1280 / 256) * controller.getWarrior().getX()) / 100
//                + " " + controller.getWarrior().getY());

//        System.out.println(controller.getPlayerFrame() + " of " + sprites.length);

        for (int i = 0; i < controller.getField().getHeight(); i++) {//*25
//            g.drawLine(i * 1280 / 256, 0, i * 1280 / 256, this.getHeight());
            g.drawLine(i * 100, 0, i * 100, this.getHeight());
        }
        for (int i = 0; i < controller.getField().getWidth(); i++) {//*2
//            g.drawLine(0, i * 650 / 15, this.getWidth(), i * 650 / 15);
            g.drawLine(0, i * 650 / 15, this.getWidth(), i * 650 / 15);
        }
    }

    private BufferedImage mirror(BufferedImage simg) {
        //get source image dimension
        int width = simg.getWidth();
        int height = simg.getHeight();
        //BufferedImage for mirror image
        BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = simg.getRGB(lx, y);
                //set mirror image pixel value - both left and right
//                mimg.setRGB(lx, y, p);
                mimg.setRGB(rx, y, p);
            }
        }
        //save mirror image


        return mimg;
    }

    private static int offsetCoors(int arg, int peaceSize) {
        return arg * (peaceSize);
//        return arg * (peaceMargin + peaceSize) + peaceMargin;
    }

}
