import org.jasypt.util.password.StrongPasswordEncryptor;

public class Tests {

    public static void main(String[] args) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String pass = passwordEncryptor.encryptPassword("Admin");
        System.out.println(pass);
    }
}
