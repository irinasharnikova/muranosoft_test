package dao;

import domain.Department;
import domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EmployeeDao {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String SURNAME_COLUMN = "surname";
    public static final String PHONE_COLUMN = "phone";
    public static final String DEPARTMENT_ID_COLUMN = "department_id";
    public static final String SELECT = "SELECT * FROM EMPLOYEE";
    public static final String SELECT_BY_DEPARTMENT_ID = "SELECT * FROM EMPLOYEE WHERE department_id = ";
    public static final String INSERT = "INSERT INTO employee (name, surname, phone, department_id) VALUES (?, ? , ?, ?)";
    public static final String UPDATE = "UPDATE employee SET name = ?, surname = ?, phone = ?, department_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM employee WHERE id = ?";
    public static final String UPDATE_DEPARTMENT_ID = "UPDATE employee SET department_id = ? WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> getEmployees() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT);
        return rows.stream().map(mapEmployee()).collect(Collectors.toList());
    }

    private Function<Map<String, Object>, Employee> mapEmployee() {
        return row -> {
            Employee employee = new Employee();
            employee.setId((Integer)row.get(ID_COLUMN));
            employee.setName((String)row.get(NAME_COLUMN));
            employee.setSurname((String)row.get(SURNAME_COLUMN));
            employee.setPhone((Long)row.get(PHONE_COLUMN));
            employee.setDepartment(Department.get((Integer) row.get(DEPARTMENT_ID_COLUMN)));
            return employee;
        };
    }

    public List<Employee> getEmployees(int departmentId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_DEPARTMENT_ID + departmentId);
        return rows.stream().map(mapEmployee()).collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) {

        jdbcTemplate.update(INSERT, employee.getName(), employee.getSurname(), employee.getPhone(), employee.getDepartmentId() );
    }

    public void updateEmployee(Integer id, Employee employee) {
        jdbcTemplate.update(UPDATE, employee.getName(), employee.getSurname(), employee.getPhone(), employee.getDepartmentId(), id);
    }

    public void deleteEmployee(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public void setDepartment(Integer employeeId, Department department) {
        jdbcTemplate.update(UPDATE_DEPARTMENT_ID, department.getDepartmentId(), employeeId);
    }

    public void detachDepartment(Integer employeeId) {
        setDepartment(employeeId, Department.UNDEFINED);
    }
}
