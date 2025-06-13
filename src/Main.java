import builder.EmployeeBuilder;
import model.Department;
import model.Employee;
import model.EmployeeRole;
import observer.FinanceDepartmentObserver;
import observer.ITDepartmentObserver;
import service.HRManager;
import strategy.*;
import util.OrgChartUtil;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * The entry point of the HR Management System application.
 * This class demonstrates how to use the various components and design patterns
 * (Singleton, Builder, Factory, Observer, Strategy, Recursion) together.
 */
public class Main {
    public static void main(String[] args) {
        // --- 1. Singleton: Get the single instance of HRManager ---
        // The HRManager uses the Singleton pattern to ensure only one instance exists application-wide.
        HRManager hrManager = HRManager.getInstance();
        System.out.println("HR Manager initialized.");

        // --- 2. Observer: Add observers ---
        // Observers (ITDepartmentObserver, FinanceDepartmentObserver) are registered with the notifier.
        // They will be automatically informed about employee hiring and termination events.
        hrManager.getNotifier().addObserver(new ITDepartmentObserver());
        hrManager.getNotifier().addObserver(new FinanceDepartmentObserver());

        // Get departments for convenience. Using orElseThrow() for mandatory departments.
        Department hrDept = hrManager.getDepartmentByName("HR")
                .orElseThrow(() -> new IllegalStateException("HR Department not found!"));
        Department itDept = hrManager.getDepartmentByName("IT")
                .orElseThrow(() -> new IllegalStateException("IT Department not found!"));
        Department salesDept = hrManager.getDepartmentByName("SALES")
                .orElseThrow(() -> new IllegalStateException("SALES Department not found!"));

        // --- 3. Builder and Factory Method: Create employees ---
        System.out.println("\n--- Adding Employees ---");

        // The EmployeeBuilder uses the Builder pattern to construct Employee objects step-by-step.
        // It then uses the Factory pattern internally to create the specific type (FullTime, PartTime).
        Employee ceo = new EmployeeBuilder()
                .setId("E000")
                .setFirstName("Andrii")
                .setLastName("Shevchenko")
                .setDepartment(hrDept)
                .setRole(EmployeeRole.ADMINISTRATOR)
                .setEmployeeType("FULLTIME")
                .setBaseSalary(150000.0)
                .setManager(null)
                .build();
        hrManager.addEmployee(ceo);

        // Full-time employee (FullTimeEmployee)
        Employee emp1_full = new EmployeeBuilder()
                .setId("E001")
                .setFirstName("Olena")
                .setLastName("Kovalchuk")
                .setDepartment(itDept)
                .setRole(EmployeeRole.DEVELOPER)
                .setEmployeeType("FULLTIME")
                .setBaseSalary(75000.0)
                .setManager(ceo)
                .build();
        hrManager.addEmployee(emp1_full);

        // Part-time employee (PartTimeEmployee)
        Employee emp2_part = new EmployeeBuilder()
                .setId("E002")
                .setFirstName("Ivan")
                .setLastName("Petrenko")
                .setDepartment(salesDept)
                .setRole(EmployeeRole.ADMINISTRATOR)
                .setEmployeeType("PARTTIME")
                .setHourlyRateAndHours(25.0, 120.0)
                .setManager(emp1_full)
                .build();
        hrManager.addEmployee(emp2_part);

        // Another full-time employee
        Employee emp3_full = new EmployeeBuilder()
                .setId("E003")
                .setFirstName("Nataliia")
                .setLastName("Ivanova")
                .setDepartment(hrDept)
                .setRole(EmployeeRole.HR_SPECIALIST)
                .setEmployeeType("FULLTIME")
                .setBaseSalary(60000.0)
                .setManager(ceo)
                .build();
        hrManager.addEmployee(emp3_full);

        // --- Check all employees ---
        hrManager.listAllEmployees(); // This method outputs the list of employees

        // --- 4. Strategy: Salary Calculation ---
        // The Strategy pattern is used here to define different salary calculation algorithms.
        System.out.println("\n--- Salary Calculation ---");
        SalaryCalculationStrategy monthlyStrategy = new MonthlySalaryStrategy();
        SalaryCalculationStrategy hourlyStrategy = new HourlyWageStrategy();

        // Calculate salary for Olena (full-time)
        hrManager.getEmployeeById("E001").ifPresent(e -> {
            try {
                double salary = monthlyStrategy.calculate(e);
                System.out.printf("%s %s (FullTime) salary: $%.2f%n", e.getFirstName(), e.getLastName(), salary);
            } catch (IllegalArgumentException ex) {
                System.out.println(e.getFirstName() + " " + e.getLastName() + ": " + ex.getMessage());
            }
        });

        // Calculate salary for Ivan (part-time)
        hrManager.getEmployeeById("E002").ifPresent(e -> {
            try {
                double salary = hourlyStrategy.calculate(e);
                System.out.printf("%s %s (PartTime) salary: $%.2f%n", e.getFirstName(), e.getLastName(), salary);
            } catch (IllegalArgumentException ex) {
                System.out.println(e.getFirstName() + " " + e.getLastName() + ": " + ex.getMessage());
            }
        });

        // Calculate salaries for all employees
        hrManager.calculateAllSalaries();

        // --- 5. Strategy: Sorting Employees ---
        System.out.println("\n--- Sorting Employees ---");
        hrManager.setSortingStrategy(new SortByLastNameStrategy());
        hrManager.listAllEmployees(); // listAllEmployees now uses the set strategy

        hrManager.setSortingStrategy(new SortByDepartmentStrategy());
        hrManager.listAllEmployees(); // listAllEmployees now uses the set strategy as well

        // --- 6. Observer: Termination Demonstration (triggers onTermination) ---
        System.out.println("\n--- Terminating an Employee ---");
        // Attempt to delete Ivan, who might have subordinates (though in this example he doesn't)
        // For demonstration, if Ivan had subordinates, a message indicating this would appear.
        hrManager.deleteEmployee("E002");

        // --- 7. Check list after deletion ---
        hrManager.listAllEmployees();

        // --- 8. (Optional) Recursion: Print Organization Chart ---
        System.out.println("\n--- Organization Chart ---");
        // The OrgChartUtil class uses recursion to print the hierarchical structure.
        OrgChartUtil.printFullOrganizationChart();

        // --- Console menu for interaction ---
        runConsoleMenu(hrManager);
    }

