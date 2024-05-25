package app.services;

import app.dto.RoleDto;
import app.exceptions.EntityNotFoundException;
import app.mappers.RoleMapper;
import app.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleDto getRoleByName(String name) {
        return roleMapper.toDto(roleRepository.findByName(name));
    }

    public Set<RoleDto> getRolesByName(Set<RoleDto> roles) {
        var userRoles = new HashSet<RoleDto>();
        roles.forEach(role -> {
            var roleFromDb = getRoleByName(role.getName());
            if (roleFromDb == null) {
                throw new EntityNotFoundException(
                        "Operation was not finished because Role was not found with name = " + role.getName()
                );
            }
            userRoles.add(roleFromDb);
        });
        return userRoles;
    }

    public Set<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toSet());
    }
}