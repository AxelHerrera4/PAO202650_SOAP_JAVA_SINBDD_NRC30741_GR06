/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monster.edu.ec.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        
        // Permite el acceso desde cualquier origen (fundamental para tu Cliente Web y React Native)
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // Define los métodos permitidos
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        
        // Define los encabezados permitidos (SOAP requiere Content-Type)
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // Tiempo de vida de la configuración en el navegador
        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
