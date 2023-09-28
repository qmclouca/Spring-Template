package com.qmclouca.base;

import com.qmclouca.base.configs.DisableTestExtension;
import com.qmclouca.base.services.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

@WebMvcTest(ProductControllerTests.class)
@AutoConfigureJsonTesters
@ExtendWith(DisableTestExtension.class)
public class ProductControllerTests {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Logger logger = (Logger) LoggerFactory.getLogger(ProductControllerTests.class);

}
