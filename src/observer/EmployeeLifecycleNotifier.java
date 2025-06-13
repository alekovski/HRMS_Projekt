package observer;

import model.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeLifecycleNotifier class: This is the "Subject" in the Observer design pattern.
 * It notifies them when an employee-related event (like hiring or termination) occurs.
 */
public class EmployeeLifecycleNotifier {
    // A list to hold all the observers (departments) that are interested in employee lifecycle events.
    private final List<EmployeeObserver> observers = new ArrayList<>();

    /**
     * Adds an observer to the list.
     */
    public void addObserver(EmployeeObserver observer) {
        observers.add(observer);
        System.out.println("Observer added: " + observer.getClass().getSimpleName());
    }

    /**
     * Removes an observer from the list.
     */
    public void removeObserver(EmployeeObserver observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    /**
     * Notifies all registered observers that a new employee has been hired.
     */
    public void notifyNewHire(Employee employee) {
        // Loop through all observers and call their onNewHire method.
        for (EmployeeObserver observer : observers) {
            observer.onNewHire(employee);
        }
    }

    /**
     * Notifies all registered observers that an employee has been terminated.
     */
    public void notifyTermination(Employee employee) {
        for (EmployeeObserver observer : observers) {
            observer.onTermination(employee);
        }
    }
}
