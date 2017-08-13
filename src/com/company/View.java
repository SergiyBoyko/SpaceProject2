package com.company;

import com.company.controller.Controller;
import com.company.model.Enemy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class View extends JPanel {
    private static final int WIDTH_SCREEN_SIZE = 1280;
    private static final int HEIGHT_SCREEN_SIZE = 720;

    private BufferedImage background;
    private List<BufferedImage> horizontalPlatforms1;
    private BufferedImage platformVertical1;
//    private BufferedImage platformVertical1;


    // The above line throws an checked IOException which must be caught.
    private BufferedImage[] playerSprites;
    private BufferedImage[] dAlienSprites;

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
        horizontalPlatforms1 = new ArrayList<BufferedImage>();
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/s_floor_platform.png")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/m_floor_platform.png")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/e_floor_platform.png")));
        platformVertical1 = ImageIO.read(new File("sources/images/decoration/wall_platform.png"));
        BufferedImage playerSheet = ImageIO.read(new File("sources/images/player/sheet_doom.gif"));
        int rows = 6;
        int cols = 9;
        int width = 477 / 9;
        int height = 432 / 6;
        playerSprites = split(rows, cols, width, height, playerSheet);
//        BufferedImage playerSheet = ImageIO.read(new File("sources/images/player/sheet_halo.png"));
//        int rows = 6;
//        int cols = 9;
//        int width = 477 / 9;
//        int height = 432 / 6;
//        playerSprites = split(rows, cols, width, height, playerSheet);
        //130x120
        rows = 1;
        cols = 3;
        width = 130;
        height = 120;
        BufferedImage dAlienSheet = ImageIO.read(new File("sources/images/enemy/dolphin_alien.gif"));
        dAlienSprites = split(rows, cols, width, height, dAlienSheet);

    }

    private BufferedImage[] split(int rows, int cols, int width, int height, BufferedImage shit) {
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("paint " + new Date());
        Color BG_COLOR = new Color(0xbbada0);
        g.setColor(BG_COLOR);
        g.drawImage(background.getSubimage(0, 100, 1280, 720)
                , 0, 0, this);
//        int offsetStableX = 1280 / 30;
        int offsetStableX = 1280 / 30;
        int offsetStableY = 650 / 15;
//        int offx;
//        int offy;
        paintStage(g, offsetStableX, offsetStableY);
        paintEnemies(g, offsetStableX, offsetStableY);
        paintPlayer(g, offsetStableX, offsetStableY);


//        System.out.println( offsetCoors(controller.getWarrior().getX(), offsetStableX) / 42
//                + " " + ((1280 / 256) * controller.getWarrior().getX()) / 100
//                + " " + controller.getWarrior().getY());

//        System.out.println(controller.getPlayerFrame() + " of " + sprites.length);
/**
 * very important lines
 */
//        for (int i = 0; i < controller.getField().getWidth()+1; i++) {//*25
//            g.drawLine(i * offsetStableX, 0, i * offsetStableX, this.getHeight());
////            g.drawLine(i * 100, 0, i * 100, this.getHeight());
//        }
//        for (int i = 0; i < controller.getField().getHeight(); i++) {//*2
////            g.drawLine(0, i * offsetStableY, this.getWidth(), i * offsetStableY);
//            g.drawLine(0, i * offsetStableY, this.getWidth(), i * offsetStableY);
//        }
/**
 * very important lines
 */
    }

    private void paintEnemies(Graphics g, int offsetStableX, int offsetStableY) {
        int offx;
        int offy;
        List<Enemy> enemies = new ArrayList<>(controller.getEnemies());
        for (Enemy enemy : enemies) {
//            Image frame;
//            if (enemy instanceof XenomorphEnemy) {
//            }
            offx = (int) (offsetCoors(enemy.getX(), offsetStableX) - 130 / 2);
//        if (controller.getPlayerFrame() < 9)
            offy = (int) (offsetCoors((int) enemy.getY(), offsetStableY)) - 60;
//        else offy = (int) (offsetCoors(controller.getWarrior().getY(), offsetStableY) - 20);
            if (enemy.getDirection() == 1) {
//            System.out.println("direction left/stop " + controller.getWarrior().getDirection());
                g.drawImage(dAlienSprites[enemy.getEnemyFrame()], offx, offy, this);
            } else {
//            System.out.println("direction right " + controller.getWarrior().getDirection());
                g.drawImage(mirror(dAlienSprites[enemy.getEnemyFrame()]), offx, offy, this);
            }

//            g.drawOval((int) (offsetStableX * enemy.getX())-3 ,
//                    (int) (offsetStableY * enemy.getY())-3, 6, 6);
        }
    }

    private void paintPlayer(Graphics g, int offsetStableX, int offsetStableY) {
        int offx;
        int offy;
        offx = (int) (offsetCoors(controller.getWarrior().getX(), offsetStableX) - 53 / 2);
        if (controller.getWarrior().getPlayerFrame() < 9) // controller.getWarrior().getPlayerFrame()
            offy = (int) (offsetCoors((int) controller.getWarrior().getY(), offsetStableY));
        else offy = (int) (offsetCoors(controller.getWarrior().getY(), offsetStableY) - 20);
        if (controller.getWarrior().getDirection() == -1 || controller.getWarrior().getDirection() == 0) {
//            System.out.println("direction left/stop " + controller.getWarrior().getDirection());
            g.drawImage(playerSprites[controller.getWarrior().getPlayerFrame()], offx, offy, this); //.getPlayerFrame()
        } else {
//            System.out.println("direction right " + controller.getWarrior().getDirection());
            g.drawImage(mirror(playerSprites[controller.getWarrior().getPlayerFrame()]), offx, offy, this);//.getPlayerFrame()
        }

//        g.drawOval((int) (offsetStableX * controller.getWarrior().getX())-3 ,
//                (int) (offsetStableY * controller.getWarrior().getY())-3, 6, 6);
    }

    private void paintStage(Graphics g, int offsetStableX, int offsetStableY) {
        int offx;
        int offy;
        char[][] mapDec = controller.getStage();
        for (int i = 0; i < mapDec.length; i++) {
            for (int j = 0; j < mapDec[0].length; j++) {
                g.setFont(new Font("Arial", Font.BOLD, 12));
//                g.drawString(j + ":" + i,
//                        (int) offsetCoors(j, 1280 / 30),(int) offsetCoors(i, offsetStableY)-20);
//                g.drawString( (int)offsetCoors(j, offsetStableX) + ":" + (int)offsetCoors(i, offsetStableY),
//                        (int) offsetCoors(j, offsetStableX),(int) offsetCoors(i, offsetStableY));
                if (mapDec[i][j] == 'l') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 25;
                    offy = (int) (offsetCoors(i, offsetStableY)) - 30;
                    g.drawImage(horizontalPlatforms1.get(1), offx, offy, 50, 100, this);
                } else if (mapDec[i][j] == 's') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 25;
                    offy = (int) (offsetCoors(i, offsetStableY)) - 30;
                    g.drawImage(horizontalPlatforms1.get(0), offx, offy, 50, 100, this);
                } else if (mapDec[i][j] == 'e') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 25;
                    offy = (int) (offsetCoors(i, offsetStableY)) - 30;
                    g.drawImage(horizontalPlatforms1.get(2), offx, offy, 50, 100, this);
                } else if (mapDec[i][j] == 'v') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 30 - 20;
                    offy = (int) offsetCoors(i, offsetStableY);
                    g.drawImage(platformVertical1, offx, offy, 100, 50, this);
                }
            }
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

    private static double offsetCoors(double arg, int peaceSize) {
        return arg * (peaceSize);
//        return arg * (peaceMargin + peaceSize) + peaceMargin;
    }

}
