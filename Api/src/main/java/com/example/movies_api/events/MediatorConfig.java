package com.example.movies_api.events;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MediatorConfig implements ApplicationContextAware {
    public static EventMediator MEDIATOR;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        MEDIATOR = new EventMediator();
        MEDIATOR.subscribe(new StatsCollectorEventHandler());
        MEDIATOR.subscribe(new LoggerEventHandler());
        MEDIATOR.subscribe(applicationContext.getBean(PersistenceEventHandler.class));
    }
}