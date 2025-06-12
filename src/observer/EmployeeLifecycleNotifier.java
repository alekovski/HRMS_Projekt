package observer;

import model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeLifecycleNotifier {
    private final List<EmployeeObserver> observers = new ArrayList<>();

    public void addObserver(EmployeeObserver observer) {
        observers.add(observer);
        System.out.println("Observer added: " + observer.getClass().getSimpleName());
    }

    public void removeObserver(EmployeeObserver observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    public void notifyNewHire(Employee employee) {
        for (EmployeeObserver observer : observers) {
            observer.onNewHire(employee);
        }
    }

    public void notifyTermination(Employee employee) {
        for (EmployeeObserver observer : observers) {
            observer.onTermination(employee);
        }
    }
}
