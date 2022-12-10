package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.UserDto;
import fudan.pm.fudanCompass.dto.request.AuthRequest;
import fudan.pm.fudanCompass.entity.User;
import fudan.pm.fudanCompass.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MapperFacade mapperFacade;

    public void register(AuthRequest request) throws Exception {
        if (ObjectUtils.isEmpty(userRepository.findByUsername(request.getUsername())))
            throw new Exception();
        userRepository.save(mapperFacade.map(request, User.class));
    }

    public UserDto login(AuthRequest request) throws Exception {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (ObjectUtils.isEmpty(user))
            throw new Exception();
        return mapperFacade.map(user, UserDto.class);
    }

}
