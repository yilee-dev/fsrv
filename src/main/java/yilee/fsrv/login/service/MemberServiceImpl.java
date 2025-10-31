package yilee.fsrv.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yilee.fsrv.login.domain.dto.MemberDto;
import yilee.fsrv.login.domain.dto.SignUpRequest;
import yilee.fsrv.login.domain.dto.SignUpResponse;
import yilee.fsrv.login.domain.entity.Member;
import yilee.fsrv.login.exception.UsernameAlreadyExistsException;
import yilee.fsrv.login.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (memberRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("[Already exists username: " + request.username() + "]");
        }

        Member entity = Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        Member saved = memberRepository.save(entity);
        return new SignUpResponse(saved.getId());
    }

    @Override
    public MemberDto find(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found member: " + id));

        return fromEntity(member);
    }

    @Override
    public MemberDto find(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username: " + username));

        return fromEntity(member);
    }

    @Override
    @Transactional
    public void modify(MemberDto dto) {
        Member member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Not found member: " + dto.getId()));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            member.updatePassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getNickname() != null && !dto.getNickname().isBlank()) {
            member.updateNickname(dto.getNickname());
        }
    }

    @Override
    public void remove(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found member: " + id));
        member.markDisabled();
    }
}
