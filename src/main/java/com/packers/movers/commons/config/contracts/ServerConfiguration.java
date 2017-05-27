package com.packers.movers.commons.config.contracts;

import com.packers.movers.commons.application.Protocol;

import java.net.MalformedURLException;
import java.net.URL;

public class ServerConfiguration {

    private final ApplicationConfiguration applicationConfiguration;

    private Protocol protocol;

    public ServerConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public int getPort() {
        return applicationConfiguration.getPort();
    }

    public String getContextPath() {
        return ensureLeadingForwardSlash(applicationConfiguration.getContextPath());
    }

    public URL getPublicUrl() throws MalformedURLException {
        return new URL(getProtocol().toString(), applicationConfiguration.getPublicHost(), getPort(), getContextPath());
    }

    public URL getPublicUrl(String path) throws MalformedURLException {
        return new URL(getProtocol().toString(), applicationConfiguration.getPublicHost(), getPort(), String.format("%s%s", getContextPath(), ensureLeadingForwardSlash(path)));
    }

    private String ensureLeadingForwardSlash(String path) {
        if (path == null || path.startsWith("/")) {
            return path;
        }

        return String.format("/%s", path);
    }
}
