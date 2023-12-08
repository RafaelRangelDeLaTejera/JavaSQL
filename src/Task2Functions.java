import java.sql.*;
import java.util.Scanner;

public class Task2Functions {

    public static void listCountries(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM homework.countries");

        System.out.println("Country list:");
        while (resultSet.next()) {
            String countryCode = resultSet.getString("country_code");
            String countryName = resultSet.getString("country_name");
            System.out.println(countryCode + ": " + countryName);
        }

        resultSet.close();
        statement.close();
    }

    public static void searchCity(Connection connection, Scanner scanner) throws SQLException {

        System.out.println("Choose how you want to search the city");
        System.out.println("1. postal code");
        System.out.println("2. country code");
        System.out.println("3. city name");
        System.out.println("4. postal code and country code");
        System.out.println("5. postal code and city name");
        System.out.println("6. country code and city name");
        System.out.println("7. all three");

        int option = scanner.nextInt();
        scanner.nextLine();

        String query = "SELECT * FROM homework.cities WHERE ";


        if(option == 1){
            System.out.print("Enter postal code: ");
            String postalCode = scanner.nextLine();

            query += "postal_code = '" + postalCode + "'";
        }
        else if(option == 2){
            System.out.print("Enter country code: ");
            String countryCode = scanner.nextLine();

            query += "country_code = '" + countryCode + "'";

        }
        else if(option == 3){
            System.out.print("Enter city name: ");
            String cityName = scanner.nextLine();

            query += "name = '" + cityName + "'";
        }
        else if(option == 4){
            System.out.println("Enter postal code: ");
            String postalCode = scanner.nextLine();

            System.out.println("Enter country code: ");
            String countryCode = scanner.nextLine();

            query += "postal_code = '" + postalCode + "'" + " AND " + "country_code = '" + countryCode + "'";

        }
        else if(option == 5){
            System.out.println("Enter postal code: ");
            String postalCode = scanner.nextLine();

            System.out.println("Enter city name: ");
            String cityName = scanner.nextLine();

            query += "postal_code = '" + postalCode + "'" + " AND " + "name = '" + cityName + "'";
        }
        else if(option == 6){
            System.out.println("Enter country code: ");
            String countryCode = scanner.nextLine();

            System.out.println("Enter city name: ");
            String cityName = scanner.nextLine();

            query += "country_code = '" + countryCode + "'" + " AND " + "name = '" + cityName + "'";

        }
        else if(option == 7){
            System.out.println("Enter postal code: ");
            String postalCode = scanner.nextLine();

            System.out.println("Enter country code: ");
            String countryCode = scanner.nextLine();

            System.out.println("Enter city name: ");
            String cityName = scanner.nextLine();

            query += "postal_code = '" + postalCode + "'" + " AND " + "country_code = '" + countryCode + "'" + " AND " + "name = '" + cityName + "'";

        }


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("City Results:");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String code = resultSet.getString("postal_code");
            String country = resultSet.getString("country_code");
            System.out.println("Name: " + name + ", Postal Code: " + code + ", Country Code: " + country);
        }

        resultSet.close();
        statement.close();
    }

    public static void addCity(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();

        System.out.print("Enter postal code: ");
        String postalCode = scanner.nextLine();

        System.out.print("Enter country code: ");
        String countryCode = scanner.nextLine();

        String insertQuery = "INSERT INTO homework.cities (name, postal_code, country_code) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        insertStatement.setString(1, cityName);
        insertStatement.setString(2, postalCode);
        insertStatement.setString(3, countryCode);

        int rowsInserted = insertStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("City added successfully.");
        } else {
            System.out.println("Failed to add the city.");
        }

        insertStatement.close();
    }

    public static void updateCity(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter current city name: ");
        String currentCityName = scanner.nextLine();

        System.out.print("Enter current postal code: ");
        String currentPostalCode = scanner.nextLine();

        System.out.print("Enter new city name (or leave empty): ");
        String newCityName = scanner.nextLine();

        System.out.print("Enter new postal code (or leave empty): ");
        String newPostalCode = scanner.nextLine();

        System.out.print("Enter new country code (or leave empty): ");
        String newCountryCode = scanner.nextLine();

        String updateQuery = "UPDATE homework.cities SET name = COALESCE(?, name), postal_code = COALESCE(?, postal_code), country_code = COALESCE(?, country_code) WHERE name = ? AND postal_code = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setString(1, newCityName.isEmpty() ? null : newCityName);
        updateStatement.setString(2, newPostalCode.isEmpty() ? null : newPostalCode);
        updateStatement.setString(3, newCountryCode.isEmpty() ? null : newCountryCode);
        updateStatement.setString(4, currentCityName);
        updateStatement.setString(5, currentPostalCode);

        int rowsUpdated = updateStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("City updated successfully.");
        } else {
            System.out.println("No matching city found to update.");
        }

        updateStatement.close();
    }

    public static void deleteCity(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter city name to delete: ");
        String cityNameToDelete = scanner.nextLine();

        System.out.print("Enter postal code of the city to delete: ");
        String postalCodeToDelete = scanner.nextLine();

        String deleteQuery = "DELETE FROM homework.cities WHERE name = ? AND postal_code = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setString(1, cityNameToDelete);
        deleteStatement.setString(2, postalCodeToDelete);

        int rowsDeleted = deleteStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("City deleted successfully.");
        } else {
            System.out.println("No matching city found to delete.");
        }

        deleteStatement.close();
    }
}
