package ${package.Controller};

import ${package.Service}.${table.serviceName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("${table.entityPath}")
public class ${table.controllerName} {

    private final ${table.serviceName} ${table.entityPath}Service;

    public ${table.controllerName}(${table.serviceName} ${table.entityPath}Service) {
        this.${table.entityPath}Service = ${table.entityPath}Service;
    }

}
