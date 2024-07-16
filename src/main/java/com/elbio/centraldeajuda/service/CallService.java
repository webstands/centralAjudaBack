package com.elbio.centraldeajuda.service;

import com.elbio.centraldeajuda.controller.dto.CallDto;
import com.elbio.centraldeajuda.controller.dto.MensagemDTO;
import com.elbio.centraldeajuda.entities.Call;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.rabbitmq.RabbitMQSender;
import com.elbio.centraldeajuda.repository.CallRepository;
import com.elbio.centraldeajuda.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CallService {

    private CallRepository callRepository;
    private UserRepository repository;
    private RabbitMQSender rabbitMQSender;

    public CallDto createCall(UUID UIIUser, String subject, String description) {
        Call call = new Call();
        call.setUser(getUser(UIIUser));
        call.setSubject(subject);
        call.setDescription(description);
        call.setCreatedAt(LocalDateTime.now());
        call.setClosed(false);
        Call savedCall = callRepository.save(call);

        MensagemDTO mensagem = new MensagemDTO(savedCall.getUser().getUserId(), savedCall.getId(), call.getSubject(), true, false, false);
        rabbitMQSender.send(mensagem);

        return convertToDto(savedCall);
    }

    public List<CallDto> listUserCalls(UUID UIIUser) {
        User user = getUser(UIIUser);
        Collection<Call> calls = callRepository.findByUser(user);
        return calls.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CallDto closeCall(Long callId, UUID UIIUser, int rating) {
        Call call = callRepository.findById(callId).orElseThrow();
        call.setClosed(true);
        call.setClosedAt(LocalDateTime.now());
        call.setClosedBy(getUser(UIIUser));
        call.setRating(rating);
        Call savedCall = callRepository.save(call);

        MensagemDTO mensagem = new MensagemDTO(savedCall.getClosedBy().getUserId(), savedCall.getId(), call.getSubject(), false, false, true);
        rabbitMQSender.send(mensagem);

        return convertToDto(savedCall);
    }

    public User getUser(UUID id){
        return repository.findByUserId(id);
    }

    public CallDto rateCall(Long callId, int rating) {
        Call call = callRepository.findById(callId).orElseThrow();
        call.setRating(rating);
        Call savedCall = callRepository.save(call);

        MensagemDTO mensagem = new MensagemDTO(savedCall.getClosedBy().getUserId(), savedCall.getId(), call.getSubject(), false, true, false);
        rabbitMQSender.send(mensagem);

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
