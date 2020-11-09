package com.getir.rig;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestWebApp extends ReadingIsGoodApplication {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    public TestWebApp(ResourceServerProperties resourceServerProperties) {
        super(resourceServerProperties);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*@Test
    public void testCustomer() throws Exception {
        mockMvc.perform(get("/customer/register")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("Jack"))
                .andExpect(jsonPath("$.surname").value("Ryan"))
                .andExpect(jsonPath("$.phone").value("905324121212"))
                .andExpect(jsonPath("$.email").value("jack.ryan@sample.com"));

    }*/
}
