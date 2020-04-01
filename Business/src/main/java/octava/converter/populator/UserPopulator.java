package octava.converter.populator;

import octava.converter.Populator;
import octava.dto.UserDto;
import octava.model.user.UserPrincipal;

public class UserPopulator implements Populator<UserPrincipal, UserDto> {

    @Override
    public void populate(UserPrincipal source, UserDto target) {

        target.setLogin(source.getLogin());
        target.setName(source.getName());
    }
}
