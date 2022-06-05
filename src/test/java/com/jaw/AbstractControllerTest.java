package com.jaw;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
@TestPropertySource("/application-dev.properties")
public abstract class AbstractControllerTest {

	@Autowired
	protected WebApplicationContext wac;

	protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	protected MockMvc mvc;

	@BeforeEach
	protected void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac)
			.addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
			.alwaysDo(print())
			.build();
	}
}
