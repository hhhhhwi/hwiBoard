package com.hwi.board.controller;

import com.hwi.board.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("인증 관련 Controller 테스트")
@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private final MockMvc mockMvc;

    public AuthControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("로그인 뷰 테스트")
    @Test
    public void givenNothing_whenTryingToLogin_thenReturnsLoginView() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}