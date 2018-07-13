package domain;

import lombok.*;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */

@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private Integer id;
    private  String name;
    private String surname;
    private long phone;
    private Department department;

    public Integer getDepartmentId() {
        return department == null ? null : department.getDepartmentId();
    }
}
