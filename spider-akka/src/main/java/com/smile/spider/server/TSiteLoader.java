package com.smile.spider.server;


import com.smile.spider.xml.WebSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TSiteLoader {

//    @Value("${spider.site.dir}")
    private String siteDir ="test";

    private static final Logger LOG = LoggerFactory.getLogger(TSiteLoader.class);

    private List<WebSite> siteList;


    @PostConstruct
    public void init() {
        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = patternResolver.getResources("/" + siteDir + "/*.xml");
            JAXBContext jc = JAXBContext.newInstance(WebSite.class);
            Unmarshaller um = jc.createUnmarshaller();
            siteList = new ArrayList<WebSite>();
            if (resources != null && resources.length > 0) {
                for (Resource r : resources) {
                    WebSite site = (WebSite) um.unmarshal(r.getInputStream());
                    siteList.add(site);
                }
            }
            siteList = Collections.unmodifiableList(siteList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public List<WebSite> getSites() {
        return siteList;
    }

}
