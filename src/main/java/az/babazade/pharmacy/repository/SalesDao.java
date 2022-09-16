package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Sales;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesDao extends CrudRepository<Sales,Long> {

    List<Sales> findAllByActive(Integer active);

    Sales findSalesByIdAndActive(Long id,Integer active);

}
