package app.controllers;

import app.dto.search.SearchResultCardData;
import app.entities.Flight;
import app.dto.search.Search;
import app.dto.search.SearchResult;
import app.enums.Airport;
import app.enums.CategoryType;
import app.services.FlightService;
import app.services.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static app.enums.Airport.OMS;
import static app.enums.Airport.VKO;
import static app.enums.CategoryType.BUSINESS;
import static app.enums.CategoryType.ECONOMY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-search-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SearchControllerIT extends IntegrationTestBase {

    @Autowired
    private SearchService searchService;
    @Autowired
    private FlightService flightService;

    // Прямые Рейсы туда: Внуково-Омск
    // Прямые рейсы обратно: Омск-Внуково

    //1. В базе: один прямой рейс туда с наличием мест (3 свободных, 2 из них эконом-класса).
    //   Поиск: рейс туда (2023-04-01) без поиска обратного рейса для 2-х пассажиров
    @DisplayName("1 test. In DB 1 direct depart flight with 3 free seats")
    @Test
    void shouldReturnOneDirectDepartFlight() throws Exception {
        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 4, 1);
        LocalDate returnDate = null;
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = ECONOMY;

        int expDirDepartFlights = 1;
        int expDirReturnFlights = 0;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());

        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();

        assertEquals(expDirDepartFlights + expDirReturnFlights, dataBackCount + dataToCount);
        assertNull(searchResult.getFlights().get(0).getDataBack());

        assertNumberOfDepartDirectFlights(expDirDepartFlights, search, json);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        Integer fareDepart = searchService.findFare(search, departFlights.get(0));

        assertEquals(15940, fareDepart);

        assertEquals(15940, searchResult.getFlights().get(0).getTotalPrice());

        assertEquals("3ч 30м", searchResult.getFlights().get(0).getDataTo().getFlightTime());

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }
    }

    //2. В базе: один прямой рейс туда и один рейс обратно с наличием мест (3 свободных мест, 2 из них эконом-класса).
    //   Поиск: рейс туда (2023-04-01) и рейс обратно (2023-04-03) для 2-х пассажиров
    @DisplayName("2 test. In DB 1 direct depart flight 1 direct return flight")
    @Test
    void shouldReturnOneDirectDepartAndOneDirectReturnFlights() throws Exception {

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 4, 1);
        LocalDate returnDate = LocalDate.of(2023, 4, 3);
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = ECONOMY;

        int expDirDepartFlights = 1;
        int expDirReturnFlights = 1;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());
        assertNotNull(searchResult.getFlights().get(0).getDataBack());
        assertNotNull(searchResult.getFlights().get(0).getDataTo());

        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();

        assertEquals(expDirDepartFlights + expDirReturnFlights, dataBackCount + dataToCount);

        assertNumberOfDepartDirectFlights(expDirDepartFlights, search, json);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        List<Flight> returnFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getTo(),
                search.getFrom(),
                Date.valueOf(search.getReturnDate()));

        Integer fareDepart = searchService.findFare(search, departFlights.get(0));

        Integer fareReturn = searchService.findFare(search, returnFlights.get(0));

        assertEquals(15940, fareDepart);
        assertEquals(15940, fareReturn);

        assertEquals(31880, searchResult.getFlights().get(0).getTotalPrice());

        assertEquals("3ч 30м", searchResult.getFlights().get(0).getDataTo().getFlightTime());
        assertEquals("3ч 50м", searchResult.getFlights().get(0).getDataBack().getFlightTime());

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }
    }

    //3. В базе: два прямых туда и два рейса обратно (туда и обратно - 3 свободных мест, по 2 из них эконом-класса).
    //    Поиск: рейсы туда (2023-03-01) и рейсы обратно (2023-04-06) для 2-х пассажиров
    @DisplayName("3 test. In DB 2 direct depart flight with 3 free seats " +
            "and 2 direct return flight with 3 free seats")
    @Test
    void shouldReturnTwoDirectDepartAndTwoDirectReturnFlightWithFreeSeats() throws Exception {

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 3, 1);
        LocalDate returnDate = LocalDate.of(2023, 4, 6);
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = ECONOMY;

        int expDirDepartFlights = 2;
        int expDirReturnFlights = 2;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());

        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();
        // деление на 2 из-за комбинаций полетов туда и обратно
        assertEquals(expDirDepartFlights + expDirReturnFlights, (dataToCount + dataBackCount) / 2);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        List<Flight> returnFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getTo(),
                search.getFrom(),
                Date.valueOf(search.getReturnDate()));

        Integer fareDepart1 = searchService.findFare(search, departFlights.get(0));
        Integer fareDepart2 = searchService.findFare(search, departFlights.get(1));

        Integer fareReturn1 = searchService.findFare(search, returnFlights.get(0));
        Integer fareReturn2 = searchService.findFare(search, returnFlights.get(1));

        assertEquals(15940, fareDepart1);
        assertEquals(15940, fareDepart2);
        assertEquals(15940, fareReturn1);
        assertEquals(15940, fareReturn2);

        int expectedTotalPrice = 31880;
        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertEquals(expectedTotalPrice, searchResult.getFlights().get(i).getTotalPrice());
        }

        assertEquals("3ч 30м", searchResult.getFlights().get(0).getDataTo().getFlightTime());
        assertEquals("3ч 30м", searchResult.getFlights().get(1).getDataTo().getFlightTime());
        assertEquals("3ч 50м", searchResult.getFlights().get(0).getDataBack().getFlightTime());
        assertEquals("3ч 40м", searchResult.getFlights().get(1).getDataBack().getFlightTime());

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }
    }

    //4. В базе: два прямых туда и два прямых рейса обратно,
    //   туда с наличием мест (3 свободных, 2 из них эконом-класса),
    //   обратно на первом (3 свободных, 2 из них эконом-класса), на втором (0).
    //   Поиск: рейс туда (2023-04-20), рейс обратно (2023-04-25) для 2-х пассажиров
    @DisplayName("4 test.In DB 2 direct depart flight with 3 free seats" +
            "and 2 direct return flight, one of them with 0 free seats")
    @Test
    void shouldReturnTwoDirectDepartAndTwoDirectReturnFlightsWithThreeSeatsOnDepartAndZeroOnReturn()
            throws Exception {

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 4, 20);
        LocalDate returnDate = LocalDate.of(2023, 4, 25);
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = ECONOMY;

        int expDirDepartFlights = 2;
        int expDirReturnFlights = 1;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());

        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();
        // деление на 2 из-за комбинаций полетов туда и обратно
        assertEquals(expDirDepartFlights + expDirReturnFlights, (dataToCount + dataBackCount) / 2);
        assertNotNull(searchResult.getFlights().get(0).getDataBack());

        assertNumberOfDepartDirectFlights(expDirDepartFlights, search, json);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        List<Flight> returnFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getTo(),
                search.getFrom(),
                Date.valueOf(search.getReturnDate()));

        Integer fareDepart1 = searchService.findFare(search, departFlights.get(0));
        Integer fareDepart2 = searchService.findFare(search, departFlights.get(1));

        Integer fareReturn1 = searchService.findFare(search, returnFlights.get(0));

        assertEquals(15940, fareDepart1);
        assertEquals(15940, fareDepart2);
        assertEquals(15940, fareReturn1);

        assertEquals(31880, searchResult.getFlights().get(0).getTotalPrice());  //dep1 - ret1
        assertEquals(15940, searchResult.getFlights().get(1).getTotalPrice());  //dep1 билет только туда
        assertEquals(31880, searchResult.getFlights().get(2).getTotalPrice());  //dep2 - ret1
        assertEquals(15940, searchResult.getFlights().get(3).getTotalPrice());  //dep2 билет только туда

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }

        assertNull(searchResult.getFlights().get(1).getDataBack());
    }

    //5. В базе: один прямой рейс туда и один прямой рейс обратно (туда - 3 (1 из них эконом), обратно - 0 свободных мест).
    //    Поиск: рейс туда (2023-04-01) и рейс обратно (2023-05-03) для 1-ого пассажиров
    @DisplayName("5 test. In DB 1 direct depart flight with 3 free seats and 1 direct return flight with 0 free seats")
    @Test
    void shouldReturnOneDirectDepartFlightsWithThreeSeats() throws Exception {

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 4, 1);
        LocalDate returnDate = LocalDate.of(2023, 5, 3);
        Integer numberOfPassengers = 1;
        CategoryType categoryOfSeats = ECONOMY;

        int expDirDepartFlights = 1;
        int expDirReturnFlights = 0;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());
        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();

        assertEquals(expDirDepartFlights + expDirReturnFlights, dataBackCount + dataToCount);

        assertNotNull(searchResult.getFlights().get(0).getDataTo());
        assertNull(searchResult.getFlights().get(0).getDataBack());

        assertNumberOfDepartDirectFlights(expDirDepartFlights, search, json);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        Integer fareDepart1 = searchService.findFare(search, departFlights.get(0));

        assertEquals(7970, fareDepart1);

        assertEquals(7970, searchResult.getFlights().get(0).getTotalPrice());   //dep1 билет только туда на 1ого

        assertEquals("3ч 30м", searchResult.getFlights().get(0).getDataTo().getFlightTime());

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }
    }

    //6. В базе: один прямой рейс туда и один прямой рейс обратно с наличием мест (3 свободных мест).
    //   Поиск: рейс туда (2023-04-02) и рейс обратно (2023-04-05) для 2-х пассажиров
    //   2 места категории бизнес в рейсе отправления, и в обратном рейсе
    @DisplayName("6 test. In DB 1 direct depart flight 1 direct return flight")
    @Test
    void shouldReturnOneDirectDepartAndOneDirectReturnFlightsWithCurrentCategory() throws Exception {

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 4, 2);
        LocalDate returnDate = LocalDate.of(2023, 4, 5);
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = BUSINESS;

        int expDirDepartFlights = 1;
        int expDirReturnFlights = 1;

        Search search = searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats)
                .getSearch();
        var json = mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        searchService.search(airportFrom, airportTo, departureDate, returnDate, numberOfPassengers, categoryOfSeats))
                ))
                .andReturn().getResponse().getContentAsString();

        var searchResult = getSearchResult(json);
        assertEquals(search, searchResult.getSearch());
        assertNotNull(searchResult.getFlights().get(0).getDataBack());
        assertNotNull(searchResult.getFlights().get(0).getDataTo());

        long dataToCount = searchResult.getFlights().stream().filter(flight -> flight.getDataTo() != null).count();
        long dataBackCount = searchResult.getFlights().stream().filter(flight -> flight.getDataBack() != null).count();

        assertEquals(expDirDepartFlights + expDirReturnFlights, dataBackCount + dataToCount);

        assertNumberOfDepartDirectFlights(expDirDepartFlights, search, json);
        assertNumberOfReturnDirectFlights(expDirReturnFlights, search, json);

        List<Flight> departFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getFrom(),
                search.getTo(),
                Date.valueOf(search.getDepartureDate()));

        List<Flight> returnFlights = flightService.getListDirectFlightsByFromAndToAndDepartureDate(
                search.getTo(),
                search.getFrom(),
                Date.valueOf(search.getReturnDate()));

        Integer fareDepart1 = searchService.findFare(search, departFlights.get(0));
        Integer fareReturn1 = searchService.findFare(search, returnFlights.get(0));

        assertEquals(41460, fareDepart1);
        assertEquals(41460, fareReturn1);

        assertEquals(82920, searchResult.getFlights().get(0).getTotalPrice());

        assertEquals("3ч 30м", searchResult.getFlights().get(0).getDataTo().getFlightTime());
        assertEquals("3ч 50м", searchResult.getFlights().get(0).getDataBack().getFlightTime());

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            assertSearchResultCities(searchResult, i, "Москва", "Омск");
        }
    }

    private SearchResult getSearchResult(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, SearchResult.class);
    }

    private void assertSearchResultCities(SearchResult searchResult, int index, String cityFromTo, String cityToFrom) {
        SearchResultCardData dataTo = searchResult.getFlights().get(index).getDataTo();
        SearchResultCardData dataBack = searchResult.getFlights().get(index).getDataBack();

        assertEquals(cityFromTo, dataTo.getCityFrom());
        assertEquals(cityToFrom, dataTo.getCityTo());

        if (dataBack != null) {
            assertEquals(cityToFrom, dataBack.getCityFrom());
            assertEquals(cityFromTo, dataBack.getCityTo());
        } else {
            assertNull(dataBack);
        }
    }

    // Посчитать количество прямых перелетов туда
    private void assertNumberOfDepartDirectFlights(int expectDirect, Search search,
                                                   String json) throws JsonProcessingException {
        var searchResult = getSearchResult(json);

        int numberOfDirectDepartFlights = 0;

        Set<String> uniqueDataBack = new HashSet<>();

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            var flight = searchResult.getFlights().get(i);
            var dataTo = flight.getDataTo();

            if (!uniqueDataBack.contains(dataTo.toString())) {
                if (dataTo.getAirportFrom().equals(search.getFrom()) &&
                        dataTo.getAirportTo().equals(search.getTo())) {
                    numberOfDirectDepartFlights++;
                }
                uniqueDataBack.add(dataTo.toString());
            }
        }
        assertEquals(expectDirect, numberOfDirectDepartFlights);
    }

    // Посчитать количество прямых перелетов обратно
    private void assertNumberOfReturnDirectFlights(int expectDirect, Search search, String json) throws JsonProcessingException {
        var searchResult = getSearchResult(json);
        int numberOfDirectReturnFlights = 0;
        Set<String> uniqueDataBack = new HashSet<>();

        for (int i = 0; i < searchResult.getFlights().size(); i++) {
            var flight = searchResult.getFlights().get(i);
            var dataBack = flight.getDataBack();

            if (dataBack != null && !uniqueDataBack.contains(dataBack.toString())) {
                if (dataBack.getAirportFrom() != null &&
                        dataBack.getAirportTo() != null &&
                        dataBack.getAirportFrom().equals(search.getTo()) &&
                        dataBack.getAirportTo().equals(search.getFrom())) {
                    numberOfDirectReturnFlights++;
                }
                uniqueDataBack.add(dataBack.toString());
            }
        }
        assertEquals(expectDirect, numberOfDirectReturnFlights);
    }

    @Test
    public void checkExceptionSearchControllerWhenDepartureDateAfterReturnDate() throws Exception {
        String errorMessage = "DepartureDate must be earlier then ReturnDate";

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 04, 01);
        LocalDate returnDate = LocalDate.of(2023, 03, 05);
        Integer numberOfPassengers = 2;
        CategoryType categoryOfSeats = ECONOMY;

        AtomicReference<String> requestUrl = new AtomicReference<>();
        mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andDo(result -> requestUrl.set(result.getRequest().getRequestURL().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkExceptionSearchControllerWhenNumberOfPassengersIsNegative() throws Exception {
        String errorMessage = "NumberOfPassengers is incorrect";

        Airport airportFrom = VKO;
        Airport airportTo = OMS;
        LocalDate departureDate = LocalDate.of(2023, 04, 01);
        LocalDate returnDate = LocalDate.of(2023, 05, 05);
        Integer numberOfPassengers = -1;
        CategoryType categoryOfSeats = ECONOMY;

        AtomicReference<String> requestUrl = new AtomicReference<>();
        mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("airportTo", String.valueOf(airportTo))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andDo(result -> requestUrl.set(result.getRequest().getRequestURL().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkExceptionSearchControllerWhenAirportToIsNull() throws Exception {
        String errorMessage = "NumberOfPassengers is incorrect";

        Airport airportFrom = VKO;
        Airport airportTo = null;
        LocalDate departureDate = LocalDate.of(2023, 04, 01);
        LocalDate returnDate = LocalDate.of(2023, 05, 05);
        Integer numberOfPassengers = -1;
        CategoryType categoryOfSeats = ECONOMY;

        AtomicReference<String> requestUrl = new AtomicReference<>();
        mockMvc.perform(get("http://localhost:8080/api/search")
                        .param("airportFrom", String.valueOf(airportFrom))
                        .param("departureDate", String.valueOf(departureDate))
                        .param("returnDate", String.valueOf(returnDate))
                        .param("numberOfPassengers", String.valueOf(numberOfPassengers))
                        .param("categoryOfSeats", String.valueOf(categoryOfSeats)))
                .andDo(print())
                .andDo(result -> requestUrl.set(result.getRequest().getRequestURL().toString()))
                .andExpect(status().isBadRequest());
    }
}