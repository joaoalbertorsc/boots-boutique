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

    List<Boot> findByMaterialAndTypeAndSizeAndQuantityAndBestSellerTrue(String material, BootType type, Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndSizeAndBestSellerTrue(String material, BootType type, Float size, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndQuantityAndBestSellerTrue(String material, BootType type, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByMaterialAndTypeAndBestSellerTrue(String material, BootType type, Boolean bestSeller);

    List<Boot> findByMaterialAndBestSellerTrue(String material, Boolean bestSeller);

    List<Boot> findByTypeAndSizeAndQuantityAndBestSellerTrue(BootType type, Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findByTypeAndSizeAndBestSellerTrue(BootType type, Float size, Boolean bestSeller);

    List<Boot> findByTypeAndBestSellerTrue(BootType type, Boolean bestSeller);

    List<Boot> findByTypeAndQuantityAndBestSellerTrue(BootType type, Integer minQuantity, Boolean bestSeller);

    List<Boot> findBySizeAndQuantityAndBestSellerTrue(Float size, Integer minQuantity, Boolean bestSeller);

    List<Boot> findBySizeAndBestSellerTrue(Float size, Boolean bestSeller);

    List<Boot> findByQuantityAndBestSellerTrue(Integer minQuantity, Boolean bestSeller);
}