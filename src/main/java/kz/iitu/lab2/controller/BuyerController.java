package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.BuyerDTO;
import kz.iitu.lab2.entity.Buyer;
import kz.iitu.lab2.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    @Autowired
    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @PostMapping
    public ResponseEntity<BuyerDTO> createBuyer(@RequestBody BuyerDTO buyer) {
        BuyerDTO createdBuyer = buyerService.createBuyer(buyer);
        return ResponseEntity.ok(createdBuyer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerDTO> getBuyerById(@PathVariable Long id) {
        return ResponseEntity.ok(buyerService.getBuyerById(id));
    }

    @GetMapping
    public ResponseEntity<List<BuyerDTO>> getAllBuyers() {
        List<BuyerDTO> buyers = buyerService.getAllBuyers();
        return ResponseEntity.ok(buyers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Long id, @RequestBody Buyer updatedBuyer) {
        Buyer updated = buyerService.updateBuyer(id, updatedBuyer);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
        return ResponseEntity.noContent().build();
    }
}
