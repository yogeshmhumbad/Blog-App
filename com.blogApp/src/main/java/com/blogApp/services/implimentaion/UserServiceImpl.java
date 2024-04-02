package com.blogApp.services.implimentaion;

import com.blogApp.config.AppConstants;
import com.blogApp.entities.Role;
import com.blogApp.entities.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payloads.UserDto;
import com.blogApp.repositories.RoleRepository;
import com.blogApp.repositories.UserRepository;
import com.blogApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);
        //encoding password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //getting roles
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        return this.modelMapper.map(this.userRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);

        User saveUser = this.userRepository.save(user);

        return this.userToDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id" , userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User UpdatedUser = this.userRepository.save(user);
        UserDto userDto1 = this.userToDto(UpdatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
       User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));

       return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        this.userRepository
                .delete(this.userRepository
                        .findById(userId)
                        .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)));
    }
    public User dtoToUser(UserDto userDto){
    User user = this.modelMapper.map(userDto,User.class);

        //        User user = new User();

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return user;
    }
    public UserDto userToDto (User user){
    UserDto userDto = this.modelMapper.map(user, UserDto.class);

        //        UserDto userDto = new UserDto();
//
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());


        return userDto;
    }

}
