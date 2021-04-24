package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static String font;
    public static JTextPane pane = new JTextPane();
    public static File opened_file;
    public static void main(String[] args) throws IOException{
        JMenu file, edit;
        JMenuBar mb;
        JMenuItem open, save, save_as, copy, paste, select_all, cut, fonts;
        open = new JMenuItem("OPEN");
        save = new JMenuItem("SAVE");
        save_as = new JMenuItem("SAVE AS");
        copy = new JMenuItem("COPY(CTRL+C)");
        paste = new JMenuItem("PASTE(CTRL+V)");
        cut = new JMenuItem("CUT(CTRL+X)");
        select_all = new JMenuItem("SELECT ALL(CTRL+A)");
        fonts = new JMenuItem("FONTS");
        mb = new JMenuBar();
        file = new JMenu("FILE");
        edit = new JMenu("EDIT");
        file.add(open);
        file.add(save);
        file.add(save_as);
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(edit);
        edit.add(select_all);
        edit.add(fonts);
        JFrame frame = new JFrame();
        frame.setJMenuBar(mb);
        frame.setTitle("Java Editor");
        ImageIcon icon = new ImageIcon("Assets/Logo.png");
        frame.setIconImage(icon.getImage());
        copy.addActionListener(actionEvent -> {
            pane.copy();
        });
        cut.addActionListener(actionEvent -> {
            pane.cut();
        });
        paste.addActionListener(actionEvent -> {
            pane.paste();
        });
        select_all.addActionListener(actionEvent -> {
            pane.selectAll();
        });
        save_as.addActionListener(actionEvent -> {
            String x = pane.getText();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Java File");
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                frame.setTitle(String.valueOf(fileToSave));
                try {
                    FileWriter fileWriter = new FileWriter(fileToSave);
                    fileWriter.write(x);
                    fileWriter.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                opened_file = fileToSave;
            }
        });
        open.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Java File");
            int userSelection = fileChooser.showOpenDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION){
                File fileToOpen = fileChooser.getSelectedFile();
                try {
                    BufferedReader scin2 = new BufferedReader(new InputStreamReader(new FileInputStream(fileToOpen)));
                    frame.setTitle(String.valueOf(fileToOpen));
                    pane.read(scin2, "Reading The File");
                    opened_file = fileToOpen;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        save.addActionListener(actionEvent -> {
            try {
                String x = pane.getText();
                FileWriter fileWriter = new FileWriter(opened_file);
                fileWriter.write(x);
                fileWriter.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        fonts.addActionListener(actionEvent -> {
            JFrame f = new JFrame("FONTS");
            final JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setSize(400,100);
            JButton b=new JButton("SET");
            b.setBounds(200,100,75,20);
            String languages[]={"Serif","SansSerif","Monospaced","Dialog","DialogInput"};
            final JComboBox cb=new JComboBox(languages);
            cb.setBounds(50, 100,90,20);
            f.add(cb); f.add(label); f.add(b);
            f.setLayout(null);
            f.setSize(350,350);
            f.setVisible(true);
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String data = ""+cb.getItemAt(cb.getSelectedIndex());
                    label.setText(data);
                    try {
                        FileWriter writer = new FileWriter("Fonts/Font.txt");
                        writer.write(data);
                        writer.close();
                        pane.setFont(new Font(data, Font.PLAIN,18));
                    } catch (IOException ioException) {

                    }
                }
            });
        });
        mb.add(file);
        mb.add(edit);
        File file1 = new File("Fonts/Font.txt");
        Scanner scin = new Scanner(file1);
        font = scin.next();
        pane.setFont(new Font(font, Font.PLAIN,18));
        pane.setBounds(1, 1, 1000, 1000);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.add(pane);
        JScrollPane scroll = new JScrollPane(pane);
        frame.add(scroll);
    }
}