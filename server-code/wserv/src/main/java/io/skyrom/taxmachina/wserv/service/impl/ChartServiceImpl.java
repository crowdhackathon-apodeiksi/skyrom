package io.skyrom.taxmachina.wserv.service.impl;

import io.skyrom.taxmachina.wserv.service.ChartService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petros
 */
@Service
public class ChartServiceImpl implements ChartService, ApplicationContextAware{
    private ApplicationContext ac;

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }

    @Override
    public Map getRankChart() {
        Map<String, Object> map = new HashMap<>();
        map.put( "rank", 3);
        map.put( "users", 11 );
        
        return map;
    }
    
    
}
