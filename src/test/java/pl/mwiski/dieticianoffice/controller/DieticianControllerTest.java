package pl.mwiski.dieticianoffice.controller;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mwiski.dieticianoffice.dto.DieticianDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.service.DieticianService;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DieticianController.class)
public class DieticianControllerTest {

    private static final String LOGIN = "dietlogin";
    private static final String PASSWORD = "dietpass";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String MAIL = "dietician@mail.com";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DieticianService dieticianService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Dietician dietician;
    private DieticianDto dieticianDto;
    private String key =

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        dieticianDto = new DieticianDto(
                dietician.getId(),
                dietician.getLogin().getLogin(),
                dietician.getLogin().getPassword(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getLogin().getRole(),
                dietician.getPhoneNumber(),
                dietician.getMail()
        );
    }

    @Test
    public void shouldReturnAllDieticians() throws Exception {
        //Given
        when(dieticianService.getAll()).thenReturn(Arrays.asList(dieticianDto));

        //When & Then
        mockMvc.perform(get("/v1/dieticians/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].login", is(LOGIN)))
                .andExpect(jsonPath("$[0].password", is(PASSWORD)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].mail", is(MAIL)));
    }

    @Test
    public void shouldGetDietician() throws Exception {
        //Given
        when(dieticianService.get(dieticianDto.getId())).thenReturn(dieticianDto);

        //When & Then
        mockMvc.perform(get("/v1/dieticians/" + dieticianDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldAddDietician() throws Exception {
        //Given
        when(dieticianService.add(dieticianDto)).thenReturn(dieticianDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(dieticianDto);

        //When & Then
        mockMvc.perform(post("/v1/dieticians/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldUpdateDietician() throws Exception {
        //Given
        when(dieticianService.update(dieticianDto)).thenReturn(dieticianDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(dieticianDto);

        //When & Then
        mockMvc.perform(put("/v1/dieticians/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldDeleteDietician() throws Exception {
        //Given
        when(dieticianService.get(dieticianDto.getId())).thenReturn(dieticianDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(dieticianDto);

        //When & Then
        mockMvc.perform(delete("/v1/dieticians/" + dieticianDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
