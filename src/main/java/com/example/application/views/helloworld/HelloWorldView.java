package com.example.application.views.helloworld;

import com.example.application.data.AuthenticatedUser;
import com.example.application.data.Candidatos;
import com.example.application.data.Eleitores;
import com.example.application.services.AuthService;
import com.example.application.services.AuthService.ServiceException;
import com.example.application.views.MainLayout;
import com.example.application.views.login.LoginView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import java.util.List;
import javax.annotation.security.PermitAll;
import org.apache.commons.lang3.ObjectUtils;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

  private Button swaggerButton;

  private Button candidatosButton;

  private Button eleitoresButton;

  private Button tokenButton;


  private final AuthService service = new AuthService();

  public HelloWorldView() {

    swaggerButton = new Button("Swagger");
    swaggerButton.addClickListener(e -> {
      UI.getCurrent().getPage().open("http://localhost:8081/app/swagger-ui.html");
    });

    Dialog candidatoDialog = new Dialog();
    candidatoDialog.setDraggable(true);
    candidatoDialog.setResizable(true);
    candidatosButton = new Button("Candidatos");
    candidatosButton.addClickListener(e -> {

      try {
        var a = service.listarCandidatos();

        VerticalLayout dialogLayout = createDialogLayoutCandidatos(a);

        candidatoDialog.add(dialogLayout);

      } catch (ServiceException ex) {
        throw new RuntimeException(ex);
      } finally {
        candidatoDialog.open();
      }
    });
    Dialog eleitoresDialog = new Dialog();
    eleitoresDialog.setDraggable(true);
    eleitoresDialog.setResizable(true);
    eleitoresButton = new Button("Eleitores");
    eleitoresButton.addClickListener(e -> {
      try {
        var a = service.listarEleitores();

        VerticalLayout dialogLayout = createDialogLayoutEleitores(a);

        eleitoresDialog.add(dialogLayout);

      } catch (ServiceException ex) {
        throw new RuntimeException(ex);
      } finally {
        eleitoresDialog.open();
      }
    });

    Dialog tokenDialog = new Dialog();
    tokenDialog.setWidth("600px");
    tokenButton = new Button("Token");
    tokenButton.addClickListener(e -> {
      tokenDialog.add(VaadinSession.getCurrent().getAttribute(AuthenticatedUser.class).getToken());

      tokenDialog.open();
    });

    setMargin(true);

    add(swaggerButton, candidatosButton, eleitoresButton, tokenButton, tokenDialog, candidatoDialog, eleitoresDialog);
  }


  private static VerticalLayout createDialogLayoutCandidatos(List<Candidatos> candidatos) {

    Grid<Candidatos> grid = new Grid<>(Candidatos.class, false);
    grid.addColumn(Candidatos::getCandidato).setHeader("Candidato");
    grid.addColumn(Candidatos::getCpf).setHeader("Cpf");
    grid.addColumn(Candidatos::getCargo).setHeader("Cargo");
    grid.addColumn(Candidatos::getEscolaridade).setHeader("Escolaridade");
    grid.addColumn(Candidatos::getId).setHeader("Id");
    grid.addColumn(Candidatos::getDeclarou_bens).setHeader("Declarou Bens");
    grid.addColumn(Candidatos::getDepesa_max).setHeader("Despesa Maxima");
    grid.addColumn(Candidatos::getDestinacao_votos).setHeader("Destinacao Votos");
    grid.addColumn(Candidatos::getEstados).setHeader("Estados");
    grid.addColumn(Candidatos::getNumero_candidato).setHeader("Numero Candidato");
    grid.addColumn(Candidatos::getEstado_civil).setHeader("Estado Civil");
    grid.addColumn(Candidatos::getGenero).setHeader("Genero");
    grid.addColumn(Candidatos::getFaixa_etaria).setHeader("Faixa Etaria");
    grid.addColumn(Candidatos::getSequencia_candidato).setHeader("Sequencia Candidato");
    grid.addColumn(Candidatos::getIdade).setHeader("Idade");
    grid.addColumn(Candidatos::getRaca).setHeader("Raca");
    grid.addColumn(Candidatos::getNome_candidato_urna).setHeader("Nome Candidato Na Urna");
    grid.addColumn(Candidatos::getNacionalidade).setHeader("Nacionalidade");
    grid.addColumn(Candidatos::getNascimento).setHeader("Nascimento");
    grid.addColumn(Candidatos::getTurno).setHeader("Turno");
    grid.addColumn(Candidatos::getOcupacao).setHeader("Ocupação");
    grid.addColumn(Candidatos::getReeleicao).setHeader("Reeleição");
    grid.addColumn(Candidatos::getPartido).setHeader("Partido");
    grid.addColumn(Candidatos::getSituacao_turno).setHeader("Situacao turno");

    grid.setItems(candidatos);

    VerticalLayout dialogLayout = new VerticalLayout(grid);
    dialogLayout.setPadding(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("min-width", "600px")
        .set("max-width", "100%").set("height", "100%");

    return dialogLayout;
  }


  private static VerticalLayout createDialogLayoutEleitores(List<Eleitores> eleitores) {

    Grid<Eleitores> grid = new Grid<>(Eleitores.class, false);
    grid.addColumn(Eleitores::getEleitoresBiometria).setHeader("Eleitores com Biometria");
    grid.addColumn(Eleitores::getEleitoresDeficientes).setHeader("Eleitores com Deficiencia");
    grid.addColumn(Eleitores::getUF).setHeader("UF");
    grid.addColumn(Eleitores::getEscolaridade).setHeader("Escolaridade");
    grid.addColumn(Eleitores::getId).setHeader("Id");
    grid.addColumn(Eleitores::getTotalEleitores).setHeader("Total Eleitores");
    grid.addColumn(Eleitores::getFaixaIdade).setHeader("Faixa Idade");
    grid.addColumn(Eleitores::getEstadoCivil).setHeader("Estado Civil");
    grid.addColumn(Eleitores::getMunicipio).setHeader("Municipio");
    grid.addColumn(Eleitores::getMunicipioBiometrico).setHeader("Municipio Biometria");

    grid.setItems(eleitores);

    VerticalLayout dialogLayout = new VerticalLayout(grid);
    dialogLayout.setPadding(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("min-width", "600px")
        .set("max-width", "100%").set("height", "100%");

    return dialogLayout;
  }

}
