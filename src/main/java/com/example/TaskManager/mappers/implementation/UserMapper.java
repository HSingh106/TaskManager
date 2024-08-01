package com.example.TaskManager.mappers.implementation;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * represents a mapper object for mapping data between UserEntity and UserDTO's
 */
@Component
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    private ModelMapper modelMapper;

    /**
     * Constructor injection that provides a modelMapper object according to the
     * bean definition within the config file
     * @param modelMapper represents the dependency to be injected
     */
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    /**
     *
     * @param user represents user entity to be mapped over to userDTO object
     * @return a userDTO corresponding to the user entity
     */
    @Override
    public UserDTO mapTo(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     *
     * @param userDTO represents userDTO object to be mapped over to user entity
     * @return a user entity corresponding to the userDTO
     */
    @Override
    public UserEntity mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
