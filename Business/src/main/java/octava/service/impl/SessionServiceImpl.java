//package octava.service.impl;
//
//import octava.model.user.User;
//import octava.service.SecurityService;
//import octava.service.SessionService;
//import octava.service.UserService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Service("sessionService")
//public class SessionServiceImpl implements SessionService {
//
//    @Resource
//    private UserService userService;
//
//    @Resource
//    private SecurityService securityService;
//
//    @Override
//    public User getCurrentUser() {
//        final String login = securityService.getLogin();
//
//        final User user = userService.getByLogin(login);
//
//        return user;
//    }
//}
