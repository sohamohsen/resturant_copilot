
import com.research.exception.BusinessRuleViolationException;
import com.research.model.Customer;
import com.research.model.MenuItem;
import com.research.repository.OrderRepository;
import com.research.service.OrderService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Order Management")
@Feature("Order Service Unit Test")
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private AutoCloseable closeable;

    private MenuItem pizza;
    private MenuItem outOfStock;
    private Customer cust;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        pizza = new MenuItem(1, "Pizza", "Good pizza", 50, null, true);
        outOfStock = new MenuItem(2, "Unavailable Item", "Out", 10, null, false);
        cust = new Customer(1, "Ajay Kumar", "a@b.com", "123", 0);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Ajay Kumar")
    @Story("Validate order totals")
    void testCalculateTotalWithServiceCharge() {
        List<MenuItem> items = Arrays.asList(pizza, new MenuItem(5, "Salad", "Fresh", 30, null, true));
        double total = orderService.calculateTotal(items);
        assertEquals(88.0, total);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Issue("BUG-003")
    @Story("Order fails with unavailable item")
    void testValidateItemsThrowsOnUnavailable() {
        List<MenuItem> items = Arrays.asList(outOfStock);
        Exception exception = assertThrows(BusinessRuleViolationException.class, () ->
                orderService.validateItems(items)
        );
        assertTrue(exception.getMessage().contains("not available"));
    }

    // Intentionally failing! Expected a different result for demo Allure + reporting
    @Test
    @Story("Broken business logic")
    @Severity(SeverityLevel.NORMAL)
    void testCalculateTotalBrokenFail() {
        List<MenuItem> items = Collections.singletonList(pizza);
        double total = orderService.calculateTotal(items);
        // Intentionally wrong expected value, should be 55.0
        assertEquals(45.0, total, "Purposely fails, should be $55.0 with service charge");
    }
}