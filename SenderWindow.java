package security;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.*;
import java.net.*;

public class SenderWindow extends AES{
    public void senderWindow(){
        //sender window frame code here
        JFrame frame = new JFrame("Sender Window");
        frame.setBounds(0,0,600, 700);
        frame.setLayout(null);
        //textArea with scrollPane in sender window code here
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPaneSender = new JScrollPane(textArea);
        scrollPaneSender.setBounds(3,3,581,388);
        frame.add(scrollPaneSender);
        //panel in sender window code here
        JPanel panel2 = new JPanel();
        panel2.setBounds(2,392,582,269);
        panel2.setBackground(Color.DARK_GRAY);
        panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        panel2.setLayout(null);
        frame.add(panel2);
        //Buttons in Sender window Panel
        JButton upload,digitalSignature, send1;
        upload = new JButton("UPLOAD DOCUMENT");
        upload.setBounds(210,50,160,40);
        panel2.add(upload);
        //Upload Button functionality code here
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame open after clicking upload button code here
                JFrame fileFrame = new JFrame("File Chooser");
                fileFrame.setSize(600,500);
                //file chooser in frame
                JFileChooser fileChooser = new JFileChooser();
                fileFrame.add(fileChooser);
                fileFrame.setVisible(true);
                //file chooser functionality code here
                fileChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FileReader readFile;
                        BufferedReader readBuffer;
                        try {
                            String fileContents;
                            int lineNumber;
                            readFile = new FileReader(fileChooser.getSelectedFile());
                            readBuffer = new BufferedReader(readFile);
                            while(true){
                                fileContents = readBuffer.readLine();
                                textArea.setText(fileContents);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        //Digital Signature button code here
        digitalSignature = new JButton("DIGITAL SIGNATURE");
        digitalSignature.setBounds(210,120,160,40);
        panel2.add(digitalSignature);
        //digital signature button functionality
        digitalSignature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //secret key frame get open after clicking encrypt button code here
                JFrame secretKeyFrame = new JFrame("Secret Key Window");
                secretKeyFrame.setLayout(new FlowLayout(1, 10, 20));
                secretKeyFrame.setSize(300, 200);
                JLabel secretKeyLabel = new JLabel("Secret Key: ");
                JTextField secretKeyField = new JTextField(18);
                JButton encrypt = new JButton("ENCRYPT");
                secretKeyFrame.add(secretKeyLabel);
                secretKeyFrame.add(secretKeyField);
                secretKeyFrame.add(encrypt);
                secretKeyFrame.setVisible(true);
                String key = "ssshhhhhhhhhhh!!!!";
                //encrypt key in secret key frame code here
                encrypt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String enteredKey = secretKeyField.getText();
                        String encrypted;
                        if(enteredKey.equals(key)){
                            String message = textArea.getText();
                            encrypted = encrypt(message, enteredKey);
                            textArea.setText(encrypted);
                        }
                        else{
                            textArea.setText("INVALID KEY PLEASE ENTER VALID KEY");
                        }
                    }
                });

            }
        });
        //Send message key code here
        send1 = new JButton("SEND MESSAGE");
        send1.setBounds(210,190,160,40);
        panel2.add(send1);
        //send message key functionality code here
        send1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Receiver Address frame code here
                JFrame addressFrame = new JFrame("Receiver Address");
                addressFrame.setLayout(new FlowLayout(1, 10, 20));
                addressFrame.setSize(300, 200);
                JTextField inetTextField, portTextField;
                JLabel inetLabel, portLabel;
                JButton sendUnderReceiverAddress;
                inetLabel = new JLabel("InetAddress: ");
                inetTextField = new JTextField(18);
                portLabel = new JLabel("Port:               ");
                portTextField = new JTextField(18);
                addressFrame.add(inetLabel);
                addressFrame.add(inetTextField);
                addressFrame.add(portLabel);
                addressFrame.add(portTextField);
                sendUnderReceiverAddress = new JButton("SEND");
                addressFrame.add(sendUnderReceiverAddress);
                addressFrame.setVisible(true);
                //send button in Receiver Address frame code here
                sendUnderReceiverAddress.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String inetAddress, portNo;
                        inetAddress = inetTextField.getText().toString();//127.0.0.1

                        portNo = portTextField.getText();
                        int portInt = Integer.parseInt(portNo);//51020
                        try {
                            //Sender of UDP
                            DatagramSocket ds = new DatagramSocket();
                            String str = textArea.getText();
                            InetAddress ip = InetAddress.getByName(inetAddress);
                            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, portInt);//51020
                            ds.send(dp);
                            ds.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SenderWindow senderObj = new SenderWindow();
        senderObj.senderWindow();

    }
}
