package ru.mirea.work.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.work.models.Country;
import ru.mirea.work.models.CountryType;
import ru.mirea.work.repositories.ICountryTypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CountryTypeServiceTest {
    @InjectMocks
    private CountryTypeService countryTypeService;
    @Mock
    private ICountryTypeRepository ictr;
    @Captor
    private ArgumentCaptor<CountryType> captor;


    private CountryType countryType1, countryType2, countryType3;
    @BeforeEach
    void setUp() {
        countryType1 = new CountryType();
        countryType1.setId(1);
        countryType1.setTypesId(1);

        countryType2 = new CountryType();
        countryType2.setId(2);
        countryType2.setTypesId(2);

        countryType3 = new CountryType();
        countryType3.setId(3);
        countryType3.setTypesId(2);
    }

    @Test
    void getCountriesByTypeId() {
        Mockito.when(ictr.findAllByTypesId(2)).thenReturn(List.of(countryType2, countryType3));
        assertEquals(2,ictr.findAllByTypesId(2).size());
    }

    @Test
    void getAll() {
        Mockito.when(ictr.findAll()).thenReturn(List.of(countryType1, countryType2, countryType3));
        assertEquals(3, ictr.findAll().size());
    }

    @Test
    void saveCountryType() {
        countryTypeService.saveCountryType(countryType1);
        Mockito.verify(ictr).save(captor.capture());
        CountryType captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    void deleteById() {
        countryTypeService.deleteById(1);
        Mockito.verify(ictr).deleteById(1);
    }
}