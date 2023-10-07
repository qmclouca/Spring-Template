package com.qmclouca.base;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import com.qmclouca.base.configs.DisableTestExtension;
import com.qmclouca.base.controllers.ClientController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestLogAppenderTests extends AppenderBase<ILoggingEvent> {

    private final List<String> logs = new ArrayList<>();
    private final Layout<ILoggingEvent> layout;

    public TestLogAppenderTests(Layout<ILoggingEvent> layout){
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
