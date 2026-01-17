import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Customer;
import com.research.repository.CustomerRepository;
import com.research.service.CustomerService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Customer Management")
@Feature("CustomerService Unit Test")
class CustomerServiceTest {
    @Mock
    CustomerRepository repository;
    @InjectMocks
    CustomerService service;
    AutoCloseable closeable;

    Customer cust;

    @BeforeEach void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        cust = new Customer(1, "Sam Smith", "sam@site.com", "555", 10);
    }
    @AfterEach void cleanup() throws Exception { closeable.close(); }

    @Test
    void addCustomerSucceeds() throws DuplicateIdException {
        doNothing().when(repository).addCustomer(cust);
        service.addCustomer(cust);
        verify(repository).addCustomer(cust);
    }

    @Test
    void getCustomerNotFoundFails() throws EntityNotFoundException {
        when(repository.getCustomerById(44)).thenThrow(new EntityNotFoundException("Not found"));
        assertThrows(EntityNotFoundException.class, () -> service.getCustomerById(44));
    }

    // Fails on purpose: expecting the wrong points
    @Test void updateCustomerFail() throws EntityNotFoundException {
        cust.setLoyaltyPoints(50);
        doNothing().when(repository).updateCustomer(cust);
        service.updateCustomer(cust);
        assertEquals(99, cust.getLoyaltyPoints(), "Should fail: (expected 99, but set 50)");
    }
}