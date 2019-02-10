package controller;

public class Constants {

    public static class Profile {
        public static final String DEV = "development";
        public static final String PROD = "production";
    }


    public static final class Controller {

        public static final class Project {
            public static final String PREFIX = "/project";

            public static final String CREATE = "/create";
            public static final String LIST = "/list";
            public static final String UPLOAD = "/upload";
        }

        public static final class RestApi {
            public static final class Rinex {
                public static final String PREFIX = "/rinex";
                public static final String UPLOAD = PREFIX + "/upload";
            }
        }
    }
}
