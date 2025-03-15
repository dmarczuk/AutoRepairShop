package com.example.autorepairshop;

import com.example.autorepairshop.error.ClientAlreadyExistException;
import com.example.autorepairshop.model.Car;
import com.example.autorepairshop.model.Client;
import com.example.autorepairshop.model.Mechanic;
import com.example.autorepairshop.model.Repair;
import com.example.autorepairshop.service.AutoRepairShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExceptionsIntegrationTest {
    @Autowired
    private AutoRepairShopService service;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void ClientAlreadyExistException() {
        //etap 1 - add client with existing phone number
        //given
        Client testClient = new Client("Julia", "Przybylska", "432124548", "juliaprzybylska.gmail.com");
        Client testClient2 = new Client("Julia", "Przybylska", "432124548", "juliaprzybylska.gmail.com");

        //when
        service.addClient(testClient);

        //then
        assertThatThrownBy(() -> service.addClient(testClient2))
                .isInstanceOf(ClientAlreadyExistException.class)
                .hasMessage("The client with the given phone number already exists in the database");
    }

    @Test
    void ClientNotFoundException() throws Exception {
        //given
        Client testKlient = new Client("Julia", "Przybylska", "432124549", "juliaprzybylska.gmail.com");
        //when
        service.addClient(testKlient);

        ResultActions perform1a = mockMvc.perform(patch("/modify/client").content("""
                {
                    "firstName": "Julia",
                    "secondName": "Przybylska",
                    "phoneNumber": "432124542",
                    "email": "juliaprzybylska777.gmail.com"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform1a.andExpect(status().isNotFound()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
//        Client client = objectMapper.readValue(contentAsString, Client.class);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"Client with the given phone number not found")
                .contains("\"status\":\"NOT_FOUND\"");

    }

    @Test
    void MechanicAlreadyExistException() throws Exception {

        Mechanic testMechanic = new Mechanic ("Zdzislaw","Przybylski","Przybyl","123");
        service.addMechanic(testMechanic);

        ResultActions perform = mockMvc.perform(post("/add/mechanic").content("""
                {
                    "firstName": "Kornel",
                    "secondName": "Janczyk",
                    "ifEmployed": true,
                    "username": "Przybyl",
                    "password": "Kornelcio321"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isConflict()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        //String normalizedActual = Normalizer.normalize(zapisanyMechanik.getLogin(), Normalizer.Form.NFC);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"A mechanic with this login already exists in the database")
                .contains("\"status\":\"CONFLICT\"");
    }

    @Test
    void MechanicNotFoundException() throws Exception {

        Mechanic testMechanic = new Mechanic("Antoni","Kowalski","Antek","321453");
        service.addMechanic(testMechanic);

        // given
        ResultActions perform = mockMvc.perform(patch("/fire/mechanic").content("""
                {
                      "username": "Antoni"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isNotFound()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Mechanic firedMechanic = objectMapper.readValue(contentAsString, Mechanic.class);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"Mechanic with the given username not found")
                .contains("\"status\":\"NOT_FOUND\"");
    }

    @Test
    void RepairNotFoundException() throws Exception {
        //given
        Repair repair = new Repair();
        service.addRepair(repair);
        ResultActions perform = mockMvc.perform(patch("/accept/repair").content("""
                 {
                     "repairId": 1,
                     "mechanicUsername": "Kazimierz"
                 }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isNotFound()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Repair acceptedRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"Mechanic is not employed in car repair shop")
                .contains("\"status\":\"NOT_FOUND\"");
    }

//    @Test
//    void CarAlreadyExistException() throws Exception {
//        //given
//        Client testClient = new Client("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
//        service.addClient(testClient);
//        Car testCar = new Car("DW 19832","Audi","S7",2011,"ABSDAE221293921284");
//        String clientPhoneNumber = "432124541";
//        service.addCar(testCar, clientPhoneNumber);
//
//        ResultActions perform = mockMvc.perform(post("/add/car").content("""
//                {
//                    "car": {
//                        "vehicleRegistration": "DW 19831",
//                        "mark": "Audi",
//                        "model": "S7",
//                        "productionYear": 2011,
//                        "vin": "ABSDAE221293821283"
//                    },
//                    "phoneNumber": "432124541"
//                }
//                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
//        //when
//        //then
//        MvcResult mvcResult = perform.andExpect(status().isConflict()).andReturn();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
////        Car savedCar = objectMapper.readValue(contentAsString, Car.class);
//        Assertions.assertThat(contentAsString)
//                .contains("message\":\"Pojazd z podanym numerem VIN istnieje juz w bazie")
//                .contains("\"status\":\"CONFLICT\"");
//    }

    @Test
    void CarNotFoundException() throws Exception {

        Client testKlient = new Client("Julia", "Przybylska", "432124543", "juliaprzybylska.gmail.com");
        service.addClient(testKlient);
        Car testCar= new Car("DW 19831","Audi","S7",2011,"ABSDAE222293821283");
        String clientPhoneNumber = "432124543";
        service.addCar(testCar, clientPhoneNumber);

        // given
        ResultActions perform = mockMvc.perform(patch("/modify/car").content("""
                {
                     "vehicleRegistration": "DW 20131",
                     "mark": "Audi",
                     "model": "S7",
                     "productionYear": 2011,
                     "vin": "ABSDAE222293821263"
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isNotFound()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
//        Car modifiedCar = objectMapper.readValue(contentAsString, Car.class);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"Car with the given VIN not found")
                .contains("\"status\":\"NOT_FOUND\"");
    }

    @Test
    void DataToEarlyExistException() throws Exception {

        Repair naprawa = new Repair();
        LocalDate date = LocalDate.parse("2024-10-19");
        naprawa.setStartDate(date);
        Repair repair = service.addRepair(naprawa);

        ResultActions perform = mockMvc.perform(patch("/modify/repairEndDate").content("""
                 {
                      "repairId": 2,
                      "endDate": "2024-10-11"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        MvcResult mvcResult = perform.andExpect(status().isNotAcceptable()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
//        Repair modifiedRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(contentAsString)
                .contains("message\":\"The end date cannot be earlier than the start date")
                .contains("\"status\":\"NOT_ACCEPTABLE\"");
    }
}
