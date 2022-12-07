package com.example.application.components.applogin;

import com.example.application.data.UserLogin;
import com.example.application.services.AuthService;
import com.example.application.services.AuthService.ServiceException;
import com.example.application.views.createcount.CreateAccountView;
import com.example.application.views.recoverypassword.RecoveryPasswordView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.RouterLink;

public class AppLoginForm extends VerticalLayout {

  private final BeanValidationBinder<UserLogin> binder;

  private final AuthService service = new AuthService();

  public AppLoginForm() {
    Span errorMessage = new Span();
    binder = new BeanValidationBinder<UserLogin>(UserLogin.class);
    Image img = new Image("images/logo_branco.png", "placeholder");
    img.setWidth("20rem");
    img.setClassName("img_logo");

    VerticalLayout layoutLogo = new VerticalLayout(img);
    layoutLogo.setClassName("layout_logo");

    EmailField emailField = new EmailField("Email");

    PasswordField passwordField = new PasswordField("Senha");

    FormLayout formLayout = new FormLayout(emailField, passwordField);
    formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));

    EmailValidator emailValidator = new EmailValidator("Email inválido!");
    binder.forField(emailField).asRequired(emailValidator).bind("email");
    binder.forField(passwordField).asRequired().bind("password");

    binder.setStatusLabel(errorMessage);

    Button createAccount = new Button("Entrar", x -> {
      try {

        UserLogin userLogin = new UserLogin();
        binder.writeBean(userLogin);
        service.login(userLogin);
        showSuccess(userLogin);
        UI.getCurrent().navigate("dashboard");

      } catch (ValidationException e1) {

      } catch (ServiceException e2) {

        e2.printStackTrace();
        errorMessage.setText("Saving the data failed, please try again");
      }
    });

    RouterLink passwordResetLink = new RouterLink("Esqueceu a senha?", RecoveryPasswordView.class);

    HorizontalLayout buttonLayout = new HorizontalLayout(passwordResetLink, createAccount);

    buttonLayout.setClassName("horizontal-layout-login-form");

    H5 dontCount = new H5("Não possui uma conta?");

    RouterLink createCountLink = new RouterLink("Criar uma conta", CreateAccountView.class);

    HorizontalLayout createCountLayout = new HorizontalLayout(dontCount, createCountLink);

    createCountLayout.setClassName("h-layout-login-create-count");

    setClassName("vertical-layout-login-form");



    setPadding(true);
    add(layoutLogo, formLayout, buttonLayout, createCountLayout);
  }

  private void showSuccess(UserLogin userLogin) {
    Notification notification = Notification.show("Login efetuado com sucesso " + userLogin.getEmail()+ ", seja bem vindo ");
    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
  }

}
