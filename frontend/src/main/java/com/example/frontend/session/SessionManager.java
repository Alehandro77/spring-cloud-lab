package com.example.frontend.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    private static final String TOKEN_KEY = "JWT_TOKEN";
    private static final String EMAIL_KEY = "USER_EMAIL";
    private static final String ROLE_KEY = "USER_ROLE";
    private static final String USER_ID_KEY = "USER_ID";

    public void saveToken(HttpSession session, String token, String email, String role, Long userId) {
        session.setAttribute(TOKEN_KEY, token);
        session.setAttribute(EMAIL_KEY, email);
        session.setAttribute(ROLE_KEY, role);
        session.setAttribute(USER_ID_KEY, userId);
    }

    public String getToken(HttpSession session) {
        return (String) session.getAttribute(TOKEN_KEY);
    }

    public String getEmail(HttpSession session) {
        return (String) session.getAttribute(EMAIL_KEY);
    }

    public String getRole(HttpSession session) {
        return (String) session.getAttribute(ROLE_KEY);
    }

    public boolean isLoggedIn(HttpSession session) {
        return getToken(session) != null;
    }

    public boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(getRole(session));
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public Long getUserId(HttpSession session) {
        return (Long) session.getAttribute(USER_ID_KEY);
    }
}