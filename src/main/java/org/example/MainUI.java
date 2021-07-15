package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Stack;


public class MainUI extends JFrame {
    private final static String START_THE_GAME = "开始游戏";
    private final static String RESTART_THE_GAME = "重新开始";
    private final static String ADMIT_THE_LOSE = "认输";
    private final static String BLACK_TERM = "黑棋准备";
    private final static String WHITE_TERM = "白棋准备";
    private final static String BLACK_WIN = "黑棋胜利";
    private final static String WHITE_WIN = "白棋胜利";
    private final static String REGRET_ONE_TERM = "悔棋";
    private final static String PLAY_WITH_AI = "和AI对战";
    private boolean AIPlay = false;
    private final Integer Xsize = 720;
    private final Integer Ysize = 900;
    private CellState[][] cells;
    private String state = "双方准备";
    private boolean winned = false;
    private final Stack<Integer> XStack = new Stack<>();
    private final Stack<Integer> YStack = new Stack<>();
    private boolean canPlay = false;


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
        this.cells = new CellState[19][19];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = CellState.NULL;
            }
        }
    }


    public boolean isWin(int i, int j) {
        int count = 0;
        CellState thisState = cells[i][j];
        int startx = Math.max(i - 4, 0);
        int endx = Math.min(i + 4, 18);
        int starty = Math.max(j - 4, 0);
        int endy = Math.min(j + 4, 18);
        for (int p = startx; p <= endx; p++) {
            if (Objects.equals(thisState, cells[p][j])) {
                count++;
                if (count == 5) {
                    return winned = true;
                }
            } else {
                count = 0;
            }
        }
        count = 0;
        for (int p = starty; p <= endy; p++) {
            if (Objects.equals(thisState, cells[i][p])) {
                count++;
                if (count == 5) {
                    return winned = true;
                }
            } else {
                count = 0;
            }
        }
        count = 0;
        int p;
        int q;
        for (p = startx, q = starty; p <= endx && q <= endy; p++, q++) {
            if (Objects.equals(thisState, cells[p][q])) {
                count++;
                if (count == 5) return winned = true;
            } else {
                count = 0;
            }
        }
        count = 0;
        for (p = startx, q = endy; p <= endx && q >= starty; p++, q--) {
            if (Objects.equals(thisState, cells[p][q])) {
                count++;
                if (count == 5) return winned = true;
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
        graphics1.drawString(state, 10 + 300 + 30 + 3, 770 + 35 + 3);
        graphics1.drawRect(10 + 400 + 40, 750 + 35, 100, 36);
        graphics1.drawString(REGRET_ONE_TERM, 10 + 400 + 40 + 3, 770 + 35 + 3);
        graphics1.drawRect(10 + 500 + 50, 750 + 35, 100, 36);
        graphics1.drawString(PLAY_WITH_AI, 10 + 500 + 50 + 3, 770 + 35 + 3);
        graphics.drawImage(buf, 0, 0, this);
    }


    class MouseAction implements MouseListener {
        private NextPlayer nextPlayer = NextPlayer.BLACK;
        private Integer x;
        private Integer y;
        private AI ai;

        @Override
        public void mouseClicked(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if (x < 710 - 17.5 && x > 10 + 35 && y > 50 + 35 && y < 750 - 17.5) {
                if (canPlay) {
                    int rx;
                    int ry;
                    if ((x - 10) % 35 <= 17.5) {
                        rx = (x - 10) / 35 - 1;
                    } else rx = (x - 10) / 35;
                    if ((y - 50) % 35 <= 17.5) {
                        ry = (y - 50) / 35 - 1;
                    } else ry = (y - 50) / 35;
                    if (Objects.equals(cells[ry][rx], CellState.NULL)) {
                        if (Objects.equals(NextPlayer.BLACK, nextPlayer)) {
                            cells[ry][rx] = CellState.BLACK;
                            XStack.push(ry);
                            YStack.push(rx);
                        } else {
                            cells[ry][rx] = CellState.WHITE;
                            XStack.push(ry);
                            YStack.push(rx);
                        }
                        MainUI.this.repaint();
                        if (isWin(ry, rx)) {
                            canPlay = false;
                            if (Objects.equals(cells[ry][rx], CellState.BLACK)) {
                                state = BLACK_WIN;
                            } else state = WHITE_WIN;
                        } else {
                            if (!AIPlay) {
                                if (Objects.equals(cells[ry][rx], CellState.BLACK)) {
                                    nextPlayer = NextPlayer.WHITE;
                                    state = WHITE_TERM;
                                } else {
                                    nextPlayer = NextPlayer.BLACK;
                                    state = BLACK_TERM;
                                }
                            }else {
                                ai.search();
                            }
                        }
                        MainUI.this.repaint();
                    }
                }

            } else if (x > 10 + 400 + 40 && x < 10 + 500 + 40 && y > 750 + 35 && y < 750 + 35 + 36) {
                if (canPlay && !XStack.isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you confirm to regret?", "Confirm", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (confirm == 0) {
                        int x = XStack.pop();
                        int y = YStack.pop();
                        CellState cellState = cells[x][y];
                        if (Objects.equals(cellState, CellState.BLACK)) {
                            nextPlayer = NextPlayer.BLACK;
                            state = BLACK_TERM;
                        } else {
                            nextPlayer = NextPlayer.WHITE;
                            state = WHITE_TERM;
                        }
                        cells[x][y] = CellState.NULL;
                        MainUI.this.repaint();
                    }
                }

            } else if (x > 10 && x < 10 + 100 && y > 750 + 35 && y < 750 + 35 + 36) {
                if (!winned && !canPlay) {
                    canPlay = true;
                    state = BLACK_TERM;
                    MainUI.this.repaint();
                }

            } else if (x > 10 + 100 + 10 && x < 10 + 200 + 10 && y > 750 + 35 && y < 750 + 35 + 36) {
                int confirm = JOptionPane.showConfirmDialog(null, "Do you confirm to restart?", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                    for (int i = 0; i < cells.length; i++) {
                        for (int j = 0; j < cells[i].length; j++) {
                            cells[i][j] = CellState.NULL;
                        }
                    }
                    canPlay = false;
                    winned = false;
                    nextPlayer = NextPlayer.BLACK;
                    state = "双方准备";
                    MainUI.this.repaint();
                }
            } else if (x > 10 + 500 + 50 && x < 10 + 600 + 50 && y > 750 + 35 && y < 750 + 35 + 36) {
                int confirm = JOptionPane.showConfirmDialog(null, "Do you play with AI?", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirm==0){
                    AIPlay = true;
                    ai = new AI();
                    ai.setAIColor(CellState.WHITE);
                    ai.setManColor(CellState.BLACK);
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


    class AI {
        private final int[][] score = new int[19][19];
        private CellState AIColor;
        private CellState manColor;

        public CellState getAIColor() {
            return AIColor;
        }

        public void setAIColor(CellState AIColor) {
            this.AIColor = AIColor;
        }

        public CellState getManColor() {
            return manColor;
        }

        public void setManColor(CellState manColor) {
            this.manColor = manColor;
        }

        public int getScore(int AIChessNum, int manChessNum) {
            if (AIChessNum > 0 && manChessNum > 0) {
                return 0;
            }
            if (manChessNum == 0 && AIChessNum == 0) {
                return 7;
            }
            if (AIChessNum == 1) {
                return 35;
            }
            if (AIChessNum == 2) {
                return 800;
            }
            if (AIChessNum == 3) {
                return 15000;
            }
            if (AIChessNum == 4) {
                return 800000;
            }
            if (manChessNum == 1) {
                return 15;
            }
            if (manChessNum == 2) {
                return 400;
            }
            if (manChessNum == 3) {
                return 1800;
            }
            if (manChessNum == 4) {
                return 100000;
            }
            return -1;
        }

        public void search() {
            int xMax = -1;
            int yMAX = -1;
            int maxScore = 0;
            int AIChessNum = 0;
            int manChessNum = 0;
            int tempScore;
            for (int i = 0; i < score.length; i++) {
                for (int j = 0; j < score[i].length; j++) {
                    score[i][j] = 0;
                }
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 15; j++) {
                    for (int k = j; k < j + 5; k++) {
                        if (Objects.equals(cells[i][k], AIColor)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[i][k], manColor)) {
                            manChessNum++;
                        }
                    }
                    tempScore = getScore(AIChessNum, manChessNum);
                    for (int k = j; k < j + 5; k++) {
                        score[i][k] += tempScore;
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 15; j++) {
                    for (int k = j; k < j + 5; k++) {
                        if (Objects.equals(cells[k][i], AIColor)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[k][i], manColor)) {
                            manChessNum++;
                        }
                    }
                    tempScore = getScore(AIChessNum, manChessNum);
                    for (int k = j; k < j + 5; k++) {
                        score[k][i] += tempScore;
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }
            }
            for (int i = 18; i >= 4; i--) {
                for (int k = i, j = 0; j < 19 && k >= 0; j++, k--) {
                    int m = k;
                    int n = j;
                    for (; m > k - 5 && k - 5 > 0; m--, n++) {
                        if (Objects.equals(cells[m][n], AIColor)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[m][n], manColor)) {
                            manChessNum++;
                        }
                    }
                    if (m == k - 5) {
                        tempScore = getScore(AIChessNum, manChessNum);
                        for (m = k, n = j; m > k - 5; m--, n++) {
                            score[m][n] += tempScore;
                        }
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }

            }
            for (int i = 1; i < 19; i++) {
                for (int k = i, j = 18; j >= 0 && k < 15; j--, k++) {
                    int m = k;
                    int n = j;
                    for (; m < k + 5 && k + 5 <= 19; m++, n--) {
                        if (Objects.equals(cells[n][m], AIChessNum)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[n][m], manColor)) {
                            manChessNum++;
                        }
                    }
                    if (m == k + 5) {
                        tempScore = getScore(AIChessNum, manChessNum);
                        for (m = k, n = j; m < k + 5; m++, n--) {
                            score[n][m] += tempScore;
                        }
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }
            }
            for (int i = 0; i < 15; i++) {
                for (int k = i, j = 0; j < 15 && k < 15; j++, k++) {
                    int m = k;
                    int n = j;
                    for (; m < k + 5 && k + 5 <= 19; m++, n++) {
                        if (Objects.equals(cells[n][m], AIChessNum)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[n][m], manColor)) {
                            manChessNum++;
                        }
                    }
                    if (m == k + 5) {
                        tempScore = getScore(AIChessNum, manChessNum);
                        for (m = k, n = j; m < k + 5; m++, n++) {
                            score[m][n] += tempScore;
                        }
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }
            }
            for (int i = 1; i < 15; i++) {
                for (int k = i, j = 0; j < 19 && k < 19; j++, k++) {
                    int m = k;
                    int n = j;
                    for (; m < k + 5 && k + 5 <= 19; m++, n++) {
                        if (Objects.equals(cells[n][m], AIColor)) {
                            AIChessNum++;
                        } else if (Objects.equals(cells[n][m], manColor)) {
                            manChessNum++;
                        }
                    }
                    if (m == k + 5) {
                        tempScore = getScore(AIChessNum, manChessNum);
                        for (m = k, n = j; m < k + 5; m++, n++) {
                            score[n][m] += tempScore;
                        }
                    }
                    manChessNum = 0;
                    AIChessNum = 0;
                }
            }
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (Objects.equals(cells[i][j], CellState.NULL) && score[i][j] > maxScore) {
                        xMax = i;
                        yMAX = j;
                        maxScore = score[i][j];
                    }
                }
            }
            if (xMax != -1 && yMAX != -1) {
                cells[xMax][yMAX] = AIColor;
                if (isWin(xMax, yMAX)) {
                    canPlay = false;
                    state = WHITE_WIN;
                    MainUI.this.repaint();
                }

            }
            MainUI.this.repaint();
        }
    }
}