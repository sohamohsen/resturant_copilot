import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.MenuCategory;
import com.research.repository.MenuCategoryRepository;
import com.research.service.MenuCategoryService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("MenuCategory Management")
@Feature("MenuCategoryService Unit Test")
class MenuCategoryServiceTest {
    @Mock
    MenuCategoryRepository repository;
    @InjectMocks
    MenuCategoryService service;
    AutoCloseable closeable;
    MenuCategory cat;

    @BeforeEach void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        cat = new MenuCategory(1, "Sweets", "Yummy sweets");
    }
    @AfterEach void clean() throws Exception { closeable.close(); }

    @Test
    void addCategoryWorks() throws DuplicateIdException {
        doNothing().when(repository).addCategory(cat);
        service.addCategory(cat);
        verify(repository).addCategory(cat);
    }

    @Test
    void deleteCategoryFailsNotFound() throws EntityNotFoundException {
        doThrow(new EntityNotFoundException("BAD!")).when(repository).deleteCategory(2);
        assertThrows(EntityNotFoundException.class, () -> service.deleteCategory(2));
    }

    @Test
    void getCategoryByIdBadTest() throws EntityNotFoundException {
        when(repository.getCategoryById(1)).thenReturn(cat);
        MenuCategory c = service.getCategoryById(1);
        assertEquals("Main Course", c.getName(), "Deliberately fails: expecting a different name");
    }
}