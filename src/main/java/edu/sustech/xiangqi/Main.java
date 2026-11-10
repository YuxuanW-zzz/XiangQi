package edu.sustech.xiangqi;

import edu.sustech.xiangqi.controller.ChessController;
import edu.sustech.xiangqi.model.Board;
import edu.sustech.xiangqi.view.ChessFrame;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.SQLOutput;

// 程序入口
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame loginFrame = new JFrame("登录或注册");
                JFrame passwordFrame = new JFrame("用户名与密码");
                loginFrame.setLayout(null);
                loginFrame.setSize(800, 400);

                JButton login = new JButton("Login");
                login.setBounds(300, 300, 80, 30);
                login.addActionListener(e -> {
                    Board board = new Board();
                    ChessFrame frame = new ChessFrame(board);
                    ChessController controller = new ChessController(board, frame.getBoardPanel());
                    frame.getBoardPanel().addMouseListener(controller);
                    frame.setVisible(true);
                });
                loginFrame.add(login);

                JLabel uname = new JLabel("用户名：");
                JLabel passwd = new JLabel("密码：");
                uname.setBounds(10, 100, 80, 30);
                passwd.setBounds(10, 150, 80, 30);
                loginFrame.add(uname);
                loginFrame.add(passwd);


                JTextField username = new JTextField();
                JTextField password = new JTextField();
                username.setBounds(100, 100, 600, 30);
                password.setBounds(100, 150, 600, 30);
                loginFrame.add(username);
                loginFrame.add(password);

                          
                JButton reg = new JButton("Register");
                reg.setBounds(400, 300, 100, 30);
                loginFrame.add(reg);
                reg.addActionListener(e -> {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true));
                        writer.write("name:" + username.getText() + "\npassword:" + password.getText() + "\n");
                        writer.flush();
                        writer.close();
                    }catch (Exception ex){
                        System.out.println("Error");
                    }
                });

                loginFrame.setVisible(true);
            }
        });
    }
}
