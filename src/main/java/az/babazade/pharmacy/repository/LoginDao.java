package az.babazade.pharmacy.repository;

import az.babazade.pharmacy.entity.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginDao extends CrudRepository<Login, Long> {

    Login findLoginByUsernameAndPasswordAndActive(String username, String password, Integer active);

    Login findLoginByIdAndTokenAndActive(Long id, String token, Integer active);

    Login findLoginByUsernameAndActive(String username,Integer active);

    List<Login> findAllByActiveAndTokenIsNotNull(Integer active);

}
