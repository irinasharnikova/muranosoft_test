package domain;

import org.junit.Test;

import static domain.Department.get;
import static org.junit.Assert.assertEquals;

public class DepartmentTest {

    @Test
    public void testGet() {
        assertEquals(Department.UNDEFINED, get(null));
        assertEquals(Department.DEVELOPMENT, get(1));
        assertEquals(Department.TESTING, get(2));
        assertEquals(Department.MANAGEMENT, get(3));
        assertEquals(Department.SUPPORT, get(4));
        assertEquals(Department.UNDEFINED, get(-1));
    }
}