package pacman.plantmarket.service.web.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pacman.plantmarket.dto.web.ProductDTO;
import pacman.plantmarket.entity.web.Product;
import pacman.plantmarket.mapper.web.ProductMapper;
import pacman.plantmarket.repository.web.ProductRepository;
import pacman.plantmarket.service.web.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("productId").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDTO);
    }
}
