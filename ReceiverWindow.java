package security;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiverWindow extends AES{
    public void receiverWindow(){
        //Receiver window code here
        JFrame receiverFrame = new JFrame("Receiver Window");
        receiverFrame.setBounds(0,0,600, 700);
        receiverFrame.setLayout(null);
        //textArea window with ScrollPane code here
        JTextArea textAreaReceiver = new JTextArea();
        JScrollPane scrollPaneReceiver = new JScrollPane(textAreaReceiver);
        scrollPaneReceiver.setBounds(3,3,581,388);
        receiverFrame.add(scrollPaneReceiver);
        //Panel in Receiver window code here
        JPanel panelReceiver = new JPanel();
        panelReceiver.setBounds(2,392,582,269);
        panelReceiver.setBackground(Color.DARK_GRAY);
        panelReceiver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        panelReceiver.setLayout(null);
        receiverFrame.add(panelReceiver);
        //Buttons in Receiver window's panel code here
        JButton decrypt, inbox;
        inbox = new JButton("INBOX");
        inbox.setBounds(210,70,160,40);
        panelReceiver.add(inbox);
        //inbox button in Receiver window functionality code here
        inbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //port number window after clicking inbox button code here
                JFrame inboxFrame = new JFrame("Port Number Window");
                inboxFrame.setLayout(new FlowLayout(1,10,20));
                inboxFrame.setSize(300,200);
                JTextField portField = new JTextField(18);
                JLabel portLabel = new JLabel("Port Number");
                JButton submitPort = new JButton("SEND PORT");
                inboxFrame.add(portLabel);
                inboxFrame.add(portField);
                inboxFrame.add(submitPort);
                inboxFrame.setVisible(true);
                //submit port button in port number window code here
                submitPort.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String enteredPort = portField.getText();
                        int intPort = Integer.parseInt(enteredPort);
                        try {
                            //Receiver of UDP(User datagram protocol) Code here
                            DatagramSocket dsReceiver = new DatagramSocket(intPort);
                            byte[] buf = new byte[1024];
                            DatagramPacket dpReceiver = new DatagramPacket(buf, 1024);
                            dsReceiver.receive(dpReceiver);
                            String str = new String(dpReceiver.getData(), 0, dpReceiver.getLength());
                            textAreaReceiver.setText(str);
                            dsReceiver.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        //decrypt button in receiver window code here
        decrypt = new JButton("DECRYPT");
        decrypt.setBounds(210,150,160,40);
        panelReceiver.add(decrypt);
        //decrypt button functionality code here
        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //secret key frame after clicking decrypt button code here
                JFrame secretKeyReceiverFrame = new JFrame("Secret Key Frame (Receiver)");
                secretKeyReceiverFrame.setLayout(new FlowLayout(1,10,20));
                secretKeyReceiverFrame.setSize(300, 200);
                JLabel secretKeyLabelReceiver;
                JTextField secretKeyFieldReceiver;
                JButton decryptSecretKeyButton;
                secretKeyLabelReceiver = new JLabel("Secret Key");
                secretKeyFieldReceiver = new JTextField(18);
                decryptSecretKeyButton = new JButton("DECRYPT");
                secretKeyReceiverFrame.add(secretKeyLabelReceiver);
                secretKeyReceiverFrame.add(secretKeyFieldReceiver);
                secretKeyReceiverFrame.add(decryptSecretKeyButton);
                String secretKeyAlreadyExist  = "ssshhhhhhhhhhh!!!!";
                //decrypt button functionality code here
                decryptSecretKeyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //checking whether the secret key entered by user does match with the original key
                        String secretKeyVar = secretKeyFieldReceiver.getText();
                        if(secretKeyVar.equals(secretKeyAlreadyExist)){
                            String strToDecrypt = textAreaReceiver.getText();
                            String decryptedText = decrypt(strToDecrypt,secretKeyVar);
                            textAreaReceiver.setText(decryptedText);
                        }
                        else{
                            textAreaReceiver.setText("INVALID KEY PLEASE ENTER VALID KEY");
                        }
                    }
                });

                secretKeyReceiverFrame.setVisible(true);
            }
        });
        receiverFrame.setVisible(true);
    }

    public static void main(String[] args) {
        ReceiverWindow receiverObj = new ReceiverWindow();
        receiverObj.receiverWindow();
    }
}
