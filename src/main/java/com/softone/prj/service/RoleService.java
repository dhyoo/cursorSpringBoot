package com.softone.prj.service;

import com.softone.prj.dto.RoleDto;
import com.softone.prj.entity.Role;
import com.softone.prj.exception.BadRequestException;
import com.softone.prj.exception.ResourceNotFoundException;
import com.softone.prj.mapper.EntityMapper;
import com.softone.prj.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final EntityMapper mapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(mapper::toRoleDto)
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(mapper::toRoleDto)
                .orElseThrow(() -> new ResourceNotFoundException("역할", "id", id));
    }

    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        if (roleDto.getCode() != null && roleRepository.findByCode(roleDto.getCode()).isPresent()) {
            throw new BadRequestException("?대? 議댁옱?섎뒗 ??븷 肄붾뱶?낅땲?? " + roleDto.getCode());
        }
        
        Role role = mapper.toRoleEntity(roleDto);
        if (role.getUserCount() == null) {
            role.setUserCount(0);
        }
        if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
            role.setPermissions(Arrays.asList("READ"));
        }
        if (role.getCreatedAt() == null) {
            role.setCreatedAt(LocalDate.now());
        }
        Role savedRole = roleRepository.save(role);
        return mapper.toRoleDto(savedRole);
    }

    @Transactional
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("역할", "id", id));
        
        if (roleDto.getName() != null) role.setName(roleDto.getName());
        if (roleDto.getCode() != null) {
            if (!role.getCode().equals(roleDto.getCode()) && 
                roleRepository.findByCode(roleDto.getCode()).isPresent()) {
                throw new BadRequestException("?대? 議댁옱?섎뒗 ??븷 肄붾뱶?낅땲?? " + roleDto.getCode());
            }
            role.setCode(roleDto.getCode());
        }
        if (roleDto.getDescription() != null) role.setDescription(roleDto.getDescription());
        if (roleDto.getPermissions() != null) role.setPermissions(roleDto.getPermissions());
        if (roleDto.getUserCount() != null) role.setUserCount(roleDto.getUserCount());
        Role updatedRole = roleRepository.save(role);
        return mapper.toRoleDto(updatedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("??븷", "id", id);
        }
        roleRepository.deleteById(id);
    }
}
