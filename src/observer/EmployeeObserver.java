package observer;

import model.Employee;

public interface EmployeeObserver {
    void onNewHire(Employee employee);
    void onTermination(Employee employee);
}