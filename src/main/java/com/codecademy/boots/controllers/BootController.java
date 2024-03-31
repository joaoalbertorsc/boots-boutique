package com.codecademy.boots.controllers;

import java.lang.Iterable;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.repositories.BootRepository;
import com.codecademy.boots.enums.BootType;
import com.codecademy.boots.exceptions.QueryNotSupportedException;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {
    private final BootRepository bootRepository;
    public BootController(BootRepository bootRepository){
        this.bootRepository = bootRepository;
    }
    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }
    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }
    @PostMapping("/")
    public Boot addBoot(@RequestBody Boot boot) {
        if(boot != null){
            this.bootRepository.save(boot);
            return boot;
        }else{
            return null;
        }
    }
    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> bootToDeleteOptional = this.bootRepository.findById(id);
        if(bootToDeleteOptional.isEmpty()){
            return null;
        }else{
            Boot bootToDelete = bootToDeleteOptional.get();
            this.bootRepository.delete(bootToDelete);
            return bootToDelete;
        }
    }
    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootToIncrementOptional = this.bootRepository.findById(id);
        if(bootToIncrementOptional.isEmpty()){
            return null;
        }
        Boot bootToIncrement = bootToIncrementOptional.get();
        bootToIncrement.setQuantity(bootToIncrement.getQuantity()+1);
        this.bootRepository.save(bootToIncrement);
        return bootToIncrement;
    }
    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootToDecremetOptional = this.bootRepository.findById(id);
        if(bootToDecremetOptional.isEmpty()){
            return null;
        }
        Boot bootToDecremet = bootToDecremetOptional.get();
        bootToDecremet.setQuantity(bootToDecremet.getQuantity()-1);
        this.bootRepository.save(bootToDecremet);
        return bootToDecremet;
    }
    @PutMapping("/{id}/update/size")
    public Boot updateBootsSize(
            @PathVariable("id") Integer id,
            @RequestParam Float newSize) {
        Optional<Boot> bootToUpdateOptional = this.bootRepository.findById(id);
        if(bootToUpdateOptional.isEmpty()) {
            return null;
        }
        Boot bootToUpdate = bootToUpdateOptional.get();
        bootToUpdate.setSize(newSize);
        this.bootRepository.save(bootToUpdate);
        return bootToUpdate;
    }
    @PutMapping("/{id}/update/type")
    public Boot updateBootsType(
            @PathVariable("id") Integer id,
            @RequestParam BootType newType) {
        Optional<Boot> bootToUpdateOptional = this.bootRepository.findById(id);
        if(bootToUpdateOptional.isEmpty()) {
            return null;
        }
        Boot bootToUpdate = bootToUpdateOptional.get();
        bootToUpdate.setType(newType);
        this.bootRepository.save(bootToUpdate);
        return bootToUpdate;
    }
    @PutMapping("/{id}/update/material")
    public Boot updateBootsMaterial(
            @PathVariable("id") Integer id,
            @RequestParam String newMaterial) {
        Optional<Boot> bootToUpdateOptional = this.bootRepository.findById(id);
        if(bootToUpdateOptional.isEmpty()) {
            return null;
        }
        Boot bootToUpdate = bootToUpdateOptional.get();
        bootToUpdate.setMaterial(newMaterial);
        this.bootRepository.save(bootToUpdate);
        return bootToUpdate;
    }
    @PutMapping("/{id}/update/bestsellers")
    public Boot updateBootsBestSellers(
            @PathVariable("id") Integer id,
            @RequestParam Boolean newBestSeller) {
        Optional<Boot> bootToUpdateOptional = this.bootRepository.findById(id);
        if(bootToUpdateOptional.isEmpty()) {
            return null;
        }
        Boot bootToUpdate = bootToUpdateOptional.get();
        bootToUpdate.setBestSeller(newBestSeller);
        this.bootRepository.save(bootToUpdate);
        return bootToUpdate;
    }
    @GetMapping("/bestsellers")
    public List<Boot> getBestSellers(){
        List<Boot> bestSellers = this.bootRepository.findByBestSellerTrue();
        if (bestSellers.isEmpty()){
            return null;
        }
        return bestSellers;
    }
    @GetMapping("/search")
    public List<Boot> searchBoots(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) BootType type,
            @RequestParam(required = false) Float size,
            @RequestParam(required = false, name = "quantity") Integer minQuantity,
            @RequestParam(required = false) Boolean bestSeller
    ) throws QueryNotSupportedException {
        boolean notNullMaterial = Objects.nonNull(material);
        boolean notNullType = Objects.nonNull(type);
        boolean notNullSize = Objects.nonNull(size);
        boolean notNullQuantity = Objects.nonNull(minQuantity);
        boolean notNullBSeller = Objects.nonNull(bestSeller);

        if (notNullMaterial) {
            if (notNullType && notNullSize && notNullQuantity && notNullBSeller) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndSizeAndQuantityAndBestSeller(material, type, size, minQuantity, bestSeller);
            }
            if (notNullType && notNullSize && notNullBSeller) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndSizeAndBestSeller(material, type, size, bestSeller);
            }
            if (notNullType && notNullQuantity && notNullBSeller) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndQuantityAndBestSeller(material, type, minQuantity, bestSeller);
            }
            if (notNullType && notNullSize && notNullQuantity) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndSizeAndQuantity(material, type, size, minQuantity);
            }
            if (notNullType && notNullBSeller) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndBestSeller(material, type, bestSeller);
            }
            if (notNullType && notNullSize) {
                // call the repository method that accepts a material, size, and type
                return this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
            }
            if (notNullType && notNullQuantity) {
                // call the repository method that accepts a material, a type, and a minimum quantity
                return this.bootRepository.findByMaterialAndTypeAndQuantity(material, type, minQuantity);
            }
            if (notNullBSeller) {
                // call the repository method that accepts a material, type, size, and minimum quantity
                return this.bootRepository.findByMaterialAndBestSeller(material, bestSeller);
            }
            if (notNullType) {
                // call the repository method that accepts a material and a type
                return this.bootRepository.findByMaterialAndType(material, type);
            }
            else {
                // call the repository method that accepts only a material
                return this.bootRepository.findByMaterial(material);
            }
        }
        if (notNullType) {
            if (notNullSize && notNullQuantity && notNullBSeller) {
                // call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndSizeAndQuantityAndBestSeller(type, size, minQuantity, bestSeller);
            }
            if (notNullQuantity && notNullBSeller) {
                // call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndQuantityAndBestSeller(type, minQuantity, bestSeller);
            }
            if (notNullSize && notNullBSeller) {
                // call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndSizeAndBestSeller(type, size, bestSeller);
            }
            if (notNullSize && notNullQuantity) {
                // call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndSizeAndQuantity(type, size, minQuantity);
            }
            if (notNullBSeller) {
                // call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndBestSeller(type, bestSeller);
            }
            if (notNullSize) {
                // call the repository method that accepts a type and a size
                return this.bootRepository.findByTypeAndSize(type, size);
            }
            if (notNullQuantity) {
                // call the repository method that accepts a type and a minimum quantity
                return this.bootRepository.findByTypeAndQuantity(type, minQuantity);
            }
            else {
                // call the repository method that accept only a type
                return this.bootRepository.findByType(type);
            }
        }
        if (notNullSize) {
            if (notNullQuantity && notNullBSeller) {
                // call the repository method that accepts a size and a minimum quantity
                return this.bootRepository.findBySizeAndQuantityAndBestSeller(size, minQuantity, bestSeller);
            }
            if (notNullQuantity) {
                // call the repository method that accepts a size and a minimum quantity
                return this.bootRepository.findBySizeAndQuantity(size, minQuantity);
            }
            if (notNullBSeller) {
                // call the repository method that accepts a size and a minimum quantity
                return this.bootRepository.findBySizeAndBestSeller(size, bestSeller);
            }
            else {
                // call the repository method that accepts only a size
                return this.bootRepository.findBySize(size);
            }
        }
        if (Objects.nonNull(minQuantity)) {
            // call the repository method that accepts only a minimum quantity
            if (Objects.nonNull(bestSeller)){
                return this.bootRepository.findByQuantityAndBestSeller(minQuantity, bestSeller);
            }
            else {
                return this.bootRepository.findByQuantity(minQuantity);
            }
        }
        else {
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
        }
    }

}