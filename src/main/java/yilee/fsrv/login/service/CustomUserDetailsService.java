package yilee.fsrv.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yilee.fsrv.login.domain.dto.MemberContext;
import yilee.fsrv.login.domain.dto.MemberDto;
import yilee.fsrv.login.domain.entity.Member;
import yilee.fsrv.login.repository.MemberRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username: " + username));

        MemberDto memberDto = memberService.fromEntity(member);
        Set<GrantedAuthority> authorities = memberDto.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        return MemberContext.builder()
                .authorities(authorities)
                .memberDto(memberDto)
                .build();
    }
}
