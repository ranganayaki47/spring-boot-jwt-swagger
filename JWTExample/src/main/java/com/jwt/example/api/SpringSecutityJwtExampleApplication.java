package com.jwt.example.api;

import com.jwt.example.api.entity.User;
import com.jwt.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecutityJwtExampleApplication {

  @Autowired private UserRepository userRepository;

  @PostConstruct
  public void initUsers() {
    List<User> userList =
        Stream.of(new User(101, "Ranga", "Ranga", "user@gmail.com")).collect(Collectors.toList());
    userRepository.saveAll(userList);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringSecutityJwtExampleApplication.class, args);
  }
}
