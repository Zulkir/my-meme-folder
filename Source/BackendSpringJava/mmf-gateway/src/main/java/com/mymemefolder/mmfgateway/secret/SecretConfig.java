package com.mymemefolder.mmfgateway.secret;

import com.amazonaws.regions.Regions;

public interface SecretConfig {
    String getAwsAccessKey();
    String getAwsSecretKey();
    Regions getAwsRegion();
    String getAwsS3BucketName();
}
