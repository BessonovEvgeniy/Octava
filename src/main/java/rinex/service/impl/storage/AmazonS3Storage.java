package rinex.service.impl.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.service.StorageService;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class AmazonS3Storage implements StorageService {

    @Value("${amazon.accessKeyID}")
    private String accessKeyID;
    @Value("${amazon.backetName}")
    private String backetName;
    @Value("${amazon.yourSecretAccessKey}")
    private String yourSecretAccessKey;

    private String amazonBacket;
    private String suffix = "/";
    private AWSCredentials credentials;
    private AmazonS3 s3Client;

    @PostConstruct
    private void init() {
        amazonBacket = "http://" + backetName + ".s3.amazonaws.com/";
//        credentials = new BasicAWSCredentials(accessKeyID, yourSecretAccessKey);
//        s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    @Override
    public String store(MultipartFile multipartFile, String newFileName) throws Exception {
        File file = new File(newFileName);
        multipartFile.transferTo(file);
        return store(file, newFileName);
    }

    private String store(File file, String newFileName) throws AmazonClientException {
        s3Client.putObject(
                new PutObjectRequest(backetName, newFileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonBacket + newFileName;
    }

    @Override
    public void delete(String fileName) throws AmazonClientException {
        if (fileName != null && !fileName.isEmpty()) {
            fileName = fileName.replace(amazonBacket, "");
            s3Client.deleteObject(backetName, fileName);
        }
    }

    @Override
    public void createFolder(String folderName) throws AmazonClientException {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        PutObjectRequest putObjectRequest = new PutObjectRequest(backetName,
                folderName + suffix, emptyContent, metadata);

        s3Client.putObject(putObjectRequest);
    }

    @Override
    public void deleteFolder(String folderName) throws AmazonClientException {

        List<S3ObjectSummary> fileList = s3Client.listObjects(backetName, folderName).getObjectSummaries();
        fileList.forEach(file -> s3Client.deleteObject(backetName, file.getKey()));
        s3Client.deleteObject(backetName, folderName);
    }
}