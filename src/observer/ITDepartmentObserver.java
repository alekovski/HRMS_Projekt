package observer;

import model.Employee;

public class ITDepartmentObserver implements EmployeeObserver {
    @Override
    public void onNewHire(Employee employee) {
        System.out.println("[IT Dept] New employee hired: " + employee.getFirstName() + " " + employee.getLastName() + ". Creating IT accounts...");
    }

    @Override
    public void onTermination(Employee employee) {
        System.out.println("[IT Dept] Employee terminated: " + employee.getFirstName() + " " + employee.getLastName() + ". Revoking IT access...");
    }
}