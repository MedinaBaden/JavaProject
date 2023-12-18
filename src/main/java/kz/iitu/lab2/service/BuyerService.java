package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.BuyerDTO;
import kz.iitu.lab2.entity.Buyer;
import kz.iitu.lab2.entity.Product;
import kz.iitu.lab2.repository.BuyerRepository;
import kz.iitu.lab2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public BuyerDTO createBuyer(BuyerDTO buyerDTO) {
        Buyer buyer = new Buyer();
        buyer.setBalance(buyerDTO.getBalance());
        buyer.setName(buyerDTO.getName());
        buyerRepository.save(buyer);
        return buyerDTO;
    }

    public BuyerDTO getBuyerById(Long id) {
        Buyer byId = buyerRepository.findById(id).orElseThrow();
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setName(byId.getName());
        buyerDTO.setBalance(byId.getBalance());
        return buyerDTO;
    }

    public List<BuyerDTO> getAllBuyers() {
        List<Buyer> buyers = buyerRepository.findAll();

        return buyers.stream()
                .map(this::convertToBuyerDTO)
                .collect(Collectors.toList());
    }

    private BuyerDTO convertToBuyerDTO(Buyer buyer) {
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setName(buyer.getName());
        buyerDTO.setBalance(buyer.getBalance());
        return buyerDTO;
    }

    public Buyer updateBuyer(Long id, Buyer updatedBuyer) {
        return buyerRepository.findById(id).map(existingBuyer -> {
            updatedBuyer.setId(id);
            return buyerRepository.save(updatedBuyer);
        }).orElse(null);
    }

    public void deleteBuyer(Long id) {
        buyerRepository.deleteById(id);
    }
}
