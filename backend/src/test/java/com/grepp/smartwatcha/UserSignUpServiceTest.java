package com.grepp.smartwatcha;

import com.grepp.smartwatcha.app.model.SignUpRequest;
import com.grepp.smartwatcha.app.service.UserSignUpService;
import com.grepp.smartwatcha.infra.jpa.repository.UserRepository;
import com.grepp.smartwatcha.infra.jpa.repository.EmailVerificationRepository;
import com.grepp.smartwatcha.infra.jpa.entity.EmailVerificationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserSignUpServiceTest {
    @Autowired
    private UserSignUpService userSignUpService;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private EmailVerificationRepository emailVerificationRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // 이메일 인증이 항상 성공했다고 가정
        Mockito.when(emailVerificationRepository.findByEmail(Mockito.anyString()))
            .thenReturn(java.util.Optional.of(EmailVerificationEntity.builder().verified(true).build()));
    }

    @Test
    @DisplayName("정상적으로 회원가입이 된다")
    void signUp_success() {
        SignUpRequest request = SignUpRequest.builder()
                .email("test@example.com")
                .password("password123")
                .name("테스터")
                .build();
        Long userId = userSignUpService.signUp(request);
        assertThat(userRepository.findById(userId)).isPresent();
    }

    @Test
    @DisplayName("이메일이 중복되면 예외가 발생한다")
    void signUp_duplicateEmail() {
        SignUpRequest request = SignUpRequest.builder()
                .email("test@example.com")
                .password("password123")
                .name("테스터")
                .build();
        userSignUpService.signUp(request);
        assertThatThrownBy(() -> userSignUpService.signUp(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용 중인 이메일");
    }
} 