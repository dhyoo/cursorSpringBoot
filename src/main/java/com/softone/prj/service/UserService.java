package com.softone.prj.service;

import com.softone.prj.dto.UserDto;
import com.softone.prj.entity.User;
import com.softone.prj.exception.BadRequestException;
import com.softone.prj.exception.ResourceNotFoundException;
import com.softone.prj.mapper.EntityMapper;
import com.softone.prj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper mapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("사용자", "id", id));
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() != null && userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new BadRequestException("이미 존재하는 이메일입니다: " + userDto.getEmail());
        }
        
        User user = mapper.toUserEntity(userDto);
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        if (user.getStatus() == null) {
            user.setStatus("활성");
        }
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDate.now());
        }
        User savedUser = userRepository.save(user);
        return mapper.toUserDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("사용자", "id", id));
        
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) {
            if (!user.getEmail().equals(userDto.getEmail()) && 
                userRepository.findByEmail(userDto.getEmail()).isPresent()) {
                throw new BadRequestException("이미 존재하는 이메일입니다: " + userDto.getEmail());
            }
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getRole() != null) user.setRole(userDto.getRole());
        if (userDto.getStatus() != null) user.setStatus(userDto.getStatus());
        if (userDto.getLastLogin() != null) {
            user.setLastLogin(LocalDate.parse(userDto.getLastLogin(), formatter));
        }
        User updatedUser = userRepository.save(user);
        return mapper.toUserDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("사용자", "id", id);
        }
        userRepository.deleteById(id);
    }
}
