package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.config.MyUserDetails;
import az.babazade.pharmacy.entity.Login;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.repository.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginDao.findLoginByUsernameAndActive(username, EnumAvailableStatus.ACTIVE.getValue());
        if(login == null){
            throw new UsernameNotFoundException("User not found: "+username);
        }
        return new MyUserDetails(login);
    }
}
