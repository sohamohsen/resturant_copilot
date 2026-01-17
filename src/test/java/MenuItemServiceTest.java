import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuCategory;
import com.research.model.MenuItem;
import com.research.repository.MenuItemRepository;
import com.research.service.MenuItemService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Menu Item Management")
@Feature("MenuItemService Unit Test")
@DisplayName("MenuItemService Human-Like Tests")
public class MenuItemServiceTest {
    @Mock
    MenuItemRepository repository;
    @InjectMocks
    MenuItemService service;
    AutoCloseable closeable;
    MenuCategory cat;
    MenuItem item1;

    @BeforeEach void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        cat = new MenuCategory(1, "Main", "Main Course");
        item1 = new MenuItem(1, "Soup", "Veg", 5.0, cat, true);
    }
    @AfterEach void cleanup() throws Exception { closeable.close(); }

    @Test
    @Story("Add menu item")
    @Severity(SeverityLevel.CRITICAL)
    void addMenuItemOk() throws DuplicateIdException {
        doNothing().when(repository).addMenuItem(item1);
        service.addMenuItem(item1);
        verify(repository, times(1)).addMenuItem(item1);
    }

    @Test
    @Description("Simulate fail: Duplicate ID error")
    void addMenuItemDuplicateId() throws DuplicateIdException {
        doThrow(new DuplicateIdException("Duplicate!")).when(repository).addMenuItem(item1);
        assertThrows(DuplicateIdException.class, () -> service.addMenuItem(item1));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void searchMenuItemsReturnsMatches() {
        List<MenuItem> result = List.of(item1);
        when(repository.searchByNameOrDescription("Soup")).thenReturn(result);
        assertFalse(service.searchMenuItems("Soup").isEmpty());
    }

    @Test
    void deleteMenuItemNotFoundShouldFail() throws EntityNotFoundException {
        doThrow(new EntityNotFoundException("NOT FOUND")).when(repository).deleteMenuItem(99);
        assertThrows(EntityNotFoundException.class, () -> service.deleteMenuItem(99));
    }

    // Intentionally failing assertion
    @Test
    void getMenuItemByIdBadTestFail() throws EntityNotFoundException {
        when(repository.getMenuItemById(1)).thenReturn(item1);
        MenuItem i = service.getMenuItemById(1);
        assertEquals("Pizza", i.getName(), "Intentionally wrong (should fail)"); // Should fail
    }
}