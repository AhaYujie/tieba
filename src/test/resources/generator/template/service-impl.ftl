package ${package.ServiceImpl};

import ${package.Entity}.${table.entityName};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* @author ${author}
* @date ${date}
*/
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${table.entityName}> implements ${table.serviceName} {

    public final ${table.mapperName} ${table.entityPath}Mapper;

    public ${table.serviceImplName}(${table.mapperName} ${table.entityPath}Mapper) {
        this.${table.entityPath}Mapper = ${table.entityPath}Mapper;
    }

}
