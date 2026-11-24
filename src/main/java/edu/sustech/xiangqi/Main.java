package edu.sustech.xiangqi;

import edu.sustech.xiangqi.controller.ChessController;
import edu.sustech.xiangqi.model.Board;
import edu.sustech.xiangqi.view.ChessFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// 程序入口
public class Main {
    private static boolean checkLogin(String username, String password) throws IOException {
        File file = new File("user.txt");
        if (!file.exists()) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String nameLine = reader.readLine();
            while (nameLine != null) {
                String passwordLine = reader.readLine();
                if (("name:" + username).equals(nameLine)
                        && ("password:" + password).equals(passwordLine)) {
                    return true;
                }
                nameLine = reader.readLine();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建登录和注册界面
                JFrame beginFrame = new JFrame("欢迎");
                JFrame loginFrame = new JFrame("登录");
                JFrame registerFrame = new JFrame("注册");
                JFrame visitorloginFrame = new JFrame("游客登陆");
                beginFrame.setSize(800, 600);
                beginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                beginFrame.setLayout(null);
                loginFrame.setSize(800, 600);
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginFrame.setLayout(null);
                registerFrame.setSize(800, 600);
                registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registerFrame.setLayout(null);
                visitorloginFrame.setSize(800 , 600);
                visitorloginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                visitorloginFrame.setLayout(null);

                // 创建按钮
                JButton login = new JButton("Login");
                JButton register = new JButton("Register");
                JButton visitorlogin = new JButton("Visitor Login");
                JButton confirmLogin = new JButton("Confirm");
                JButton confirmRegister = new JButton("Confirm");
                JButton confirmVisitor = new JButton("Confirm");
                confirmLogin.setBounds(350, 300, 100, 30);
                confirmRegister.setBounds(350, 300, 100, 30);
                confirmVisitor.setBounds(350 ,300 ,100 , 30);
                register.setBounds(250, 200, 200, 30);
                login.setBounds(250, 300, 200, 30);
                visitorlogin.setBounds(250, 400, 200, 30);
                loginFrame.add(confirmLogin);
                registerFrame.add(confirmRegister);
                visitorloginFrame.add(confirmVisitor);

                beginFrame.add(login);
                beginFrame.add(register);
                beginFrame.add(visitorlogin);
                beginFrame.setVisible(true);

                // 创建标签和文本框
                JLabel usernameLoginLabel = new JLabel("用户名：");
                JLabel passwordLoginLabel = new JLabel("密码：");
                JLabel usernameRegisterLabel = new JLabel("用户名：");
                JLabel passwordRegisterLabel = new JLabel("密码：");
                JTextField usernameLoginField = new JTextField();
                JTextField passwordLoginField = new JTextField();
                JTextField usernameRegisterField = new JTextField();
                JTextField passwordRegisterField = new JTextField();
               
                usernameLoginField.setBounds(100, 100, 200, 30);
                passwordLoginField.setBounds(100, 150, 200, 30);
                usernameRegisterField.setBounds(100, 100, 200, 30);
                passwordRegisterField.setBounds(100, 150, 200, 30);
                usernameLoginLabel.setBounds(20, 100, 80, 30);
                passwordLoginLabel.setBounds(20, 150, 80, 30);
                usernameRegisterLabel.setBounds(20, 100, 80, 30);
                passwordRegisterLabel.setBounds(20, 150, 80, 30);

                loginFrame.add(usernameLoginLabel);
                loginFrame.add(passwordLoginLabel);
                loginFrame.add(usernameLoginField);
                loginFrame.add(passwordLoginField);
                loginFrame.add(confirmLogin);

                registerFrame.add(usernameRegisterLabel);
                registerFrame.add(passwordRegisterLabel);
                registerFrame.add(usernameRegisterField);
                registerFrame.add(passwordRegisterField);
                registerFrame.add(confirmRegister);

                login.addActionListener(e -> loginFrame.setVisible(true));
                register.addActionListener(e -> registerFrame.setVisible(true));
                visitorlogin.addActionListener(e -> visitorloginFrame.setVisible(true));
                confirmLogin.addActionListener(e -> {
                    try {
                        if (!checkLogin(usernameLoginField.getText(), passwordLoginField.getText())) {
                            JOptionPane.showMessageDialog(loginFrame, "用户名或密码错误，请先注册或重新输入");
                            return;
                        }
                        Board board = new Board();
                        ChessFrame frame = new ChessFrame(board);
                        ChessController controller = new ChessController(board, frame.getBoardPanel());
                        frame.getBoardPanel().addMouseListener(controller);
                        frame.setVisible(true);
                        loginFrame.dispose();
                        beginFrame.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(loginFrame, "读取用户文件失败：" + ex.getMessage());
                    }
                });
                confirmRegister.addActionListener(e -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
                        writer.write("name:" + usernameRegisterField.getText() + "\npassword:"
                                + passwordRegisterField.getText() + "\n");
                        writer.flush();
                        JOptionPane.showMessageDialog(registerFrame, "注册成功，请登录");
                        registerFrame.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(registerFrame, "保存用户文件失败：" + ex.getMessage());
                    }
                });
                confirmVisitor.addActionListener(e -> {
                    Board board = new Board();
                    ChessFrame frame = new ChessFrame(board);
                    ChessController controller = new ChessController(board, frame.getBoardPanel());
                    frame.getBoardPanel().addMouseListener(controller);
                    frame.setVisible(true);
                    visitorloginFrame.dispose();
                    beginFrame.dispose();
                });
            }
        });
    }
}
