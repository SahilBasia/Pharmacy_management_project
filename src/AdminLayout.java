
// IMPORTING USEFUL CLASSES
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AdminLayout extends Frame implements ActionListener{

    TextField name, check, add_delete, id,price ;
    Button searchButton,logoutButton, addButton, delButton;
    List med_list, id_list;
    Label checkLabel2,nameLabel1,nameLabel2,checkLabel1,idLabel,priceLabel,listLabel, idLabel2;
    AdminLayout(){

        // INITIALISING ALL COMPONENTS

        name = new TextField();
        check = new TextField();
        add_delete = new TextField();
        id = new TextField();
        price = new TextField();
        id_list = new List();
        searchButton = new Button("Search");
        logoutButton = new Button("Logout");
        addButton = new Button("ADD");
        delButton = new Button("DELETE");
        med_list = new List(100);
        checkLabel2 = new Label();
        nameLabel1 = new Label("Name:");
        nameLabel2 = new Label("Name:");
        checkLabel1 = new Label("Status:");
        idLabel = new Label("ID:");
        priceLabel = new Label("Price:");
        listLabel = new Label("Medicines:");
        idLabel2 = new Label("ID:");

        // SETTING SET BOUNDS FOR EVERY COMPONENT
        nameLabel1.setBounds(40,60,50,30); // name label 1
        checkLabel1.setBounds(30,110,70,30); //check label 1
        name.setBounds(110,60,100,30); // name search field
        check.setBounds(110,110,100,30); // get field
        searchButton.setBounds(250,77,80,30); // search button
        add_delete.setBounds(110,160,70,30); // name delete add field
        price.setBounds(245,160,60,30); //price field
        priceLabel.setBounds(200,160,40,30); //price label
        id.setBounds(365,160,60,30); // id field
        idLabel.setBounds(325,165,20,20); //id label
        nameLabel2.setBounds(40,160,50,30); // name label 2
        addButton.setBounds(110,200,80,30); // add button
        delButton.setBounds(205,200,80,30); // delete button
        id_list.setBounds(310,250,40,120); //id list
        idLabel2.setBounds(280,310,30,30); //id label 2
        listLabel.setBounds(40,310,70,30);
        med_list.setBounds(110,250,150,120); // list
        checkLabel2.setBounds(300,200,100,30); // check label 2
        logoutButton.setBounds(120,390,100,30); // logout button


        String query = "select name_medicine from medicines";
        String query_id = "select id from medicines";
        try {
            Backend obj = new Backend();
            Backend obj1 = new Backend();
            String[] arr = obj.user_Back(query).getItems(); // We are getting String array from list.
            String[] arr1 = obj1.user_Back(query_id).getItems();
            for(String i: arr) {    // RETRIEVING DATA FROM DATABASE OF MEDICINES NAME.
                med_list.add(i);
            }
            for(String i: arr1) {
                id_list.add(i);     // RETRIEVING DATA FROM DATABASE OF MEDICINES ID.
            }
        }catch(Exception e) {
            System.out.println("Exception Occurred");
        }

        // ADDING ACTION LISTENERS TO BUTTONS
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        delButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // ADDING EVERY COMPONENT TO LAYOUT
        add(listLabel);
        add(idLabel);
        add(priceLabel);
        add(nameLabel2);
        add(checkLabel1);
        add(nameLabel1);
        add(price);
        add(id);
        add(searchButton);
        add(logoutButton);
        add(addButton);
        add(delButton);
        add(name);
        add(check);
        add(add_delete);
        add(med_list);
        add(checkLabel2);
        add(id_list);
        add(idLabel2);

        setSize(500,500);
        setLayout(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame f =(Frame)e.getSource();
                f.dispose();  //CLOSES THE FRAME
            }
        });

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton){ // SETTING UP LOGOUT BUTTON
            MyLoginWindow login = new MyLoginWindow();
            dispose(); // DISPOSING THE WINDOW AFTER CLICKING LOGOUT BUTTON
        }
        if (e.getSource() == searchButton) { // SETTING UP SEARCH BUTTON
            String t1 = name.getText();
            String[] items = med_list.getItems();
            boolean val = false;
            for (String i : items) {    // NORMAL FOR LOOP, WHICH IS SEARCHING THE LIST AND GIVING US THE STATUS OF MEDICINE.
                if (i.equals(t1)) {
                    val = true;
                    break;
                }
            }
            if (val) {
                check.setText("Item present");
            } else check.setText("Not present");
        }
        if (e.getSource() == addButton){ // SETTING UP ADD BUTTON

            String value = add_delete.getText(); // GETTING THE NAME VALUE FROM ADD DELETE FIELD
            int id_db = Integer.parseInt(id.getText()); // GETTING ID VALUE FROM IF FIELD (IN INT FORM)
            String query_id = id.getText(); // GETTING ID VALUE FROM ID FIELD (IN STRING FORM)
            int price_db = Integer.parseInt(price.getText()); // GETTING PRICE VALUE FROM PRICE FIELD (IN INT FORM)
            // WE WANT ID AND PRICE IN INT FORM BECAUSE, IN DATABASE WE HAVE INITIALISED THEM AS INT.

            // QUERY FOR INSERTING VALUES IN DATABASE
            String query = "insert into medicines(id, name_medicine, price) values('"+id_db+"','"+value+"','"+price_db+"')";

            // IF ANY FIELD WILL BE LEFT EMPTY, IT WON'T ADD.
            if(!(value.isEmpty()) && !(query.isEmpty()) && !(query_id.isEmpty())) {
                try {
                    Backend obj = new Backend();
                    obj.add_del(query); // SENDING OUR QUERY TO add_del METHOD IN BACKEND CLASS.
                }catch(Exception q) {
                    q.printStackTrace();
                }
                String[] items = med_list.getItems();
                boolean check = true;
                for (String i: items){       // checking if item is already present in list.
                    if (i.equals(value)){
                        check = false;
                        checkLabel2.setText("Already Present");
                        break;
                    }
                }
                if (check) {
                    med_list.add(value);// add only if not in list.
                    id_list.add(query_id);
                    checkLabel2.setText("ADDED");
                }
            }
        }
        if (e.getSource() == delButton){ // SETTING UP DELETE BUTTON
            String value = add_delete.getText(); // Taking medicine value from add_delete field.

            // This QUERY will DELETE medicine from DATABASE.
            String query = "delete from medicines where name_medicine='"+value+"'";
            String query_id = id.getText();
            /*Backend obj = new Backend();*/

            try {
                Backend obj1 = new Backend();
                obj1.add_del(query); // SENDING OUR QUERY TO add_del METHOD IN BACKEND CLASS.
                String[] arr1 = obj1.user_Back(query_id).getItems(); // We are getting string array of list ID.
                for(String i: arr1) {
                    id_list.add(i); // We are Refreshing the list to display in list field.
                }

            }catch(Exception q) {
                System.out.println("");
            }
            String[] items = med_list.getItems();
            boolean check = true;
            for (String i: items){
                if (!i.equals(value)){
                    check = false;   // checking if item is present or not.
                    checkLabel2.setText("Not present");
                    break;
                }
            }
            if (!check) {
                id_list.remove(query_id);
                med_list.remove(value);// delete only if present.
                checkLabel2.setText("DELETED");
            }
        }
    }
}

