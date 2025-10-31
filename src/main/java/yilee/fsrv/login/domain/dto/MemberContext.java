package yilee.fsrv.login.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberContext implements UserDetails {

    private Set<GrantedAuthority> authorities;

    private MemberDto memberDto;

    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", memberDto.getId());
        claims.put("username", memberDto.getUsername());
        claims.put("nickname", memberDto.getNickname());
        claims.put("roles", memberDto.getRoles());
        claims.put("joinDate", memberDto.getJoinDate());
        claims.put("isDisabled", memberDto.getIsDisabled());
        claims.put("disabledAt", memberDto.getDisabledAt());
        return claims;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getUsername();
    }
}