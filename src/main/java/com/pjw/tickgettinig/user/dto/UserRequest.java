package com.pjw.tickgettinig.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserRequest {

  @Getter
  @Setter
  @Schema(description = "사용자 계정 생성 요청 청보")
  public static class Regist {

    @NotBlank(message = "이름은 필수입니다.")
    @Length(min = 2, max = 20)
    private String username;

    @NotBlank(message = "이름은 필수입니다.")
    @Length(min = 2, max = 20)
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String mobile;
  }

  @Getter
  @Setter
  @Schema(description = "사용자 계정 생성 요청 청보")
  public static class Edit {

    private Long id;

    @NotBlank(message = "이름은 필수입니다.")
    @Length(min = 2, max = 20)
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String mobile;
  }

}
