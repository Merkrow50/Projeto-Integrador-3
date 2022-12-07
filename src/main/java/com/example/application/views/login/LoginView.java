package com.example.application.views.login;

import com.example.application.components.DefaultHorizontalLayout;
import com.example.application.components.applogin.AppLoginForm;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "")
@RouteAlias(value = "")
public class LoginView extends Div {
  private final AppLoginForm login = new AppLoginForm();

  public LoginView() {

    DefaultHorizontalLayout layout = new DefaultHorizontalLayout();

    layout.addItemDivSecondColumn(this.login);

    Image img = new Image("images/voting.svg", "placeholder plant");
    img.setClassName("voto_image");
    layout.addItemDivFirstColumn(img);

    setClassName("div-login-page");
    add(layout);

  }

}
