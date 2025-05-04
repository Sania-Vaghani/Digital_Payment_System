package Train;

import Main.Connect;
import Main.DPS;

import java.io.*;
import java.util.*;
import java.sql.*;

public class Train {
    static Scanner sc = new Scanner(System.in);
    double amount, seatNumber1, seatNumber2;
    int seat;
    int trainNum = 0;
    String trainName, trainRoute, journeyTime, source, destination, deptTime;
    char ch = 'A';
    ArrayList<String> sn = new ArrayList<>();
    int k = 0;
    DPS dps = new DPS();

    public void trainTicket(Connection con, int id) throws Exception {
        Train t = new Train();

        System.out.println("\n...~...~...~...~...~...~...~...~...~...~...~...");
        System.out.println("\n\t-:Railway Reservation System :- ");
        System.out.println("\n...~...~...~...~...~...~...~...~...~...~...~...");
        boolean flag = true;
        while (flag) {
            System.out.println("\n\nPress [1] to Check Available Train.");
            System.out.println("Press [2] to Book Train Tickets.");
            System.out.println("Press [3] to Display Your Train Detail By Searching Train Number.");
            System.out.println("Press [4] to Cancel Your Train.");
            System.out.println("Press [5] to See Past Booking History.");
            System.out.println("Press [6] to Exit");
            System.out.print("Enter : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    t.availableTrain();
                    break;
                case 2:
                    t.bookTrain(id);
                    break;
                case 3:
                    t.displayTicket(id);
                    break;
                case 4:
                    t.cancelTicket(con);
                    break;
                case 5:
                    t.pastBookingHistory(con);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter Valid Choice");

            }
        }
    }

    public void availableTrain() throws Exception {

        BufferedReader br = new BufferedReader(
                new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\TrainDetail.txt"));
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        System.out.println(
                "Train Number       Train Name            Train Route                   Journey Time            Departure Time");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        String line;
        while ((line = br.readLine()) != null) {
            String[] train = line.split(" ");
            trainNum = Integer.parseInt(train[0].trim());
            trainName = train[1].trim();
            trainRoute = train[2].trim();
            journeyTime = train[3].trim();
            deptTime = train[4].trim();
            System.out.printf("%-15d   %-20s   %-25s       %-20s  %-10s%n", trainNum, trainName, trainRoute,
                    journeyTime,
                    deptTime);
        }
    }

    public void bookTrain(int id) throws Exception {
        System.out.print("\tEnter Train Number : ");
        trainNum = sc.nextInt();
        Boolean flag = false;
        BufferedReader br1 = new BufferedReader(
                new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\TrainDetail.txt"));
        String line1;

        while ((line1 = br1.readLine()) != null) {
            String[] train1 = line1.split(" ");
            int trainNum1 = Integer.parseInt(train1[0].trim());
            if (trainNum1 == trainNum) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag == true) {

            System.out.print("\tEnter Number of Seats to Book : ");
            seat = sc.nextInt();

            boolean flag1 = true;
            while (flag1) {
                System.out.println("\n\tEnter Seat Preference");
                System.out.println("\n\tPress[1] for AC Seater Class.");
                System.out.println("\tPress[2] for AC 2-Tier Class.");
                System.out.println("\tPress[3] for AC 3-Tier Class.");
                System.out.println("\tPress[4] for Non-AC 2-Tier Class.");
                System.out.println("\tPress[5] for Non-AC 3-Tier Class.");
                System.out.println("\tPress[6] General Class.");
                System.out.print("\tEnter Your Choice : ");
                int preference = sc.nextInt();

                switch (preference) // Switch will pass the choice of user
                {
                    case 1:
                        System.out.println("\tYou have Selected AC Seater Class.");
                        amount = 1500 * seat;
                        flag1 = false;
                        break;
                    case 2:
                        System.out.println("\tYou have Selected AC 2-Tier Class.");
                        amount = 1350 * seat;
                        flag1 = false;
                        break;
                    case 3:
                        System.out.println("\tYou have Selected AC 3-Tier Class.");
                        amount = 1200 * seat;
                        flag1 = false;
                        break;
                    case 4:
                        System.out.println("\tYou have Selected Non-AC 2-Tier Class.");
                        amount = 950 * seat;
                        flag1 = false;
                        break;
                    case 5:
                        System.out.println("\tYou have Selected Non-AC 3-Tier Class.");
                        amount = 700 * seat;
                        flag1 = false;
                        break;
                    case 6:
                        System.out.println("\tYou have Selected General Class.");
                        amount = 500 * seat;
                        flag1 = false;
                        break;
                    default:
                        System.out.println("\tInvalid Input.");
                        break;
                }
            }

            for (int i = 0; i < seat; i++) // seat number will be generated enetered by user
            {
                seatNumber1 = Math.random() * 100;
                seatNumber2 = Math.random() * 100;

                if (ch > 'Z') {
                    ch = 'A';
                }
                while (seatNumber1 == seatNumber2) // if two random seat number generated are same than once again
                                                   // new seat
                                                   // number will be generated
                {
                    seatNumber1 = Math.random() * 100;
                    seatNumber2 = Math.random() * 100;
                }
                seatNumber1 = seatNumber1 + seatNumber2;

                String seatNum = "" + ch + (int) seatNumber1;
                sn.add(seatNum);
                ch++;
            }
            BufferedReader br = new BufferedReader(
                    new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\TrainDetail.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] train = line.split(" ");
                if (train[0].equals(trainNum + "")) {
                    trainName = train[1].trim();
                    trainRoute = train[2].trim();
                    deptTime = train[4].trim();
                    String[] route = trainRoute.split("--->");
                    source = route[0].trim();
                    destination = route[1].trim();
                }
            }
            System.out.println("\nYour Train Tickets Are Successfully Booked...");
            System.out.println("\n\t..._..._..._..._..._..._..._..._..._..._..._..._");
            System.out.println("\n\t\t  Train Number\t : " + trainNum);
            System.out.println("\t\t  Your Seat Number : " + String.join(",", sn));
            System.out.println("\t\t  Your Total Ticket Price : " + amount);
            System.out.println("\t\t  Train Name : " + trainName);
            System.out.println("\t\t  Soure : " + source);
            System.out.println("\t\t  Destination  : " + destination);
            System.out.println("\t\t  Departure Time  : " + deptTime);
            System.out.println("\n\t..._..._..._..._..._..._..._..._..._..._..._..._\n");

            Connection con = Connect.Check();

            String sql = "INSERT INTO train(trainno,userid,seatbook,amount,source,destination,departure_time) VALUES (?,?,?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainNum);
            ps.setInt(2, id);
            ps.setString(3, String.join(",", sn));
            ps.setDouble(4, amount);
            ps.setString(5, source);
            ps.setString(6, destination);
            ps.setString(7, deptTime);
            ps.executeUpdate();

            String sql1 = "SELECT bookid from train order by bookid desc limit 1";
            PreparedStatement pst = con.prepareStatement(sql1);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Your Booking Id := " + rs.getInt("bookid"));
            }
        } else {
            System.out.println("No such train available");

        }
    }

