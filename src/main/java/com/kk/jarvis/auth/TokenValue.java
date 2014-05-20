package com.kk.jarvis.auth;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class TokenValue {
    private String signature;
    private String payload;

    private TokenValue(String signature, String payload){
        Preconditions.checkArgument(StringUtils.isNotBlank(signature));
        Preconditions.checkArgument(StringUtils.isNotBlank(payload));

        this.signature = signature;
        this.payload = payload;
    }

    public static TokenValue parse(String stringValue){
        Preconditions.checkArgument(StringUtils.isNotBlank(stringValue));

        String[] tokenSplit = stringValue.split(Pattern.quote("."), 2);

        Preconditions.checkArgument(tokenSplit.length == 2);

        return new TokenValue(tokenSplit[0], tokenSplit[1]);
    }

    public String getSignature() {
        return signature;
    }

    public String getPayload() {
        return payload;
    }

    public String toString() {
        return signature + "-----" + payload;
    }
}