package service;

import model.Department;
import model.Employee;
import observer.EmployeeLifecycleNotifier;
import strategy.EmployeeSortingStrategy;
import strategy.SortByLastNameStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This is a central class for managing employees and departments.
 * It follows the "Singleton" design pattern, meaning only one instance of HRManager can exist.
 */
public class HRManager {
    private static HRManager instance; // The single instance of HRManager (Singleton pattern)
    private final List<Employee> employees;
    private final Map<String, Department> departments;
    private final EmployeeLifecycleNotifier notifier;
    private EmployeeSortingStrategy employeeSortingStrategy;

    /**
     * This constructor initializes employee list, department map, notifier, and a default sorting strategy.
     */
    private HRManager() {
        employees = new ArrayList<>();
        departments = new HashMap<>();
        notifier = new EmployeeLifecycleNotifier();
        this.employeeSortingStrategy = new SortByLastNameStrategy(); // Default sorting strategy

        // Initialize some default departments
        departments.put("HR", new Department("Human Resources", "HR-001"));
        departments.put("IT", new Department("Information Technology", "IT-001"));
        departments.put("SALES", new Department("Sales", "SALES-001"));
    }

    /**
     * Returns the single instance of HRManager (Singleton pattern).
     */
    public static HRManager getInstance() {
        if (instance == null) {
            instance = new HRManager();
        }
        return instance;
    }

    /**
     * Adds a new employee to the HR system and notifies observers about the new hire.
     */
    public void addEmployee(Employee employee) {
        // Check if an employee with the same ID already exists
        boolean exists = employees.stream().anyMatch(e -> e.getId().equals(employee.getId()));
        if (exists) {
            System.out.println("Error: Employee with ID " + employee.getId() + " already exists.");
            return;
        }
        employees.add(employee);
        System.out.println("Added employee: " + employee.getFirstName() + " " + employee.getLastName() + " (ID: " + employee.getId() + ")"); // Output in English
        notifier.notifyNewHire(employee); // Notify observers
    }

    /**
     * Retrieves an employee by their ID. Uses Optional to handle cases where the employee might not be found.
     */
    public Optional<Employee> getEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    /**
     * Deletes an employee from the HR system by their ID.
     * Prevents deletion if the employee has subordinates.
     * Notifies observers about the termination.
     */
    public boolean deleteEmployee(String employeeId) {
        Optional<Employee> employeeToRemove = getEmployeeById(employeeId);
        if (employeeToRemove.isPresent()) {
            Employee employee = employeeToRemove.get();
            List<Employee> subordinates = employees.stream()
                    .filter(e -> e.getManager() != null && e.getManager().getId().equals(employeeId))
                    .toList();

            if (!subordinates.isEmpty()) {
                System.out.println("Employee " + employee.getFirstName() + " " + employee.getLastName() + " has subordinates. Cannot delete."); // Output in English
                return false; // Cannot delete if there are subordinates
            }

            employees.remove(employee);
            System.out.println("Deleted employee with ID: " + employeeId + " (" + employee.getFirstName() + " " + employee.getLastName() + ")"); // Output in English
            notifier.notifyTermination(employee); // Notify observers
            return true;
        }
        System.out.println("Employee with ID " + employeeId + " not found for deletion.");
        return false;
    }

    /**
     * Retrieves a copy of the list of all employees.
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    /**
     * Retrieves a copy of the map of all departments.
     */
    public Map<String, Department> getDepartments() {
        return new HashMap<>(departments);
    }

    /**
     * Retrieves a department by its name (case-insensitive).
     */
    public Optional<Department> getDepartmentByName(String name) {
        return Optional.ofNullable(departments.get(name.toUpperCase()));
    }

    /**
     * Gets the EmployeeLifecycleNotifier instance.
     */
    public EmployeeLifecycleNotifier getNotifier() {
        return notifier;
    }

    /**
     * Prints a list of all employees to the console, applying the current sorting strategy.
     */
    public void listAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display."); // Output in English
            return;
        }
        System.out.println("\n--- List of All Employees ---"); // Output in English
        List<Employee> employeesToList = new ArrayList<>(employees);
        if (employeeSortingStrategy != null) {
            employeeSortingStrategy.sort(employeesToList); // Apply the current sorting strategy
        }
        employeesToList.forEach(System.out::println);
        System.out.println("---------------------------------");
    }

    /**
     * Calculates and prints the salary for all employees.
     */
    public void calculateAllSalaries() {
        if (employees.isEmpty()) {
            System.out.println("No employees to calculate salaries for.");
            return;
        }
        employees.forEach(emp -> {
            System.out.printf("%s %s (ID: %s) - Calculated Salary: %.2f€%n",
                    emp.getFirstName(), emp.getLastName(), emp.getId(), emp.calculateSalary());
        });
    }

    /**
     * Sets the sorting strategy to be used for listing employees and demonstrates the "Strategy" design pattern.
     */
    public void setSortingStrategy(EmployeeSortingStrategy strategy) {
        this.employeeSortingStrategy = strategy;
        System.out.println("Sorting strategy set: " + strategy.getClass().getSimpleName());
    }

    /**
     * Updates an existing employee's information.
     */
    public boolean updateEmployee(Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(updatedEmployee.getId())) {
                employees.set(i, updatedEmployee); // Replace the old employee object with the updated one
                System.out.println("Employee with ID: " + updatedEmployee.getId() + " updated successfully.");
                return true;
            }
        }
        System.out.println("Employee with ID " + updatedEmployee.getId() + " not found for update.");
        return false;
    }
}
