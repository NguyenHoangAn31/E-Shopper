package com.example.security.services.cartdetail;

import java.util.Map;

public interface CartDetailService {
    
    public Map<String, Object> cartAction(String action, int id);
}
