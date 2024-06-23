package com.example.cashcardapp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal){
        System.out.println("test");
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName())
                .map(cashCard -> ResponseEntity.ok(cashCard))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CashCard cashCard, UriComponentsBuilder uriComponentsBuilder) {
        CashCard saved = cashCardRepository.save(cashCard);
        URI location = uriComponentsBuilder
                .path("/cashcards/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).build();
       }

    @GetMapping
    public ResponseEntity findAll(Principal principal, Pageable pageable){
        List<CashCard> cashCards = cashCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC,"amount"))
                )
        ).getContent();
        return ResponseEntity.ok(cashCards);
    }
}
