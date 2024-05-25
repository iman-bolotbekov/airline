package app.controllers;


import app.dto.FlightSeatDto;
import app.entities.Aircraft;
import app.entities.Flight;
import app.entities.FlightSeat;
import app.entities.Seat;
import app.enums.Airport;
import app.enums.FlightStatus;
import app.mappers.FlightSeatMapper;
import app.repositories.AircraftRepository;
import app.repositories.CategoryRepository;
import app.repositories.DestinationRepository;
import app.repositories.FlightRepository;
import app.repositories.FlightSeatRepository;
import app.repositories.SeatRepository;
import app.services.FlightSeatService;
import app.services.FlightService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.equalTo;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-flightSeat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FlightSeatControllerIT extends IntegrationTestBase {

    @Autowired
    private FlightSeatService flightSeatService;
    @Autowired
    private FlightSeatRepository flightSeatRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private FlightService flightService;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FlightSeatMapper flightSeatMapper;

    public FlightSeat createFlightSeat() {
        var aircraft = new Aircraft();
        aircraft.setId(Long.valueOf(1));
        aircraft.setAircraftNumber("Number:A-1");
        aircraft.setModel("ModelAir");
        aircraft.setFlightRange(500);
        aircraft.setModelYear(2008);

        var seat = new Seat();
        seat.setSeatNumber("2B");
        seat.setIsNearEmergencyExit(false);
        seat.setIsLockedBack(true);
        seat.setCategory(categoryRepository.findById(3L).get());
        seat.setAircraft(aircraft);

        var from = destinationRepository.getDestinationByAirportCode(Airport.OMS);
        var to = destinationRepository.getDestinationByAirportCode(Airport.VKO);

        var savedSeat = seatRepository.save(seat);

        var savedAircraft = aircraftRepository.save(aircraft);
        var flight = new Flight();
        flight.setFrom(from);
        flight.setTo(to);
        flight.setCode("OMSSVX");
        flight.setFlightStatus(FlightStatus.COMPLETED);
        flight.setAircraft(savedAircraft);
        flight.setArrivalDateTime(LocalDateTime.parse("2023-12-23T00:00:00"));
        flight.setDepartureDateTime(LocalDateTime.parse("2023-12-23T15:00:00"));
        var savedFlight = flightRepository.save(flight);

        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setFare(1000);
        flightSeat.setIsBooked(false);
        flightSeat.setIsSold(false);
        flightSeat.setIsRegistered(false);
        flightSeat.setSeat(savedSeat);
        flightSeat.setFlight(savedFlight);

        return flightSeatRepository.save(flightSeat);
    }

    @Test
    void shouldGetAllFlightSeatsByNullPage() throws Exception {
        createFlightSeat();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats?size=2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllFlightSeatsByNullSize() throws Exception {
        createFlightSeat();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats?page=0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBadRequestByPage() throws Exception {
        createFlightSeat();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats?page=-1&size=2"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBadRequestBySize() throws Exception {
        createFlightSeat();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats?page=0&size=0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetPageFlightSeats() throws Exception {
        createFlightSeat();
        var pageable = PageRequest.of(0, 4);
        mockMvc.perform(get("http://localhost:8080/api/flight-seats?page=0&size=4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(flightSeatService
                        .getAllFlightSeats(pageable.getPageNumber(), pageable.getPageSize()))));
    }


    @Test
    void shouldGetFlightSeatDTOById() throws Exception {
        FlightSeat flightSeat = createFlightSeat();
        Long id = flightSeat.getId();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }


    @Test
    void shouldGetFlightSeats() throws Exception {
        var flightId = createFlightSeat().getFlight().getId().toString();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats")
                        .param("flightId", flightId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    void shouldGetFreeSeats() throws Exception {
        FlightSeat flightSeat = createFlightSeat();
        var flightId = flightSeat.getFlight().getId().toString();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats")
                        .param("flightId", flightId)
                        .param("isSold", "false")
                        .param("isRegistered", "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    void shouldGetNonSoldFlightSeatsByFlightId() throws Exception {
        FlightSeat flightSeat = createFlightSeat();
        var flightId = flightSeat.getFlight().getId().toString();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats")
                        .param("flightId", flightId)
                        .param("isSold", "false")
                        .param("isRegistered", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    void shouldAddFlightSeatsByFlightId() throws Exception {
        var flightId = createFlightSeat().getFlight().getId().toString();
        var flightSeats = flightSeatService.getFlightSeatsByFlightId(Long.valueOf(flightId));
        for (FlightSeatDto flightSeat : flightSeats) {
            flightSeatService.deleteFlightSeatById(flightSeat.getId());
        }
        mockMvc.perform(
                        post("http://localhost:8080/api/flight-seats/generate?flightId={flightId}", flightId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldEditFlightSeatById() throws Exception {
        FlightSeat flightSeat = createFlightSeat();
        Long id = flightSeat.getId();
        var flightSeatBD = flightSeatService.getFlightSeat(id).get();
        flightSeatBD.setFare(100);
        flightSeatBD.setIsSold(false);
        flightSeatBD.setIsRegistered(false);
        long numberOfFlightSeat = flightSeatRepository.count();

        mockMvc.perform(patch("http://localhost:8080/api/flight-seats/{id}", id)
                        .content(objectMapper.writeValueAsString(Mappers.getMapper(FlightSeatMapper.class).toDto(flightSeatBD)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(flightSeatRepository.count(), equalTo(numberOfFlightSeat)));
    }

    @Test
    void shouldGenerateFlightSeatsForFlightIdempotent() throws Exception {
        var flightId = createFlightSeat().getFlight().getId().toString();
        mockMvc.perform(post("http://localhost:8080/api/flight-seats/generate")
                        .param("flightId", flightId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllFlightSeats() throws Exception {
        createFlightSeat();
        mockMvc.perform(get("http://localhost:8080/api/flight-seats"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @DisplayName("Проверка отсутствия возможности у пользователя изменить id места в самолете при POST запросе")
    void shouldNotChangeIdByUserFromPostRequest() throws Exception {
        FlightSeatDto flightSeatDto = flightSeatMapper.toDto(createFlightSeat());
        flightSeatDto.setId(33L);

        mockMvc.perform(post("http://localhost:8080/api/flight-seats")
                        .content(objectMapper.writeValueAsString(flightSeatDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(not(33)));
    }

    @Test
    @DisplayName("Проверка отсутствия возможности у пользователя изменить id места в самолете при PATCH запросе")
    void shouldNotChangeIdByUserFromPatchRequest() throws Exception {

        FlightSeatDto flightSeatDto = flightSeatMapper.toDto(createFlightSeat());
        flightSeatDto.setId(33L);
        var id = 1L;

        mockMvc.perform(patch("http://localhost:8080/api/flight-seats/{id}", id)
                        .content(objectMapper.writeValueAsString(flightSeatDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.id").value(not(33)));
    }
}