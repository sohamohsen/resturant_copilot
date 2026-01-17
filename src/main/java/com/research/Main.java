package com.research;

import com.research.model.*;
import com.research.repository.*;
import com.research.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);

    private final StaffService staffService;
    private final MenuCategoryService menuCategoryService;
    private final MenuItemService menuItemService;
    private final CustomerService customerService;
    private final TableService tableService;
    private final OrderService orderService;
    private final ReservationService reservationService;

    // ================= CONSTRUCTOR =================
    public Main() {

        // -------- Repositories --------
        StaffRepository staffRepository = new StaffRepository();
        MenuCategoryRepository menuCategoryRepository = new MenuCategoryRepository();
        MenuItemRepository menuItemRepository = new MenuItemRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        TableRepository tableRepository = new TableRepository();
        OrderRepository orderRepository = new OrderRepository();
        ReservationRepository reservationRepository = new ReservationRepository();

        // -------- Services --------
        this.staffService = new StaffService(staffRepository);
        this.menuCategoryService = new MenuCategoryService(menuCategoryRepository);
        this.menuItemService = new MenuItemService(menuItemRepository);
        this.customerService = new CustomerService(customerRepository);
        this.tableService = new TableService(tableRepository);
        this.orderService = new OrderService(orderRepository);
        this.reservationService = new ReservationService(
                reservationRepository,
                tableRepository
        );
    }

    // ================= APPLICATION ENTRY =================
    public static void main(String[] args) {
        new Main().run();
    }

    // ================= MAIN LOOP =================
    public void run() {
        boolean exit = false;

        while (!exit) {
            try {
                printMainMenu();
                int choice = getIntInput("Select an option: ");

                switch (choice) {
                    case 1 -> staffMenu();
                    case 2 -> menuCategoryMenu();
                    case 3 -> menuItemMenu();
                    case 4 -> customerMenu();
                    case 5 -> tableMenu();
                    case 6 -> orderMenu();
                    case 7 -> reservationMenu();
                    case 8 -> {
                        System.out.println("Exiting application. Goodbye!");
                        exit = true;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ================= MAIN MENU =================
    private void printMainMenu() {
        System.out.println("\n===== Restaurant Management System =====");
        System.out.println("1. Staff Management");
        System.out.println("2. Menu Category Management");
        System.out.println("3. Menu Item Management");
        System.out.println("4. Customer Management");
        System.out.println("5. Table Management");
        System.out.println("6. Order Management");
        System.out.println("7. Reservation Management");
        System.out.println("8. Exit");
    }

    // =====================================================
    // ================= STAFF MANAGEMENT ==================
    // =====================================================
    private void staffMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nStaff Management");
            System.out.println("1. Add Staff");
            System.out.println("2. View All Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. Delete Staff");
            System.out.println("5. Search Staff by Name");
            System.out.println("6. Search Staff by Role");
            System.out.println("7. Back");

            int choice = getIntInput("Select: ");

            switch (choice) {
                case 1 -> addStaff();
                case 2 -> viewAllStaff();
                case 3 -> updateStaff();
                case 4 -> deleteStaff();
                case 5 -> searchStaffByName();
                case 6 -> searchStaffByRole();
                case 7 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addStaff() {
        int id = getPositiveIntInput("ID: ");
        String name = getNonEmptyInput("Full Name: ");
        String role = getNonEmptyInput("Role: ");
        String email = getNonEmptyInput("Email: ");
        String phone = getNonEmptyInput("Phone: ");

        staffService.addStaff(new Staff(id, name, role, email, phone));
        System.out.println("Staff added successfully.");
    }

    private void viewAllStaff() {
        List<Staff> staff = staffService.getAllStaff();
        if (staff.isEmpty()) System.out.println("No staff found.");
        else staff.forEach(System.out::println);
    }

    private void updateStaff() {
        int id = getPositiveIntInput("Staff ID: ");
        Staff staff = staffService.getStaffById(id);

        staff.setFullName(getNonEmptyInput("Name (" + staff.getFullName() + "): "));
        staff.setRole(getNonEmptyInput("Role (" + staff.getRole() + "): "));
        staff.setEmail(getNonEmptyInput("Email (" + staff.getEmail() + "): "));
        staff.setPhone(getNonEmptyInput("Phone (" + staff.getPhone() + "): "));

        staffService.updateStaff(staff);
        System.out.println("Staff updated.");
    }

    private void deleteStaff() {
        int id = getPositiveIntInput("Staff ID: ");
        staffService.deleteStaff(id);
        System.out.println("Staff deleted.");
    }

    private void searchStaffByName() {
        String name = getNonEmptyInput("Search Name: ");
        staffService.searchStaffByName(name).forEach(System.out::println);
    }

    private void searchStaffByRole() {
        String role = getNonEmptyInput("Search Role: ");
        staffService.searchStaffByRole(role).forEach(System.out::println);
    }

    // =====================================================
    // ============== MENU CATEGORY MANAGEMENT =============
    // =====================================================
    private void menuCategoryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nMenu Category Management");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> addMenuCategory();
                case 2 -> viewMenuCategories();
                case 3 -> updateMenuCategory();
                case 4 -> deleteMenuCategory();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addMenuCategory() {
        int id = getPositiveIntInput("ID: ");
        String name = getNonEmptyInput("Name: ");
        String description = getNonEmptyInput("Description: ");
        menuCategoryService.addCategory(new MenuCategory(id, name, description));
        System.out.println("Category added.");
    }

    private void viewMenuCategories() {
        List<MenuCategory> categories = menuCategoryService.getAllCategories();
        if (categories.isEmpty()) System.out.println("No categories found.");
        else categories.forEach(System.out::println);
    }

    private void updateMenuCategory() {
        int id = getPositiveIntInput("Category ID: ");
        MenuCategory category = menuCategoryService.getCategoryById(id);

        category.setName(getNonEmptyInput("Name (" + category.getName() + "): "));
        category.setDescription(getNonEmptyInput("Description (" + category.getDescription() + "): "));

        menuCategoryService.updateCategory(category);
        System.out.println("Category updated.");
    }

    private void deleteMenuCategory() {
        int id = getPositiveIntInput("Category ID: ");
        menuCategoryService.deleteCategory(id);
        System.out.println("Category deleted.");
    }

    // =====================================================
    // ============== MENU ITEM MANAGEMENT ================
    // =====================================================
    private void menuItemMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nMenu Item Management");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View Menu Items");
            System.out.println("3. Update Menu Item");
            System.out.println("4. Delete Menu Item");
            System.out.println("5. Search Menu Items");
            System.out.println("6. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> addMenuItem();
                case 2 -> viewMenuItems();
                case 3 -> updateMenuItem();
                case 4 -> deleteMenuItem();
                case 5 -> searchMenuItems();
                case 6 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addMenuItem() {
        int id = getPositiveIntInput("ID: ");
        String name = getNonEmptyInput("Name: ");
        String description = getNonEmptyInput("Description: ");
        double price = getPositiveDoubleInput("Price: ");
        int categoryId = getPositiveIntInput("Category ID: ");
        MenuCategory category = menuCategoryService.getCategoryById(categoryId);
        boolean available = getBooleanInput("Is Available [Y/N]: ");
        menuItemService.addMenuItem(new MenuItem(id, name, description, price, category, available));
        System.out.println("Menu item added.");
    }

    private void viewMenuItems() {
        List<MenuItem> items = menuItemService.getAllMenuItems();
        if (items.isEmpty()) System.out.println("No menu items found.");
        else items.forEach(System.out::println);
    }

    private void updateMenuItem() {
        int id = getPositiveIntInput("Menu Item ID: ");
        MenuItem item = menuItemService.getMenuItemById(id);

        item.setName(getNonEmptyInput("Name (" + item.getName() + "): "));
        item.setDescription(getNonEmptyInput("Description (" + item.getDescription() + "): "));
        item.setPrice(getPositiveDoubleInput("Price (" + item.getPrice() + "): "));
        int categoryId = getPositiveIntInput("Category ID (" + item.getCategory().getId() + "): ");
        MenuCategory category = menuCategoryService.getCategoryById(categoryId);
        item.setCategory(category);
        item.setAvailable(getBooleanInput("Is Available [Y/N]: "));

        menuItemService.updateMenuItem(item);
        System.out.println("Menu item updated.");
    }

    private void deleteMenuItem() {
        int id = getPositiveIntInput("Menu Item ID: ");
        menuItemService.deleteMenuItem(id);
        System.out.println("Menu item deleted.");
    }

    private void searchMenuItems() {
        String search = getNonEmptyInput("Search: ");
        List<MenuItem> results = menuItemService.searchMenuItems(search);
        if (results.isEmpty()) System.out.println("No matching menu items found.");
        else results.forEach(System.out::println);
    }

    // =====================================================
    // =============== CUSTOMER MANAGEMENT ================
    // =====================================================
    private void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nCustomer Management");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Search Customers");
            System.out.println("6. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> viewCustomers();
                case 3 -> updateCustomer();
                case 4 -> deleteCustomer();
                case 5 -> searchCustomers();
                case 6 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addCustomer() {
        int id = getPositiveIntInput("ID: ");
        String name = getNonEmptyInput("Full Name: ");
        String email = getNonEmptyInput("Email: ");
        String phone = getNonEmptyInput("Phone: ");
        int loyalty = getNonNegativeIntInput("Loyalty Points: ");
        customerService.addCustomer(new Customer(id, name, email, phone, loyalty));
        System.out.println("Customer added.");
    }

    private void viewCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) System.out.println("No customers found.");
        else customers.forEach(System.out::println);
    }

    private void updateCustomer() {
        int id = getPositiveIntInput("Customer ID: ");
        Customer customer = customerService.getCustomerById(id);

        customer.setFullName(getNonEmptyInput("Name (" + customer.getFullName() + "): "));
        customer.setEmail(getNonEmptyInput("Email (" + customer.getEmail() + "): "));
        customer.setPhone(getNonEmptyInput("Phone (" + customer.getPhone() + "): "));
        customer.setLoyaltyPoints(getNonNegativeIntInput("Loyalty Points (" + customer.getLoyaltyPoints() + "): "));

        customerService.updateCustomer(customer);
        System.out.println("Customer updated.");
    }

    private void deleteCustomer() {
        int id = getPositiveIntInput("Customer ID: ");
        customerService.deleteCustomer(id);
        System.out.println("Customer deleted.");
    }

    private void searchCustomers() {
        String search = getNonEmptyInput("Search: ");
        List<Customer> results = customerService.searchCustomers(search);
        if (results.isEmpty()) System.out.println("No matching customers found.");
        else results.forEach(System.out::println);
    }

    // =====================================================
    // ================ TABLE MANAGEMENT ==================
    // =====================================================
    private void tableMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nTable Management");
            System.out.println("1. Add Table");
            System.out.println("2. View Tables");
            System.out.println("3. Update Table");
            System.out.println("4. Delete Table");
            System.out.println("5. View Available Tables");
            System.out.println("6. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> addTable();
                case 2 -> viewTables();
                case 3 -> updateTable();
                case 4 -> deleteTable();
                case 5 -> viewAvailableTables();
                case 6 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addTable() {
        int id = getPositiveIntInput("ID: ");
        int tableNumber = getPositiveIntInput("Table Number: ");
        int capacity = getPositiveIntInput("Capacity: ");
        tableService.addTable(new RestaurantTable(id, tableNumber, capacity, TableStatus.AVAILABLE));
        System.out.println("Table added.");
    }

    private void viewTables() {
        List<RestaurantTable> tables = tableService.getAllTables();
        if (tables.isEmpty()) System.out.println("No tables found.");
        else tables.forEach(System.out::println);
    }

    private void updateTable() {
        int id = getPositiveIntInput("Table ID: ");
        RestaurantTable table = tableService.getTableById(id);

        table.setTableNumber(getPositiveIntInput("Table Number (" + table.getTableNumber() + "): "));
        table.setCapacity(getPositiveIntInput("Capacity (" + table.getCapacity() + "): "));

        tableService.updateTable(table);
        System.out.println("Table updated.");
    }

    private void deleteTable() {
        int id = getPositiveIntInput("Table ID: ");
        tableService.deleteTable(id);
        System.out.println("Table deleted.");
    }

    private void viewAvailableTables() {
        List<RestaurantTable> tables = tableService.getAvailableTables();
        if (tables.isEmpty()) System.out.println("No available tables found.");
        else tables.forEach(System.out::println);
    }

    // =====================================================
    // ================ ORDER MANAGEMENT ==================
    // =====================================================
    private void orderMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nOrder Management");
            System.out.println("1. Create Order");
            System.out.println("2. View Orders");
            System.out.println("3. Update Order Status");
            System.out.println("4. View Order Details");
            System.out.println("5. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> createOrder();
                case 2 -> viewOrders();
                case 3 -> updateOrderStatus();
                case 4 -> viewOrderDetails();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createOrder() {
        int orderId = getPositiveIntInput("Order ID: ");
        int custId = getPositiveIntInput("Customer ID: ");
        Customer customer = customerService.getCustomerById(custId);
        List<MenuItem> items = new java.util.ArrayList<>();
        while (true) {
            int itemId = getPositiveIntInput("Add Menu Item ID (or 0 to finish): ");
            if (itemId == 0) break;
            MenuItem item = menuItemService.getMenuItemById(itemId);
            items.add(item);
        }
        orderService.validateItems(items);
        double total = orderService.calculateTotal(items);
        Order order = new Order(orderId, customer, items, total, OrderStatus.PENDING, java.time.LocalDateTime.now());
        orderService.addOrder(order);
        int loyaltyEarned = (int) (total / 10);
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + loyaltyEarned);
        customerService.updateCustomer(customer);
        System.out.println("Order created. Total: $" + String.format("%.2f", total) + " | Loyalty Points earned: " + loyaltyEarned);
    }

    private void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) System.out.println("No orders found.");
        else orders.forEach(System.out::println);
    }

    private void updateOrderStatus() {
        int orderId = getPositiveIntInput("Order ID: ");
        Order order = orderService.getOrderById(orderId);
        System.out.println("Current Status: " + order.getStatus());
        System.out.println("1. PENDING\n2. PREPARING\n3. SERVED\n4. PAID");
        int st = getIntInput("Select new status: ");
        switch (st) {
            case 1 -> order.setStatus(OrderStatus.PENDING);
            case 2 -> order.setStatus(OrderStatus.PREPARING);
            case 3 -> order.setStatus(OrderStatus.SERVED);
            case 4 -> order.setStatus(OrderStatus.PAID);
            default -> System.out.println("Invalid order status.");
        }
        orderService.updateOrder(order);
        System.out.println("Order status updated.");
    }

    private void viewOrderDetails() {
        int orderId = getPositiveIntInput("Order ID: ");
        Order order = orderService.getOrderById(orderId);
        System.out.println(order);
        System.out.println("Ordered items:");
        for (MenuItem item : order.getItems()) {
            System.out.println("    " + item);
        }
        System.out.println("Order Total (with 10% service charge): $" + String.format("%.2f", order.getTotalAmount()));
    }

    // =====================================================
    // ============= RESERVATION MANAGEMENT ===============
    // =====================================================
    private void reservationMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nReservation Management");
            System.out.println("1. Create Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Back");

            int choice = getIntInput("Select: ");
            switch (choice) {
                case 1 -> createReservation();
                case 2 -> viewReservations();
                case 3 -> cancelReservation();
                case 4 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createReservation() {
        int id = getPositiveIntInput("Reservation ID: ");
        int custId = getPositiveIntInput("Customer ID: ");
        int tableId = getPositiveIntInput("Table ID: ");
        java.time.LocalDate date = java.time.LocalDate.parse(getNonEmptyInput("Reservation Date (YYYY-MM-DD): "));
        java.time.LocalTime time = java.time.LocalTime.parse(getNonEmptyInput("Reservation Time (HH:MM): "));
        int guests = getPositiveIntInput("Number of Guests: ");
        Customer customer = customerService.getCustomerById(custId);
        RestaurantTable table = tableService.getTableById(tableId);
        Reservation reservation = new Reservation(id, customer, table, date, time, guests);
        reservationService.createReservation(reservation);
        System.out.println("Reservation created.");
    }

    private void viewReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) System.out.println("No reservations found.");
        else reservations.forEach(System.out::println);
    }

    private void cancelReservation() {
        int id = getPositiveIntInput("Reservation ID: ");
        reservationService.cancelReservation(id);
        System.out.println("Reservation cancelled and table released.");
    }

    // =====================================================
    // ================= INPUT UTILITIES ===================
    // =====================================================
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }
    }

    private int getPositiveIntInput(String prompt) {
        while (true) {
            int n = getIntInput(prompt);
            if (n > 0) return n;
            System.out.println("Value must be positive.");
        }
    }

    private int getNonNegativeIntInput(String prompt) {
        while (true) {
            int n = getIntInput(prompt);
            if (n >= 0) return n;
            System.out.println("Value must be >= 0.");
        }
    }

    private double getPositiveDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double d = Double.parseDouble(scanner.nextLine());
                if (d > 0) return d;
                System.out.println("Value must be positive.");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }
    }

    private String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    private boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Enter Y or N.");
        }
    }
}