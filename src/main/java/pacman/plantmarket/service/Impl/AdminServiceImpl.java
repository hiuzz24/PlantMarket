package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.*;
import pacman.plantmarket.mapper.OrderMapper;
import pacman.plantmarket.mapper.ProductMapper;
import pacman.plantmarket.repository.CategoryRepository;
import pacman.plantmarket.repository.OrderDetailRepository;
import pacman.plantmarket.repository.OrderRepository;
import pacman.plantmarket.repository.ProductRepository;
import pacman.plantmarket.service.AdminService;
import pacman.plantmarket.service.ProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Page<ProductDTO> getAllProduct(Integer page, Integer size, Integer selectedValue, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId").ascending());
        Page<Product> products;
        Specification<Product> specification = Specification.allOf();

        if (selectedValue != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("categoryId"), selectedValue));
        }

        if (status != null && !status.isEmpty()) {
            Integer threshold = 10;
            specification = specification.and((root, query, criteriaBuilder)
                    -> switch (status) {
                case "In_Stock" -> criteriaBuilder.greaterThan(root.get("stockQuantity"), threshold);
                case "Out_Of_Stock" -> criteriaBuilder.lessThanOrEqualTo(root.get("stockQuantity"), 0);
                case "Low_Stock" -> criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get("stockQuantity"), 0),
                        criteriaBuilder.lessThanOrEqualTo(root.get("stockQuantity"), threshold));
                default -> null;
            });
        }


        products = productRepository.findAll(specification, pageable);
        return products.map(productMapper::toDTO);
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NoSuchElementException("Not found product"));

        productMapper.updateEntityFromDTO(productDTO, product);
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Not found category"));

        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return productMapper.toDTO(product);
    }

    @Transactional
    @Override
    public ProductDTO toggleDeleteStatus(Integer productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NoSuchElementException("Not found product"));
        product.setIsDeleted(!product.getIsDeleted());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public void createNew(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new RuntimeException("Name of product is existed in db");
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Not found category in db"));

        Product product = productMapper.toEntity(productDTO);
        product.setCategory(category);
        product.setIsDeleted(false);

        productRepository.save(product);

    }

    @Override
    public Page<OrderDTO> getAllOrder(Integer page, Integer size, String searchTerm, String status, LocalDate startDate, LocalDate endDate) {
        PageRequest request = PageRequest.of(page, size, Sort.by("orderId").ascending());
        Page<Order> orders;
        Specification<Order> specification = Specification.allOf();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                String pattern = "%" + searchTerm.toLowerCase() + "%";

                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), pattern),
                        criteriaBuilder.like(root.get("phoneNumber"), pattern)
                );
            });
        }

        if (status != null && !status.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status));
        }

        if (startDate != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate.atStartOfDay()));
        }

        if (endDate != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate.atTime(LocalTime.MAX)));
        }

        orders = orderRepository.findAll(specification, request);

        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    @Override
    public void changeNewStatus(OrderStatus newStatus, Integer orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Not found order"));

        if(order.getStatus().equals(newStatus)) return ;

        if(newStatus == OrderStatus.CANCELLED && OrderStatus.PAID == order.getStatus()){
            throw new IllegalArgumentException("Order is paid can't cancel automatically");
        }

        if (order.getPaymentMethod() == PaymentMethod.COD) {
            switch (newStatus) {
                case SHIPPED:
                    for (OrderDetail orderDetail : order.getOrderDetails()) {
                        productService.reduceStock(orderDetail.getProductId(), orderDetail.getQuantity());
                    }
                    break;
                case CANCELLED:
                    if (order.getStatus() == OrderStatus.SHIPPED) {
                        productService.restoreStock(order);
                    }
                    break;
            }
        }
        order.setStatus(newStatus);
    }

    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Not found order"));

        return orderMapper.toDTO(order);
    }

}
