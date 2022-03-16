package online.ahayujie.project.repository;

import online.ahayujie.project.bean.model.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogEsRepository extends ElasticsearchRepository<EsBlog, Long> {
}
