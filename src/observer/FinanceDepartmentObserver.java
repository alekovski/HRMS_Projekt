package observer;

import model.Employee;

/**
 * This class implements the EmployeeObserver interface.
 * It will handle actions related to payroll when employees are hired or terminated.
 */
public class FinanceDepartmentObserver implements EmployeeObserver {
    /**
     * This method is called when a new employee is hired.
     */
    @Override
    public void onNewHire(Employee employee) {
        System.out.println("[Finance Dept] New employee hired: " + employee.getFirstName() + " " + employee.getLastName() + ". Adding to payroll..."); // Output in English
    }

    /**
     * When an employee is terminated.
     */
    @Override
    public void onTermination(Employee employee) {
        System.out.println("[Finance Dept] Employee terminated: " + employee.getFirstName() + " " + employee.getLastName() + ". Removing from payroll..."); // Output in English
    }
}
