package TICTACTOE;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel restartPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton("Restart");
    boolean pemain1;
    String playerX;
    String playerO;

    public TicTacToe() {
        setPlayerX(JOptionPane.showInputDialog(frame, "Masukkan nama pemain X:"));
        setPlayerO(JOptionPane.showInputDialog(frame, "Masukkan nama pemain O:"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("Ink Free", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(10, 10, 700, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        restartButton.setFont(new Font("Ink Free", Font.BOLD, 40));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        restartPanel.setLayout(new BorderLayout());
        restartPanel.setBackground(new Color(50, 50, 50));
        restartPanel.add(restartButton, BorderLayout.CENTER);

        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(restartPanel, BorderLayout.SOUTH);

        giliran();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText().equals("")) {
                    if (pemain1) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        pemain1 = false;
                        textField.setText("Giliran " + getPlayerO());
                    } else {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        pemain1 = true;
                        textField.setText("Giliran " + getPlayerX());
                    }
                    check();
                }
            }
        }
    }

    public void giliran() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            pemain1 = true;
            textField.setText("Giliran " + getPlayerX());
        } else {
            pemain1 = false;
            textField.setText("Giliran " + getPlayerO());
        }
    }

    public void check() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        for (int i = 0; i < 3; i++) {
            if (checkLine(board[i][0], board[i][1], board[i][2])) return;
            if (checkLine(board[0][i], board[1][i], board[2][i])) return;
        }

        if (checkLine(board[0][0], board[1][1], board[2][2])) return;
        if (checkLine(board[0][2], board[1][1], board[2][0])) return;

        if (checkDraw()) {
            textField.setText("Imbang bzirr");
            disableButtons();
        }
    }

    private boolean checkLine(String a, String b, String c) {
        if (a.equals(b) && b.equals(c) && !a.equals("")) {
            if (a.equals("X")) {
                xWins();
            } else {
                oWins();
            }
            return true;
        }
        return false;
    }

    private boolean checkDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    public void xWins() {
        textField.setText(getPlayerX() + " Menang!!!");
        disableButtons();
    }

    public void oWins() {
        textField.setText(getPlayerO() + " Menang!!!");
        disableButtons();
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        giliran();
    }

    public String getPlayerX() {
        return playerX;
    }

    public void setPlayerX(String playerX) {
        this.playerX = playerX;
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
