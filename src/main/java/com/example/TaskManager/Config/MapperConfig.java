package com.example.TaskManager.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper Config contains the layout to construct and return modelMapper beans
 */
@Configuration
public class MapperConfig {

    /**
     * Returns a modelMapper into bean application context whenever needed
     * @return ModelMapper object
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
