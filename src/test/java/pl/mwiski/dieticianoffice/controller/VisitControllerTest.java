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
import pl.mwiski.dieticianoffice.dto.SimpleDieticianDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.dto.VisitDto;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.Visit;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import pl.mwiski.dieticianoffice.service.VisitService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VisitController.class)
public class VisitControllerTest {

    private String key =

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;
    private static final boolean COMPLETED = false;
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String MAIL = "user@mail.com";
    private static final String DIET_MAIL = "dietician@mail.com";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VisitService visitService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Visit visit;
    private VisitDto visitDto;
    private User user;
    private SimpleUserDto simpleUserDto;
    private Dietician dietician;
    private SimpleDieticianDto simpleDieticianDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        user.setId(1);
        simpleUserDto = new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getLogin().getLogin(),
                user.getLogin().getRole(),
                user.getPhoneNumber(),
                user.getMail());

        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        simpleDieticianDto =  new SimpleDieticianDto(
                dietician.getId(),
                dietician.getName(),
                dietician.getLastName(),
                dietician.getLogin().getLogin(),
                user.getLogin().getRole(),
                dietician.getPhoneNumber(),
                dietician.getMail());

        visit = new Visit(1, DATE_AND_TIME, user, dietician, AVAILABLE, COMPLETED);
        dietician.getVisits().add(visit);
        user.getVisits().add(visit);
        visitDto = new VisitDto(1, MapperUtils.dateToString(DATE_AND_TIME), simpleUserDto, simpleDieticianDto, AVAILABLE, COMPLETED);
    }

    @Test
    public void shouldReturnAllVisits() throws Exception {
        //Given
        when(visitService.getAll()).thenReturn(Arrays.asList(visitDto));

        //When & Then
        mockMvc.perform(get("/v1/visits/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)))
                .andExpect(jsonPath("$[0].dietician.id", is(0)))
                .andExpect(jsonPath("$[0].dietician.name", is(NAME)))
                .andExpect(jsonPath("$[0].dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$[0].available", is(AVAILABLE)))
                .andExpect(jsonPath("$[0].completed", is(COMPLETED)));
    }

    @Test
    public void shouldReturnAvailableVisits() throws Exception {
        //Given
        when(visitService.getAvailableVisits(DATE_AND_TIME.toLocalDate().toString())).thenReturn(Arrays.asList(visitDto));

        //When & Then
        mockMvc.perform(get("/v1/visits/date/" + key)
                .param("date", DATE_AND_TIME.toLocalDate().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)))
                .andExpect(jsonPath("$[0].dietician.id", is(0)))
                .andExpect(jsonPath("$[0].dietician.name", is(NAME)))
                .andExpect(jsonPath("$[0].dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$[0].available", is(AVAILABLE)))
                .andExpect(jsonPath("$[0].completed", is(COMPLETED)));
    }

    @Test
    public void shouldGetUserVisits() throws Exception {
        //Given
        when(visitService.getUserVisits(simpleUserDto.getId())).thenReturn(Arrays.asList(visitDto));

        //When & Then
        mockMvc.perform(get("/v1/visits/users/" + simpleUserDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)))
                .andExpect(jsonPath("$[0].dietician.id", is(0)))
                .andExpect(jsonPath("$[0].dietician.name", is(NAME)))
                .andExpect(jsonPath("$[0].dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$[0].available", is(AVAILABLE)))
                .andExpect(jsonPath("$[0].completed", is(COMPLETED)));
    }

    @Test
    public void shouldGetDieticianVisits() throws Exception {
        //Given
        when(visitService.getDieticianVisits(simpleDieticianDto.getId())).thenReturn(Arrays.asList(visitDto));

        //When & Then
        mockMvc.perform(get("/v1/visits/dieticians/" + simpleDieticianDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)))
                .andExpect(jsonPath("$[0].dietician.id", is(0)))
                .andExpect(jsonPath("$[0].dietician.name", is(NAME)))
                .andExpect(jsonPath("$[0].dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$[0].available", is(AVAILABLE)))
                .andExpect(jsonPath("$[0].completed", is(COMPLETED)));
    }

    @Test
    public void shouldGetVisit() throws Exception {
        //Given
        when(visitService.get(visit.getId())).thenReturn(visitDto);

        //When & Then
        mockMvc.perform(get("/v1/visits/" + visit.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)))
                .andExpect(jsonPath("$.dietician.id", is(0)))
                .andExpect(jsonPath("$.dietician.name", is(NAME)))
                .andExpect(jsonPath("$.dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$.available", is(AVAILABLE)))
                .andExpect(jsonPath("$.completed", is(COMPLETED)));
    }

    @Test
    public void shouldAddVisit() throws Exception {
        //Given
        when(visitService.add(visitDto)).thenReturn(visitDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(visitDto);

        //When & Then
        mockMvc.perform(post("/v1/visits/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)))
                .andExpect(jsonPath("$.dietician.id", is(0)))
                .andExpect(jsonPath("$.dietician.name", is(NAME)))
                .andExpect(jsonPath("$.dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$.available", is(AVAILABLE)))
                .andExpect(jsonPath("$.completed", is(COMPLETED)));
    }

    @Test
    public void shouldScheduleVisit() throws Exception {
        //Given
        when(visitService.schedule(visitDto.getId(), simpleUserDto.getId())).thenReturn(visitDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(visitDto);

        //When & Then
        mockMvc.perform(put("/v1/visits/" + visitDto.getId() + "/users/" + simpleUserDto.getId() + "/" + key)
                .param("content", "content")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.dateTime", is(DATE_AND_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)))
                .andExpect(jsonPath("$.dietician.id", is(0)))
                .andExpect(jsonPath("$.dietician.name", is(NAME)))
                .andExpect(jsonPath("$.dietician.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.dietician.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.dietician.mail", is(DIET_MAIL)))
                .andExpect(jsonPath("$.available", is(AVAILABLE)))
                .andExpect(jsonPath("$.completed", is(COMPLETED)));
    }

    @Test
    public void shouldCancelVisit() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(visitDto);

        //When & Then
        mockMvc.perform(delete("/v1/visits/" + visitDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
