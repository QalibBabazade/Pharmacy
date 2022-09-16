package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugDao extends CrudRepository<Drug, Long> {

    List<Drug> findAllByActive(Integer active);

    Drug findDrugByIdAndActive(Long id, Integer active);

}
