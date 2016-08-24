package com.smile.spider.server;


import akka.actor.ActorRef;
//import com.eraser.platform.log.service.impl.FileLogService;
//import com.eraser.platform.log.util.LogUtil;
import com.smile.spider.actor.ActorManager;
import com.smile.spider.actor.WebSiteTaskActor;
import com.smile.spider.utils.SpiderConstants;
import com.smile.spider.xml.WebSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class SpiderServer {

    private static final Logger LOG = LoggerFactory.getLogger(SpiderServer.class);

    @Autowired
    private TSiteLoader siteLoader;

    private final static AtomicBoolean isStart = new AtomicBoolean(false);


    @PostConstruct
    public void start() {
        if (isStart.compareAndSet(false, true)) {
//            LogUtil.setLogService(new FileLogService());
            ActorManager.getActorSystem().scheduler().schedule(Duration.create(0, TimeUnit.MINUTES), Duration.create(6 * 60, TimeUnit.MINUTES), new Runnable() {
                @Override
                public void run() {
                    final List<WebSite> webSites = siteLoader.getSites();
                    for (WebSite webSite : webSites) {
                        ActorManager.selectActor(SpiderConstants.WEB_SITE_ACTOR, WebSiteTaskActor.class).tell(webSite, ActorRef.noSender());
                    }
                }
            }, ActorManager.getActorSystem().dispatcher());
        }
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-mongodb.xml");
    }

}
