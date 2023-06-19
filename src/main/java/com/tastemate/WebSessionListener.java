package com.tastemate;

import com.tastemate.domain.MemberVO;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSessionListener implements HttpSessionListener {
  public static WebSessionListener sessionListener = null;
  private static Hashtable<String, MemberVO> loginSessionList = new Hashtable<>();

  public static synchronized WebSessionListener getInstance() {
    if (sessionListener == null) {
      sessionListener = new WebSessionListener();
    }
    return sessionListener;
  }

  @Override
  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
  }

  public void sessionCreated(HttpSession session) {
    MemberVO memberVO = (MemberVO) session.getAttribute("vo");
    loginSessionList.put(session.getId(), memberVO);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    log.info("sessionDestroyed");
    loginSessionList.remove(httpSessionEvent.getSession().getId());
  }

  public List<MemberVO> currentUserList() {
    List<MemberVO> currentUserList = new ArrayList<>();
    for (String key : loginSessionList.keySet()) {
      currentUserList.add(loginSessionList.get(key));
    }
    return currentUserList;
  }
}
