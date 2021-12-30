// IMPORTING CLASSES
import java.awt.*;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//CLASS FOR USER LAYOUT START
class UserLayout extends Frame implements ActionListener
{
    TextField name_field, cost_field,total, qty_field;          //TEXTFIELD
    Button checkButton,addButton, printButton, logoutButton;    // BUTTONS
    TextArea bill_sheet;                                        //TEXTAREA
    List med_list;                                              //LIST
    Label head,name,qty,price,check,print_note;                 //LABEL
    int sum=0;
    //CONSTRUCTOR START HERE
    UserLayout()
    {

        // INITIALIZATION START HERE
        head=new Label("This is the billing screen");
        name=new Label("Name:");
        qty=new Label("Qty :");
        price=new Label("Cost");
        check=new Label("");
        total=new TextField("Total :");
        print_note=new Label("");
        bill_sheet =new TextArea();
        Label head2=new Label("Bill");
        printButton =new Button("Print bill");
        med_list =new List(100);
        Font plainFont = new Font("Serif", Font.PLAIN, 14);
        head.setBounds(200,30,300,40);
        name_field =new TextField();
        cost_field =new TextField();
        qty_field =new TextField();
        checkButton =new Button("Check");
        logoutButton = new Button("Logout");
        addButton=new Button("Add");
        this.setResizable(false);
        //INITIALIZATION ENDS HERE

        //SETTING BOUNDS START HERE
        name.setBounds(20,100,40,30);
        qty.setBounds(20,200,50,30);
        price.setBounds(20,150,50,30);
        name_field.setBounds(70,100,100,30);
        cost_field.setBounds(70,150,100,30);
        qty_field.setBounds(70,200,100,30);
        check.setBounds(70,225,200,20);
        checkButton.setBounds(70,250,100,30);
        addButton.setBounds(70,300,100,30);
        logoutButton.setBounds(350,475,100,30);
        total.setBounds(350,350,200,50);
        printButton.setBounds(350,430,100,30);
        med_list.setBounds(70,350,200,200);
        head2.setBounds(430,70,100,30);
        print_note.setBounds(350,550,150,30);
        bill_sheet.setBounds(350,100,200,200);
        head2.setFont(plainFont);
        //SETTING BOUND END HERE

        //SNIPPET FOR SHOWING ALL MEDICINE IN LIST
        String query = "select name_medicine from medicines";
        try {
            Backend obj = new Backend();
            String[] arr = obj.user_Back(query).getItems();
            for(String i: arr) { //FOR LOOP TO ADD ALL NAME IN LIST
                med_list.add(i);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        //SNIPPET END HERE

        //SETTING FONT FOR HEAD
        head.setFont(plainFont);

        //SETTINGS FOR FRAME OF USERLAYOUT
        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setResizable(false);           //PREVENT TO RESIZE THE FRAME WINDOW
        this.setVisible(true);
        this.setSize(600,600);
        //SETTING END HERE

        //ADDING ALL ACTION LISTERNER BUTTON
        addButton.addActionListener(this);
        logoutButton.addActionListener(this);
        checkButton.addActionListener(this);
        printButton.addActionListener(this);

        // ADDING IN FRAME
        add(bill_sheet);
        add(name_field);
        add(cost_field);
        add(qty_field);
        add(checkButton);
        add(addButton);
        add(total);
        add(printButton);
        add(head2);
        add(head);
        add(name);
        add(med_list);
        add(check);
        add(qty);
        add(price);
        add(logoutButton);
        add(print_note);


        //WINDOW LISTENER TO CLOSE THE WINDOW THROUGH CROSS BUTTON
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame f =(Frame)e.getSource();
                f.dispose();  //CLOSES THE FRAME
            }
        });
        //WINDOWS LISTENER END HERE
    }

    // ADD,PRINT,LOGOUT,CHECK BUTTONS action listener
    //THIS IS FOR ADDING THE BILL DETAILS TO A BILL SHEET I.E. TO ADD NAME_OF_MEDICINE,QTY,PRICE TO SHEET
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton){
            String[] items= med_list.getItems();
            for (String i:items) {
                if (i.equals(name_field.getText())) {
                    bill_sheet.append(name_field.getText() + "  Rs" + cost_field.getText() + " X " + qty_field.getText() + "\n");
                    int new_cost = Integer.valueOf(cost_field.getText()) * Integer.valueOf(qty_field.getText()); // CONVERT THE STRING PRICE AND QTY FIELD TO INTEGER TO GET TOTAL VALUE OF MEDICINE
                    sum += new_cost;                                                                // TO GET TOTAL COST OF BILL
                    total.setText("Total :" + " Rs" + sum);
                    check.setText("valid");
                    break;
                } else {
                    // IF MEDICINE DOES NOT EXIST WHILE ADDING IN BILL
                    check.setText("add valid medicine");
                }
            }
        }
    // ADDING DATA TO BILL END HERE

    //  THIS IS TO CHECK THE COST OF MEDICINE AVAILABILITY AT TIME OF BILLING
        else if (e.getSource() == checkButton){
            String t1= name_field.getText();                     // GET NAME FROM NAME FIELD
            String[] items= med_list.getItems();                //  RETRIEVES ALL MEDICINE PRESENT IN DATABASE
            boolean val=false;
            for(String i:items)
            {
                //RECURSIVELY CHECK FOR ALL MEDICINE USING O(n) COMPLEXITY
                if(i.equals(t1)) {
                    val = true;
                    break;
                }
            }

            Backend obj = new Backend(); //MAKING BACKEND OBJECT
            String query = "select price from medicines where name_medicine='"+t1+"'";  //QUERY TO GET PRICE FROM DATABASE

            if (val == false){
                check.setText("Check valid medicine");
            }
            else
            {
                String x="";
                try {
                    x=obj.check_cost(query);
                    cost_field.setText(x);                                          // TO SET COST IN COST_FIELD

                }catch(Exception q) {
                    q.printStackTrace();
                }
            }
        }
        // THIS IS FOR PRINING BILL_SHEET I.E. TO MAKE TXT FILE OF BILL
        else if (e.getSource() == printButton) {
            try {
                FileWriter f = new FileWriter("C:\\Users\\Aman\\Desktop\\Projectfile.txt");  //MAKING A FILE
                f.write(bill_sheet.getText());                   // WRITING TO FILE
                f.close();
                print_note.setText("Print file created");
                TimeUnit.SECONDS.sleep(5);
                print_note.setText("");
            } catch (Exception g) {
                System.out.println(g);
            }
        }

        // TO LOGOUT OF WINDOW
        else if (e.getSource() == logoutButton){
            MyLoginWindow login = new MyLoginWindow();
            dispose();
        }
    }
}
