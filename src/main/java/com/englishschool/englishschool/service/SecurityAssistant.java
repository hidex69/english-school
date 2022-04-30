package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.exception.EntityNotFoundException;
import com.englishschool.englishschool.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityAssistant {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        UserEntity user = userRepository.findByEmail(userName).orElseThrow(EntityNotFoundException::new);
        return user;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
