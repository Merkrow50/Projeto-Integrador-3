package com.example.application.views.recoverypassword;

import com.example.application.components.DefaultHorizontalLayout;
import com.example.application.components.apprecoverypassword.AppRecoveryForm;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "recovery-password")
public class RecoveryPasswordView extends Div {

  public RecoveryPasswordView(){
    DefaultHorizontalLayout defaultHorizontalLayout = new DefaultHorizontalLayout();
    Image img = new Image("images/recovery_password.svg", "recovery password image");
    img.setClassName("voto_image");
    defaultHorizontalLayout.addItemDivFirstColumn(img);

    AppRecoveryForm appRecoveryForm = new AppRecoveryForm();
    defaultHorizontalLayout.addItemDivSecondColumn(appRecoveryForm);

    add(defaultHorizontalLayout);
  }

}
