package globallogic.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    //Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    public boolean hasAccess(String path){
        boolean status;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //logger.info(username);
        for(GrantedAuthority gra: auth.getAuthorities()){
            String rolUser = gra.getAuthority();
            //logger.info(rolUser);
        }

        return true;
    }
}
