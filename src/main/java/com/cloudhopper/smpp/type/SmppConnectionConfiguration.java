package com.cloudhopper.smpp.type;

/*
 * #%L
 * ch-smpp
 * %%
 * Copyright (C) 2009 - 2015 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.cloudhopper.smpp.SmppConstants;

/**
 * Configuration to create a TCP/IP connection (Channel) for an SmppSession.
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class SmppConnectionConfiguration {

    private String host;
    private int port;
    private long connectTimeout;
    private String clientBindHost;
    private int clientBindPort;

    private String proxyHost;
    private int proxyPort;

    public SmppConnectionConfiguration() {
        this(null, 0, SmppConstants.DEFAULT_CONNECT_TIMEOUT);
    }

    public SmppConnectionConfiguration(String host, int port, long connectTimeout) {
        this(host, port, connectTimeout, null, 0);
    }

    public SmppConnectionConfiguration(String host, int port, long connectTimeout,
                                       String clientBindHost, int clientBindPort) {
        this.host = host;
        this.port = port;
        this.connectTimeout = connectTimeout;
        this.clientBindHost = clientBindHost;
        this.clientBindPort = clientBindPort;
    }

    public void setHost(String value) {
        this.host = value;
    }

    public String getHost() {
        return this.host;
    }

    public void setPort(int value) {
        this.port = value;
    }

    public int getPort() {
        return this.port;
    }

    public void setConnectTimeout(long value) {
        this.connectTimeout = value;
    }

    public long getConnectTimeout() {
        return this.connectTimeout;
    }

    public String getClientBindHost() {
        return clientBindHost;
    }

    public void setClientBindHost(String clientBindHost) {
        this.clientBindHost = clientBindHost;
    }

    public int getClientBindPort() {
        return clientBindPort;
    }

    public void setClientBindPort(int clientBindPort) {
        this.clientBindPort = clientBindPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
}