    public void displayTicket(int id) throws Exception {
        // check from database if ticket booked or not for that train
        System.out.print("\tEnter Your Booking ID : ");
        int bid = sc.nextInt();

        Connection con = Connect.Check();
        String sql = "SELECT * from train where bookid = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getInt("bookid") == bid) {
                System.out.print("\tEnter Your Train Number : ");
                trainNum = sc.nextInt();
                System.out.println("\n\t..._..._..._..._..._..._..._..._..._..._..._...");
                BufferedReader br = new BufferedReader(
                        new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\TrainDetail.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] train = line.split(" ");
                    if (train[0].equals(trainNum + "")) {
                        trainName = train[1].trim();
                        trainRoute = train[2].trim();
                        deptTime = train[4].trim();
                        String[] route = trainRoute.split("--->");
                        source = route[0].trim();
                        destination = route[1].trim();
                        break;
                    }
                }
                double amount = rs.getDouble(5);
                String sn = rs.getString(4);
                System.out.println("\nYour Train Ticket...");
                System.out.println("\n\t..._..._..._..._..._..._..._..._..._..._..._..._");
                System.out.println("\n\t\t  Train Number\t : " + trainNum);
                System.out.println("\t\t  Your Seat Number : " + sn);
                System.out.println("\t\t  Your Total Ticket Price : " + amount);
                System.out.println("\t\t  Train Name : " + trainName);
                System.out.println("\t\t  Soure : " + source);
                System.out.println("\t\t  Destination  : " + destination);
                System.out.println("\t\t  Departure Time  : " + deptTime);
                System.out.println("\n\t..._..._..._..._..._..._..._..._..._..._..._..._\n");
            } else {

            }

        } else {
            System.out.println("\n\t\t\t***  Train Not Found  ***");
        }
    }

    public void cancelTicket(Connection con) throws Exception {
        System.out.print("\tEnter Booking ID : ");
        int bid = sc.nextInt();
        System.out.print("\tEnter Train Number : ");
        int trainnum = sc.nextInt();
        String sql = "DELETE from train WHERE bookid = ? and trainno = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, bid);
        pst.setInt(2, trainnum);
        int r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("\n\t\t\t***  Ticket Cancelled Successfully  ***");
        } else {
            System.out.println("\n\t\t\t***  Ticket Not Found  ***");
        }
    }

    public void pastBookingHistory(Connection con) throws Exception {
        String sql = "select * from train where userid=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, dps.userid);
        ResultSet rs = pst.executeQuery();
        if (rs.isBeforeFirst() == true) {
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Booking ID       Train Number         Source                 Destination                Seat Book                       Amount                 Departure Time");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-15d %-20d %-25s %-25s %-30s %-24.2f %-25s %n", rs.getInt(1), rs.getInt(2),
                        rs.getString(6),
                        rs.getString(7), rs.getString(4), rs.getDouble(5), rs.getString(8));
            }
        } else {
            System.out.println("\n\t\t\t***  No Booking History  ***");
        }
    }

}
