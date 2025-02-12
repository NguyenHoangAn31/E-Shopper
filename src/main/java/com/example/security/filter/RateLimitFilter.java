// package com.example.security.filter;


// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import io.github.bucket4j.Bandwidth;
// import io.github.bucket4j.Bucket;
// import io.github.bucket4j.Refill;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;
// import java.time.Duration;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ConcurrentMap;

// @Component
// public class RateLimitFilter extends OncePerRequestFilter {
    
//     private final ConcurrentMap<String, Bucket> cache = new ConcurrentHashMap<>();

//     private Bucket createNewBucket() {
//         return Bucket.builder()
//                 .addLimit(Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1)))) // 50 requests/phút
//                 .build();
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         System.out.println("Filter | SimpleOncePerRequestFilter executed...");

//         // chỉ cho phép 1 ip gửi 50 request login trong 1 phút
//         String ip = request.getRemoteAddr(); // Giới hạn theo IP
//         Bucket bucket = cache.computeIfAbsent(ip, k -> createNewBucket());

//         if (bucket.tryConsume(1)) {
//             filterChain.doFilter(request, response);
//         } else {
//             response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//             response.getWriter().write("Filter | Rate limit exceeded. Try again later.");
            
//         }
//     }
// }
