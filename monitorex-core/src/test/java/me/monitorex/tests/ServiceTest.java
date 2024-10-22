package me.monitorex.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.monitorex.monitor.Service;

class ServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service("ExampleService", "example.com", 8080, 60);
    }

    @Test
    void testServiceConstructor() {
        assertEquals("ExampleService", service.getName());
        assertEquals("example.com", service.getUrl());
        assertEquals(8080, service.getPort());
        assertEquals(60, service.getInterval());
    }

    @Test
    void testSetName() {
        service.setName("NewName");
        assertEquals("NewName", service.getName());
    }

    @Test
    void testSetUrl() {
        service.setUrl("newurl.com");
        assertEquals("newurl.com", service.getUrl());
    }

    @Test
    void testSetPort() {
        service.setPort(9090);
        assertEquals(9090, service.getPort());
    }

    @Test
    void testSetInterval() {
        service.setInterval(120);
        assertEquals(120, service.getInterval());
    }

    @Test
    void testEquals() {
        Service service2 = new Service("ExampleService", "example.com", 8080, 60);
        assertEquals(service, service2);
    }

    @Test
    void testHashCode() {
        Service service2 = new Service("ExampleService", "example.com", 8080, 60);
        assertEquals(service.hashCode(), service2.hashCode());
    }
    
    @Test
    void testEqualsWithSameObject() {
        Service service = new Service("ExampleService", "example.com", 8080, 60);
        assertEquals(service, service);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Service service = new Service("ExampleService", "example.com", 8080, 60);
        String differentClassObject = "This is a string";
        assertNotEquals(service, differentClassObject);
    }

    @Test
    void testEqualsWithNull() {
        Service service = new Service("ExampleService", "example.com", 8080, 60);
        Service nullService = null;
        assertNotEquals(service, nullService);
    }

    @Test
    void testEqualsWithAllAttributesEqual() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("ExampleService", "example.com", 8080, 60);
        assertEquals(service1, service2);
    }

    @Test
    void testEqualsWithNameDifferent() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("AnotherService", "example.com", 8080, 60);
        assertNotEquals(service1, service2);
    }

    @Test
    void testEqualsWithUrlDifferent() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("ExampleService", "anotherexample.com", 8080, 60);
        assertNotEquals(service1, service2);
    }

    @Test
    void testEqualsWithPortDifferent() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("ExampleService", "example.com", 9090, 60);
        assertNotEquals(service1, service2);
    }

    @Test
    void testEqualsWithIntervalDifferent() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("ExampleService", "example.com", 8080, 120);
        assertNotEquals(service1, service2);
    }

    @Test
    void testHashCodeConsistentWithEquals() {
        Service service1 = new Service("ExampleService", "example.com", 8080, 60);
        Service service2 = new Service("ExampleService", "example.com", 8080, 60);
        Service service3 = new Service("DifferentService", "example.com", 8080, 60);

        assertEquals(service1.hashCode(), service2.hashCode());
        assertNotEquals(service1.hashCode(), service3.hashCode());
    }

}