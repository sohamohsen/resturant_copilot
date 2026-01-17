import com.research.exception.EntityNotFoundException;
import com.research.model.RestaurantTable;
import com.research.model.TableStatus;
import com.research.repository.TableRepository;
import com.research.service.TableService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Table Management")
@Feature("TableService Unit Test")
class TableServiceTest {
    @Mock
    TableRepository repository;
    @InjectMocks
    TableService service;
    AutoCloseable closeable;

    RestaurantTable table;

    @BeforeEach void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        table = new RestaurantTable(5, 99, 4, TableStatus.AVAILABLE);
    }
    @AfterEach void cleanup() throws Exception { closeable.close(); }

    @Test
    void getAvailableTablesSome() {
        List<RestaurantTable> tables = Arrays.asList(table, new RestaurantTable(6, 12, 2, TableStatus.RESERVED));
        when(repository.getAvailableTables()).thenReturn(List.of(table));
        List<RestaurantTable> found = service.getAvailableTables();
        assertEquals(1, found.size());
    }

    @Test void markTableStatusWorks() throws EntityNotFoundException {
        when(repository.getTableById(5)).thenReturn(table);
        doNothing().when(repository).updateTable(table);
        service.markTableStatus(5, TableStatus.OCCUPIED);
        assertEquals(TableStatus.OCCUPIED, table.getStatus());
    }

    // Fails on purpose: expects more available tables than in mock
    @Test
    void getAvailableTablesWrongCountFail() {
        when(repository.getAvailableTables()).thenReturn(List.of(table));
        List<RestaurantTable> found = service.getAvailableTables();
        assertEquals(2, found.size(), "Should fail: only one available in mock");
    }
}