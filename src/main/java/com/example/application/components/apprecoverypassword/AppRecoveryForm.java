package com.example.application.components.apprecoverypassword;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AppRecoveryForm extends VerticalLayout {

  public AppRecoveryForm(){

    Image img = new Image("images/logo_branco.png", "placeholder");
    img.setWidth("20rem");
    img.setClassName("img_logo");

    VerticalLayout layoutLogo = new VerticalLayout(img);
    layoutLogo.setClassName("layout_logo");

    TextField lastNameField = new TextField("Email");
    FormLayout formLayout = new FormLayout(lastNameField);
    formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));

    Button createAccount = new Button("Enviar");

    setPadding(true);
    add(layoutLogo, formLayout, createAccount);
  }

}
