package Movie;

import java.io.*;
import java.util.*;

public class Movie {
    static Scanner sc = new Scanner(System.in);
    String[][] regular = new String[7][9];
    String[][] recliner = new String[2][8];
    ArrayList<Integer> m1 = new ArrayList<>();
    ArrayList<Integer> m2 = new ArrayList<>();
    ArrayList<Integer> m3 = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Movie m = new Movie();
        m.main();
    }

    public void main() throws Exception {
        boolean movieLoop = true;
        while (movieLoop) {
            System.out.println();
            System.out.print("\n1.SEE MOVIE LIST\n2.BOOK TICKTES\n3.EXIT\nEnter your choice:");
            int movieChoice = sc.nextInt();
            switch (movieChoice) {
                case 1:
                    movieList();
                    break;
                case 2:
                    seatChoose();
                    break;
                case 3:
                    movieLoop = false;
                    // main ma redirect karvanu che!!
                    break;
                default:
                    System.out.println("Invalid Choice!!");
                    break;
            }
        }
    }

    void seatSetter() {
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 9; j++) {
                String val = "" + i + j;
                regular[i - 1][j - 1] = val;
            }
        }
        for (int i = 8; i <= 9; i++) {
            for (int j = 0; j < 8; j++) {
                String val = "" + i + (j + 1);
                recliner[i - 8][j] = val;
            }
        }
    }

    void movieList() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("D:\\PROJECT\\Group_Project\\DigitalPaymentSystem\\MovieDetail.txt"));
        String read;
        System.out.println();
        System.out.println("-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^ MOVIE LIST ^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-");
        System.out.println();
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Number\t\t    Name\t\tPrice\t\t     Duration");
        System.out.print("------------------------------------------------------------------------");
        while ((read = br.readLine()) != null) {
            String[] mDetails = read.split(" ");
            System.out.printf("\n%5d %20s %20f %20s ",
                    Integer.parseInt(mDetails[0]), mDetails[1], Double.parseDouble(mDetails[2]), mDetails[3]);
        }
        br.close();
    }

    void setSeats(ArrayList<Integer> al) {
        seatSetter();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if (al.contains(Integer.parseInt(regular[i][j]))) {
                        regular[i][j] = "BK";
                    }
                } catch (Exception e) {
                    // System.out.println(e);
                }
                System.out.print("    |" + regular[i][j] + "|    ");
            }
            System.out.println();
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    if (al.contains(Integer.parseInt(recliner[i][j]))) {
                        recliner[i][j] = "BK";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (j == 0)
                    System.out.print("          |" + recliner[i][j] + "|    ");
                else {
                    System.out.print("    |" + recliner[i][j] + "|    ");
                }
            }
            System.out.println();
        }
    }

    void seatChoose() throws Exception {
        movieList();
        System.out.println();
        boolean checkMovieID = false;
        while (!checkMovieID) {
            System.out.print("Select moive : ");
            int movieId = sc.nextInt();
            if (movieId >= 1 && movieId <= 3) {
                checkMovieID = true;
                if (checkMovieID) {
                    if (movieId == 1) {
                        selectSeats(checkMovieID, m1);
                    } else if (movieId == 2) {
                        selectSeats(checkMovieID, m2);
                    } else if (movieId == 3) {
                        selectSeats(checkMovieID, m3);
                    }
                }
            }
        }
    }

    void selectSeats(boolean id, ArrayList<Integer> al) {
        if (id) {
            setSeats(al);
            int seatNumber, num;
            String seat;
            System.out.print("1.Regular Seats\n2.Recliner Seats\nEnter your choice:");
            int seatChoice = sc.nextInt();
            switch (seatChoice) {
                case 1:
                    System.out.print("Enter number of seats you want to book:");
                    num = sc.nextInt();
                    for (int k = 0; k < num; k++) {
                        System.out.print("Enter seat number" + (k + 1) + ":");
                        seatNumber = sc.nextInt();
                        if (al.contains(seatNumber)) {
                            System.out.println("Seat unavailable");
                            continue;
                        } else {
                            al.add(seatNumber);
                        }
                        seat = "" + seatNumber;
                        int regularFlag = 0;
                        for (int i = 0; i < regular.length; i++) {
                            for (int j = 0; j < regular[0].length; j++) {
                                if (regular[i][j].equals(seat)) {
                                    regularFlag = 1;
                                    regular[i][j] = "BK";
                                    break;
                                }
                            }
                        }
                        if (regularFlag == 0) {
                            System.out.println("Enter valid seat number!");
                        }
                    }
                    setSeats(al);
                    break;
                case 2:
                    System.out.print("Enter number of seats you want to book:");
                    num = sc.nextInt();
                    for (int k = 0; k < num; k++) {
                        System.out.print("Enter seat number " + (k + 1) + ":");
                        seatNumber = sc.nextInt();
                        if (al.contains(seatNumber)) {
                            System.out.println("Seat unavailable");
                            continue;
                        } else {
                            al.add(seatNumber);
                        }
                        seat = "" + seatNumber;
                        int reclinerFlag = 0;
                        for (int i = 0; i < recliner.length; i++) {
                            for (int j = 0; j < recliner[0].length; j++) {
                                if (regular[i][j].equals(seat)) {
                                    reclinerFlag = 1;
                                    regular[i][j] = "BK";
                                    break;
                                }
                            }
                        }
                        if (reclinerFlag == 0) {
                            System.out.println("Enter valid seat number!");
                        }
                    }
                    setSeats(al);
                    break;
                default:
                    System.out.println("Inavlid Choice!!");
            }
        }
    }

}
