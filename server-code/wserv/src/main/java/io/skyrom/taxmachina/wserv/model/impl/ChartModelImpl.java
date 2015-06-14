package io.skyrom.taxmachina.wserv.model.impl;

import io.skyrom.taxmachina.wserv.dto.DTO;
import io.skyrom.taxmachina.wserv.dto.DTOFactory;
import io.skyrom.taxmachina.wserv.dto.RankDTO;
import io.skyrom.taxmachina.wserv.model.ChartModel;
import io.skyrom.taxmachina.wserv.service.ChartService;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Petros
 */
@Component
@EnableTransactionManagement
public class ChartModelImpl implements ChartModel, ApplicationContextAware {
    private ApplicationContext ac;
    
    @Autowired (required = true)
    private ChartService chartService;

    @Transactional
    @Override
    public DTO rank() throws Exception {
        
        Map<String, Object> map = chartService.getRankChart();
        
        DTOFactory dtoFactory = ac.getBean( DTOFactory.class );
        RankDTO dto =  ( RankDTO ) dtoFactory.build( null, RankDTO.class );
        dto.setRank(( int ) map.get( "rank" ));
        dto.setUsers(( int ) map.get( "users" ));
        
        return ( DTO ) dto;
    }

    @Override
    public void setApplicationContext( ApplicationContext ac ) throws BeansException {
        this.ac = ac;
    }
    
}
