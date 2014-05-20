package com.kk.jarvis.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.common.io.CharStreams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class JarvisTokenDecoder {
    public static final String DEFAULT_ALGORITHM = "HmacSHA256";

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initialize() {

    }

    /**
     * Decodes the given token using the given secret key - it then validates the token signature.
     *
     * @param tokenValue
     * @param tokenType
     * @return
     * @throws Exception
     */
    public <T extends JarvisToken> T decodeValue(TokenValue tokenValue, String secretKey, Class<T> tokenType) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(secretKey), "The secret key is required.");
        Preconditions.checkNotNull(tokenValue, "The token value is required.");

        String decodedPayload = base64Decode(tokenValue.getPayload());

        T token = objectMapper.readValue(decodedPayload, tokenType);

        String generatedSignature = createSignature(decodedPayload, Charsets.UTF_8, secretKey, token.getAlgorithm());

        token.markAuthenticated(StringUtils.equals(tokenValue.getSignature(), generatedSignature));

        return token;
    }

    /**
     * Creates a signature using the given value.
     *
     * @param value
     * @param charset
     * @param algorithm
     * @return
     * @throws Exception
     */
    protected String createSignature(String value, Charset charset, String secretKey, String algorithm) throws Exception {
        String actualAlgorithm = StringUtils.trimToNull(algorithm) == null ? DEFAULT_ALGORITHM : algorithm.replaceAll(Pattern.quote("-"), "");

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), actualAlgorithm);

        Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
        mac.init(secretKeySpec);

        byte[] utf8Data = value.getBytes(charset);
        byte[] digest = mac.doFinal(utf8Data);

        return base64Encode(digest);
    }

    /**
     * Does a base64 encode.
     *
     * @param str
     * @param charset
     * @return
     */
    protected String base64Encode(String str, Charset charset) {
        return base64Encode(str.getBytes(charset));
    }

    /**
     * Does a base64 encode.
     *
     * @param data
     * @return
     */
    protected String base64Encode(byte[] data) {
        return BaseEncoding.base64Url().omitPadding().encode(data);
    }

    /**
     * Does a base64 decode.
     *
     * @param str
     * @return
     * @throws java.io.IOException
     */
    protected String base64Decode(String str) throws IOException {
        return BaseEncoding.base64Url().decodingSource(CharStreams.asCharSource(str)).asCharSource(Charsets.UTF_8).read();
    }
}
