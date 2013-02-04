package com.mirantis.aminakov.bigdatacourse.webapp;

import org.springframework.web.servlet.ModelAndView;
import static org.junit.Assert.*;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.webapp.HelloController;

public class HelloControllerTest {

    @Test
    public void testHandleRequestView() throws Exception {
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("index", modelAndView.getViewName());
    }
}