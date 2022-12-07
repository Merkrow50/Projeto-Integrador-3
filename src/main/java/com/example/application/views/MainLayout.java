package com.example.application.views;


import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.data.AuthenticatedUser;
import com.example.application.views.about.AboutView;
import com.example.application.views.helloworld.HelloWorldView;
import com.example.application.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.TextAlignment;
import org.apache.commons.lang3.ObjectUtils;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        var loggoutButton = new Button("Sair");
        loggoutButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getSession().setAttribute(AuthenticatedUser.class, null);
            UI.getCurrent().navigate(LoginView.class);
        });
        loggoutButton.setWidth("80px");
        loggoutButton.addClassName("logout-button");
        loggoutButton.addClassNames(Background.ERROR);
        loggoutButton.addClassNames(AlignContent.END);
        addToNavbar(true, toggle, viewTitle, loggoutButton);
    }

    private void addDrawerContent() {
        H1 appName = new H1("VOTE");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, TextAlignment.CENTER);


        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Dashboard", HelloWorldView.class, "la la-globe"));
        nav.addItem(new AppNavItem("About", AboutView.class, "la la-file"));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
