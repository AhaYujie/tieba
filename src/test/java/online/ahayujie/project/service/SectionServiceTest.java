package online.ahayujie.project.service;

import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.dto.SectionUpdateParam;
import online.ahayujie.project.bean.model.Section;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SectionServiceTest {
    @Autowired
    private SectionService sectionService;

    @Test
    void create() {
        SectionCreateParam param = new SectionCreateParam();
        param.setName("section for test");
        param.setDescription("test description");
        Section section = sectionService.create(param);
        log.info(section.toString());
    }

    @Test
    void update() {
        SectionCreateParam param = new SectionCreateParam();
        param.setName("section for test");
        param.setDescription("test description");
        Section section = sectionService.create(param);
        log.info(section.toString());
        SectionUpdateParam param1 = new SectionUpdateParam();
        param1.setId(section.getId());
        param1.setName("update name");
        Section section1 = sectionService.update(param1);
        log.info(section1.toString());
    }
}