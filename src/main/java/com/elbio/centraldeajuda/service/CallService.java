package com.elbio.centraldeajuda.service;

import com.elbio.centraldeajuda.controller.dto.CallDto;
import com.elbio.centraldeajuda.entities.Call;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;

    public CallDto createCall(User user, String subject, String description) {
        Call call = new Call();
        call.setUser(user);
        call.setSubject(subject);
        call.setDescription(description);
        call.setCreatedAt(LocalDateTime.now());
        call.setClosed(false);
        Call savedCall = callRepository.save(call);
        return convertToDto(savedCall);
    }

    public Page<CallDto> listUserCalls(User user, Pageable pageable) {
        Page<Call> calls = callRepository.findByUser(user, pageable);
        return calls.map(this::convertToDto);
    }

    public CallDto closeCall(Long callId, User closedBy, int rating) {
        Call call = callRepository.findById(callId).orElseThrow();
        call.setClosed(true);
        call.setClosedAt(LocalDateTime.now());
        call.setClosedBy(closedBy);
        call.setRating(rating);
        Call savedCall = callRepository.save(call);
        return convertToDto(savedCall);
    }

    public CallDto rateCall(Long callId, int rating) {
        Call call = callRepository.findById(callId).orElseThrow();
        call.setRating(rating);
        Call savedCall = callRepository.save(call);
        return convertToDto(savedCall);
    }

    public List<CallDto> searchCalls(String subject, Long id) {
        List<Call> calls = callRepository.findBySubjectContainingOrId(subject, id);
        return calls.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CallDto convertToDto(Call call) {
        return new CallDto(
                call.getId(),
                call.getSubject(),
                call.getDescription(),
                call.getCreatedAt(),
                call.getClosedAt(),
                call.isClosed(),
                call.getUser().getUserId(),
                call.getClosedBy() != null ? call.getClosedBy().getUserId() : null,
                call.getRating()
        );
    }
}
