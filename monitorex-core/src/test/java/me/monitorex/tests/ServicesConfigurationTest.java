package me.monitorex.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.monitorex.monitor.Service;
import me.monitorex.monitor.ServicesConfiguration;

class ServicesConfigurationTest {

    private ServicesConfiguration config;
    private Service service1;
    private Service service2;

    @BeforeEach
    void setUp() {
        service1 = new Service("DatabaseService", "db.example.com", 3306, 60);
        service2 = new Service("EmailService", "mail.example.com", 25, 120);
        config = new ServicesConfiguration("MyConfig", new ArrayList<>());
        config.addService(service1);
        config.addService(service2);
    }

    @Test
    @DisplayName("Test adding a new service")
    void testAddService() {
        Service service3 = new Service("LoggingService", "log.example.com", 8080, 30);
        config.addService(service3);

        assertTrue(config.getServices().contains(service3));
        assertEquals(3, config.getServices().size());
    }

    @Test
    @DisplayName("Test retrieving services")
    void testGetServices() {
        List<Service> services = config.getServices();

        assertNotNull(services);
        assertTrue(services.contains(service1));
        assertTrue(services.contains(service2));
    }

    @Test
    @DisplayName("Test setting services")
    void testSetServices() {
        List<Service> newServices = new ArrayList<>();
        newServices.add(new Service("CacheService", "cache.example.com", 6379, 45));
        config.setServices(newServices);

        assertEquals(newServices, config.getServices());
        assertFalse(config.getServices().contains(service1));
        assertFalse(config.getServices().contains(service2));
    }

    @Test
    @DisplayName("Test getting the configuration name")
    void testGetName() {
        assertEquals("MyConfig", config.getName());
    }

    @Test
    @DisplayName("Test setting the configuration name")
    void testSetName() {
        config.setName("NewConfigName");
        assertEquals("NewConfigName", config.getName());
    }

    @Test
    @DisplayName("Test constructor without services initializes to null")
    void testConstructorWithoutServices() {
        ServicesConfiguration emptyConfig = new ServicesConfiguration();
        assertNull(emptyConfig.getServices());
    }

    @Test
    @DisplayName("Test adding a service with a null list initializes the list")
    void testAddServiceWithNullList() {
        ServicesConfiguration newConfig = new ServicesConfiguration();
        Service newService = new Service("NewService", "new.example.com", 8081, 15);
        newConfig.addService(newService);

        assertNotNull(newConfig.getServices());
        assertTrue(newConfig.getServices().contains(newService));
    }
}