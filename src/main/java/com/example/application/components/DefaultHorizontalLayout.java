package com.example.application.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DefaultHorizontalLayout extends HorizontalLayout {

  private final Div firstColumn = new Div();

  private final Div secondColumn = new Div();


  public DefaultHorizontalLayout(){
    setClassName("horizontal-layout-login-page");
    firstColumn.setClassName("div-login-banner");
    add(firstColumn);

    secondColumn.setClassName("div-login-form");
    setPadding(true);
    add(secondColumn);
  }

  public void addItemDivSecondColumn(Component component){
    this.secondColumn.add(component);
  }

  public void addItemDivFirstColumn(Component component){
    this.firstColumn.add(component);
  }

}
