package org.zerock.apiserver.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/*
 * 로그인 성공했을때 처리
 */
@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("-----------------");
        log.info(authentication);
        log.info("-----------------");

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        Map<String, Object> claims = memberDTO.getClaims();

        // 지금 당장 사용할수 있는 권리 같은 것
        // 유효시간이 짧은 이유는 해킹 당할것을 대비
        String accessToken = JWTUtil.generateToken(claims, 10);
        // 일종의 교환권 같은것
        String refreshToken = JWTUtil.generateToken(claims, 60*24);

        claims.put("accessToken",accessToken);
        claims.put("refreshToken",refreshToken);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

    }
}
