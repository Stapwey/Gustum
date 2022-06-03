package ru.mirea.work.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.work.models.Country;
import ru.mirea.work.models.Type;
import ru.mirea.work.repositories.ICountryRepository;
import ru.mirea.work.repositories.ITypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TypeServiceTest {
    @InjectMocks
    private TypeService typeService;
    @Mock
    private ITypeRepository itr;
    @Captor
    private ArgumentCaptor<Type> captor;

    private Type type1, type2, type3;
    @BeforeEach
    void setUp() {
        type1 = new Type();
        type1.setId(1);
        type1.setName("type1");

        type2 = new Type();
        type2.setId(2);
        type2.setName("type2");

        type3 = new Type();
        type3.setId(3);
        type3.setName("type3");
    }

    @Test
    void getAllTypes() {
        Mockito.when(itr.findAll()).thenReturn(List.of(type1, type2, type3));
        assertEquals(List.of(type1, type2, type3), itr.findAll());
    }

    @Test
    void saveType() {
        typeService.saveType(type1);
        Mockito.verify(itr).save(captor.capture());
        Type captured = captor.getValue();
        assertEquals("type1",captured.getName());
    }

    @Test
    void deleteById() {
        typeService.deleteById(3);
        Mockito.verify(itr).deleteById(3);
    }
}