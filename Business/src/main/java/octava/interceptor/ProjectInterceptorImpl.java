//package octava.interceptor;
//
//import octava.model.project.ProjectModel;
//import octava.service.SessionService;
//import org.hibernate.CallbackException;
//import org.hibernate.EmptyInterceptor;
//import org.hibernate.type.Type;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//
//import static java.util.Objects.isNull;
//
//public class ProjectInterceptorImpl extends EmptyInterceptor {
//
//    @Resource
//    private SessionService sessionService;
//
//    @Override
//    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
//        final ProjectModel project = (ProjectModel) entity;
//
//        if (isNull(project.getCreatedBy())) {
//            project.setCreatedBy(sessionService.getCurrentUser());
//        }
//
//        return true;
//    }
//
//}
