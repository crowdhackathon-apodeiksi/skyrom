package io.skyrom.taxmachina.wserv.dto;


import io.skyrom.taxmachina.wserv.dto.annotations.DTOMapper;
import io.skyrom.taxmachina.wserv.dto.annotations.FieldMapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 *
 * @author petros
 */
@Component
@Scope( value = "prototype" )
public class DTOFactory {

    public DTO build( Object targetObject, Class mapperClass ) throws Exception {

        Class targetClass = targetObject.getClass();
        Object dto = mapperClass.newInstance();

        if ( mapperClass.isAnnotationPresent( DTOMapper.class ) ) {

            for ( Field mapperField : mapperClass.getDeclaredFields() ) {

                if ( mapperField.isAnnotationPresent( FieldMapper.class ) ) {
                    mapperField.setAccessible( true );

                    Annotation annotation = mapperField.getAnnotation( FieldMapper.class );
                    FieldMapper fieldMapper = ( FieldMapper ) annotation;

                    String mapperFieldName = fieldMapper.field();
                    Class mapperClassMapperName = fieldMapper.mapper();

                    Field field = targetClass.getDeclaredField( mapperFieldName );
                    field.setAccessible( true );

                    Object objectValue = field.get( targetObject );
                    if ( objectValue instanceof Collection ) {

                        Set set = new HashSet();
                        for ( Object ob : ( Collection ) objectValue ) {
                            Object o = build( ob, mapperClassMapperName );
                            set.add( o );
                        }

                        mapperField.set( dto, set );
                    } else if ( mapperClassMapperName.isAnnotationPresent( DTOMapper.class ) ) {
                        Object o = build( unproxy( objectValue ), mapperClassMapperName );
                        mapperField.set( dto, o );
                    } else {
                        mapperField.set( dto, objectValue );
                    }

                }

            }
        }

        return ( DTO ) dto;
    }

    private <T> T unproxy( T entity ) {
        if ( entity == null ) {
            throw new NullPointerException( "Entity passed for initialization is null" );
        }

        Hibernate.initialize( entity );
        if ( entity instanceof HibernateProxy ) {
            entity = ( T ) ( ( HibernateProxy ) entity ).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
}
