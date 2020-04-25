package ${package.Mapper};

import ${package.Entity}.${table.entityName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import ${superMapperClassPackage};

/**
* @author ${author}
* @since ${date}
*/
@Mapper
@Component
public interface ${table.mapperName} extends ${superMapperClass}<${table.entityName}> {

}
