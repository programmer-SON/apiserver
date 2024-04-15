package org.zerock.apiserver.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.apiserver.domain.MemberRole;

import java.util.*;
import java.util.stream.Collectors;

public class MemberDTO extends User {

    private String email, pw, nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();



    public MemberDTO(String email, String pw, String nickname, boolean social, List<String> roleNames) {
        super(
                email,
                pw,
                roleNames.stream().map(str->new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList())
        );

        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }

    // 데이터를 Security JWT 문자열로 주고 받기 위해사용
    // JWT 문자열을 Claims라고 함
    public Map<String, Object> getClaims(){
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", email);
        dataMap.put("pw", pw);
        dataMap.put("nickname", nickname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }


}
