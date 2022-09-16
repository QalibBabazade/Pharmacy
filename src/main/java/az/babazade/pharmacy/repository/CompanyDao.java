package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDao extends CrudRepository<Company,Long> {

    List<Company> findAllByActive(Integer active);

    Company findCompanyByIdAndActive(Long id,Integer active);
}
