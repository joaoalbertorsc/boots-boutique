package com.codecademy.boots.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.enums.BootType;

public interface BootRepository extends CrudRepository<Boot, Integer>{
    public List<Boot> findByMaterialAndTypeAndSizeAndQuantity(String material, BootType type, Float size, Integer minQuantity);
    public List<Boot> findByMaterialAndSizeAndType(String material, Float size, BootType type);
    public List<Boot> findByMaterialAndTypeAndQuantity(String material, BootType type, Integer minQuantity);
    public List<Boot> findByMaterialAndType(String material, BootType type);
    public List<Boot> findByMaterial(String material);
    public List<Boot> findByTypeAndSizeAndQuantity(BootType type, Float size, Integer minQuantity);
    public List<Boot> findByTypeAndSize(BootType type, Float size);
    public List<Boot> findByTypeAndQuantity(BootType type, Integer minQuantity);
    public List<Boot> findByType(BootType type);
    public List<Boot> findBySizeAndQuantity(Float size, Integer minQuantity);
    public List<Boot> findBySize(Float size);
    public List<Boot> findByQuantity(Integer minQuantity);
    public List<Boot> findByBestSellerTrue();

    List<Boot> findByMaterialAndTypeAndSizeAndQuantityAndBestSeller(String material, BootType type, Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndSizeAndBestSeller(String material, BootType type, Float size, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndQuantityAndBestSeller(String material, BootType type, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndBestSeller(String material, BootType type, Boolean bestSeller);

    List<Boot> findByMaterialAndBestSeller(String material, Boolean bestSeller);

    List<Boot> findByTypeAndSizeAndQuantityAndBestSeller(BootType type, Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByTypeAndSizeAndBestSeller(BootType type, Float size, Boolean bestSeller);

    List<Boot> findByTypeAndBestSeller(BootType type, Boolean bestSeller);

    List<Boot> findByTypeAndQuantityAndBestSeller(BootType type, Integer minQuantity, Boolean bestSeller);

    List<Boot> findBySizeAndQuantityAndBestSeller(Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findBySizeAndBestSeller(Float size, Boolean bestSeller);

    List<Boot> findByQuantityAndBestSeller(Integer minQuantity, Boolean bestSeller);
}