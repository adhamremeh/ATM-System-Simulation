package project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class Admin
{
    // delete account function, we pass the id to check the user to delete
    public void deleteAccount(String id) throws FileNotFoundException, IOException
    {
        // get users from data base
        ArrayList<String> user = new ArrayList<String>();
        
        StringTokenizer tokenizer;
        
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);
        
        while (filedata.hasNextLine()) 
        {
            user.add(filedata.nextLine());
        }
        //////////////////////////////
        
        // encrypt the parameter id to compare it later
        String idEncrypted = "";
        for (int i = 0; i < id.length(); i++)
        {
            char tempChar = id.charAt(i);
            int tempASCII = (int)tempChar + 2;
            idEncrypted = idEncrypted + (char)tempASCII;
        }
         
        // check if the user has the same id, then delete this user data
        for (int i = 0; i < user.size(); i++)
        {
            tokenizer = new StringTokenizer( user.get(i) , "," );

            if (tokenizer.nextToken().equals(idEncrypted))
                user.remove (i);
        }
        
        // print the users arraylist in a text file called database
        FileWriter fw = new FileWriter("database.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        for (int i = 0; i < user.size(); i++)
        {
            
            if (i == user.size()-1)
                pw.print(user.get(i));
            else 
                pw.println(user.get(i));
        }
        
        fw.close();
        ////////////////////////////////////////////////////////////
        
    }
    
    public String add(String PIN_GUI, String Name, String Address, String Phone_number, String Balance) throws FileNotFoundException, IOException
    {
        // check the inputs if they are empty or not, balance excluded because it's optional
        if ("".equals(Name) || "".equals(Address) || "".equals(Phone_number) || "".equals(PIN_GUI))
            return "All required fields must be filled";
        else 
        {
            // check > PIN input is numeric or not 
            try
            {
                Integer.parseInt(PIN_GUI);
                // check > PIN input is 4 numbers
                if (PIN_GUI.length() > 4 || PIN_GUI.length() < 4)
                    return "PIN must be 4 numbers";
                else
                {
                    // check > Name input is Alphabetic
                    for (int i = 0; i < Name.length(); i++)
                    {
                        if (!Character.isAlphabetic(Name.charAt(i)))
                            return "Name must be Alphabetic";
                    }
                    try
                    {
                        // check > phone number is numeric
                        Integer.parseInt(Phone_number);
                        try
                        {
                            // check > Balance is not empty then check if it's numeric
                            if (!Balance.equals(""))
                                Integer.parseInt(Balance);
                        }
                        catch (Exception exc)
                        {
                            return "Balance field must be numbers";
                        }
                    }
                    catch (Exception exc)
                    {
                        return "Phone number field must be numbers";
                    }
                }
            }
            catch (Exception exc)
            {
                return "PIN field must be numbers";
            }
        }
        
        int ID = 0;
        String PIN = "";
        String name;
        String address;
        String phone_number;
        String balance;
        
        // get users from data base
        ArrayList<String> user = new ArrayList<String>();

        StringTokenizer tokenizer;
        
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);
        
        while (filedata.hasNextLine()) 
        {
            user.add(filedata.nextLine());
        }
        //////////////////////////////
        
        // decrypt the last id in database and generate a new one 
        tokenizer = new StringTokenizer(user.get(user.size()-1), ",");
        String tempID = tokenizer.nextToken();
        String tempIDString = "";
        for (int i = 0; i < tempID.length(); i++)
        {
            char tempChar = tempID.charAt(i);
            int tempASCII = (int)tempChar - 2;
            tempIDString = tempIDString + (char)tempASCII;
        }
        ID = Integer.parseInt(tempIDString)+1;
        /////////////////////////////////////////////////////////

        ///////////////////// Assign values ///////////////////////
        PIN = PIN_GUI;

        name = Name;

        address = Address;

        phone_number = Phone_number;
        ///////////////////////////////////////////////////////////

        if ("".equals(Balance))
            balance = "0";
        else
            balance = Balance;

        // print the new user to our database file, here we assign filewriter's append value to true 
        // because we want to add a user not to make a new file with edits 
        FileWriter fw = new FileWriter("database.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        pw.printf("%n");
        // make a string with the format we need, which is the format of everyline in database
        String newUser = new String(new char[5-Integer.toString(ID).length()]).replace('\0', '0') + Integer.toString(ID) + "," + PIN + ","+ name + "," + address + "," + phone_number + "," + balance;
        // encrypt and print every character of newUser string to our file 
        for (int i = 0; i < newUser.length(); i++)
        {
            char tempChar = newUser.charAt(i);
            int tempASCII = (int)tempChar;
            if (tempChar != ',')
                tempASCII = (int)tempChar + 2;
            char dataChar = (char)tempASCII;
            pw.print(dataChar);
        }

        fw.close();

        return "true";
    }
    
    public boolean Update(String ID, String PIN_GUI, String Address, String Phone_number) throws FileNotFoundException, IOException
    {
        // id must be filled check
        if (ID.equals(""))
            return false;
        
        String PIN = "";
        String address;
        String phone_number;

        // get users from data base
        ArrayList<String> user = new ArrayList<String>();
        
        StringTokenizer tokenizer;
        
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);
        
        while (filedata.hasNextLine()) 
        {
            user.add(filedata.nextLine());
        }
        //////////////////////////////
        
        // two nested for loops to decrypt the whole user ArrayList
        String userDecryptedString = "";
        for (int i = 0; i < user.size(); i++)
        {
            for (int j = 0; j < user.get(i).length(); j++)
            {
                char tempChar = user.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar - 2;
                char dataChar = (char)tempASCII;
                userDecryptedString = userDecryptedString + dataChar;
            }
            user.set(i, userDecryptedString);
            userDecryptedString = "";
        }
        ////////////////////////////////////////////////////////////
        
        PIN = PIN_GUI;
        address = Address;
        phone_number = Phone_number;
        
        for (int i = 0; i < user.size(); i++)
        {
            tokenizer = new StringTokenizer(user.get(i), ",");
            if (tokenizer.nextToken().equals(ID))
            {
                String[] splitUser = user.get(i).split(",");
                if (!PIN.equals(""))
                    splitUser[1] = PIN;
                if (!address.equals(""))
                    splitUser[3] = address;
                if (!phone_number.equals(""))
                    splitUser[4] = phone_number;
                String updatedUser = "";
                for (int j = 0; j < splitUser.length-1; j++)
                {
                    updatedUser = updatedUser + splitUser[j] + ",";
                }
                updatedUser = updatedUser + splitUser[splitUser.length-1];
                user.set(i, updatedUser);
            }
        }
        
        // two nested for loops to Encrypt the whole user ArrayList
        String userEncryptedString = "";
        for (int i = 0; i < user.size(); i++)
        {
            for (int j = 0; j < user.get(i).length(); j++)
            {
                char tempChar = user.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar + 2;
                char dataChar = (char)tempASCII;
                userEncryptedString = userEncryptedString + dataChar;
            }
            user.set(i, userEncryptedString);
            userEncryptedString = "";
        }
        ////////////////////////////////////////////////////////////
        
        // print the users arraylist in a text file called database, the same as in delete function
        FileWriter fw = new FileWriter("database.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        for (int i = 0; i < user.size(); i++)
        {
            if (i == user.size()-1)
                pw.print(user.get(i));
            else 
                pw.println(user.get(i));
        }
        
        fw.close();
        ////////////////////////////////////////////////////////////////////////////////////////
        
        return true;
    }
    
    // this function take all users in database, decrypt it and return it in a string form
    public String decryptData() throws FileNotFoundException
    {
        // get users from data base
        ArrayList<String> user = new ArrayList<String>();

        StringTokenizer tokenizer;
        
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);
        while (filedata.hasNextLine()) 
        {
            user.add(filedata.nextLine());
        }
        //////////////////////////////
        
        // this string with for loops is different from the whole data decryption because we don't add it in
        // array or something, we just add every line in our data base to this string in arranged form
        String dataFullString = "";
        
        for (int i = 0; i < user.size(); i++)
        {
            for (int j = 0; j < user.get(i).length(); j++)
            {
                char tempChar = user.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar - 2;
                char dataChar = (char)tempASCII;
                dataFullString = dataFullString + dataChar;
            }
            dataFullString = dataFullString + "\n";
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        return dataFullString;
    }
    
}

