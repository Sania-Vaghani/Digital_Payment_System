package Main;

import java.io.*;
import java.util.*;
import java.sql.*;

public class Registration {
    static int generateOTP() {
        int num = (int) (Math.random() * 1000000);
        return num;
    }

    public static boolean checkMobile(String mno) {
        if ((mno.startsWith("9") || mno.startsWith("8") || mno.startsWith("7")) && mno.length() == 10) {
            for (char c : mno.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        } else {
            System.out.println("Invalid length. or Invalid Start");
            return false;
        }
    }

    public static void userData(HashMap<String, Integer> hm, String mob, Connection con)
            throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean f = true;
        while (f) {
            String username = "";
            BufferedReader br = new BufferedReader(
                    new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\BankDetail.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                String arr[] = s.split(" ");
                if (arr[0].equals(mob)) {
                    username = arr[2];
                }
            }
            int userAmount = 0;
            if (hm.containsKey(mob)) {
                userAmount = hm.get(mob);
            }
            System.out.print("\t\t\t\t\tEnter Bank Name : ");
            String bankName = sc.nextLine();
            String upi = mob + "@" + bankName;

            boolean flag = true;
            String accNum = "";
            while (flag) {
                System.out.print("\t\t\t\t\tEnter Account Number : ");
                accNum = sc.nextLine();
                if (accNum.length() == 5) {
                    for (char c : accNum.toCharArray()) {
                        if (Character.isDigit(c)) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                    if (flag == true) {
                        System.out.println("\n\t\t\t\t\tEnter Integer Data Only.");
                    }
                } else {
                    System.out.println("\n\t\t\t\t\tAccount Number should be of Length of 5.");
                }
            }
            String pass = "";
            flag = true;
            boolean digitCheck = false;
            boolean charCheck = false;
            while (flag) {
                System.out.print("\t\t\t\t\tEnter Password : ");
                pass = sc.nextLine();
                if (pass.length() == 8) {
                    for (char c : pass.toCharArray()) {
                        if (Character.isDigit(c)) {
                            digitCheck = true;
                        }
                        if (Character.isAlphabetic(c)) {
                            charCheck = true;
                        }
                    }
                    if (digitCheck == false || charCheck == false) {
                        System.out.println("\n\t\t\t\t\tPassword Should contain Atleast One Digit and One Alphabet.");
                    } else {
                        flag = false;
                        break;
                    }
                } else {
                    System.out.println("\n\t\t\t\t\tPassword should be of Length of 8.");
                }
            }
            try {
                String sql = "INSERT INTO user(username,mno,upid,bankname,accno,balance,passwords) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, mob);
                pst.setString(3, upi);
                pst.setString(4, bankName);
                pst.setString(5, accNum);
                pst.setInt(6, userAmount);
                pst.setString(7, pass);
                int r = pst.executeUpdate();
                //System.out.println((r > 0) ? "Inserted" : "Failed");
                f = false;
                break;
            } catch (Exception e) {
                System.out.println("\n\t\t\t\t\t***  Invalid Credentials.  ***");
            }
        }

    }
}