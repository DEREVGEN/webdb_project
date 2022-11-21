package com.project.webdb.lotto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {


    String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    String nickname;
    String pwd;
}
