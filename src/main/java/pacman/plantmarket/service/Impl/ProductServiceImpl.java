package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.Product;
import pacman.plantmarket.exception.PaymentException;
import pacman.plantmarket.mapper.ProductMapper;
import pacman.plantmarket.repository.ProductRepository;
import pacman.plantmarket.service.ProductService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getAllProduct(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("productId").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDTO);
    }

    @Transactional
    @Override
    public void reduceStock(Integer productId, Integer quantity) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NoSuchElementException("Not found product"));

        if(product.getStockQuantity() < quantity){
            throw new PaymentException("Stock quantity of" +product.getName()+ " not enough");
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}
