package app.controllers;

import app.dto.AccountDto;
import app.dto.RoleDto;
import app.repositories.AccountRepository;
import app.services.AccountService;
import app.services.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.equalTo;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-account-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountControllerIT extends IntegrationTestBase {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountRepository accountRepository;

    private AccountDto createAccountDto() {
        var accountDTO = new AccountDto();
        accountDTO.setUsername("ivanuser");
        accountDTO.setFirstName("Ivan");
        accountDTO.setLastName("Ivanov");
        accountDTO.setBirthDate(LocalDate.of(2023, 3, 23));
        accountDTO.setPhoneNumber("7933333333");
        accountDTO.setEmail("manager2@mail.ru");
        accountDTO.setPassword("Test123@");
        accountDTO.setSecurityQuestion("Test");
        accountDTO.setAnswerQuestion("Test");
        accountDTO.setRoles(Set.of(roleService.getRoleByName("ROLE_MANAGER")));
        return accountDTO;
    }

    // Пагинация 2.0
    @Test
    void shouldGetAllAccounts() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllAccountsByNullPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts?size=2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllAccountsByNullSize() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts?page=0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPageAccounts() throws Exception {
        var pageable = PageRequest.of(0, 2);
        mockMvc.perform(get("http://localhost:8080/api/accounts?page=0&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountService
                        .getAllAccounts(pageable.getPageNumber(), pageable.getPageSize()))));
    }

    @Test
    void shouldGetBadRequestByPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts?page=-1&size=2"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBadRequestBySize() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts?page=0&size=0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Пагинация 2.0
    @Test
    void shouldGetAccountById() throws Exception {
        var id = 4L;
        mockMvc.perform(get("http://localhost:8080/api/accounts/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(accountService.getAccountById(id).get())));
    }

    @Test
    void shouldGetNotExistedAccount() throws Exception {
        var id = 100L;
        mockMvc.perform(get("http://localhost:8080/api/accounts/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPostNewAccount() throws Exception {
        var accountDTO = createAccountDto();
        accountDTO.setId(100L);
        mockMvc.perform(post("http://localhost:8080/api/accounts")
                        .content(objectMapper.writeValueAsString(accountDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(accountDTO.getId())));
    }

    @Test
    void shouldReturnValidationErrorsForInvalidAccount() throws Exception {
        var accountDTO = createAccountDto();
        accountDTO.setFirstName("");
        accountDTO.setLastName("Ivanov123");
        accountDTO.setBirthDate(LocalDate.of(2026, 3, 23));
        accountDTO.setPhoneNumber("79333");
        accountDTO.setEmail("manager2@mail#.ru");
        accountDTO.setPassword("Test123");
        accountDTO.setSecurityQuestion("");
        accountDTO.setAnswerQuestion("");
        accountDTO.setRoles(Set.of(roleService.getRoleByName("ROLE_MANAGER")));

        mockMvc.perform(post("http://localhost:8080/api/accounts")
                        .content(objectMapper.writeValueAsString(accountDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteAccountById() throws Exception {
        var id = 4L;
        mockMvc.perform(delete("http://localhost:8080/api/accounts/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/accounts/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteNotExistedAccount() throws Exception {
        var id = 100L;
        mockMvc.perform(delete("http://localhost:8080/api/accounts/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        Long id = 4L;
        var updatableAccount = accountService.getAccountById(id).get();

        updatableAccount.setEmail("test@mail.ru");
        long numberOfAccounts = accountRepository.count();

        mockMvc.perform(patch("http://localhost:8080/api/accounts/{id}", id)
                        .content(objectMapper.writeValueAsString(updatableAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andExpect(result -> assertThat(accountRepository.count(), equalTo(numberOfAccounts)));
    }

    @Test
    void shouldUpdateNotExistedAccount() throws Exception {
        Long id = 42L;
        var updatableAccount = createAccountDto();

        mockMvc.perform(patch("http://localhost:8080/api/accounts/{id}", id)
                        .content(objectMapper.writeValueAsString(updatableAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateIgnoreIdFieldInAccountDto() throws Exception {
        Long id = 4L;
        var updatableAccount = accountService.getAccountById(id).get();
        updatableAccount.setId(100L);

        mockMvc.perform(patch("http://localhost:8080/api/accounts/{id}", id)
                        .content(objectMapper.writeValueAsString(updatableAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(updatableAccount.getId())));
    }

    @Test
    void shouldUpdateNotOverrideUnchangedFieldsAccountDto() throws Exception {
        Long id = 4L;
        var updatableAccount = accountService.getAccountById(id).get();
        updatableAccount.setSecurityQuestion("test");
        updatableAccount.setLastName("testName");

        mockMvc.perform(patch("http://localhost:8080/api/accounts/{id}", id)
                        .content(objectMapper.writeValueAsString(updatableAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatableAccount.getFirstName()))
                .andExpect(jsonPath("$.email").value(updatableAccount.getEmail()))
                .andExpect(jsonPath("$.birthDate").value(updatableAccount.getBirthDate().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(updatableAccount.getPhoneNumber()));
    }

    @Test
    void shouldGetAllRoles() throws Exception {
        var allRoles = roleService.getAllRoles();
        MockHttpServletResponse response = mockMvc.perform(get("http://localhost:8080/api/accounts/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var rolesFromResponse = objectMapper.readValue(response.getContentAsString(), new TypeReference<Set<RoleDto>>() {
        });
        assertEquals(allRoles, rolesFromResponse);
    }

    @Test
    @Sql(value = "/sqlQuery/delete-roles-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldGetNotExistedAllRoles() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/accounts/roles"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}