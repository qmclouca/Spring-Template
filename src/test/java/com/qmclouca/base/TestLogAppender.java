package com.qmclouca.base;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

import java.util.ArrayList;
import java.util.List;

public class TestLogAppender extends AppenderBase<ILoggingEvent> {
    private final List<String> logs = new ArrayList<>();
    private final Layout<ILoggingEvent> layout;

    public TestLogAppender(Layout<ILoggingEvent> layout){
        this.layout = layout;
    }

    @Override
    protected void append(ILoggingEvent eventObject){
        logs.add(layout.doLayout(eventObject));
    }
    public  List<String> getLogs(){
        return logs;
    }

}
