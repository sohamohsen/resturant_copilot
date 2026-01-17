
import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Staff;
import com.research.repository.StaffRepository;
import com.research.service.StaffService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Staff Management")
@Feature("Staff Service Unit Test")
@DisplayName("StaffService Human-Like Unit Tests")
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    private AutoCloseable closeable;

    private Staff john;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        john = new Staff(1, "John Doe", "Chef", "john@rest.com", "12345");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @Story("Adding a staff member")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Happy path: Staff added successfully")
    void testAddStaffSuccess() throws DuplicateIdException {
        doNothing().when(staffRepository).addStaff(john);
        staffService.addStaff(john);
        verify(staffRepository, times(1)).addStaff(john);
    }

    @Test
    @Story("Adding a staff member")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Negative: Duplicate staff ID")
    void testAddStaffDuplicateId() throws DuplicateIdException {
        doThrow(new DuplicateIdException("Staff ID already exists.")).when(staffRepository).addStaff(john);
        DuplicateIdException ex = assertThrows(DuplicateIdException.class, () -> staffService.addStaff(john));
        assertTrue(ex.getMessage().contains("already"));
    }

    @Test
    @Story("Find staff by ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Happy: Find existing staff by ID")
    void testGetStaffById() throws EntityNotFoundException {
        when(staffRepository.getStaffById(1)).thenReturn(john);
        Staff staff = staffService.getStaffById(1);
        assertEquals("John Doe", staff.getFullName());
    }

    @Test
    @Story("Find staff by ID")
    @Severity(SeverityLevel.MINOR)
    @Description("Negative: Not found throws exception")
    void testGetStaffByIdNotFound() throws EntityNotFoundException {
        when(staffRepository.getStaffById(404)).thenThrow(new EntityNotFoundException("Staff not found!"));
        assertThrows(EntityNotFoundException.class, () -> staffService.getStaffById(404));
    }

    @Test
    @Story("Delete staff")
    @Severity(SeverityLevel.NORMAL)
    @Description("Delete staff fails if not exists")
    void testDeleteStaffNotFound() throws EntityNotFoundException {
        doThrow(new EntityNotFoundException("Staff not found!")).when(staffRepository).deleteStaff(77);
        assertThrows(EntityNotFoundException.class, () -> staffService.deleteStaff(77));
    }

    // Intentionally flaky/failing: search expecting wrong result
    @Test
    @Story("Search staff by role")
    @Severity(SeverityLevel.NORMAL)
    @Description("Should return only chefs, but purposely fails assertion")
    void testSearchStaffByRoleFail() {
        List<Staff> staffList = Arrays.asList(
                new Staff(1, "John Chef", "Chef", "j1@a.com", "x"),
                new Staff(2, "Fake Waiter", "Waiter", "w2@a.com", "y")
        );
        when(staffRepository.searchByRole("Chef")).thenReturn(staffList);

        List<Staff> found = staffService.searchStaffByRole("Chef");
        // Fails on purpose: expects size=1 while mock returns 2, to simulate test coverage!
        assertEquals(1, found.size(), "Should only find chefs.");
    }
}