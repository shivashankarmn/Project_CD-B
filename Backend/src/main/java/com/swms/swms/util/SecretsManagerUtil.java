package com.swms.swms.util;
 
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class SecretsManagerUtil {
    private static final String SECRET_NAME = "MyAppDBSecret-GP1";
    private static final Region REGION = Region.AP_NORTHEAST_3; // Set to ap-northeast-3
 
    public static String getRDSPassword() {
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(REGION)
                .build()) {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(SECRET_NAME)
                    .build();
            GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            String secret = getSecretValueResponse.secretString();
            // Parse JSON secret
            ObjectMapper mapper = new ObjectMapper();
            JsonNode secretJson = mapper.readTree(secret);
            // Extract only the password
            return secretJson.get("password").asText(); // Returns "Password123"
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve RDS password from Secrets Manager", e);
        }
    }
}