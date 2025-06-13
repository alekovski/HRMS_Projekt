package model;

/**
 * Department class: Represents a department within an organization.
 * It holds the department's name and a unique ID.
 *
 * @param name These fields are 'final', meaning their values are set once in the constructor and cannot be changed later. The name of the department (e.g., "HR", "IT")
 * @param id   The unique ID of the department (e.g., "HR-001")
 */
public record Department(String name, String id) {
    /**
     * Constructor for the Department class.
     * Initializes a new Department object with a given name and ID.
     */
    public Department {
    }

    /**
     * Getter method to retrieve the department's name.
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * And to retrieve the department's ID.

     */
    @Override
    public String id() {
        return id;
    }

    /**
     * Overrides the default toString() method to provide a custom string representation of a Department object.
     * This is useful for printing Department details.
     */
    @Override
    public String toString() {
        return "Department{" + // English output
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
