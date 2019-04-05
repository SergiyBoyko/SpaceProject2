package com.company;

import com.company.controller.Controller;
import com.company.model.BarrierSystem;
import com.company.model.Enemy;
import com.company.model.GorillaEnemy;
import com.company.model.Missile;
import com.company.model.Weapon;
import com.company.model.XenomorphEnemy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class View extends JPanel {
    private static final int WIDTH_SCREEN_SIZE = 1280;
    private static final int HEIGHT_SCREEN_SIZE = 720;

    private List<BufferedImage> background;
    private List<BufferedImage> horizontalPlatforms1;
    private List<BufferedImage> verticalPlatforms1;

    private Image greenBlood;
    private Image playerAim;
    private BufferedImage healthBar;

    private List<BufferedImage> plasmaBarrier;
    private List<BufferedImage> generator;

    private BufferedImage[] mainPlayerSprites;
    private BufferedImage[] rpgPlayerSprites;
    private BufferedImage[] missilesSprites;
    private BufferedImage[] dAlienSprites;
    private BufferedImage[] gAlienSprites;

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadSources() throws IOException, InterruptedException {
//        JOptionPane.showMessageDialog(null, "background");
//        Thread.sleep(1000);
        background = new ArrayList<BufferedImage>();
        background.add(ImageIO.read(new File("sources/images/backgrounds/9.jpg")));
        background.add(ImageIO.read(new File("sources/images/backgrounds/10.jpg")));

//        JOptionPane.showMessageDialog(null, "horizontalPlatforms1");
//        Thread.sleep(1000);
        horizontalPlatforms1 = new ArrayList<BufferedImage>();
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/s_floor_platform.png")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/m_floor_platform.png")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/e_floor_platform.png")));

        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/s_stone_floor_platform.gif")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/m_stone_floor_platform.png")));
        horizontalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/e_stone_floor_platform.png")));

//        JOptionPane.showMessageDialog(null, "verticalPlatforms1");
//        Thread.sleep(1000);
        verticalPlatforms1 = new ArrayList<BufferedImage>();
        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/s_wall_platform.png")));
        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/e_wall_platform.png")));
        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/ladder_platform.png")));
        verticalPlatforms1.add(mirror(ImageIO.read(new File("sources/images/decoration/ladder_platform.png"))));

        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/stone_wall_platform.png")));
        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/stone_wall_platform.png")));
        verticalPlatforms1.add(ImageIO.read(new File("sources/images/decoration/ladder_stone_platform.png")));
        verticalPlatforms1.add(mirror(ImageIO.read(new File("sources/images/decoration/ladder_stone_platform.png"))));

//        JOptionPane.showMessageDialog(null, "generator");
//        Thread.sleep(1000);
        generator = new ArrayList<>();
        generator.add(ImageIO.read(new File("sources/images/control/generator_b.png")));
        generator.add(ImageIO.read(new File("sources/images/control/generator_unb.png")));

//        JOptionPane.showMessageDialog(null, "plasmaBarrier");
//        Thread.sleep(1000);
        plasmaBarrier = new ArrayList<>();
        plasmaBarrier.add(ImageIO.read(new File("sources/images/control/bar_unl.png")));
        plasmaBarrier.add(ImageIO.read(new File("sources/images/control/bar_l.png")));

//        JOptionPane.showMessageDialog(null, "greenBlood & playerAim");
//        Thread.sleep(1000);
        greenBlood = ImageIO.read(new File("sources/images/effects/blood_g.png"));
        playerAim = ImageIO.read(new File("sources/images/aim/aim.png"));
        healthBar = ImageIO.read(new File("sources/images/interface/bar_health.png"));

//        int rows;
//        int cols;
//        int width;
//        int height;
//        BufferedImage playerSheet = ImageIO.read(new File("sources/images/player/sheet_doom.gif"));
//        int rows = 6;
//        int cols = 9;
//        int width = 477 / 9;
//        int height = 432 / 6;
//        playerSprites = split(rows, cols, width, height, playerSheet);

