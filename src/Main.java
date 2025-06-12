import builder.EmployeeBuilder;
import model.Department;
import model.Employee;
import model.EmployeeRole;
import observer.FinanceDepartmentObserver;
import observer.ITDepartmentObserver;
import service.HRManager;
import strategy.*;
import util.OrgChartUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // --- 1. Singleton: Отримуємо єдиний екземпляр HRManager ---
        HRManager hrManager = HRManager.getInstance();
        System.out.println("HR Manager initialized.");

        // --- 2. Observer: Додаємо спостерігачів ---
        hrManager.getNotifier().addObserver(new ITDepartmentObserver());
        hrManager.getNotifier().addObserver(new FinanceDepartmentObserver());

        // Отримуємо відділи для зручності
        Department hrDept = hrManager.getDepartmentByName("HR").orElseThrow();
        Department itDept = hrManager.getDepartmentByName("IT").orElseThrow();
        Department salesDept = hrManager.getDepartmentByName("SALES").orElseThrow();

        // --- 3. Builder та Factory Method: Створюємо співробітників ---
        System.out.println("\n--- Adding Employees ---");

        Employee ceo = new EmployeeBuilder()
                .setId("E000")
                .setFirstName("Boss")
                .setLastName("Man")
                .setDepartment(hrDept)
                .setRole(EmployeeRole.ADMINISTRATOR)
                .setEmployeeType("FULLTIME")
                .setParam1(150000.0)
                .setManager(null) // CEO не має менеджера
                .build();
        hrManager.addEmployee(ceo);

        // Повний робочий день
        Employee emp1_full = new EmployeeBuilder()
                .setId("E001")
                .setFirstName("Alice")
                .setLastName("Smith")
                .setDepartment(itDept)
                .setRole(EmployeeRole.DEVELOPER)
                .setEmployeeType("FULLTIME")
                .setParam1(75000.0)
                .setManager(ceo)// Вказуємо тип для Factory
                .build();
        hrManager.addEmployee(emp1_full);
        // Присвоїмо зарплату після створення об'єкта, або змініть Factory
        // Якщо Factory створює FullTimeEmployee, то базова зарплата встановлюється там
        // У Factory я вже додав передачу salary/rate, тому можна так:

        // Частковий робочий день (PartTimeEmployee)
        Employee emp2_part = new EmployeeBuilder()
                .setId("E002")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setDepartment(salesDept)
                .setRole(EmployeeRole.ADMINISTRATOR)
                .setEmployeeType("PARTTIME")
                .setParam1(25.0)  // Погодинна ставка
                .setParam2(120.0) // Відпрацьовані години
                .setManager(emp1_full)
                .build();
        hrManager.addEmployee(emp2_part);

        // Ще один повний робочий день
        Employee emp3_full = new EmployeeBuilder()
                .setId("E003")
                .setFirstName("Carol")
                .setLastName("White")
                .setDepartment(hrDept)
                .setRole(EmployeeRole.HR_SPECIALIST)
                .setEmployeeType("FULLTIME")
                .setParam1(60000.0)
                .setManager(ceo) // Carol підпорядкована CEO
                .build();
        hrManager.addEmployee(emp3_full);

        // --- Перевіряємо всіх співробітників ---
        System.out.println("\n--- All Employees ---");
        hrManager.getAllEmployees().forEach(System.out::println);

        // --- 4. Strategy: Розрахунок зарплати ---
        System.out.println("\n--- Salary Calculation ---");
        SalaryCalculationStrategy monthlyStrategy = new MonthlySalaryStrategy();
        SalaryCalculationStrategy hourlyStrategy = new HourlyWageStrategy();

        Optional<Employee> alice = hrManager.getEmployeeById("E001");
        alice.ifPresent(e -> {
            try {
                double salary = monthlyStrategy.calculate(e);
                System.out.println(e.getFirstName() + "'s (FullTime) salary: $" + String.format("%.2f", salary));
            } catch (IllegalArgumentException ex) {
                System.out.println(e.getFirstName() + ": " + ex.getMessage());
            }
        });

        Optional<Employee> bob = hrManager.getEmployeeById("E002");
        bob.ifPresent(e -> {
            try {
                double salary = hourlyStrategy.calculate(e);
                System.out.println(e.getFirstName() + "'s (PartTime) salary: $" + String.format("%.2f", salary));
            } catch (IllegalArgumentException ex) {
                System.out.println(e.getFirstName() + ": " + ex.getMessage());
            }
        });

        // --- 5. Strategy: Сортування співробітників ---
        System.out.println("\n--- Sorting Employees ---");
        List<Employee> sortedEmployees = hrManager.getAllEmployees(); // Отримуємо копію для сортування

        EmployeeSortingStrategy sortByLastName = new SortByLastNameStrategy();
        System.out.println("Sorted by Last Name:");
        sortByLastName.sort(sortedEmployees);
        sortedEmployees.forEach(e -> System.out.println(e.getLastName() + ", " + e.getFirstName()));

        sortedEmployees = hrManager.getAllEmployees(); // Отримуємо свіжу копію
        EmployeeSortingStrategy sortByDepartment = new SortByDepartmentStrategy();
        System.out.println("\nSorted by Department:");
        sortByDepartment.sort(sortedEmployees);
        sortedEmployees.forEach(e -> System.out.println(e.getDepartment().getName() + ": " + e.getFirstName() + " " + e.getLastName()));

        // --- 6. Observer: Демонстрація звільнення (викликає onTermination) ---
        System.out.println("\n--- Terminating an Employee ---");
        hrManager.deleteEmployee("E002");

        // --- 7. Перевірка списку після видалення ---
        System.out.println("\n--- Employees After Termination ---");
        hrManager.getAllEmployees().forEach(System.out::println);

        // --- 8. (Опціонально) Rekursion: Друк організаційної структури ---
        // Для цього прикладу треба, щоб у Employee був менеджер
        // та щоб ви їх створили і призначили
        System.out.println("\n--- Organization Chart ---");
        OrgChartUtil.printFullOrganizationChart();
        // Приклад:
        // Employee ceo = EmployeeFactory.createEmployee("FULLTIME", "E000", "Boss", "Man", itDept, EmployeeRole.ADMINISTRATOR, 150000.0);
        // hrManager.addEmployee(ceo);
        // emp1_direct.setManager(ceo); // Встановлюємо менеджера для Аліси
        // bob.ifPresent(e -> e.setManager(alice.orElse(null))); // Боб під Алісою
        // OrgChartUtil.printFullOrganizationChart();
        System.out.println("Please set managers for employees to see the full chart.");

        // --- Консольне меню для взаємодії (якщо потрібно) ---
        System.out.println("\n--- Console Menu (Example) ---");
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
            System.out.println("8. Print Organization Chart"); // Додаємо опцію для орг. структури
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    hrManager.listAllEmployees();
                    break;
                case "2":
                    System.out.print("Enter employee ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter first name: ");
                    String fName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lName = scanner.nextLine();
                    System.out.print("Enter department (HR, IT, SALES): ");
                    String deptName = scanner.nextLine().toUpperCase();
                    Department selectedDept = hrManager.getDepartmentByName(deptName)
                            .orElse(new Department(deptName, "D-" + deptName.substring(0,2))); // Просте створення, якщо немає
                    System.out.print("Enter role (DEVELOPER, QA_ENGINEER, PROJECT_MANAGER, HR_SPECIALIST, ADMINISTRATOR): ");
                    EmployeeRole role = EmployeeRole.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter employee type (FULLTIME/PARTTIME): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter manager ID (leave empty if no manager): ");
                    String managerId = scanner.nextLine(); // <--- ОГОЛОШУЄМО ЗМІННУ ТУТ**
                    Employee manager = null;
                    if (!managerId.isEmpty()) {
                        manager = hrManager.getEmployeeById(managerId)
                                .orElse(null); // Може бути null, якщо менеджер не знайдений
                        if (manager == null) {
                            System.out.println("Manager with ID " + managerId + " not found. Employee will be added without a manager.");
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
                            builder.setParam1(baseSalary);
                        } else if ("PARTTIME".equalsIgnoreCase(type)) {
                            System.out.print("Enter hourly rate: ");
                            double hourlyRate = Double.parseDouble(scanner.nextLine());
                            System.out.print("Enter hours worked: ");
                            double hoursWorked = Double.parseDouble(scanner.nextLine());
                            builder.setParam1(hourlyRate);
                            builder.setParam2(hoursWorked);
                        } else {
                            System.out.println("Invalid employee type. Could not add employee.");
                            break;
                        }
                        hrManager.addEmployee(builder.build());
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Error adding employee: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("Enter employee ID to update: ");
                    String updateId = scanner.nextLine();
                    Optional<Employee> empToUpdateOpt = hrManager.getEmployeeById(updateId);
                    if (empToUpdateOpt.isPresent()) {
                        Employee empToUpdate = empToUpdateOpt.get();
                        System.out.println("Updating employee: " + empToUpdate.getFirstName() + " " + empToUpdate.getLastName());

                        System.out.print("Enter new first name (current: " + empToUpdate.getFirstName() + ", leave empty to keep): ");
                        String newFName = scanner.nextLine();
                        if (!newFName.isEmpty()) empToUpdate.setFirstName(newFName);

                        System.out.print("Enter new last name (current: " + empToUpdate.getLastName() + ", leave empty to keep): ");
                        String newLName = scanner.nextLine();
                        if (!newLName.isEmpty()) empToUpdate.setLastName(newLName);

                        System.out.print("Enter new department (current: " + empToUpdate.getDepartment().getName() + ", leave empty to keep): ");
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

                        // Оновлення менеджера для існуючого співробітника
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


                        System.out.println("Employee updated successfully.");
                    } else {
                        System.out.println("Employee with ID " + updateId + " not found.");
                    }
                    break;
                case "4":
                    System.out.print("Enter employee ID to delete: ");
                    String delId = scanner.nextLine();
                    if (hrManager.deleteEmployee(delId)) {
                        System.out.println("Employee deleted successfully.");
                    } else {
                        System.out.println("Employee with ID " + delId + " not found.");
                    }
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
        }
    }
}