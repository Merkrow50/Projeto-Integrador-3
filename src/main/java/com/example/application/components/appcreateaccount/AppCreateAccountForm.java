package com.example.application.components.appcreateaccount;

import com.example.application.data.UserDetails;
import com.example.application.services.AuthService;
import com.example.application.services.AuthService.ServiceException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;

public class AppCreateAccountForm extends VerticalLayout {

  private final PasswordField passwordField2;
  private final BeanValidationBinder<UserDetails> binder;
  private boolean enablePasswordValidation;

  private final AuthService service = new AuthService();

  public AppCreateAccountForm() {

    H2 title = new H2("Cadastrar Conta");

    TextField name = new TextField("Nome");
    TextField email = new TextField("Email");
    PasswordField passwordField1 = new PasswordField("Senha");
    passwordField2 = new PasswordField("Confirme a senha");

    Checkbox allowTermsBox = new Checkbox("Li e concordo com os Termos e Condições de Uso.");
    allowTermsBox.getStyle().set("padding-top", "10px");

    Dialog dialog = new Dialog();
    dialog.getElement().setAttribute("aria-label",
        "Termos e Condições de Uso");
    VerticalLayout dialogLayout = createDialogLayout(dialog);
    dialog.add(dialogLayout);
    Button buttonDialogTerms = new Button("Termos e Condições de Uso", e -> dialog.open());

    Span errorMessage = new Span();
    Button submitButton = new Button("Cadastrar");

    FormLayout formLayout = new FormLayout(title, name, email, passwordField1, passwordField2, buttonDialogTerms, allowTermsBox, errorMessage, submitButton);
    formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

    formLayout.setMaxWidth("500px");
    formLayout.getStyle().set("margin", "0 auto");

    errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
    errorMessage.getStyle().set("padding", "15px 0");

    setPadding(true);
    add(dialog, formLayout);

    binder = new BeanValidationBinder<UserDetails>(UserDetails.class);

    binder.forField(name).asRequired().bind("name");
    binder.forField(allowTermsBox).bind("allowsTerms");
    EmailValidator emailValidator = new EmailValidator("Email inválido!");
    binder.forField(email).asRequired(emailValidator).bind("email");
    binder.forField(passwordField1).asRequired().withValidator(this::passwordValidator).bind("password");

    passwordField2.addValueChangeListener(e -> {

      enablePasswordValidation = true;

      binder.validate();
    });

    binder.setStatusLabel(errorMessage);

    submitButton.addClickListener(e -> {
      try {

        UserDetails detailsBean = new UserDetails();
        binder.writeBean(detailsBean);
        service.register(detailsBean);
        showSuccess(detailsBean);
        UI.getCurrent().navigate("dashboard");

      } catch (ValidationException e1) {

      } catch (ServiceException e2) {

        e2.printStackTrace();
        errorMessage.setText("Saving the data failed, please try again");
      }
    });

  }

  private static VerticalLayout createDialogLayout(Dialog dialog) {
    H2 headline = new H2("Termos e Condições de Uso");
    headline.getStyle().set("margin", "var(--lumo-space-m) 0")
        .set("font-size", "1.5em").set("font-weight", "bold");

    Paragraph paragraph = new Paragraph(
        "");
    Button closeButton = new Button("Close");
    closeButton.addClickListener(e -> dialog.close());

    VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph,
        closeButton);
    dialogLayout.setPadding(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
    dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);

    return dialogLayout;
  }

  private void showSuccess(UserDetails detailsBean) {
    Notification notification = Notification.show("Data saved, welcome ");
    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
  }


  private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

    if (pass1 == null || pass1.length() < 8) {
      return ValidationResult.error("A senha deve conter minímo 8 caractéres");
    }

    if (!enablePasswordValidation) {
      enablePasswordValidation = true;
      return ValidationResult.ok();
    }

    String pass2 = passwordField2.getValue();

    if (pass1.equals(pass2)) {
      return ValidationResult.ok();
    }

    return ValidationResult.error("As Senhas não combinam");
  }

}
