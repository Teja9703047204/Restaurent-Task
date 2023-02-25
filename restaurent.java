import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.*;

class assignment3 {
    Scanner sc = new Scanner(System.in);
    ArrayList<ArrayList<String>> a1 = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> a2 = new ArrayList<ArrayList<String>>();
    ArrayList<Integer> a3 = new ArrayList<>();
    ArrayList<Integer> a4 = new ArrayList<>();

    void readfiles() {
        try {
            Scanner s1 = new Scanner(new FileReader("menuList.csv"));
            Scanner s2 = new Scanner(new FileReader("orderDetails.csv"));
            String s;
            while (s1.hasNext()) {
                s = s1.nextLine();
                String[] str = s.split(",");
                List<String> dummy = Arrays.asList(str);
                ArrayList<String> a11 = new ArrayList<>(dummy);
                a1.add(a11);
            }
            while (s2.hasNext()) {
                String ss = s2.nextLine();
                String[] str1 = ss.split(",");
                List<String> dummy1 = Arrays.asList(str1);
                ArrayList<String> a21 = new ArrayList<>(dummy1);
                a2.add(a21);

            }
        } catch (Exception e) {
            System.out.println("Running Error");
        }
    }

    void display(ArrayList<ArrayList<String>> a1) {
        int n = a1.size();
        for (int i = 0; i < n; i++) {
            ArrayList<String> store = a1.get(i);
            for (String string : store) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    void menu() {
        String arr[] = { "Generate new bill", "View the total collection for today", "Cancel the bill" };
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(i + 1 + "-" + arr[i] + "\n");
        }
        System.out.print("Enter Your Choice: ");
    }

    void generate() {
        System.out.println("Generate new bill");
        display(a1);
        order();
    }

    void collection() {
        sc.nextLine();
        System.out.println("View the total collection for today");
        System.out.print("Enter date:: ");
        String s = sc.nextLine();
        Double collection = 0.0;
        for (int i = 0; i < (a2.size()); i++) {
            ArrayList<String> s1 = a2.get(i);
            if ((s1.get(1)).equals(s)) {
                Double b = Double.parseDouble(s1.get(2));
                collection += b;
                System.out.println(s1 + "\n");
            }
        }
        System.out.println("Total collection of day: " + collection);

    }

    void cancelbill() {
        System.out.println("Cancel the bill:: ");
        display(a2);
        System.out.print("Enter the id in Above list:: ");
        int n = sc.nextInt();
        int t = a2.size();
        if (n > t) {
            System.out.println("Enter Valid Id");
        } else {
            n = n - 1;
            (a2.get(n)).set(4, "cancelled");
            try {
                FileWriter objq = new FileWriter("orderDetails.csv", false);
                for (int i = 0; i < (a2.size()); i++) {
                    ArrayList<String> str1 = a2.get(i);
                    String str11 = String.join(",", str1);
                    str11 += "\n";
                    FileWriter objq1 = new FileWriter("orderDetails.csv", true);
                    objq1.write(str11);
                    objq1.close();
                }
                objq.close();
                display(a2);
                System.out.println("Cancelled Order");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Error");
            }
        }

    }

    void details() {
        int n = sc.nextInt();
        switch (n) {
            case 1:
                generate();
                break;
            case 2:
                collection();
                break;
            case 3:
                cancelbill();
                break;

            default:
                System.out.println("Enter Valid Number");
                break;
        }
    }

    void order() {
        System.out.print("Enter order Id: ");
        int n = sc.nextInt();
        System.out.print("Enter Quantity: ");
        int m = sc.nextInt();
        a3.add(n);
        a4.add(m);
        System.out.print("If you want to order again if Yes->press y or No->press n: ");
        char ch = sc.next().charAt(0);
        if (ch == 'y') {
            order();
        } else {
            int ordercount = a3.size();
            Double total = 0.0;
            System.out.print("check your details:: ");
            for (int i = 0; i < ordercount; i++) {
                int k = a3.get(i);
                int l = a4.get(i);
                ArrayList<String> dump2 = a1.get(k - 1);
                Double a = Double.parseDouble(dump2.get(2));
                total += a * l;
                System.out.println(a3.get(i) + " ");
            }
            System.out.println("total:  " + total);
            System.out.print("Do you want to confirm order if YES-Enter y or No-Enter n: ");
            char ch1 = sc.next().charAt(0);
            if (ch1 == 'y') {
                String s = ",";
                LocalDate date1 = LocalDate.now();
                DateTimeFormatter obj22 = DateTimeFormatter.ofPattern("d-MMM-yy");
                String date = date1.format(obj22);
                int x = a2.size() + 1;
                String a = x + s + date + s + total + .00 + s;
                for (int i = 0; i < (a3.size()); i++) {
                    a += a3.get(i) + " ";
                    a += a4.get(i) + " ";
                }
                a += s + "Approved";
                try {
                    File newn = new File("orderDetails.csv");
                    FileWriter obj2 = new FileWriter(newn, true);
                    obj2.write("\n" + a);
                    obj2.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("error");
                }

                System.out.println("Thank You Visit Again");
            } else {
                menu();
                details();
            }

        }
    }

}

class restaurent {
    public static void main(String[] args) {
        assignment3 obj = new assignment3();
        obj.readfiles();
        obj.menu();
        obj.details();

    }
}