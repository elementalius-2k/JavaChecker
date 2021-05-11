package courseproject.javacheck.repositories.elasticsearchRepos;

import courseproject.javacheck.model.elasticsearchModels.Work;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WorkRepo extends ElasticsearchRepository<Work, String> {
    List<Work> findAllByAllNamesContains(String name);
    Work findByWorkId(Integer id);
}
