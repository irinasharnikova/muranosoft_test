package dao;

import config.SpringConfig;
import domain.Department;
import domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class EmployeeDaoIntegrationTest {

    @Autowired
    private EmployeeDao dao;

    private List<Integer> initialAmountOfPortfollios;

    @Before
    public void setInitialValue() {
        initialAmountOfPortfollios = dao.getEmployees().stream().map(developer -> developer.getId()).distinct().collect(Collectors.toCollection(CopyOnWriteArrayList :: new));
    }

    @After
    public void deleteTestEmployees() {
        List<Integer> afterTest = dao.getEmployees().stream().map(developer -> developer.getId()).distinct().collect(Collectors.toCollection(CopyOnWriteArrayList :: new));
        afterTest.stream().filter(id -> !initialAmountOfPortfollios.contains(id)).collect(Collectors.toList()).forEach(id -> dao.deleteEmployee(id));
        assertEquals(initialAmountOfPortfollios.size(), dao.getEmployees().size());
    }

    @Test
    public void testAddAndGetAndUser() {

        Employee employee = createEmployee();
        employee.setDepartment(Department.DEVELOPMENT);
        dao.addEmployee(employee);

        List<Employee> developersAfterChanges = dao.getEmployees(Department.DEVELOPMENT.getDepartmentId());
        assertNotNull(developersAfterChanges);
        assertTrue(developersAfterChanges.size() >= 1); // new rows are added
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setName("Peter");
        employee.setSurname("Petrov");
        employee.setPhone(12347574636L);
        return employee;
    }

        @Test
    public void testUpdateEmployeeAndSetDepartment() {
        Employee employee = createEmployee();
        dao.addEmployee(employee);
        List<Employee> employees = dao.getEmployees();

        assertNotNull(employees);
        assertTrue(employees.size() >= 1);

        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee);
        employee.setDepartment(Department.DEVELOPMENT);
        dao.updateEmployee(firstEmployee.getId(), employee);

        assertNotNull(dao.getEmployees());
        Employee employee1 = dao.getEmployees().stream().filter(e -> firstEmployee.getId().equals(e.getId())).findFirst().get();
        assertEquals(Department.DEVELOPMENT, employee1.getDepartment()); // check that it was updated

        dao.setDepartment(employee1.getId(), Department.MANAGEMENT);

        Employee result = dao.getEmployees().stream().filter(e -> employee1.getId().equals(e.getId())).findFirst().get();
        assertNotNull(result);
        assertEquals(Department.MANAGEMENT, result.getDepartment());
    }

    @Test
    public void testDetachDepartment() {
        Employee employee = createEmployee();
        employee.setDepartment(Department.MANAGEMENT);
        dao.addEmployee(employee);
        List<Employee> employees = dao.getEmployees(Department.MANAGEMENT.getDepartmentId());

        assertNotNull(employees);
        assertTrue(employees.size() >= 1);

        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee);
        assertNotEquals(Department.UNDEFINED, firstEmployee.getDepartment());

        dao.detachDepartment(firstEmployee.getId());

        assertNotNull(dao.getEmployees());

        Employee employee1 = dao.getEmployees().stream().filter(e -> firstEmployee.getId().equals(e.getId())).findFirst().get();
        assertEquals(Department.UNDEFINED, employee1.getDepartment()); // check that it was updated
    }

    @Test //no errors!
    public void testDeleteNonExistingUser() {
        dao.deleteEmployee(-1);
    }

    @Test //no errors!
    public void testUpdateNonExistingUser() {
        dao.updateEmployee(-1, new Employee());
    }

    @Test
    public void testSetDepartmentToNotExistingEmployee() {
        dao.setDepartment(-1, Department.MANAGEMENT);
    }

    @Test
    public void testDetachDepartmentForNonExistingUser() {
        dao.detachDepartment(-1);
    }
}
