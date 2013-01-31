package com.mirantis.aminakov.bigdatacourse;

import org.springframework.web.servlet.ModelAndView;
import static org.junit.Assert.*;
import org.junit.Test;

public class HelloControllerTest {

    @Test
    public void testHandleRequestView() throws Exception {
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("view", modelAndView.getViewName());
    }
}