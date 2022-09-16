package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends CrudRepository<Category,Long> {

    List<Category> findAllByActive(Integer active);

    Category findCategoryByIdAndActive(Long id,Integer active);
}
