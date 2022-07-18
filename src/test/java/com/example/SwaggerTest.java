package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class SwaggerTest {

   private static final String DEFAULT_SWAGGER_UI_PATH = "/swagger-ui.html";
   private static final String CUSTOM_SWAGGER_UI_PATH = "/custom" + DEFAULT_SWAGGER_UI_PATH;
   private static final String DEFAULT_API_PATH = "/v3/api-docs";
   private static final String CUSTOM_API_PATH = "/custom" + DEFAULT_API_PATH;

   @Autowired
   protected MockMvc mvc;

   public static class BaseTest {

      @Value("${springdoc.swagger-ui.path:#{null}}")
      protected String swagPath;

      @Value("${springdoc.api-docs.path:#{null}}")
      protected String apiPath;

      @Value("${springdoc.swagger-ui.enabled:#{null}}")
      protected String swagEnabled;

      @Value("${springdoc.api-docs.enabled:#{null}}")
      protected String apiEnabled;

      @BeforeEach
      void setUp() {
         logStuff();
      }

      protected void logStuff() {
         log.info("Swagger enabled: {}", swagEnabled);
         log.info("Swagger path: {}", swagPath);
         log.info("Api path enabled: {}", apiEnabled);
         log.info("Api path: {}", apiPath);
      }
   }

   @Nested
   @ActiveProfiles({"swagger-disabled"})
   public class SwaggerDisabledTest extends BaseTest {

      @Test
      public void apiCustomPathNotFound() throws Exception {
         log.info("Checking {}", CUSTOM_API_PATH);
         mvc.perform(get(CUSTOM_API_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void apiDefaultPathNotFound() throws Exception {
         log.info("Checking {}", DEFAULT_API_PATH);
         mvc.perform(get(DEFAULT_API_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void uiCustomPathNotFound() throws Exception {
         log.info("Checking {}", CUSTOM_SWAGGER_UI_PATH);
         mvc.perform(get(CUSTOM_SWAGGER_UI_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void uiDefaultPathNotFound() throws Exception {
         log.info("Checking {}", DEFAULT_SWAGGER_UI_PATH);
         mvc.perform(get(DEFAULT_SWAGGER_UI_PATH))
               .andExpect(status().isNotFound());
      }

   }

   @Nested
   @ActiveProfiles({"swagger-default"})
   public class SwaggerEnabledDefaultTest extends BaseTest {

      @Test
      public void apiCustomPathNotFound() throws Exception {
         log.info("Checking {}", CUSTOM_API_PATH);
         mvc.perform(get(CUSTOM_API_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void uiCustomPathNotFound() throws Exception {
         log.info("Checking {}", CUSTOM_SWAGGER_UI_PATH);
         mvc.perform(get(CUSTOM_SWAGGER_UI_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void apiDefaultPathOk() throws Exception {
         log.info("Checking {}", DEFAULT_API_PATH);
         mvc.perform(get(DEFAULT_API_PATH))
               .andExpect(status().isOk());
      }

      @Test
      public void uiDefaultPathOk() throws Exception {
         log.info("Checking {}", DEFAULT_SWAGGER_UI_PATH);
         mvc.perform(get(DEFAULT_SWAGGER_UI_PATH))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/swagger-ui/index.html"));
      }

   }

   @Nested
   @ActiveProfiles({"swagger-custom"})
   public class SwaggerEnabledCustomTest extends BaseTest {

      @Test
      public void apiCustomPathOk() throws Exception {
         log.info("Checking {}", CUSTOM_API_PATH);
         mvc.perform(get(CUSTOM_API_PATH))
               .andExpect(status().isOk());
      }

      @Test
      public void uiCustomPathOk() throws Exception {
         log.info("Checking {}", CUSTOM_SWAGGER_UI_PATH);
         mvc.perform(get(CUSTOM_SWAGGER_UI_PATH))
               .andExpect(status().isOk());
      }

      @Test
      public void apiDefaultPathNotFound() throws Exception {
         log.info("Checking {}", DEFAULT_API_PATH);
         mvc.perform(get(DEFAULT_API_PATH))
               .andExpect(status().isNotFound());
      }

      @Test
      public void uiDefaultPathNotFound() throws Exception {
         log.info("Checking {}", DEFAULT_SWAGGER_UI_PATH);
         mvc.perform(get(DEFAULT_SWAGGER_UI_PATH))
               .andExpect(status().isNotFound());
      }

   }
}
