package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pacman.plantmarket.dto.CategoryDTO;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.OrderStatus;
import pacman.plantmarket.service.AdminService;
import pacman.plantmarket.service.CategoryService;
import pacman.plantmarket.service.ProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final CategoryService categoryService;

    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                          @RequestParam(value = "selectedValue", required = false) Integer selectedValue,
                                                          @RequestParam(value = "status",required = false) String status) {
        Page<ProductDTO> productDTOS = adminService.getAllProduct(page, size, selectedValue,status);
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                                          @RequestParam(value = "searchTerm",required = false) String searchTerm,
                                          @RequestParam(value = "statusFilter",required = false) String statusFilter,
                                          @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                          @RequestParam(value = "endDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){

        log.info("Fetching orders - Page: {}, Size: {}, Status: {}", page, size, statusFilter);
        Page<OrderDTO> orderDTOS = adminService.getAllOrder(page,size,searchTerm,statusFilter,startDate,endDate);
        return ResponseEntity.ok(orderDTOS);
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "productId") Integer productId,
                                           @RequestBody ProductDTO productDTO) {
        ProductDTO productDTO1 = adminService.updateProduct(productId,productDTO);

        return ResponseEntity.ok(productDTO1);
    }

    @PatchMapping("/product/toggleDeleteStatus/{productId}")
    public ResponseEntity<?> toggleDeleteStatus(@PathVariable(value = "productId") Integer productId){
        ProductDTO dto = adminService.toggleDeleteStatus(productId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/product/newProduct")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDTO productDTO){
        adminService.createNew(productDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/orders/changeStatus/{orderId}")
    public ResponseEntity<?> changeStatus(@RequestParam(value = "newStatus")OrderStatus newStatus,
                                          @PathVariable(value = "orderId") Integer orderId){
        adminService.changeNewStatus(newStatus,orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/getOrderById/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable(value = "orderId") Integer orderId){
        OrderDTO orderDTO = adminService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }
}
