package com.creativamente.api.config;

import com.creativamente.api.model.*;
import com.creativamente.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepo,
            ProyectoRepository proyectoRepo,
            TareaRepository tareaRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // 1. Sembrar usuarios iniciales si no existen (password "1234" encriptada con BCrypt)
            if (usuarioRepo.count() == 0) {
                String hash1234 = passwordEncoder.encode("1234");
                usuarioRepo.save(new Usuario("cliente", "TechStore S.A.", hash1234, RolNombre.ROLE_CLIENTE, "vista-cliente", true));
                usuarioRepo.save(new Usuario("director", "Director de Proyectos", hash1234, RolNombre.ROLE_DIRECTOR, "vista-director", true));
                usuarioRepo.save(new Usuario("especialista", "Ana Pérez (Especialista)", hash1234, RolNombre.ROLE_ESPECIALISTA, "vista-especialista", true));
                usuarioRepo.save(new Usuario("admin", "Administrador General", hash1234, RolNombre.ROLE_ADMIN, "vista-admin", true));
            }

            // 2. Sembrar proyectos iniciales si no existen
            if (proyectoRepo.count() == 0) {
                Proyecto p1 = proyectoRepo.save(new Proyecto("PR001", "Campaña Social Media TechStore", "TechStore S.A.",
                        "Redes Sociales", 12000000L, LocalDate.of(2026, 1, 15), LocalDate.of(2026, 3, 15), "En ejecución"));
                Proyecto p2 = proyectoRepo.save(new Proyecto("PR002", "Branding ModaHoy", "ModaHoy",
                        "Branding", 8500000L, LocalDate.of(2026, 2, 1), LocalDate.of(2026, 4, 30), "Planificado"));
                Proyecto p3 = proyectoRepo.save(new Proyecto("PR003", "Campaña PPC Alimentos Sanos", "Alimentos Sanos",
                        "Publicidad", 5000000L, LocalDate.of(2026, 1, 10), LocalDate.of(2026, 6, 30), "En ejecución"));
                Proyecto p4 = proyectoRepo.save(new Proyecto("PR004", "Estrategia de Contenido Turismo Sur", "Turismo Sur",
                        "Contenido", 15000000L, LocalDate.of(2026, 3, 1), LocalDate.of(2026, 8, 31), "Planificado"));

                // 3. Sembrar tareas iniciales vinculadas a los proyectos
                if (tareaRepo.count() == 0) {
                    tareaRepo.save(new Tarea("Ana Pérez", "Diseñar piezas gráficas", p1, "Pendiente", "Alta", 8));
                    tareaRepo.save(new Tarea("Carlos Soto", "Redactar copys redes sociales", p2, "Pendiente", "Media", 5));
                    tareaRepo.save(new Tarea("María Rojas", "Configurar campaña PPC", p3, "Completada", "Alta", 6));
                    tareaRepo.save(new Tarea("Jorge Díaz", "Optimización SEO y analítica", p4, "Pendiente", "Baja", 3));
                }
            }
        };
    }
}