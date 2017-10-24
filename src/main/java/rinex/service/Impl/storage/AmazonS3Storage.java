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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class AmazonS3Storage implements StorageService {

    private final static String AMAZON_ACCESS_KEY_ID ="$(AMAZON_ACCESS_KEY_ID)";
    private final static String AMAZON_BACKET_NAME = "$(AMAZON_BACKET_NAME)";
    private final static String AMAZON_YOUR_SECRET_ACCESS_KEY = "$(AMAZON_YOUR_SECRET_ACCESS_KEY)";
    private final static String AMAZON_BACKET = "http://" + AMAZON_BACKET_NAME + ".s3.amazonaws.com/";
    private final static String SUFFIX = "/";
    private final static AWSCredentials CREDENTIALS = new BasicAWSCredentials(AMAZON_ACCESS_KEY_ID, AMAZON_YOUR_SECRET_ACCESS_KEY);
    private final static AmazonS3 S_3_CLIENT = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS)).build();

    @Override
    public String store(MultipartFile multipartFile, String newFileName) throws Exception {
        File file = new File(newFileName);
        multipartFile.transferTo(file);
        return store(file, newFileName);
    }

    private String store(File file, String newFileName) throws AmazonClientException {
        S_3_CLIENT.putObject(
                new PutObjectRequest(AMAZON_BACKET_NAME, newFileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return AMAZON_BACKET + newFileName;
    }

    @Override
    public void delete(String fileName) throws AmazonClientException {
        if (fileName != null && !fileName.isEmpty()) {
            fileName = fileName.replace(AMAZON_BACKET, "");
            S_3_CLIENT.deleteObject(AMAZON_BACKET_NAME, fileName);
        }
    }

    @Override
    public void createFolder(String folderName) throws AmazonClientException {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        PutObjectRequest putObjectRequest = new PutObjectRequest(AMAZON_BACKET_NAME,
                folderName + SUFFIX, emptyContent, metadata);

        S_3_CLIENT.putObject(putObjectRequest);
    }

    @Override
    public void deleteFolder(String folderName) throws AmazonClientException {

        List<S3ObjectSummary> fileList = S_3_CLIENT.listObjects(AMAZON_BACKET_NAME, folderName).getObjectSummaries();
        fileList.forEach(file -> S_3_CLIENT.deleteObject(AMAZON_BACKET_NAME, file.getKey()));
        S_3_CLIENT.deleteObject(AMAZON_BACKET_NAME, folderName);
    }
}
