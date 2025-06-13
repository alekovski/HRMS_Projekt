package observer;

import model.Employee;

/**
 * This is the "Observer" interface in the Observer design pattern.
 */
public interface EmployeeObserver {
    /**
     * This method is called when a new employee is hired.
     */
    void onNewHire(Employee employee);

    /**
     * And this method is called when an employee is terminated.
     */
    void onTermination(Employee employee);
}