class User {
    int balance;
    
    String[] splitUser;
    
    ArrayList<String> user = new ArrayList<String>();
    StringTokenizer tokenizer;
    
    User(String id) throws FileNotFoundException
    {
        // get all data from database
        File file = new File("database.txt");
        Scanner filedata = new Scanner(file);
        
        while (filedata.hasNextLine()) 
        {
            user.add(filedata.nextLine());
        }
        /////////////////////////////
        
        // decrypt the data in the constructor to use it in every comparison later
        fullDataDecryption();
             
        // get user balance >>>>> here we use the decrypted data (for example)
        for (int i = 0; i < user.size(); i++)
        {
            tokenizer = new StringTokenizer( user.get(i) , "," );

            if (tokenizer.nextToken().equals(id))
            {
                splitUser = user.get(i).split(",");
                balance = Integer.parseInt(splitUser[splitUser.length-1]);
                break;
            }
        }
        //////////////////////////////////////////////////////////////////////
    }

    void deposit(int depositValue) throws IOException {
        balance = balance + depositValue;
        
        String fullUser = "";
        
        // change the balance with the new balance
        for (int i = 0; i < splitUser.length-1; i++)
            fullUser = fullUser + splitUser[i] + ",";
        fullUser = fullUser + Integer.toString(balance);
        //////////////////////////////////////////
        
        // encrypt the user with edited balance
        String tempFullUser = "";
        for (int i = 0; i < fullUser.length(); i++)
        {
            char tempChar = fullUser.charAt(i);
            int tempASCII = (int)tempChar;
            if (tempChar != ',')
                tempASCII = (int)tempChar + 2;
            tempFullUser = tempFullUser + (char)tempASCII;
        }
        fullUser = tempFullUser;
        //////////////////////////////////////////
        
        FileWriter fw = new FileWriter("database.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        // encrypt data before we write it in our file 
        fullDataEncryption();
        
        // print the users arraylist in a text file called database, the same as in delete function in admin class
        for (int i = 0; i < user.size(); i++)
        {
            if (user.get(i).split(",")[0].equals(fullUser.split(",")[0]))
            {
                user.set(i, fullUser);
            }
            if (i == user.size()-1)
                pw.print(user.get(i));
            else
                pw.println(user.get(i));
        }
        fw.close();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // decrypt it again to use it if needed
        fullDataDecryption();
    }
    
    void withdrawal(int withdrawalValue) throws IOException {
        balance = balance - withdrawalValue;
        
        String fullUser = "";
        
        // change the balance with the new balance
        for (int i = 0; i < splitUser.length-1; i++)
            fullUser = fullUser + splitUser[i] + ",";
        fullUser = fullUser + Integer.toString(balance);
        //////////////////////////////////////////
        
        // encrypt the user with edited balance
        String tempFullUser = "";
        for (int i = 0; i < fullUser.length(); i++)
        {
            char tempChar = fullUser.charAt(i);
            int tempASCII = (int)tempChar;
            if (tempChar != ',')
                tempASCII = (int)tempChar + 2;
            tempFullUser = tempFullUser + (char)tempASCII;
        }
        fullUser = tempFullUser;
        //////////////////////////////////////////
        
        FileWriter fw = new FileWriter("database.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        // encrypt data before we write it in our file 
        fullDataEncryption();
        
        // print the users arraylist in a text file called database, the same as in delete function in admin class
        for (int i = 0; i < user.size(); i++)
        {
            if (user.get(i).split(",")[0].equals(fullUser.split(",")[0]))
            {
                user.set(i, fullUser);
            }
            if (i == user.size()-1)
                pw.print(user.get(i));
            else
                pw.println(user.get(i));
        }
        fw.close();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // decrypt it again to use it if needed
        fullDataDecryption();
    }
    
    // just a function to return the balance of the user 
    int CheckAccount() throws UnsupportedEncodingException{
        return balance;
    }
    
    // function to encrypt all data
    private void fullDataEncryption()
    {
        String userEncryptedString = "";
        for (int i = 0; i < user.size(); i++)
        {
            for (int j = 0; j < user.get(i).length(); j++)
            {
                char tempChar = user.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar + 2;
                char dataChar = (char)tempASCII;
                userEncryptedString = userEncryptedString + dataChar;
            }
            user.set(i, userEncryptedString);
            userEncryptedString = "";
        }
    }
    
    // function to decrypt all data
    private void fullDataDecryption()
    {
        String userDecryptedString = "";
        for (int i = 0; i < user.size(); i++)
        {
            for (int j = 0; j < user.get(i).length(); j++)
            {
                char tempChar = user.get(i).charAt(j);
                int tempASCII = (int)tempChar;
                if (tempChar != ',')
                    tempASCII = (int)tempChar - 2;
                char dataChar = (char)tempASCII;
                userDecryptedString = userDecryptedString + dataChar;
            }
            user.set(i, userDecryptedString);
            userDecryptedString = "";
        }
    }
}


public class Project {
    public static void main(String[] args) throws IOException{

        jframe a = new jframe();
        a.main();
      
    }    
}