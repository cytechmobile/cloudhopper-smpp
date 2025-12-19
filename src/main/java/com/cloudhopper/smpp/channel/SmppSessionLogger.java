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

import com.cloudhopper.smpp.type.LoggingOptions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.logging.InternalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.channel.ChannelHandler.Sharable;

/**
 * Channel handler responsible for logging the bytes sent/received on an 
 * SmppSession.  The internal "options" object is tied directly to the SmppSession
 * so that changes can be made on-the-fly during runtime.
 *
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
@Sharable
public class SmppSessionLogger extends ChannelDuplexHandler {

    private final Logger logger;
    private final LoggingOptions options;

    public enum Direction {
        UP, DOWN
    }

    /**
     * Creates a new instance with the specified logger name.
     */
    public SmppSessionLogger(String name) {
        this(name, new LoggingOptions());
    }

    public SmppSessionLogger(String name, LoggingOptions options) {
        this.options = options;
        this.logger = LoggerFactory.getLogger(options.getLoggerName() != null ? options.getLoggerName() : name);
    }

    /**
     * Gets the logger that this handler uses to log a ChannelEvent
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Gets the logging options used by this handler for logging.
     */
    public LoggingOptions getOptions() {
        return this.options;
    }

    /**
     * Logs the specified event to the {@link InternalLogger} returned by
     * {@link #getLogger()}. If hex dump has been enabled for this handler,
     * the hex dump of the {@link ByteBuf} in a {@link Object} will
     * be logged together.
     */
    protected void log(Direction direction, Object obj) {
        // handle logging of message events (PDU, ByteBuf, etc.)
        if (this.options.isLogBytesEnabled() && obj instanceof ByteBuf buffer) {
            logger.info("{} {} bytes: [{}]", options.getLogParamPrefix() == null ? "" : options.getLogParamPrefix(),
                    direction == Direction.UP ? "read" : "write", ByteBufUtil.hexDump(buffer));
        }
    }

    // INBOUND
    // @Override
    // public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
    //     log(Direction.UP, e);
    //     ctx.sendUpstream(e);
    // }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log(Direction.UP, msg);
        ctx.fireChannelRead(msg);
    }

    // OUTBOUND
    // @Override
    // public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
    //     log(Direction.DOWN, e);
    //     ctx.sendDownstream(e);
    // }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log(Direction.DOWN, msg);
        ctx.write(msg, promise);
    }
}
