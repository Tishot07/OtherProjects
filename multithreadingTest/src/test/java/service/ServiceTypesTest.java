package service;


import org.junit.Test;
import types.TypeA;

import static org.mockito.Mockito.*;

public class ServiceTypesTest {

    @Test
    public void addTypes() {

        ServiceTypes mockService = mock(ServiceTypes.class);
        TypeA temp = new TypeA(1, 20, 500);
        mockService.addTypes(temp);
        mockService.run();
        verify(mockService).addTypes(temp);
        verify(mockService).run();
    }

}