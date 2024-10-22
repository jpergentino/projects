package me.monitorex.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.monitorex.monitor.MonitorThread;
import me.monitorex.monitor.Service;

class MonitorThreadTest {

    @Test
    @DisplayName("MonitorThread runs with a non-null Service")
    void testMonitorThreadRunWithNonNullService() {
        // Arrange
        Service service = new Service("ExampleService", "example.com", 8080, 60);
        MonitorThread monitorThread = new MonitorThread(service);
        
        // Act & Assert
        assertDoesNotThrow(monitorThread::run);
    }

    @Test
    @DisplayName("MonitorThread throws NullPointerException with null Service")
    void testMonitorThreadRunWithNullService() {

    	MonitorThread monitorThread = new MonitorThread(null);
        
        Exception exception = assertThrows(NullPointerException.class, monitorThread::run);
        assertEquals(MonitorThread.MSG_SERVICE_IS_REQUIRED, exception.getMessage());
    }
}