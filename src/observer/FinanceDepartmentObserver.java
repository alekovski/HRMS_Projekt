package observer;

import model.Employee;

public class FinanceDepartmentObserver implements EmployeeObserver {
    @Override
    public void onNewHire(Employee employee) {
        System.out.println("[Finance Dept] New employee hired: " + employee.getFirstName() + " " + employee.getLastName() + ". Adding to payroll...");
    }

    @Override
    public void onTermination(Employee employee) {
        System.out.println("[Finance Dept] Employee terminated: " + employee.getFirstName() + " " + employee.getLastName() + ". Removing from payroll...");
    }
}