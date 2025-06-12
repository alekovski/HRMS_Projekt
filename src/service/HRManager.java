package service;

import model.Department;
import model.Employee;
import observer.EmployeeLifecycleNotifier;
import strategy.EmployeeSortingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HRManager {
    private static HRManager instance;
    private final List<Employee> employees;
    private final Map<String, Department> departments;
    private final EmployeeLifecycleNotifier notifier;
    private EmployeeSortingStrategy employeeSortingStrategy;

    private HRManager() {
        employees = new ArrayList<>();
        departments = new HashMap<>();
        notifier = new EmployeeLifecycleNotifier();

        departments.put("HR", new Department("Human Resources", "HR-001"));
        departments.put("IT", new Department("Information Technology", "IT-001"));
        departments.put("SALES", new Department("Sales", "SALES-001"));
    }

    public static HRManager getInstance() {
        if (instance == null) {
            instance = new HRManager();
        }
        return instance;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Added employee: " + employee.getFirstName() + " " + employee.getLastName());
        notifier.notifyNewHire(employee);
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public boolean deleteEmployee(String employeeId) {
        employees.removeIf(e -> {
            boolean removed = e.getId().equals(employeeId);
            if (removed) {
                System.out.println("Removed employee with ID: " + employeeId);
                notifier.notifyTermination(e);
            }
            return removed;
        });
        return false;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Map<String, Department> getDepartments() {
        return new HashMap<>(departments);
    }

    public Optional<Department> getDepartmentByName(String name) {
        return Optional.ofNullable(departments.get(name.toUpperCase()));
    }

    public EmployeeLifecycleNotifier getNotifier() {
        return notifier;
    }

    public void listAllEmployees() {
    }

    public void calculateAllSalaries() {
        if (employees.isEmpty()) {
            System.out.println("No employees to calculate salaries for.");
            return;
        }
        employees.forEach(emp -> {
            System.out.printf("%s %s (ID: %s) - Calculated Salary: %.2f€%n",
                    emp.getFirstName(), emp.getLastName(), emp.getId(), emp.calculateSalary());
        });
        // Тут можна додати сповіщення через notifier, якщо потрібно
        // notifier.notifySalaryCalculation("All employees' salaries have been calculated.");
    }

    public void setSortingStrategy(EmployeeSortingStrategy strategy) {
    }
    
}
