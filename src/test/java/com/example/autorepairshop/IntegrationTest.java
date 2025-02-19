package com.example.autorepairshop;

import com.example.autorepairshop.model.Car;
import com.example.autorepairshop.model.Client;
import com.example.autorepairshop.model.Mechanic;
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

import java.util.Collections;
import java.util.List;

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
            //etap 1 - dodanie klienta
            //given
        Client testClient = new Client("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        //Client testClient2 = new Client("Julia", "Przybylska", "432124541", "juliaprzybylska.gmail.com");
        //when
        Client savedClient = autoService.addClient(testClient);
        //Client savedClient2 = autoService.addClient(savedClient2);
        //then
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getEmail()).isEqualTo(testClient.getEmail());
        Assertions.assertThat(savedClient.getPhoneNumber()).isEqualTo(testClient.getPhoneNumber());

        Assertions.assertThat(savedClient.getPhoneNumber()).isEqualTo(testClient.getPhoneNumber());

            //etap 2 - wyswietlanie klientow
            //given
            ResultActions listOfClients = mockMvc.perform(get("/clients").content("""
                    {
                        "firstName": "Bozena",
                        "secondName": "Kowalska",
                        "phoneNumber": "753289111",
                        "email": "bozenakowalska@gmail.com"
                                    
                    }
                    """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
            //when
            List<Client> klienci = autoService.showClients();
            //then = do dokończenia
            MvcResult mvcResult = listOfClients.andExpect(status().isOk()).andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();
            List<Client> clients = objectMapper.readValue(contentAsString, new TypeReference<List<Client>>() {});
            Assertions.assertThat(clients.get(0).getEmail()).isEqualTo("bozenakowalska777@gmail.com");
            //then
            Assertions.assertThat(klienci).hasSize(2);
            Assertions.assertThat(klienci.get(0).getPhoneNumber()).isEqualTo(testClient.getPhoneNumber());

            //etap 3 - modyfikacja danych klienta
            //given
            ResultActions perform = mockMvc.perform(patch("/modify/client").content("""
                {
                    "firstName": "Bozena",
                    "secondName": "Kowalska",
                    "phoneNumber": "753289111",
                    "email": "kowalska@gmail.com"
                
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
            //when
            //then
            MvcResult mvcResult1a = perform.andExpect(status().isOk()).andReturn();
            String contentAsString1a = mvcResult1a.getResponse().getContentAsString();
            Client client1a = objectMapper.readValue(contentAsString1a, Client.class);
            Assertions.assertThat(client1a.getEmail()).isEqualTo("bozenakowalska777@gmail.com");

            // etap 4 - wyswietlanie klientow
            //given
            //when
            klienci = autoService.showClients();
            //then
            Assertions.assertThat(klienci).hasSize(2);
            Assertions.assertThat(klienci.get(0).getEmail()).isEqualTo("bozenakowalska777@gmail.com");

            // etap 5 - dodawanie pojazdow
            //given
            Car testPojazd = new Car("DW 19831","Audi","S7",2011,"ABSDAE221293821283");
            String telefonKlienta = "753289111";
            ResultActions perform2 = mockMvc.perform(post("/add/car").content("""
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
            MvcResult mvcResult2 = perform2.andExpect(status().isOk()).andReturn();
            String contentAsString2 = mvcResult2.getResponse().getContentAsString();
            Car savedCar = objectMapper.readValue(contentAsString2, Car.class);
            Assertions.assertThat(savedCar.getVin()).isEqualTo("ABSDAE221293821283");
            // etap 6 - modyfikuj dane pojazdów
            // given
            ResultActions perform3 = mockMvc.perform(patch("/modify/car").content("""
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
            MvcResult mvcResult3 = perform3.andExpect(status().isOk()).andReturn();
            String contentAsString3 = mvcResult3.getResponse().getContentAsString();
            Car zmodyfikowanyPojazd = objectMapper.readValue(contentAsString3, Car.class);
            Assertions.assertThat(zmodyfikowanyPojazd.getVehicleRegistration()).isEqualTo("DW 20131");
            // etap 7 - wyswietlanie pojazdów
            //given
            List<Car> pojazdy = autoService.showCars();
            //then
            Assertions.assertThat(pojazdy).hasSize(1);
            Assertions.assertThat(pojazdy.get(0).getVehicleRegistration()).isEqualTo("DW 20131");
            // etap 8 - dodawanie mechanika
            //given
            Mechanic testMechanik = new Mechanic ("Zdzisław","Przybylski","Zdzisław","123");
            ResultActions perform4 = mockMvc.perform(post("/add/mechanic").content("""
                {
                    "firstName": "Zdzisław",
                    "secondName": "Przybylski",
                    "ifEmployed": true,
                    "username": "zdzislaw",
                    "password": "zdzislaw123"
                          }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
            //when
            //then
            MvcResult mvcResult4 = perform4.andExpect(status().isOk()).andReturn();
            String contentAsString4 = mvcResult4.getResponse().getContentAsString();
            Mechanic zapisanyMechanik = objectMapper.readValue(contentAsString4, Mechanic.class);
            Assertions.assertThat(zapisanyMechanik.getUsername()).isEqualTo("Zdzisław");
            // szyfrowanie hasła nastęonym razem
            // etap 9 - zwolnienie mechanika
            // given
            ResultActions perform5 = mockMvc.perform(patch("/fire/mechanic").content("""
                {
                    "username": "zdzislaw"
                  }
                """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
            //when
            //then
            MvcResult mvcResult5 = perform5.andExpect(status().isOk()).andReturn();
            String contentAsString5 = mvcResult5.getResponse().getContentAsString();
            Mechanic zwolnionyMechanik = objectMapper.readValue(contentAsString5, Mechanic.class);
            Assertions.assertThat(zwolnionyMechanik.getIfEmployed()).isEqualTo("NIE");
            // etap 10 - wyswietlanie mechanika
            //given
            ResultActions perform4a = mockMvc.perform(post("/add/mechanic").content("""
                    {
                        "firstName": "Michal",
                        "secondName": "Przybylski",
                        "ifEmployed": true,
                        "username": "michal",
                        "password": "michal123"
                              }
                    """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
            List<Mechanic> mechanicy = autoService.showMechanics();
            //then
            Assertions.assertThat(mechanicy).hasSize(2);
            // etap 11 - dodawanie nowego zgloszenia
            //given
            ResultActions perform6 = mockMvc.perform(post("/add/new/ticket").content("""
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
            MvcResult mvcResult6 = perform6.andExpect(status().isOk()).andReturn();
            String contentAsString6 = mvcResult6.getResponse().getContentAsString();
            Car zapisanyPojazd = objectMapper.readValue(contentAsString6, Car.class);
            Assertions.assertThat(zapisanyPojazd.getVin()).isEqualTo("ABSDAE221293821283");
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
    }