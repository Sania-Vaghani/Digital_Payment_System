package Main;

import Train.*;
import Movie.*;
import Main.Registration.*;
import Main.Connect.*;

import java.io.*;
import java.util.*;
import java.sql.*;

public class DPS {
    static Scanner sc = new Scanner(System.in);
     public static int userid = 0;
    public static void main(String[] args) throws Exception {

        Connection con = Connect.Check();

        HashMap<String, Integer> hm = new HashMap<>();

        System.out.println("\t\t\t\t\t-^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^-");
        System.out.println("\t\t\t\t\t\t\t DIGITAL PAYMENT SYSTEM");
        System.out.println("\t\t\t\t\t-^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^--^.^-");

        boolean flag = true;
        int balance = 0;
        String mobile = "";
        String username = "";
        String mob = "";
        String pass = "";
       

        while (flag) {
            System.out.println("\n\t\t\t\t\tPress [1] if want to Login.");
            System.out.println("\t\t\t\t\tPress [2] if want to SignUp.");
            System.out.println("\t\t\t\t\tPress [3] to Exit.");
            System.out.print("\n\t\t\t\t\tEnter Your Choice : ");
            int user_input = sc.nextInt();
            sc.nextLine();
            switch (user_input) {
                case 1:
                    System.out.print("\n\t\t\t\t\tEnter Your Mobile Number : ");
                    String uMob = sc.nextLine();
                    System.out.print("\t\t\t\t\tEnter Password : ");
                    String upassMob = sc.nextLine();
                    
                    String query = "SELECT mno,passwords from user where mno = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, uMob);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()) {
                        mob = rs.getString("mno");
                        pass = rs.getString("passwords");
                    }
                    if(mob.equals(uMob))
                    {
                        if(pass.equals(upassMob))
                        {
                            System.out.println("\n\t\t\t\t\tLogin Successful");
                            String sql = "SELECT userid FROM user where mno = ? ";
                            PreparedStatement pst = con.prepareStatement(sql);
                            pst.setString(1, uMob);
                            ResultSet rs1 = pst.executeQuery();
                            while(rs1.next()) {
                                userid = rs1.getInt("userid");
                            }
                            System.out.println("\n\t\t\t\t\tPress [1] to Book Train.");
                            System.out.println("\t\t\t\t\tPress [2] to Book Movie.");
                            System.out.print("\t\t\t\t\tEnter Your Choice : ");
                            int ch = sc.nextInt();
                            switch (ch) {
                                case 1:
                                    Train t = new Train();
                                    t.trainTicket(con,userid);
                                    break;
                                case 2:
                                    Movie m=new Movie();
                                    m.main();
                                default:
                                    break;
                            }
                        }else{
                            System.out.println("\n\t\t\t\t\tWrong Password");
                        }
                    }else
                    {
                        System.out.println("\n\t\t\t\t\tWrong Mobile Number");
                    }
                    break;
                case 2:
                    boolean otp = true;
                    boolean mno = true;
                    while (mno) {
                        BufferedReader br = new BufferedReader(
                                new FileReader("D:/PROJECT/Group_Project/DigitalPaymentSystem/BankDetail.txt"));
                        String x;
                        while ((x = br.readLine()) != null) {
                            String word[] = x.split(" ");
                            balance = Integer.parseInt(word[1]);
                            mobile = word[0];
                            hm.put(word[0], balance);
                        }
                        br.close();
                        System.out.print("\n\t\t\t\t\tEnter Your Number : ");
                        String userMob = sc.nextLine();
                        if (Registration.checkMobile(userMob) == true)
                        {
                            mno = false;
                            if (hm.containsKey(userMob)) {
                                while (otp) {
                                    int userOTP = 0;
                                    int otpstore = Registration.generateOTP();
                                    System.out.println("\n\t\t\t\t\tGenerated OTP : " + otpstore);
                                    try {
                                        System.out.print("\t\t\t\t\tEnter OTP : ");
                                        userOTP = sc.nextInt();
                                        if (otpstore == userOTP) {
                                            Registration.userData(hm, userMob, con);
                                            otp = false;

                                        } else {
                                            System.out.println("\n\t\t\t\t\t*** INVALID OTP *** ");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("\n\t\t\t\t\t*** Enter Valid OTP in Integer ***");
                                        sc.nextLine();
                                    }
                                }
                            }
                            else{
                                System.out.println("\n\t\t\t\t\t*** Enter Valid Mobile Number ***");
                            }
                        } else {
                            System.out.println("\n\t\t\t\t\t*** Enter Valid Mobile Number ***");
                        }
                    }
                    break;
                case 3:
                    flag = false;
                    System.out.println("\n\t\t\t\t\tExiting the system.");
                    break;
                default:
                    System.out.println("\n\t\t\t\t\tInvalid choice. Please Enter 1, 2, or 3.");
                    break;
            }
        }
    }
}