//        BufferedImage playerSheet = ImageIO.read(new File("sources/images/player/newHalo/sheet_halo.png"));
//        JOptionPane.showMessageDialog(null, "mainPlayerSprites");
//        Thread.sleep(1000);
        BufferedImage[] sprites = new BufferedImage[24];
        for (int i = 0; i < 24; i++) {
            String f = String.format("sources/images/player/mainHalo/%d.png", i);
            sprites[i] = ImageIO.read(new File(f));
        }
        mainPlayerSprites = sprites;

//        JOptionPane.showMessageDialog(null, "rpgPlayerSprites");
//        Thread.sleep(1000);
        sprites = new BufferedImage[24];
        for (int i = 0; i < 24; i++) {
            String f = String.format("sources/images/player/rpgHalo/%drpg.png", i);
            sprites[i] = ImageIO.read(new File(f));
        }
        rpgPlayerSprites = sprites;

//        sprites = new BufferedImage[29];
//        for (int i = 0; i < 29; i++) {
//            String f = String.format("sources/images/missiles/%d.png", i);
//            sprites[i] = ImageIO.read(new File(f));
//        }
//        missilesSprites = sprites;
//        JOptionPane.showMessageDialog(null, "missilesSprites");
//        Thread.sleep(1000);
        sprites = new BufferedImage[29];
        for (int i = 0; i < 13; i++) {
            String f = String.format("sources/images/missiles/%d.png", i);
            sprites[i] = ImageIO.read(new File(f));
        }
        BufferedImage[] temp2 = split(4, 4, 64, 64, ImageIO.read(new File("sources/images/missiles/explosion.png")));
        System.arraycopy(temp2, 0, sprites, 13, temp2.length);
        missilesSprites = sprites;

//        JOptionPane.showMessageDialog(null, "dAlienSprites");
//        Thread.sleep(1000);
        sprites = new BufferedImage[18];
        for (int i = 0; i < 18; i++) {
            String f = String.format("sources/images/enemy/dolphin_alien/%d.png", i);
            sprites[i] = ImageIO.read(new File(f));
        }
        dAlienSprites = sprites;

//        JOptionPane.showMessageDialog(null, "gAlienSprites");
//        Thread.sleep(1000);
        sprites = new BufferedImage[17];
        for (int i = 0; i < 17; i++) {
            String f = String.format("sources/images/enemy/gorilla_alien/%d.png", i);
            sprites[i] = ImageIO.read(new File(f));
        }
        gAlienSprites = sprites;

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

    private int edge;
//    private int tempFocus;

    private int getXFocus(int offsetStableX) {
        double x = controller.getWarrior().getX();
        int dir = (int) controller.getWarrior().getDirection();
        int offx = (int) offsetCoors(x, offsetStableX);
        int xFocus = 0;
//        if (x >= controller.getField().getWidth() * 0.25 && x <= controller.getField().getWidth() * 0.825
//                && offx >= this.getWidth() * 0.25 && offx <= this.getWidth() * 0.75) {
//            xFocus = tempFocus;
//        } else
//        if (offx < this.getWidth() * 0.25 && x > controller.getField().getWidth() * 0.14) {
//            xFocus = (int) (-offx + this.getWidth() * 0.25);
//            tempFocus = xFocus;
//        } else
        if (offx > this.getWidth() * 0.5 && x < controller.getField().getWidth()) {//* 0.58
            xFocus = (int) (-offx + this.getWidth() * 0.5);
            edge = xFocus;
//            tempFocus = xFocus;
        }
//            else if (offx > this.getWidth() * 0.5 && x > controller.getField().getWidth() * 0.65)
//            xFocus = edge;
//        else xFocus = tempFocus;
        return xFocus;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        int offsetStableX = 1280 / 30;
        int offsetStableX = 50;
        int offsetStableY = 650 / 15;
        int xFocus = getXFocus(offsetStableX);
//        System.out.println("paint " + new Date());
        Color BG_COLOR = new Color(0xbbada0);
        g.setColor(BG_COLOR);

        g.drawImage(background.get(controller.getLevel()).getSubimage(0, 30, 1280, 720)
                , 0, 0, this);

//        int offx;
//        int offy;
        paintStage(g, offsetStableX, offsetStableY, xFocus);
        paintBGE(g, offsetStableX, offsetStableY, xFocus);
        paintEnemies(g, offsetStableX, offsetStableY, xFocus);
        paintPlayer(g, offsetStableX, offsetStableY, xFocus);
        paintEffects(g, offsetStableX, offsetStableY, xFocus);


        if (controller.isLevelComplete()) {
            g.setColor(new Color(0xfff6bc));
            g.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 100));
            g.drawString("Demo Level Complete", getWidth() / 2 - 400, getHeight() / 2);
        }

        if (controller.isGameOver()) {
            g.setColor(new Color(0xfff6bc));
            g.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 100));
            g.drawString("GAME OVER", getWidth() / 2 - 300, getHeight() / 2);
        }

