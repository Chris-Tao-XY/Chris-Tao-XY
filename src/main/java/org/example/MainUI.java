package org.example;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class MainUI extends JFrame {
    private Integer Xsize = 720;
    private Integer Ysize = 900;
    private CellState[][] cells;

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
        this.repaint();
    }

    public void initCells() {
        this.cells = new CellState[20][20];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = CellState.NULL;
            }
        }
        cells[0][0] = CellState.BLACK;
    }


    public void paint(Graphics graphics) {
        graphics.setColor(new Color(50, 50, 50));
        for (int i = 10; i <= 710; i += 35) {
            graphics.drawLine(i, 50, i, 750);
        }
        for (int i = 50; i <= 750; i += 35){
            graphics.drawLine(10, i, 710, i);
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (Objects.equals(cells[i][j], CellState.BLACK)) {
                    graphics.setColor(new Color(50, 50, 50));
                    graphics.fillOval((j + 1) * 35 + 10 - 12, (i + 1) * 35 + 50 - 12, 24, 24);
                } else if (Objects.equals(cells[i][j], CellState.WHITE)) {
                    graphics.setColor(new Color(250, 250, 0));
                    graphics.fillOval((j + 1) * 35 + 10 - 12, (i + 1) * 35 + 50 - 12, 24, 24);
                }
            }
        }

    }


}