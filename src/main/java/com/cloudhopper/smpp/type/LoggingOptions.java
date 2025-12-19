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

import com.cloudhopper.smpp.pdu.Pdu;

import java.util.Set;

/**
 *
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class LoggingOptions {
    protected boolean logPdu;
    protected boolean logBytes;
    protected String loggerName;
    protected String logParamPrefix;
    protected Set<Integer> excludeLogPdus;

    public LoggingOptions() {
        this.logPdu = true;
        this.logBytes = false;
    }

    public void setLogPdu(boolean value) {
        this.logPdu = value;
    }

    public boolean isLogPduEnabled() {
        return this.logPdu;
    }

    public boolean isLogPduEnabled(int commandId) {
        return this.logPdu && (this.excludeLogPdus == null || !this.excludeLogPdus.contains(commandId));
    }

    public boolean isLogPduEnabled(Pdu pdu) {
        return pdu != null && this.isLogPduEnabled(pdu.getCommandId());
    }

    public void setLogBytes(boolean value) {
        this.logBytes = value;
    }

    public boolean isLogBytesEnabled() {
        return this.logBytes;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLogParamPrefix() {
        return logParamPrefix;
    }

    public void setLogParamPrefix(String logParamPrefix) {
        this.logParamPrefix = logParamPrefix;
    }

    public Set<Integer> getExcludeLogPdus() {
        return excludeLogPdus;
    }

    public void setExcludeLogPdus(Set<Integer> excludeLogPdus) {
        this.excludeLogPdus = excludeLogPdus;
    }
}
