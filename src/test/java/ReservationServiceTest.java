
import com.research.exception.BusinessRuleViolationException;
import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Customer;
import com.research.model.Reservation;
import com.research.model.RestaurantTable;
import com.research.model.TableStatus;
import com.research.repository.ReservationRepository;
import com.research.repository.TableRepository;
import com.research.service.ReservationService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Reservation Management")
@Feature("Reservation Service Unit Test")
class ReservationServiceTest {

    @Mock
    ReservationRepository resRepo;
    @Mock
    TableRepository tableRepo;
    @InjectMocks
    ReservationService reservationService;
    private AutoCloseable closeable;

    private RestaurantTable table;
    private Customer cust;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        table = new RestaurantTable(1, 25, 2, TableStatus.AVAILABLE);
        cust = new Customer(1, "Alex Alexey", "alex@rest.com", "111", 0);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Fail: Table not available (negative test)")
    void createReservationTableNotAvailable() throws EntityNotFoundException {
        when(tableRepo.getTableById(1)).thenReturn(new RestaurantTable(1, 25, 2, TableStatus.RESERVED));
        Reservation reservation = new Reservation(2, cust, table, LocalDate.now(), LocalTime.of(14, 0), 2);
        assertThrows(
                BusinessRuleViolationException.class,
                () -> reservationService.createReservation(reservation)
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create reservation with capacity issue (should fail)")
    void createReservationOverCapacity() throws EntityNotFoundException {
        when(tableRepo.getTableById(1)).thenReturn(table);
        Reservation reservation = new Reservation(2, cust, table, LocalDate.now(), LocalTime.of(15, 0), 5);
        assertThrows(
                BusinessRuleViolationException.class,
                () -> reservationService.createReservation(reservation)
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Passing: Reservation can be created normally")
    void createAndReleaseReservation() throws EntityNotFoundException, DuplicateIdException, BusinessRuleViolationException {
        RestaurantTable mockTable = new RestaurantTable(1, 25, 4, TableStatus.AVAILABLE);
        when(tableRepo.getTableById(1)).thenReturn(mockTable);
        when(resRepo.getAllReservations()).thenReturn(Collections.emptyList());
        doNothing().when(resRepo).addReservation(any());
        doNothing().when(tableRepo).updateTable(any());

        Reservation reservation = new Reservation(1, cust, mockTable, LocalDate.now().plusDays(1), LocalTime.of(17, 0), 2);

        assertDoesNotThrow(() -> reservationService.createReservation(reservation));
        verify(resRepo, times(1)).addReservation(any());
        verify(tableRepo, times(1)).updateTable(any());
    }
}