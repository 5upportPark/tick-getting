package com.pjw.tickgettinig.user;

import com.pjw.tickgettinig.user.dto.UserInfo;
import com.pjw.tickgettinig.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<UserInfo> getUser(@RequestParam Long id) {
    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<UserInfo> updateUser(@RequestBody UserRequest.Edit req) {
    return new ResponseEntity<>(userService.editUser(req), HttpStatus.OK);
  }
}
