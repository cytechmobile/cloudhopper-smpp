package com.cloudhopper.smpp.channel;

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

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * Utility methods for working with Netty Channels.
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class ChannelUtil {
    public static final String ATTR_PROXY_CLIENT_IP = "proxiedClientIp";
    public static final String ATTR_PROXY_CLIENT_PORT = "proxiedClientPort";

    /**
     * Create a name for the channel based on the remote host's IP and port.
     */
    static public String createChannelName(Channel channel) {
        // check if anything is null
        if (channel == null || channel.remoteAddress() == null) {
            return "ChannelWasNull";
        }
        var proxiedRemoteHost = getProxiedChannelOriginalRemoteHost(channel);
        if (proxiedRemoteHost != null && !proxiedRemoteHost.isBlank()) {
            int remotePort = getProxiedChannelOriginalRemotePort(channel);
            return proxiedRemoteHost + ":" + remotePort;
        }
        // create a channel name
        if (channel.remoteAddress() instanceof InetSocketAddress addr) {
            // just get the raw IP address
            String remoteHostAddr = addr.getAddress().getHostAddress();
            int remoteHostPort = addr.getPort();
            return remoteHostAddr + ":" + remoteHostPort;
        }
        return channel.remoteAddress().toString();
    }

    static public String getChannelRemoteHost(Channel channel) {
        if (channel == null || channel.remoteAddress() == null) {
            return null;
        }
        // if channel has proxyClientIp attribute, use that
        var proxiedClientHost = getProxiedChannelOriginalRemoteHost(channel);
        if (proxiedClientHost != null && !proxiedClientHost.isBlank()) {
            return proxiedClientHost;
        }
        // create a channel name
        if (channel.remoteAddress() instanceof InetSocketAddress addr) {
            // just get the raw IP address
            return addr.getAddress().getHostAddress();
        }
        return null;
    }

    static public int getChannelRemotePort(Channel channel) {
        if (channel == null || channel.remoteAddress() == null) {
            return 0;
        }
        // if channel has proxyClientPort attribute, use that
        int proxiedClientPort = getProxiedChannelOriginalRemotePort(channel);
        if (proxiedClientPort > 0) {
            return proxiedClientPort;
        }
        // create a channel name
        if (channel.remoteAddress() instanceof InetSocketAddress addr) {
            // just get the raw IP address
            return addr.getPort();
        }
        return 0;
    }

    public static boolean isProxied(Channel channel) {
        var proxiedHost = getProxiedChannelOriginalRemoteHost(channel);
        return proxiedHost != null && !proxiedHost.isBlank();
    }

    public static String getProxiedChannelOriginalRemoteHost(Channel channel) {
        if (channel == null || channel.remoteAddress() == null) {
            return null;
        }
        // if channel has proxyClientIp attribute, use that
        var clientIpAttr = channel.attr(AttributeKey.valueOf(ATTR_PROXY_CLIENT_IP)).get();
        if (clientIpAttr != null && clientIpAttr instanceof String clientIp && !clientIp.isBlank()) {
            return clientIp;
        }
        return null;
    }

    public static String getProxiedChannelProxyHost(Channel channel) {
        if (isProxied(channel) && channel.remoteAddress() instanceof InetSocketAddress addr) {
            // just get the raw IP address
            return addr.getAddress().getHostAddress();
        }
        return null;
    }

    public static int getProxiedChannelOriginalRemotePort(Channel channel) {
        if (channel == null || channel.remoteAddress() == null) {
            return 0;
        }
        // if channel has proxyClientIp attribute, use that
        var clientPortAttr = channel.attr(AttributeKey.valueOf(ATTR_PROXY_CLIENT_PORT)).get();
        if (clientPortAttr != null && clientPortAttr instanceof Integer clientPort) {
            return clientPort;
        }
        return 0;
    }

    public static int getProxiedChannelProxyPort(Channel channel) {
        if (isProxied(channel) && channel.remoteAddress() instanceof InetSocketAddress addr) {
            // just get the raw IP address
            return addr.getPort();
        }
        return 0;
    }
}
