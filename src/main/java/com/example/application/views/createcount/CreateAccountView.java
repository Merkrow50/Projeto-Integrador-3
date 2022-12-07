package com.example.application.views.createcount;

import com.example.application.components.DefaultHorizontalLayout;
import com.example.application.components.appcreateaccount.AppCreateAccountForm;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;

@Route(value = "create-account")
public class CreateAccountView extends Div {

  public CreateAccountView(){
    DefaultHorizontalLayout defaultHorizontalLayout = new DefaultHorizontalLayout();
    Image img = new Image("images/profile.svg", "profile image");
    img.setClassName("voto_image");
    defaultHorizontalLayout.addItemDivFirstColumn(img);

    AppCreateAccountForm appRecoveryForm = new AppCreateAccountForm();
    defaultHorizontalLayout.addItemDivSecondColumn(appRecoveryForm);

    add(defaultHorizontalLayout);
  }

}