//        System.out.println( offsetCoors(controller.getWarrior().getX(), offsetStableX) / 42
//                + " " + ((1280 / 256) * controller.getWarrior().getX()) / 100
//                + " " + controller.getWarrior().getY());

//        System.out.println(controller.getPlayerFrame() + " of " + sprites.length);
/**
 * very important lines
 */
//        for (int i = 0; i < controller.getField().getWidth() + 1; i++) {//*25
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

    private void paintEnemies(Graphics g, int offsetStableX, int offsetStableY, int xFocus) {
        int offx = 0;
        int offy = 0;
        List<Enemy> enemies = new ArrayList<>(controller.getEnemies());
        for (Enemy enemy : enemies) {
            BufferedImage frame;
            if (enemy instanceof XenomorphEnemy) {
                frame = dAlienSprites[enemy.getEnemyFrame()];
                offx = (int) (offsetCoors(enemy.getX(), offsetStableX)
                        - dAlienSprites[enemy.getEnemyFrame()].getWidth() / 2) + xFocus;
                if (frame.getHeight() > 80) offy = (int) (offsetCoors((int) enemy.getY(), offsetStableY)) - 86 / 2;
                else offy = (int) (offsetCoors((int) enemy.getY(), offsetStableY));
                if (enemy.getDirection() == 1) {
                    g.drawImage(frame, offx, offy, this);//direction left/stop
                } else {
                    g.drawImage(mirror(frame), offx, offy, this); //direction right
                }
            } else if (enemy instanceof GorillaEnemy) {
                frame = gAlienSprites[enemy.getEnemyFrame()];
                offx = (int) (offsetCoors(enemy.getX(), offsetStableX)
                        - gAlienSprites[enemy.getEnemyFrame()].getWidth() / 2) + xFocus;
                if (frame.getHeight() > 80) offy = (int) (offsetCoors((int) enemy.getY(), offsetStableY)) - 86 / 2;
                else offy = (int) (offsetCoors((int) enemy.getY(), offsetStableY));
                if (enemy.getDirection() == 1) {
                    g.drawImage(frame, offx, offy, this);//direction left/stop
                } else {
                    g.drawImage(mirror(frame), offx, offy, this); //direction right
                }
            }
            if (enemy.isAlive()) {
                Color c = g.getColor();
                // health bar
                g.setColor(Color.red);
                g.drawLine(offx, offy - 15,
                        (int) (offx + enemy.getHealth()
                                / enemy.getMaxHealth() * 100), offy - 15);
                g.setColor(c);
            }

//            g.drawOval((int) (offsetStableX * enemy.getX()) - 3,
//                    (int) (offsetStableY * enemy.getY()) - 3, 6, 6);
        }
    }

    private void paintBGE(Graphics g, int offsetStableX, int offsetStableY, int xFocus) {
        int offx;
        int offy;

        List<BarrierSystem> barrierSystems = new ArrayList<>(controller.getBarrierSystems());
        for (BarrierSystem bs : barrierSystems) {
            int locked = bs.isAlive() ? 1 : 0;
            offx = (int) (offsetCoors(bs.getX(), offsetStableX) - 50 / 2) + xFocus;
            offy = (int) (offsetCoors(bs.getY(), offsetStableY) - 50 / 2);
            g.drawImage(generator.get(locked), offx, offy, 50, 50, this);
            List<BarrierSystem.Barrier> barriers = bs.getBarriers();
            for (BarrierSystem.Barrier barrier : barriers) {
                offx = (int) (offsetCoors(barrier.getX(), offsetStableX) - 50 / 2) + xFocus;
                offy = (int) (offsetCoors(barrier.getY(), offsetStableY));
                g.drawImage(plasmaBarrier.get(locked), offx, offy, 50, 50, this);
            }
        }
    }

    private void paintEffects(Graphics g, int offsetStableX, int offsetStableY, int xFocus) {
        int offx;
        int offy;

        ArrayList<Missile> missiles = new ArrayList<Missile>(controller.getMissiles());
        for (Missile missile : missiles) {
            BufferedImage image = missilesSprites[missile.getMissileFrame()];
            int size = missile.getMissileFrame() < 13 ? 4 : 2;
            offx = (int) (offsetCoors(missile.getX(), offsetStableX) - image.getWidth() / size) + xFocus;
            offy = (int) (offsetCoors(missile.getY(), offsetStableY) - image.getHeight() / size);
            if (missile.getDirection() > 0)
                g.drawImage(image, offx, offy, image.getWidth() / (size / 2), image.getHeight() / (size / 2), this);
            else
                g.drawImage(mirror(image), offx, offy, image.getWidth() / (size / 2), image.getHeight() / (size / 2), this);
        }

        Map<Double, Double> obj = new HashMap<Double, Double>(controller.getBloodEffects());
//        System.out.println("size b=" + obj.size());
        for (Map.Entry<Double, Double> coors : obj.entrySet()) {
            offx = (int) (offsetCoors(coors.getKey(), offsetStableX) - 144 / 6) + xFocus; //144 - img width
            offy = (int) (offsetCoors(coors.getValue(), offsetStableY) - 144 / 6);
            g.drawImage(greenBlood, offx, offy, 144 / 3, 144 / 3, this);
        }
    }

    private void paintPlayer(Graphics g, int offsetStableX, int offsetStableY, int xFocus) {
        int offx;
        int offy;
        BufferedImage image;
        if (controller.getWarrior().getWeapon() == Weapon.PISTOL)
            image = mainPlayerSprites[controller.getWarrior().getPlayerFrame()];
        else image = rpgPlayerSprites[controller.getWarrior().getPlayerFrame()];
        offx = (int) (offsetCoors(controller.getWarrior().getX(), offsetStableX) - image.getWidth() / 2) + xFocus;
        offy = (int) (offsetCoors(controller.getWarrior().getY(), offsetStableY) - 5);
        if (controller.getWarrior().isCrouched()) offy += 15;
        if (image.getHeight() <= 40) offy += 60 - image.getHeight();
        if (controller.getWarrior().getDirection() == 1) {
            g.drawImage(image, offx, offy, this);
        } else {
//            System.out.println("direction right " + controller.getWarrior().getDirection());
            g.drawImage(mirror(image), offx, offy, this);
        }

        if (controller.getWarrior().isAlive()) {
            Color c = g.getColor();
            // health bar
            g.drawImage(healthBar, 5, 10, (int) (healthBar.getWidth() / 1.5), healthBar.getHeight() / 2, this);
            if (controller.getWarrior().getHealth() < 0.4 * controller.getWarrior().getMaxHealth())
                g.setColor(Color.red);
            else if (controller.getWarrior().getHealth() < 0.7 * controller.getWarrior().getMaxHealth())
                g.setColor(Color.yellow);
            else g.setColor(Color.green);
            g.fillRoundRect(15, 48, (int) (controller.getWarrior().getHealth()
                    / controller.getWarrior().getMaxHealth() * 192), 12, 5, 5);
            g.drawLine(15, 45, (int) (controller.getWarrior().getReloadingProgress() * 130) + 15, 45);
            g.drawLine(offx, offy - 15,
                    (int) (offx + controller.getWarrior().getHealth()
                            / controller.getWarrior().getMaxHealth() * 40), offy - 15);
            // aim
            g.setColor(new Color(0xA9A9A9));
            g.setFont(new Font("Stencil", Font.PLAIN, 20));
            g.drawString(controller.getWarrior().getWeapon().name(), 25, 40);
            g.drawString(String.valueOf(controller.getWarrior().getAmmo()), 100, 40);
            int tx = (int) ((int) offx + 20
                    + controller.getWarrior().getDirectedShotRange() * offsetStableX - 3);
            int ty = offy + 5 + 3;
            g.drawImage(playerAim, tx - 5, ty - 5, 10, 10, this);
//            g.drawOval(tx, ty - 3, 6, 6);
//            g.drawLine((int) offx + 20, ty, tx, ty);
            g.setColor(c);
        }

//        g.drawOval((int) (offsetStableX * controller.getWarrior().getX()) - 3,
//                (int) (offsetStableY * controller.getWarrior().getY()) - 3, 6, 6);
    }

    private void paintStage(Graphics g, int offsetStableX, int offsetStableY, int xFocus) {
        int offx;
        int offy;
        int k = controller.getLevel();
        char[][] mapDec = controller.getStage();
        for (int i = 0; i < mapDec.length; i++) {
            for (int j = 0; j < mapDec[0].length; j++) {
//                g.setFont(new Font("Arial", Font.BOLD, 12));
//                g.drawString(j + ":" + i,
//                        (int) offsetCoors(j, 1280 / 30),(int) offsetCoors(i, offsetStableY)-20);
//                g.drawString( (int)offsetCoors(j, offsetStableX) + ":" + (int)offsetCoors(i, offsetStableY),
//                        (int) offsetCoors(j, offsetStableX),(int) offsetCoors(i, offsetStableY));
                char el = mapDec[i][j];
                if (el == 'l' || el == 's' || el == 'e') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 25 + xFocus;
                    offy = (int) (offsetCoors(i, offsetStableY)) - 30;
                    Image image = el == 'l' ? horizontalPlatforms1.get(1 + (k * 3))
                            : el == 's' ? horizontalPlatforms1.get((k * 3))
                            : horizontalPlatforms1.get(2 + (k * 3));
                    g.drawImage(image, offx, offy, 50, 100, this);
                } else if (el == 'v' || el == 'd') {
//                    offx = (int) (offsetCoors(j, (offsetStableX))) - 30 - 20;
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 30 - 20 + xFocus;
                    offy = (int) offsetCoors(i, offsetStableY);
                    g.drawImage(verticalPlatforms1.get(1 + (k * 4)), offx, offy, 100, 50, this);
                    if (el == 'v') g.drawImage(verticalPlatforms1.get((k * 4)), offx, offy, 100, 50, this);

                } else if (mapDec[i][j] == 'm') {
                    offx = (int) (offsetCoors(j, (offsetStableX))) - 80 / 2 + xFocus;
                    offy = (int) offsetCoors(i, offsetStableY) - 15;
//                    g.drawImage(verticalPlatforms1.get(0), offx, offy, 100, 50, this);
                    if (controller.isLadder())
                        g.drawImage(verticalPlatforms1.get(2 + (k * 4)), offx, offy, 80, 75, this);
                    else g.drawImage(verticalPlatforms1.get(3 + (k * 4)), offx, offy, 80, 75, this);
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
