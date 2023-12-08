import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class DatabaseInterface {
    public static void main(String[] args) throws IOException {

        //set the path of your credentials forlder to read your username and password
        String fileName = "assets/credentials.txt";
        String username = "";
        String password = "";

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        username = bufferedReader.readLine();
        password = bufferedReader.readLine();

        String url = "jdbc:postgresql://s-l112.engr.uiowa.edu/mdb_student16";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");

            Scanner scanner = new Scanner(System.in);

            boolean isRunning = true;

            while (isRunning){
                System.out.println("Database Menu:");
                System.out.println("1. List all countries");
                System.out.println("2. Search for/select a city");
                System.out.println("3. Add a new city");
                System.out.println("4. Update a city");
                System.out.println("5. Delete a city");
                System.out.println("6. List all active venues by country");
                System.out.println("7. List all inactive venues");
                System.out.println("8. Delete a venue using the name");
                System.out.println("9. Add an event");
                System.out.println("10. Exit");
                System.out.print("Enter your choice: ");

                int input = scanner.nextInt();
                scanner.nextLine();

                if(input == 1){
                    Task2Functions.listCountries(connection);
                }
                else if (input == 2){
                    Task2Functions.searchCity(connection, scanner);

                }
                else if(input == 3){
                    Task2Functions.addCity(connection,scanner);
                }
                else if (input == 4){
                    Task2Functions.updateCity(connection,scanner);
                }
                else if(input == 5){
                    Task2Functions.deleteCity(connection,scanner);
                }
                else if(input == 6){
                    Task3Functions.listActiveVenuesByCountry(connection,scanner);
                }
                else if(input == 7){
                    Task3Functions.listInactiveVenues(connection);
                }
                else if(input == 8){
                    Task3Functions.deleteVenueByName(connection,scanner);
                }
                else if(input == 9){
                    Task4Functions.addEvent(connection,scanner);
                }
                else if (input == 10){
                    isRunning = false;
                }
            }


            connection.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
