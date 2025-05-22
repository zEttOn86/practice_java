package com.vogella.tasks.services.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.vogella.tasks.model.TaskService;

class TransientTaskServiceIntegrationTest {

	@Test
    public void assertServiceAccessWithOSGiContextWorks() {
        TaskService taskService = getService(TaskService.class);
        assertNotNull(taskService, "No TaskService found");
    }

    static <T> T getService(Class<T> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(TransientTaskServiceIntegrationTest.class);
        if (bundle != null) {
            ServiceTracker<T, T> st =
                new ServiceTracker<T, T>(
                    bundle.getBundleContext(), clazz, null);
            st.open();
            if (st != null) {
                try {
                    // give the runtime some time to startup
                    return st.waitForService(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
