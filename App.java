import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Stud", "root", "root");
            displayMenu(connection);
            connection.close();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void displayMenu(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    AddStudent(connection);
                    break;
                case 2:
                    StudentList(connection);
                    break;
                case 3:
                    UpdateStudents(connection);
                    break;
                case 4:
                    DeleteStudent(connection);
                    break;
                case 0:
                    System.out.println("bye...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void AddStudent(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of students to add: ");
            int numStudents = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numStudents; i++) {
                System.out.println("Enter details for Student " + (i + 1) + ":");

                System.out.print("Enter the name: ");
                String name = scanner.nextLine();

                System.out.print("Enter the email: ");
                String email = scanner.nextLine();

                System.out.print("Enter the mobile number: ");
                String mobileNo = scanner.nextLine();

                String sql = "INSERT INTO table1 (name, email, mobile_no) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, mobileNo);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Failed to add Student.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void StudentList(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM table1";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String mobileNo = resultSet.getString("mobile_no");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Mobile No: " + mobileNo);
                System.out.println("-");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void UpdateStudents(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of students to update: ");
            int numStudents = scanner.nextInt();
            scanner.nextLine();
    
            for (int i = 0; i < numStudents; i++) {
                System.out.println("Enter details for Student " + (i + 1) + " to update:");
    
                System.out.print("Enter the ID of the student to update: ");
                int id = scanner.nextInt();
                scanner.nextLine();
    
                String sql = "SELECT * FROM table1 WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
    
                    if (resultSet.next()) {
                        System.out.println("Enter the new details:");
    
                        System.out.print("Enter the name: ");
                        String name = scanner.nextLine();
    
                        System.out.print("Enter the email: ");
                        String email = scanner.nextLine();
    
                        System.out.print("Enter the mobile number: ");
                        String mobileNo = scanner.nextLine();
    
                        sql = "UPDATE table1 SET name = ?, email = ?, mobile_no = ? WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                            updateStatement.setString(1, name);
                            updateStatement.setString(2, email);
                            updateStatement.setString(3, mobileNo);
                            updateStatement.setInt(4, id);
    
                            int rowsUpdated = updateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("Student updated successfully!");
                            } else {
                                System.out.println("Failed to update Student.");
                            }
                        }
                    } else {
                        System.out.println("No Student found with the given ID.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    private static void DeleteStudent(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ID of the Student to delete: ");
            int id = scanner.nextInt();


            String sql = "DELETE FROM table1 WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("No Student found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}

