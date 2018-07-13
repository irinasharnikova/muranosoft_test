package domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.*;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */

/*
 * If requirements are changed and departments are modified constantly, transform enum -> class and create corresponding
 * table in database.
 */
public enum Department {
    UNDEFINED(null),
    DEVELOPMENT(1),
    TESTING(2),
    MANAGEMENT(3),
    SUPPORT(4);

    @Getter
    private final Integer departmentId;

    Department(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public static Department get(Integer id) {
        if (id == null) {
            return UNDEFINED;
        }

        return stream(Department.values()).filter(department -> id.equals(department.departmentId)).findFirst().orElse(UNDEFINED);
    }

}
