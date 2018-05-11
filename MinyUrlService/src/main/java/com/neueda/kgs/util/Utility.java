package com.neueda.kgs.util;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.validator.UrlValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utility {
    public static String getOperatingSystemType(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getGroup().getName();
    }
    public static String getBrowserType(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getBrowser().getGroup().getName();
    }

    public static String getHostname() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        return hostName;
    }

    public static boolean isUrlValid(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    public static String urlNormalization(String url) {
        if (!url.toLowerCase().startsWith("https://") && !url.toLowerCase().startsWith("http://"))
            url = "http://".concat(url);
        if (url.indexOf("www") < 0)
            url = url.replace("://", "://www.");

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);

        return url;
    }
}
