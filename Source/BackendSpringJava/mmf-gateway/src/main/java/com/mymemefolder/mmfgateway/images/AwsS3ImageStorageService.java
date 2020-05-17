package com.mymemefolder.mmfgateway.images;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.mymemefolder.mmfgateway.secret.SecretConfig;
import com.mymemefolder.mmfgateway.utils.InputStreamWithSize;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import org.springframework.stereotype.Service;

@Service
public class AwsS3ImageStorageService implements ImageStorageService {
    private final AmazonS3 client;
    private final String bucketName;
    private long totalSize;

    private static final long MAX_BUCKET_SIZE = 4L * (1 << 30);

    public AwsS3ImageStorageService(SecretConfig secretConfig) {
        var credentials = new BasicAWSCredentials(
                secretConfig.getAwsAccessKey(),
                secretConfig.getAwsSecretKey());
        client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(secretConfig.getAwsRegion())
                .build();
        bucketName = secretConfig.getAwsS3BucketName();
        totalSize = getActualTotalSize();
    }

    private long getActualTotalSize() {
        var size = 0L;
        var req = client.listObjects(bucketName);
        while (true) {
           size += req.getObjectSummaries()
                   .stream()
                   .mapToLong(S3ObjectSummary::getSize)
                   .sum();
           if (!req.isTruncated())
               break;
           req = client.listNextBatchOfObjects(req);
        }
        return size;
    }

    private String toObjKey(int userId, String key) {
        return String.format("user%d/%s", userId, key);
    }

    @Override
    public InputStreamWithSize readByKey(int userId, String key) {
        var obj = client.getObject(bucketName, toObjKey(userId, key));
        var size = obj.getObjectMetadata().getContentLength();
        var stream = obj.getObjectContent();
        return new InputStreamWithSize(stream, size);
    }

    @Override
    public void save(int userId, String key, InputStreamWithSize streamWithLength)
            throws InvalidOperationException {
        var size = streamWithLength.getSize();
        if (totalSize + size > MAX_BUCKET_SIZE)
            throw new InvalidOperationException("Image storage is full.");
        totalSize += size;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        client.putObject(bucketName, toObjKey(userId, key), streamWithLength.getStream(), metadata);
    }

    @Override
    public void delete(int userId, String key) {
        String objKey = toObjKey(userId, key);
        var objSize = client.listObjects(bucketName, objKey)
                .getObjectSummaries()
                .stream()
                .mapToLong(S3ObjectSummary::getSize)
                .sum();
        client.deleteObject(bucketName, objKey);
        totalSize -= objSize;
    }
}
