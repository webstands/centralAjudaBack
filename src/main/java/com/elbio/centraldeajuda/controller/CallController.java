package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.CallDto;
import com.elbio.centraldeajuda.service.CallService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("calls")
@AllArgsConstructor
public class CallController {

    private CallService callService;

    @PostMapping
    public ResponseEntity<CallDto> createCall(@RequestBody CallDto callDto) {
        CallDto newCallDto = callService.createCall(callDto.userId(), callDto.subject(), callDto.description());
        return ResponseEntity.ok(newCallDto);
    }

    @PutMapping
    public ResponseEntity<CallDto> updateCall(@RequestBody CallDto callDto) {
        CallDto newCallDto = callService.updateCall(callDto);
        return ResponseEntity.ok(newCallDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CallDto>> listUserCalls(@PathVariable UUID id) {
        List<CallDto> callDtos = callService.listUserCalls(id);
        return ResponseEntity.ok(callDtos);
    }

    @GetMapping
    public ResponseEntity<List<CallDto>> listUserCallTodos() {
        List<CallDto> callDtos = callService.listUserCallTodos();
        return ResponseEntity.ok(callDtos);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<CallDto> closeCall(@PathVariable Long id, UUID closedBy, @RequestParam int rating) {
        CallDto callDto = callService.closeCall(id, closedBy, rating);
        return ResponseEntity.ok(callDto);
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<CallDto> rateCall(@PathVariable Long id, @RequestParam int rating) {
        CallDto callDto = callService.rateCall(id, rating);
        return ResponseEntity.ok(callDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CallDto>> searchCalls(@RequestParam String query) {
        List<CallDto> callDtos = callService.searchCalls(query, Long.parseLong(query));
        return ResponseEntity.ok(callDtos);
    }

    @Transactional
    @DeleteMapping(path= "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        callService.deleteCall(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
