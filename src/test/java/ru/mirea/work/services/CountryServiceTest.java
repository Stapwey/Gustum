package ru.mirea.work.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.work.models.Country;
import ru.mirea.work.repositories.ICountryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @InjectMocks
    private CountryService countryService;
    @Mock
    private ICountryRepository icr;
    @Captor
    private ArgumentCaptor<Country> captor;

    private Country country1, country2;
    @BeforeEach
    void setUp() {
        country1 = new Country();
        country1.setId(1);
        country1.setName("Country1");

        country2 = new Country();
        country2.setId(2);
        country2.setName("Country2");
    }

    @Test
    void getAllCountries() {
        Mockito.when(icr.findAll()).thenReturn(List.of(country1,country2));
        assertEquals(2,icr.findAll().size());
    }

    @Test
    void getCountryById() {
        Mockito.when(icr.findById(1)).thenReturn(country1);
        assertEquals(country1, icr.findById(1));
    }

    @Test
    void saveCountry() {
        countryService.saveCountry(country1);
        Mockito.verify(icr).save(captor.capture());
        Country capturedCountry = captor.getValue();
        assertEquals("Country1", capturedCountry.getName());
    }

    @Test
    void deleteById() {
        countryService.deleteById(1);
        Mockito.verify(icr).deleteById(1);
    }
}