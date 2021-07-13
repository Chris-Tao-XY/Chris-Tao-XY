package org.example;


import javafx.scene.control.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class MainUI extends JFrame {
    private final static String START_THE_GAME = "开始游戏";
    private final static String RESTART_THE_GAME = "重新开始";
    private final static String ADMIT_THE_LOSE = "认输";
    private final static String BLACK_TERM = "黑棋准备";
    private final static String WHITE_TERM = "白棋准备";
    private final static String BLACK_WIN = "黑棋胜利";
    private final static String WHITE_WIN = "白棋胜利";
    private Integer Xsize = 720;
    private Integer Ysize = 900;
    private CellState[][] cells;
    private String state = "开始";
    private boolean canPlay = true;

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public Integer getXsize() {
        return Xsize;
    }

    public void setXsize(Integer xsize) {
        Xsize = xsize;
    }

    public Integer getYsize() {
        return Ysize;
    }

    public void setYsize(Integer ysize) {
        Ysize = ysize;
    }

    public CellState[][] getCells() {
        return cells;
    }

    public void setCells(CellState[][] cells) {
        this.cells = cells;
    }

    public MainUI() {
        super();
        this.setTitle("五子棋");
        this.setResizable(false);
        this.setSize(this.Xsize, this.Ysize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initCells();
        MouseAction mouseAction = new MouseAction();
        this.addMouseListener(mouseAction);
    }

    public void initCells() {
        this.cells = new CellState[20][20];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = CellState.NULL;
            }
        }
    }


    public boolean isWin(int i, int j) {
        int count = 0;
        CellState thisState = cells[i][j];
        int startx = i - 4 >= 0 ? i - 4 : 0;
        int endx = i + 4 <= 19 ? i + 4 : 19;
        int starty = j - 4 >= 0 ? j - 4 : 0;
        int endy = j + 4 <= 19 ? j + 4 : 19;
        for (int p = startx; p <= endx; p++) {
            if (Objects.equals(thisState, cells[i][p])) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }
        count = 0;
        for (int p = starty; p <= endy; p++) {
            if (Objects.equals(thisState, cells[p][j])) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }
        count = 0;
        int p = 0;
        int q = 0;
        for (p = startx, q = starty; p <= endx && q <= endy; p++, q++) {
            if (Objects.equals(thisState, cells[p][q])) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }
        count = 0;
        for (p = startx, q = endy; p <= endx && q >= starty; p++, q--) {
            if (Objects.equals(thisState, cells[p][q])) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        BufferedImage buf = new BufferedImage(730, 900, BufferedImage.TYPE_INT_RGB);
        Graphics graphics1 = buf.createGraphics();
        graphics1.setColor(Color.WHITE);
        graphics1.fillRect(0, 0, 730, 900);
        graphics1.setColor(new Color(50, 50, 50));
        for (int i = 10; i <= 710; i += 35) {
            graphics1.drawLine(i, 50, i, 750);
        }
        for (int i = 50; i <= 750; i += 35) {
            graphics1.drawLine(10, i, 710, i);
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (Objects.equals(cells[i][j], CellState.BLACK)) {
                    graphics1.setColor(new Color(50, 50, 50));
                    graphics1.fillOval((j + 1) * 35 + 10 - 12, (i + 1) * 35 + 50 - 12, 24, 24);
                } else if (Objects.equals(cells[i][j], CellState.WHITE)) {
                    graphics1.setColor(new Color(250, 250, 0));
                    graphics1.fillOval((j + 1) * 35 + 10 - 12, (i + 1) * 35 + 50 - 12, 24, 24);
                }
            }
        }
        graphics1.setColor(new Color(2, 2, 2));
        graphics1.setFont(new Font("黑体", Font.BOLD, 20));
        graphics1.drawRect(10, 750 + 35, 100, 36);
        graphics1.drawString(START_THE_GAME, 10 + 3, 770 + 35 + 3);
        graphics1.drawRect(10 + 100 + 10, 750 + 35, 100, 36);
        graphics1.drawString(RESTART_THE_GAME, 10 + 100 + 10 + 3, 770 + 35 + 3);
        graphics1.drawRect(10 + 200 + 20, 750 + 35, 100, 36);
        graphics1.drawString(ADMIT_THE_LOSE, 10 + 200 + 20 + 3, 770 + 35 + 3);
        graphics1.drawRect(10 + 300 + 30, 750 + 35, 100, 36);
        graphics1.drawString(state, 10 + 300 + 30 + 3, 770 + 35 + 3);
        graphics.drawImage(buf, 0, 0, this);
    }


    class MouseAction implements MouseListener {
        private NextPlayer nextPlayer = NextPlayer.BLACK;
        private Integer x;
        private Integer y;
        private Graphics graphics;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public Graphics getGraphics() {
            return graphics;
        }

        public NextPlayer getNextPlayer() {
            return nextPlayer;
        }

        public void setNextPlayer(NextPlayer nextPlayer) {
            this.nextPlayer = nextPlayer;
        }

        public void setGraphics(Graphics graphics) {
            this.graphics = graphics;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (canPlay) {
                x = e.getX();
                y = e.getY();
                int rx = 0;
                int ry = 0;
                if (x < 710-17.5 && x > 10 + 35 && y > 50 + 35 && y < 750-17.5) {
                    if ((x - 10) % 35 <= 17.5) {
                        rx = (x - 10) / 35 - 1;
                    } else rx = (x - 10) / 35;
                    if ((y - 50) % 35 <= 17.5) {
                        ry = (y - 50) / 35 - 1;
                    } else ry = (y - 50) / 35;
                }
                if (Objects.equals(cells[ry][rx], CellState.NULL)) {
                    if (Objects.equals(NextPlayer.BLACK, nextPlayer)) {
                        cells[ry][rx] = CellState.BLACK;
                        nextPlayer = NextPlayer.WHITE;
                        state = WHITE_TERM;
                    } else {
                        cells[ry][rx] = CellState.WHITE;
                        nextPlayer = NextPlayer.BLACK;
                        state = BLACK_TERM;
                    }
                    MainUI.this.repaint();
                    if (isWin(ry, rx)) {
                        canPlay = false;
                        if (Objects.equals(cells[ry][rx], CellState.BLACK)) {
                            state = BLACK_WIN;
                        } else state = WHITE_WIN;
                        MainUI.this.repaint();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}