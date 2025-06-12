package strategy;

import model.Employee;

public interface SalaryCalculationStrategy {
    double calculate(Employee employee);
}