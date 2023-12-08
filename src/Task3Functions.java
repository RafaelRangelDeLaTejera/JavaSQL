import java.sql.*;
import java.util.Scanner;
public class Task3Functions {
    public static void listActiveVenuesByCountry(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter country code: ");
        String countryCode = scanner.nextLine();

        String query = "SELECT * FROM homework.venues WHERE country_code = ? AND active = true";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, countryCode);

        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Active Venues in " + countryCode + ":");
        while (resultSet.next()) {
            String venueName = resultSet.getString("name");
            System.out.println("Venue Name: " + venueName);
        }

        resultSet.close();
        preparedStatement.close();
    }

    // List Inactive Venues
    public static void listInactiveVenues(Connection connection) throws SQLException {
        String query = "SELECT * FROM homework.venues WHERE active = false";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("Inactive Venues:");
        while (resultSet.next()) {
            String venueName = resultSet.getString("name");
            System.out.println("Venue Name: " + venueName);
        }

        resultSet.close();
        statement.close();
    }

    // Delete a Venue Using the Venue Name
    public static void deleteVenueByName(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter venue name to delete: ");
        String venueNameToDelete = scanner.nextLine();

        String query = "SELECT * FROM homework.venues WHERE name = ? AND active = true";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, venueNameToDelete);
        ResultSet resultSet = preparedStatement.executeQuery();

        // List matching venues and ask for confirmation
        System.out.println("Matching Venues to Delete:");
        if (resultSet.next()) {
            String venueName = resultSet.getString("name");
            int venueId = resultSet.getInt("venue_id");
            System.out.println("Venue Name: " + venueName + ", Venue ID: " + venueId);

            System.out.print("Confirm deletion (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y")) {
                // Set venues as inactive
                query = "UPDATE homework.venues SET active = false WHERE name = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, venueNameToDelete);
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Venues deleted successfully.");
                } else {
                    System.out.println("No matching venues found to delete.");
                }
            } else {
                System.out.println("Deletion canceled.");
            }


        }
else{
            System.out.println("No matches");
}




        resultSet.close();
        preparedStatement.close();
    }

}
