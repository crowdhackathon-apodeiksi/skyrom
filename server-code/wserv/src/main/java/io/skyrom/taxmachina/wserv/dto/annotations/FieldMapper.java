package io.skyrom.taxmachina.wserv.dto.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author petros
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( value = { ElementType.FIELD } )
public @interface FieldMapper {

    public String field();

    public Class mapper() default Class.class;
}
