package az.babazade.pharmacy.schedule;

import az.babazade.pharmacy.entity.Login;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.repository.LoginDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class MySchedule {

    @Autowired
    private LoginDao loginDao;

    private static final Logger LOGGER = LogManager.getLogger(MySchedule.class);

    @Scheduled(fixedRate = 2000)
    @Async
    public void fixedRate() {
        LOGGER.info("Schedule is started ...");
       logoutToken();
        LOGGER.info("Schedule is ended ...");

    }


   /* @Scheduled(fixedDelay = 2000)
    public void fixedDelay() {
        System.out.println("Fixed Delay");
    }*/

    private void logoutToken(){

        List<Login> loginList = loginDao.findAllByActiveAndTokenIsNotNull(EnumAvailableStatus.ACTIVE.getValue());
       if(!loginList.isEmpty()) {
           for (Login login : loginList) {
               if(login.getDataDate() != null) {
                   if (login.getDataDate().before(Date.from(Instant.now().minusSeconds(60)))) {
                       login.setToken(null);
                       loginDao.save(login);
                       LOGGER.info("Success is nulled!");
                   }
               }
           }
       }
    }
}
