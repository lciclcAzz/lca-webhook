//package com.lciclcazz.webhook.controller;
//
//import com.lciclcazz.webhook.Application;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@Transactional
//public class HooksControllerTest {
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        HooksController hooksController = new HooksController();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(hooksController).build();
//    }
//
//    @Test
//    public void testIndex() throws Exception {
//        mockMvc.perform(post("hooks/gitlab"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("hooks/gitlab"))
//                .andExpect(forwardedUrl("hooks/gitlab"));
//    }
//}