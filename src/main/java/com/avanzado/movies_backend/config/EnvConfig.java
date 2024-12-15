package com.avanzado.movies_backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    static {
        Dotenv dotenv = Dotenv.configure()
            .directory("./") // Asegúrate de que esté en la raíz del proyecto
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

        System.setProperty("SECRET_KEY", dotenv.get("SECRET_KEY"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }
}