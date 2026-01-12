package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.Product;
import pacman.plantmarket.mapper.ProductMapper;
import pacman.plantmarket.repository.ProductRepository;
import pacman.plantmarket.service.AdminService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getAllProduct(Integer page, Integer size, Integer selectedValue) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("productId").ascending());
        Page<Product> products;
        if(selectedValue != null){
            products = productRepository.findByCategory_CategoryId(selectedValue,pageable);
        }else{
            products = productRepository.findAll(pageable);
        }
        return products.map(productMapper::toDTO);
    }
}
