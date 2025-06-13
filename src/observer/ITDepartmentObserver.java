package observer;

import model.Employee;

/**
 * This class implements the EmployeeObserver interface.
 * It will handle actions related to IT accounts and access when employees are hired or terminated.
 */
public class ITDepartmentObserver implements EmployeeObserver {
    /**
     * This method is called when a new employee is hired.
     */
    @Override
    public void onNewHire(Employee employee) {
        System.out.println("[IT Dept] New employee hired: " + employee.getFirstName() + " " + employee.getLastName() + ". Creating IT accounts..."); // Output in English
    }

    /**
     * When an employee is terminated.
     */
    @Override
    public void onTermination(Employee employee) {
        System.out.println("[IT Dept] Employee terminated: " + employee.getFirstName() + " " + employee.getLastName() + ". Revoking IT access..."); // Output in English
    }
}
