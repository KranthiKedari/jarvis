package com.kk.jarvis.auth;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.google.common.io.CharStreams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class JarvisAuthTokenResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(JarvisAuthTokenResolver.class);

    private JarvisTokenDecoder jarvisTokenDecoder;
    private Class<? extends JarvisToken> tokenType;
    private String secretKeyName;
    private JarvisSecurity jarvisSecurity;
    private String tokenHeaderName;


    public JarvisAuthTokenResolver(
            JarvisTokenDecoder jarvisTokenDecoder,
            Class<? extends  JarvisToken> tokenType,
            String secretKeyName,
            JarvisSecurity jarvisSecurity,
            String tokenHeaderName

    )
    {
        this.jarvisTokenDecoder = jarvisTokenDecoder;
        this.tokenType = tokenType;
        this.secretKeyName = secretKeyName;
        this.jarvisSecurity = jarvisSecurity;
        this.tokenHeaderName = tokenHeaderName;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == tokenType;
    }

    /**
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        logger.error ("Method Name is {}" , parameter.getMethod().getName());

        String tokenHeaderValue = getTokenValue(webRequest);
        String provider = getTokenProvider(webRequest);
        String secretKey = getSecretKey(provider);

        logger.error ("Additional data :" +  tokenHeaderValue   + "  " +  provider + "  " +secretKey);
        TokenValue tokenValue = TokenValue.parse(tokenHeaderValue);

        logger.error(tokenValue.toString());
        logger.error(BaseEncoding.base64Url().decodingSource(CharStreams.asCharSource(tokenValue.getPayload())).asCharSource(Charsets.UTF_8).read());

        JarvisToken token = jarvisTokenDecoder.decodeValue(tokenValue, secretKey, tokenType);

        authorize(token, parameter, provider);
        validate(token, parameter);

        return token;
    }

    private void validate(JarvisToken token, MethodParameter parameter) {
        logger.error("validating token");

        if (parameter.getMethod().getAnnotation(TokensThatNeedNotBeValidated.class) == null && !token.isValid()) {
            logger.error("Token validation failed for method {} with token {}", parameter.getMethod().getName(), token);
            throw new SecurityException();
        }
        logger.error("validated successfully");
    }

    private void authorize(JarvisToken token, MethodParameter parameter, String provider) {

        token.markAuthorized(true); //TODO add acls

        /*Collection<String> acls = null;
        logger.error("Acl" + acls.toString());
        if (acls.contains(parameter.getMethod().getName())) {
            token.markAuthorized(true);
            logger.error("Authorized successfully");

        } */
    }

    protected String getSecretKey(String provider) {
        String secretKey = jarvisSecurity.getSecret(provider, secretKeyName);
        if (StringUtils.isBlank(secretKey)) {
            logDebug("Missing secret key with name {} for provider {}", secretKey, provider);
            throw new SecurityException();
        }
        return secretKey;
    }

    private String getTokenProvider(NativeWebRequest webRequest) {
        String provider = webRequest.getHeader(JarvisTokenConstants.DEFAULT_AUTH_PROVIDER_HEADER);
        if (StringUtils.isBlank(provider)) {
            logDebug("Missing Token Provider Header {}", JarvisTokenConstants.DEFAULT_AUTH_PROVIDER_HEADER);
            throw new SecurityException();
        }
        return provider;
    }

    protected String getTokenValue(NativeWebRequest webRequest) {
        String value = webRequest.getHeader(tokenHeaderName);
        if (StringUtils.isBlank(value)) {
            logDebug("Missing Token header " + tokenHeaderName);
            throw new SecurityException();
        }
        return value;
    }

    private void logDebug(String message, Object... vars) {
        if (true || logger.isDebugEnabled()) {
            logger.debug(message, vars);
        }
    }
}
