package com.example.TaskManager.mappers.implementation;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDTO mapTo(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserEntity mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
