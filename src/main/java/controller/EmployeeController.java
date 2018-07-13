
package controller;

import dao.EmployeeDao;
import domain.Department;
import domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */
@RestController
@RequestMapping()
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping(path = "/employee", produces = APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees() {
        List<Employee> employees;
        employees = employeeDao.getEmployees();

        return employees;
    }

    @GetMapping(path = "/employeeByDepartment", produces = APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByDepartmentId(@RequestParam("departmentId") Integer departmentId) {
        List<Employee> employees;
        if (departmentId == null) {
            employees = employeeDao.getEmployees();
        } else {
            employees = employeeDao.getEmployees(departmentId);
        }

        return employees;
    }

    @PostMapping(path = "/addEmployee", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        employeeDao.addEmployee(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/updateEmployee/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employeeDao.updateEmployee(id, employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/setDepartment/{employeeId}/{departmentId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity setDepartment(@PathVariable Integer employeeId, @PathVariable Integer departmentId) {
        employeeDao.setDepartment(employeeId, Department.get(departmentId));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/detachDepartment/{employeeId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity detachDepartment(@PathVariable Integer employeeId) {
        employeeDao.detachDepartment(employeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/deleteEmployee/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        employeeDao.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}