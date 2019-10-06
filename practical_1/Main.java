import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static Request getRequest(Scanner sc) {
        return new Request(sc.nextInt(), sc.nextInt(), sc.nextInt());
    }
    
    public static List<Driver> getDrivers(Scanner sc) {
        List<Driver> driversList = new ArrayList<>();

        while (sc.hasNext()) {
            String driverType = sc.next();
            String licensePlate = sc.next();
            int waitTime = sc.nextInt();

            if (driverType.equals("NormalCab")) {
                driversList.add(new NormalCab(licensePlate, waitTime));
            } else if (driverType.equals("PrivateCar")) {
                driversList.add(new PrivateCar(licensePlate, waitTime));
            }
        }

        return driversList;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Request request = getRequest(sc);
        List<Driver> driversList = getDrivers(sc);
        List<Booking> bookingsList = new ArrayList<>();

        for (Driver driver : driversList) {
            bookingsList.add(new Booking(driver, new JustRide(), request));
            
            if (driver instanceof NormalCab) {
                bookingsList.add(new Booking(driver, new TakeACab(), request));
            } else if (driver instanceof PrivateCar) {
                bookingsList.add(new Booking(driver, new ShareARide(), request));
            }
        }

        Collections.sort(bookingsList);
        
        for (Booking booking : bookingsList) {
            System.out.println(booking);
        }
    }
}
