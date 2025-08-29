package com.codecademy.boots.controllers;

import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.enums.BootType;
import com.codecademy.boots.exceptions.NotImplementedException;
import com.codecademy.boots.exceptions.QueryNotSupportedException;
import com.codecademy.boots.repositories.BootRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {
    private final BootRepository bootRepository;

    public BootController(BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    @GetMapping
    public Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    @PostMapping
    public ResponseEntity<Boot> addBoot(@RequestBody Boot boot) {
        Boot savedBoot = this.bootRepository.save(boot);
        return ResponseEntity.ok(savedBoot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boot> deleteBoot(@PathVariable("id") Integer id) {
        return this.bootRepository.findById(id).map(boot -> {
            this.bootRepository.delete(boot);
            return ResponseEntity.ok(boot);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/quantity/increment")
    public ResponseEntity<Boot> incrementQuantity(@PathVariable("id") Integer id) {
        return updateBoot(id, boot -> boot.setQuantity(boot.getQuantity() + 1));
    }

    @PutMapping("/{id}/quantity/decrement")
    public ResponseEntity<Boot> decrementQuantity(@PathVariable("id") Integer id) {
        return updateBoot(id, boot -> {
            if (boot.getQuantity() > 0) {
                boot.setQuantity(boot.getQuantity() - 1);
            }
        });
    }

    @PutMapping("/{id}/update/size")
    public ResponseEntity<Boot> updateBootsSize(
            @PathVariable("id") Integer id,
            @RequestParam Float newSize) {
        return updateBoot(id, boot -> boot.setSize(newSize));
    }

    @PutMapping("/{id}/update/type")
    public ResponseEntity<Boot> updateBootsType(
            @PathVariable("id") Integer id,
            @RequestParam BootType newType) {
        return updateBoot(id, boot -> boot.setType(newType));
    }

    @PutMapping("/{id}/update/material")
    public ResponseEntity<Boot> updateBootsMaterial(
            @PathVariable("id") Integer id,
            @RequestParam String newMaterial) {
        return updateBoot(id, boot -> boot.setMaterial(newMaterial));
    }

    @PutMapping("/{id}/update/bestsellers")
    public ResponseEntity<Boot> updateBootsBestSellers(
            @PathVariable("id") Integer id,
            @RequestParam Boolean newBestSeller) {
        return updateBoot(id, boot -> boot.setBestSeller(newBestSeller));
    }

    @GetMapping("/bestsellers")
    public ResponseEntity<List<Boot>> getBestSellers() {
        List<Boot> bestSellers = this.bootRepository.findByBestSellerTrue();
        return ResponseEntity.ok(bestSellers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Boot>> searchBoots(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) BootType type) throws QueryNotSupportedException {
        if (material != null && type != null) {
            throw new QueryNotSupportedException("Searching by material and type simultaneously is not supported.");
        }
        List<Boot> results;
        if (material != null) {
            results = this.bootRepository.findByMaterial(material);
        } else if (type != null) {
            results = this.bootRepository.findByType(type);
        } else {
            results = (List<Boot>) this.bootRepository.findAll();
        }
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public Boot updateBoot(@PathVariable("id") Integer id, @RequestBody Boot boot) {
        throw new NotImplementedException("Updating a full boot object is not yet implemented. Please use specific update endpoints.");
    }

    private ResponseEntity<Boot> updateBoot(Integer id, Consumer<Boot> updater) {
        return this.bootRepository.findById(id).map(boot -> {
            updater.accept(boot);
            this.bootRepository.save(boot);
            return ResponseEntity.ok(boot);
        }).orElse(ResponseEntity.notFound().build());
    }
}
