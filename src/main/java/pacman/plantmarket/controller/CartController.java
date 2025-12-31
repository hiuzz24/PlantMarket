package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.dto.CartItemDTO;
import pacman.plantmarket.service.CartService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping()
    public ResponseEntity<CartDTO> getCartByUser(){
        CartDTO cartDTO = cartService.getCartByUser();
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,String>> addToCart(@RequestBody CartItemDTO cartItemDTO){
        boolean addToCart = cartService.addToCart(cartItemDTO);
        if(!addToCart){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message","Error to add to cart"));
        }else{
            return ResponseEntity.ok()
                    .body(Map.of("message","Add to cart successfully!"));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartItemDTO cartItemDTO){
        cartService.updateCart(cartItemDTO);
        return ResponseEntity.ok().build();
    }
}
