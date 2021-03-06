package octava.constants;

public class Constants {

    public static final class Controller {

        public static final class Project {
            public static final String PROJECT = "project";
            public static final String PROJECTS = "projects";

            private static final String PREFIX = "/project";

            public static final String CREATE = PREFIX + "/create";
            public static final String UPDATE = PREFIX + "/update";
            public static final String UPDATE_REST = UPDATE + "/{path}/{status}";
            public static final String INFO = PREFIX + "/info";
            public static final String LIST = PREFIX + "/list";
            public static final String UPLOAD = PREFIX + "/upload";
        }

        public static final class RestApi {
            public static final class Rinex {
                public static final String PREFIX = "/rinex";
                public static final String UPLOAD = PREFIX + "/upload";
            }
        }
    }

    public interface Properties {
        String RINEX_FOLDER = "ppa.rinex.folder";
    }
}
