package com.example.autorepairshop;

import com.example.autorepairshop.error.ClientAlreadyExistException;
import com.example.autorepairshop.model.Car;
import com.example.autorepairshop.model.Client;
import com.example.autorepairshop.model.Mechanic;
import com.example.autorepairshop.model.Repair;
import com.example.autorepairshop.repository.ClientRepository;
import com.example.autorepairshop.service.AutoRepairShopService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTest {
    private ClientRepository clientRepository;
    @Autowired
    private AutoRepairShopService autoService;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;


    @Test
    void happyPath() throws Exception {
        //etap 1 - add client
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/add/client").content("""
                {
                              "firstName": "Piotr",
                              "secondName": "Nowak",
                              "phoneNumber": "753289111",
                              "email": "pnowak@gmail.com"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        MvcResult mvcResult = perform.andExpect(status().isCreated()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Client savedClient = objectMapper.readValue(contentAsString, Client.class);
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getEmail()).isEqualTo("pnowak@gmail.com");
        Assertions.assertThat(savedClient.getPhoneNumber()).isEqualTo("753289111");

        //etap 2 - show list of clients
        //given
        //when
        ResultActions listOfClients = mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON_VALUE));

        mvcResult = listOfClients.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        List<Client> clients = objectMapper.readValue(contentAsString, new TypeReference<List<Client>>() {
        });

        //then
        Assertions.assertThat(clients).hasSize(1);
        Assertions.assertThat(clients.get(0).getPhoneNumber()).isEqualTo("753289111");
        Assertions.assertThat(clients.get(0).getEmail()).isEqualTo("pnowak@gmail.com");

        //etap 3 - modify client
        //given
        perform = mockMvc.perform(patch("/modify/client").content("""
                {
                    "firstName": "Piotr",
                    "secondName": "Nowak",
                    "phoneNumber": "753289111",
                    "email": "piotrnowak@gmail.com"
                                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult= perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Client client2 = objectMapper.readValue(contentAsString, Client.class);
        Assertions.assertThat(client2.getEmail()).isEqualTo("piotrnowak@gmail.com");

        // etap 4 - add car
        //given
        Car testCar = new Car("DW 19831", "Audi", "S7", 2011, "ABSDAE221293821283");
        String clientPhoneNumber = "753289111";
        perform = mockMvc.perform(post("/add/car").content("""
                {
                    "car": {
                        "vehicleRegistration": "DW 19831",
                        "mark": "Audi",
                        "model": "S7",
                        "productionYear": 2011,
                        "vin": "ABSDAE221293821283"
                    },
                    "phoneNumber": "753289111"
                         }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isCreated()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Car savedCar = objectMapper.readValue(contentAsString, Car.class);
        Assertions.assertThat(savedCar.getVin()).isEqualTo("ABSDAE221293821283");

        // etap 5 - modify car
        // given
        perform = mockMvc.perform(patch("/modify/car").content("""
                {
                     "vehicleRegistration": "DW 20131",
                     "mark": "Audi",
                     "model": "S7",
                     "productionYear": 2011,
                     "vin": "ABSDAE221293821283"
                 }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Car modifiedCar = objectMapper.readValue(contentAsString, Car.class);
        Assertions.assertThat(modifiedCar.getVehicleRegistration()).isEqualTo("DW 20131");

        // etap 6 - show list of cars
        //given
        //when
        ResultActions listOfCars = mockMvc.perform(get("/cars").contentType(MediaType.APPLICATION_JSON_VALUE));
        mvcResult = listOfCars.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        List<Car> cars = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        //then
        Assertions.assertThat(cars).hasSize(1);
        Assertions.assertThat(cars.get(0).getVehicleRegistration()).isEqualTo("DW 20131");
        Assertions.assertThat(cars.get(0).getVin()).isEqualTo("ABSDAE221293821283");

        // etap 7 - add mechanic
        //given
        perform = mockMvc.perform(post("/add/mechanic").content("""
                {
                    "firstName": "Adam",
                    "secondName": "Kowalski",
                    "ifEmployed": true,
                    "username": "Kowal",
                    "password": "123"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isCreated()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Mechanic savedMechanic = objectMapper.readValue(contentAsString, Mechanic.class);
        Assertions.assertThat(savedMechanic.getUsername()).isEqualTo("Kowal");

        // szyfrowanie hasła nastęonym razem
        // etap 8 - zwolnienie mechanika
        // given
        // when
        perform = mockMvc.perform(patch("/fire/mechanic").content("""
                {
                    "username": "Kowal"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Mechanic firedMechanic = objectMapper.readValue(contentAsString, Mechanic.class);
        Assertions.assertThat(firedMechanic.getIfEmployed()).isEqualTo("NO");

        // etap 10 - add mechanic and show list of mechanics
        //given
        //when
        perform = mockMvc.perform(post("/add/mechanic").content("""
                {
                    "firstName": "Michal",
                    "secondName": "Przybylski",
                    "ifEmployed": true,
                    "username": "michal",
                    "password": "michal123"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));

        ResultActions listOfMechanics = mockMvc.perform(get("/mechanics").contentType(MediaType.APPLICATION_JSON_VALUE));

        //then = do dokończenia
        mvcResult = listOfClients.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        List<Client> mechanics = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });
        //then
        Assertions.assertThat(mechanics).hasSize(2);

        // etap 11 - add new ticket
        //given
        perform = mockMvc.perform(post("/add/new/ticket").content("""
                {
                    "client": {
                             "firstName": "Piotr",
                             "secondName": "Kowalski",
                             "phoneNumber": "953289111",
                             "email": "kowalski_piotr@gmail.com"
                         },
                         "car": {
                             "vehicleRegistration": "KR 19831",
                             "mark": "Audi",
                             "model": "S7",
                             "productionYear": 2011,
                             "vin": "XXXDAE221293821283"
                         }
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isCreated()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Car savedCarFromTicket = objectMapper.readValue(contentAsString, Car.class);
        Assertions.assertThat(savedCarFromTicket.getVin()).isEqualTo("ABSDAE221293821283");

        // etap 12 - accept ticket
        //given
        perform = mockMvc.perform(patch("/accept/ticket").content("""
               {
                   "repairId": 1,
                   "mechanicUsername": "michal"
               }
               """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Repair acceptRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(acceptRepair.getMechanic().getUsername()).isEqualTo("Stanislaw");

        // etap 13 - modify descritpion
        //given
        //when
        perform = mockMvc.perform(patch("/modify/description").content("""
                 {
                     "repairId": 1,
                     "description": "description",
                     "state": "state - started",
                     "repairProtocol": "repair protocol"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        Repair modifiedRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(modifiedRepair.getDescription()).isEqualTo("description");
        Assertions.assertThat(modifiedRepair.getState()).isEqualTo("state - started");
        Assertions.assertThat(modifiedRepair.getRepairProtocol()).isEqualTo("repair protocol");

        // etap 14 - modify start date of repair
        //given
        //when
        perform = mockMvc.perform(patch("/modify/repairStartDate").content("""
                 {
                      "repairId": 1,
                      "startDate": "2024-12-30"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        modifiedRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(modifiedRepair.getStartDate()).isEqualTo("2024-10-02T02:00:00.000");

        // etap 15 - modify end date of repair
        //given
        perform = mockMvc.perform(patch("/modyfikuj/repairEndDate").content("""
                 {
                      "repairId": 1,
                      "endDate": "2025-01-12"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //when
        //then
        mvcResult = perform.andExpect(status().isOk()).andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        modifiedRepair = objectMapper.readValue(contentAsString, Repair.class);
        Assertions.assertThat(modifiedRepair.getEndDate()).isEqualTo("2024-10-11T02:00:00.000");
    }

//        @Test
//        void wyjatki() {
//            //etap 1 - dodanie klienta z istniejącym numerem
//            //given
//            Klient testKlient = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
//            Klient testKlient2 = new Klient("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
//            //when
//            Klient zapisanyKlient = warsztatSerwis.Dodawanie_klienta(testKlient);
//            Klient zapisanyKlient2 = warsztatSerwis.Dodawanie_klienta(testKlient2);
//            //then
//            Assertions.assertThat(zapisanyKlient).isNotNull();
//        }

//    @Test
//    void KlientAlreadyExistException() {
//        //etap 1 - dodanie klienta z istniejącym numerem
//        //given
//        Client testKlient = new Client("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
//        Client testKlient2 = new Client("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
//        //when
//        Client zapisanyKlient = autoService.addClient(testKlient);
//        //Klient zapisanyKlient2 = warsztat_serwis.Dodawanie_klienta(testKlient2);
//        //then
//        assertThatThrownBy(() -> autoService.addClient(testKlient2))
//                .isInstanceOf(ClientAlreadyExistException.class)
//                .hasMessage("The client with the given phone number already exists in the database");
//    }

}