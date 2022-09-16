package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Connector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectorDao extends CrudRepository<Connector,Long> {

    List<Connector> findAllByActive(Integer active);

    Connector findConnectorByIdAndActive(Long id,Integer active);
}
