import java.sql.*;
import java.util.Scanner;
public class Task4Functions {
    public static void addEvent(Connection connection, Scanner scanner) throws SQLException {
        try {
            // Prompt the user for event information
            System.out.print("Enter event title: ");
            String title = scanner.nextLine();

            System.out.print("Enter event start timestamp (YYYY-MM-DD HH:MM:SS): ");
            String starts = scanner.nextLine();

            System.out.print("Enter event end timestamp (YYYY-MM-DD HH:MM:SS): ");
            String ends = scanner.nextLine();

            System.out.print("Enter venue name: ");
            String venue = scanner.nextLine();

            System.out.print("Enter venue postal code: ");
            String postal = scanner.nextLine();

            System.out.print("Enter venue country code: ");
            String country = scanner.nextLine();

            // make sure the stored procedure modifies the venues of the homework schema
            Statement setSchemaStatement = connection.createStatement();
            setSchemaStatement.execute("SET search_path TO homework");
            setSchemaStatement.close();


            // Call the add_event procedure
            CallableStatement addEventProcedure = connection.prepareCall("{ ? = call add_event(?, CAST(? AS TIMESTAMP), CAST(? AS TIMESTAMP), ?, ?, ?) }");
            addEventProcedure.registerOutParameter(1, Types.BOOLEAN);  // Out parameter for the return value
            addEventProcedure.setString(2, title);
            addEventProcedure.setString(3, starts);
            addEventProcedure.setString(4, ends);
            addEventProcedure.setString(5, venue);
            addEventProcedure.setString(6, postal);
            addEventProcedure.setString(7, country);

            addEventProcedure.execute();

            boolean eventAdded = addEventProcedure.getBoolean(1);

            if (eventAdded) {
                System.out.println("Event added successfully.");
            } else {
                System.out.println("Failed to add the event.");
            }

            addEventProcedure.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add the event. Please check your input.");
        }
    }


}
