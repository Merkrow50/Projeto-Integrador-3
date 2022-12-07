package com.example.application.services;

import com.example.application.data.AuthenticatedUser;
import com.example.application.data.Candidatos;
import com.example.application.data.Eleitores;
import com.example.application.data.UserDetails;
import com.example.application.data.UserLogin;
import com.vaadin.flow.server.VaadinSession;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService implements Serializable {

  private final WebClient client = WebClient.create();

  public void register(UserDetails userDetails) throws ServiceException {

    AuthenticatedUser responseSpec = client.post()
        .uri("http://localhost:8081/app/v1/users/create")
        .body(BodyInserters.fromValue(userDetails))
        .retrieve()
        .bodyToMono(AuthenticatedUser.class)
        .block();

    VaadinSession.getCurrent().setAttribute(AuthenticatedUser.class, responseSpec);

  }


  public void login(UserLogin userLogin) throws ServiceException {

    AuthenticatedUser responseSpec = client.post()
        .uri("http://localhost:8081/app/v1/auth/login")
        .body(BodyInserters.fromValue(userLogin))
        .retrieve()
        .bodyToMono(AuthenticatedUser.class)
        .block();

    VaadinSession.getCurrent().setAttribute(AuthenticatedUser.class, responseSpec);

  }

  public List<Candidatos> listarCandidatos() throws ServiceException {

    var authenticatedUser = VaadinSession.getCurrent().getAttribute(AuthenticatedUser.class);

    return Objects.requireNonNull(client.get()
        .uri("http://localhost:8081/app/v1/candidatos/listar")
        .headers(h -> h.setBearerAuth(authenticatedUser.getToken()))
        .retrieve()
        .toEntityList(Candidatos.class).block()).getBody();
  }

  public List<Eleitores> listarEleitores() throws ServiceException {

    var authenticatedUser = VaadinSession.getCurrent().getAttribute(AuthenticatedUser.class);

    return Objects.requireNonNull(client.get()
        .uri("http://localhost:8081/app/v1/eleitores/listar")
        .headers(h -> h.setBearerAuth(authenticatedUser.getToken()))
        .retrieve()
        .toEntityList(Eleitores.class).block()).getBody();
  }

  public static class ServiceException extends Exception {

    public ServiceException(String msg) {
      super(msg);
    }
  }
}
