package com.jaw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
@TestPropertySource("/application-dev.properties")
public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mvc;

	protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
}