    /**
     * Starts an interactive console menu for HR management.
     */
    private static void runConsoleMenu(HRManager hrManager) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- HR Management System Menu ---");
            System.out.println("1. List All Employees");
            System.out.println("2. Add New Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Calculate Salaries");
            System.out.println("6. Sort Employees by Last Name");
            System.out.println("7. Sort Employees by Department");
            System.out.println("8. Print Organization Chart");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        hrManager.listAllEmployees();
                        break;
                    case "2":
                        addNewEmployee(hrManager, scanner);
                        break;
                    case "3":
                        updateExistingEmployee(hrManager, scanner);
                        break;
                    case "4":
                        deleteExistingEmployee(hrManager, scanner);
                        break;
                    case "5":
                        System.out.println("Calculating salaries for all employees:");
                        hrManager.calculateAllSalaries();
                        break;
                    case "6":
                        hrManager.setSortingStrategy(new SortByLastNameStrategy());
                        hrManager.listAllEmployees();
                        break;
                    case "7":
                        hrManager.setSortingStrategy(new SortByDepartmentStrategy());
                        hrManager.listAllEmployees();
                        break;
                    case "8":
                        System.out.println("\n--- Organization Chart ---");
                        OrgChartUtil.printFullOrganizationChart();
                        break;
                    case "9":
                        System.out.println("Exiting HR Management System. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input error. Please enter a valid value.");
                scanner.nextLine(); // Clear buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method to add a new employee via console input.
     */
    private static void addNewEmployee(HRManager hrManager, Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter first name: ");
        String fName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lName = scanner.nextLine();

        Department selectedDept = null;
        while (selectedDept == null) {
            System.out.print("Enter department (HR, IT, SALES): ");
            String deptName = scanner.nextLine().toUpperCase();
            selectedDept = hrManager.getDepartmentByName(deptName).orElse(null);
            if (selectedDept == null) {
                System.out.println("Department '" + deptName + "' not found. Please try again.");
            }
        }

        EmployeeRole role = null;
        while (role == null) {
            System.out.print("Enter role (DEVELOPER, QA_ENGINEER, PROJECT_MANAGER, HR_SPECIALIST, ADMINISTRATOR): ");
            try {
                role = EmployeeRole.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role. Please try again.");
            }
        }

        String type = null;
        while (!"FULLTIME".equalsIgnoreCase(type) && !"PARTTIME".equalsIgnoreCase(type)) {
            System.out.print("Enter employee type (FULLTIME/PARTTIME): ");
            type = scanner.nextLine();
            if (!"FULLTIME".equalsIgnoreCase(type) && !"PARTTIME".equalsIgnoreCase(type)) {
                System.out.println("Invalid employee type. Please enter 'FULLTIME' or 'PARTTIME'.");
            }
        }

        System.out.print("Enter manager ID (leave empty if no manager): ");
        String managerId = scanner.nextLine();
        Employee manager = null;
        if (!managerId.isEmpty()) {
            manager = hrManager.getEmployeeById(managerId).orElse(null);
            if (manager == null) {
                System.out.println("Manager with ID " + managerId + " not found. Employee will be added without a manager."); // Output in English
            }
        }

        try {
            EmployeeBuilder builder = new EmployeeBuilder()
                    .setId(id)
                    .setFirstName(fName)
                    .setLastName(lName)
                    .setDepartment(selectedDept)
                    .setRole(role)
                    .setEmployeeType(type)
                    .setManager(manager);

            if ("FULLTIME".equalsIgnoreCase(type)) {
                System.out.print("Enter base salary: ");
                double baseSalary = Double.parseDouble(scanner.nextLine());
                builder.setBaseSalary(baseSalary);
            } else if ("PARTTIME".equalsIgnoreCase(type)) {
                System.out.print("Enter hourly rate: ");
                double hourlyRate = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter hours worked: ");
                double hoursWorked = Double.parseDouble(scanner.nextLine());
                builder.setHourlyRateAndHours(hourlyRate, hoursWorked);
            }
            hrManager.addEmployee(builder.build());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric value entered for salary/rate/hours. " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    /**
     * Helper method to update an existing employee via console input.
     */
    private static void updateExistingEmployee(HRManager hrManager, Scanner scanner) {
        System.out.print("Enter employee ID to update: ");
        String updateId = scanner.nextLine();
        Optional<Employee> empToUpdateOpt = hrManager.getEmployeeById(updateId);

        if (empToUpdateOpt.isPresent()) {
            Employee empToUpdate = empToUpdateOpt.get();
            System.out.println("Updating employee: " + empToUpdate.getFirstName() + " " + empToUpdate.getLastName() + " (ID: " + empToUpdate.getId() + ")");

            System.out.print("Enter new first name (current: " + empToUpdate.getFirstName() + ", leave empty to keep): ");
            String newFName = scanner.nextLine();
            if (!newFName.isEmpty()) empToUpdate.setFirstName(newFName);

            System.out.print("Enter new last name (current: " + empToUpdate.getLastName() + ", leave empty to keep): ");
            String newLName = scanner.nextLine();
            if (!newLName.isEmpty()) empToUpdate.setLastName(newLName);

            System.out.print("Enter new department (current: " + empToUpdate.getDepartment().name() + ", leave empty to keep): ");
            String newDeptName = scanner.nextLine();
            if (!newDeptName.isEmpty()) {
                hrManager.getDepartmentByName(newDeptName.toUpperCase())
                        .ifPresent(empToUpdate::setDepartment);
            }

            System.out.print("Enter new role (current: " + empToUpdate.getRole() + ", leave empty to keep): ");
            String newRoleStr = scanner.nextLine();
            if (!newRoleStr.isEmpty()) {
                try {
                    EmployeeRole newRole = EmployeeRole.valueOf(newRoleStr.toUpperCase());
                    empToUpdate.setRole(newRole);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid role. Keeping current role.");
                }
            }

            // Update manager
            System.out.print("Enter new manager ID (current: " + (empToUpdate.getManager() != null ? empToUpdate.getManager().getId() : "None") + ", leave empty to keep, enter 'none' to remove): ");
            String newManagerId = scanner.nextLine();
            if (!newManagerId.isEmpty()) {
                if ("none".equalsIgnoreCase(newManagerId)) {
                    empToUpdate.setManager(null);
                } else {
                    Employee newManager = hrManager.getEmployeeById(newManagerId).orElse(null);
                    if (newManager != null) {
                        empToUpdate.setManager(newManager);
                    } else {
                        System.out.println("New manager with ID " + newManagerId + " not found. Keeping current manager.");
                    }
                }
            }

            // Updating salary/rate for existing employees (if needed)
            // This is more complex as an employee type might change or not.
            // Currently, Employee does not have setters for baseSalary, hourlyRate, hoursWorked directly in the abstract class.
            // If these fields change, you would need to recreate the object or add setters to the concrete Employee types.
            // For simplicity, we assume only general Employee attributes are updated here.
            // For changing type or salary, more complex logic needs to be implemented.
            System.out.println("Employee updated successfully (general data)."); // Output in English
        } else {
            System.out.println("Employee with ID " + updateId + " not found."); // Output in English
        }
    }

    /**
     * Helper method to delete an employee via console input.
     */
    private static void deleteExistingEmployee(HRManager hrManager, Scanner scanner) {
        System.out.print("Enter employee ID to delete: ");
        String delId = scanner.nextLine();
        if (hrManager.deleteEmployee(delId)) {
            System.out.println("Employee deleted successfully.");
        } else {
            // Error message is already displayed in hrManager.deleteEmployee
        }
    }
}
