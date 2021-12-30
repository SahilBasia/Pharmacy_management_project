//package Project;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
// libraries for hashing classes
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

class MyLoginWindow extends Frame
{
    //DEFINING 
    TextField name,pass;
    Label auth;
    Button b_submit,b_cancel;

    MyLoginWindow()
    {
        //SETTINGS FOR FRAME
        setLayout(new FlowLayout());
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(400,400);
        
        // to close the windows using close button (x) on right corner
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame frame = (Frame) e.getSource();
                frame.dispose();
            }
        });
        
        //DEFINING THE LABELS,FIELD
        this.setTitle("pharma managment system");
        Label l=new Label("pharma system V.0.1");
        final Label message= new Label("");
        Label n=new Label("Username:",Label.CENTER);
        Label p=new Label("password:",Label.CENTER);
        auth = new Label("");
        name=new TextField(20);
        pass=new TextField(20);
        pass.setEchoChar('*');
        b_submit=new Button("submit");
        b_cancel=new Button("cancel");

        // Adding all the components in Layout
        this.add(n);
        this.add(name);
        this.add(p);
        this.add(pass);
        this.add(b_submit);
        this.add(b_cancel);
        this.add(l);
        this.add(message);
        this.add(auth);

        //Setting bounds for every component.
        n.setBounds(70,90,90,60);
        p.setBounds(70,130,90,60);
        l.setBounds(95,30,200,60);
        name.setBounds(200,100,90,20);
        pass.setBounds(200,140,90,20);
        b_submit.setBounds(100,260,70,40);
        b_cancel.setBounds(180,260,70,40);
        auth.setBounds(120,200,150,20);

        //THIS CLEARES ALL INPUT FIELD WHEN CLICKED CANCEL BUTTON
        b_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name.setText("");
                pass.setText("");
            }
        });

        // event for showing message of user logged in app
        //EVENT FOR TO SUBMIT DETAILS WHEN CLICKED SUBMIT
        b_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String userName= name.getText(); //GETTING USERNAME FROM NAME FIELD
                String password= pass.getText(); //GETTING PLAIN PASSWORD FROM PASS FIELD
                // get the username from console
                // get the row which has the same name

                // ENCRYPTION OF HASH STARTS HERE
                try
                {

                    // invoking the static getInstance() method of the MessageDigest class
                    // Notice it has MD5 in its parameter.
                    MessageDigest msgDst = MessageDigest.getInstance("MD5");

                    // the digest() method is invoked to compute the message digest
                    // from an input digest() and it returns an array of byte
                    byte[] msgArr = msgDst.digest(password.getBytes());

                    // getting signum representation from byte array msgArr
                    BigInteger bi = new BigInteger(1, msgArr);

                    // Converting into hex value
                    String hshtxt = bi.toString(16);

                    while (hshtxt.length() < 32)
                    {
                        hshtxt = "0" + hshtxt;
                    }
                    password= hshtxt;

                }
                // ENCRYPTION END HERE
                // for handling the exception

                catch (NoSuchAlgorithmException abc)
                {
                    throw new RuntimeException(abc);
                }

                // BELOW INTERACTING WITH DATABASE TO CHECK AUTHENTICATION
                String database_pass = "select username from login where username='"+userName+"' AND pass='"+password+"'";
                try {
                    Backend obj = new Backend();
                    if(obj.back(database_pass).equals("admin")) {
                        dispose();
                        AdminLayout ad = new AdminLayout();
                    }else if(obj.back(database_pass).equals("testUser")) {
                        dispose();
                        UserLayout ud = new UserLayout();
                    }else {
                        auth.setText(obj.back(database_pass));
                        TimeUnit.SECONDS.sleep(2);
                        auth.setText("");
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
    //main function TO STAR PROGRAM
    public static void main(String args[])
    {
        MyLoginWindow login=new MyLoginWindow();

    }

}
