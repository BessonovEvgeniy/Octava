package rinex.service.Impl.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@PropertySource(value = "classpath:amazon.properties")
public class AmazonS3Storage implements StorageService {

    @Value("${amazonAccessKeyID}")
    private String amazonAccessKeyID;
    @Value("${amazonBacketName}")
    private String amazonBacketName;
    @Value("${amazonYourSecretAccessKey}")
    private String amazonYourSecretAccessKey;

    private final String SUFFIX = "/";

    StringBuilder amazonBacket = new StringBuilder("http://" + amazonBacketName + ".s3.amazonaws.com/");
    AWSCredentials credentials = new BasicAWSCredentials(amazonAccessKeyID, amazonYourSecretAccessKey);
    AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    @Override
    public void init(){}

    @Override
    public String store(MultipartFile multipartFile, String newFileName) throws Exception {
        File file = new File(newFileName);
        multipartFile.transferTo(file);
        return store(file, newFileName);
    }

    private String store(File file, String newFileName) throws IOException, AmazonClientException {
        s3client.putObject(
                new PutObjectRequest(amazonBacketName, newFileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonBacket.append(newFileName).toString();
    }

    @Override
    public void delete(String fileName) throws AmazonClientException {
        if (fileName != null && !fileName.isEmpty()) {
            fileName = fileName.replace(amazonBacket.toString(), "");
            s3client.deleteObject(amazonBacketName, fileName);
        }
    }

    @Override
    public void createFolder(String folderName) throws AmazonClientException {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        PutObjectRequest putObjectRequest = new PutObjectRequest(amazonBacketName,
                folderName + SUFFIX, emptyContent, metadata);

        s3client.putObject(putObjectRequest);
    }

    @Override
    public void deleteFolder(String folderName) throws AmazonClientException {

        List<S3ObjectSummary> fileList = s3client.listObjects(amazonBacketName, folderName).getObjectSummaries();

        fileList.stream().forEach(file -> s3client.deleteObject(amazonBacketName, file.getKey()));

        s3client.deleteObject(amazonBacketName, folderName);
    }
}
