package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleDao extends CrudRepository<Role,Long> {

    Role findRoleByNameAndActive(String name,Integer active);
}
