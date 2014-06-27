package ru.korus.tmis.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("dashboard")
//@Theme("mytheme")
@SuppressWarnings("serial")
public class AdminUI extends UI implements Button.ClickListener {
    //GridLayout root = new GridLayout(3, 3);

    CssLayout root = new CssLayout();
    GridLayout loginLayout;
    Button signinButton;
    CssLayout menu = new CssLayout();


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getSource() == signinButton) {
            siginButtonClick(clickEvent);
        }
    }



    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = AdminUI.class, widgetset = "ru.korus.tmis.admin.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();
        Label bg = new Label();

        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);

        buildLoginView();

    }

    private void buildLoginView() {

        addStyleName("login");
        loginLayout = new GridLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login-layout");
        root.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        VerticalLayout labels = new VerticalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        final Image image = new Image(null, new ThemeResource("img/logo-mis.png"));
        labels.addComponent(image);
        labels.setComponentAlignment(image, Alignment.TOP_CENTER);

        Label name = new Label("<center>ФГБУ «ФНКЦ ДГОИ им. Дмитрия Рогачева» <br> Минздрава России</center>");
        name.setContentMode(ContentMode.HTML);
        name.setSizeUndefined();
        name.addStyleName("h2");
        name.addStyleName("light");
        labels.addComponent(name);
        labels.setComponentAlignment(name, Alignment.TOP_CENTER);

        Label title = new Label("Панель управления администратора");
        title.setSizeUndefined();
        title.addStyleName("h1");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.TOP_CENTER);

        Label welcome = new Label("Медицинская информационная система");
        welcome.setSizeUndefined();
        welcome.addStyleName("h2");
        //title.addStyleName("light");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.TOP_CENTER);

        VerticalLayout fields = labels;//new VerticalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final String width = "250px";
        final TextField username = new TextField("Логин");
        username.setWidth(width);
        username.focus();
        fields.addComponent(username);
        fields.setComponentAlignment(username, Alignment.TOP_CENTER);

        final PasswordField password = new PasswordField("Пароль");
        password.setWidth(width);
        fields.addComponent(password);
        fields.setComponentAlignment(password, Alignment.TOP_CENTER);

        Button signin = new Button("Войти");
        signin.setWidth(width);
        signin.addStyleName("default");
        initSigninLisener(signin);
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.TOP_CENTER);

        loginPanel.addComponent(fields);

        loginLayout.addComponent(loginPanel);

        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
        //root.addComponent(labels, 1, 1);
        //root.addComponent(fields, 1, 2);
        //loginLayout.addComponent(loginPanel);
        //loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    private void initSigninLisener(Button signin) {
        signin.addClickListener(this);
    }

    private void siginButtonClick(Button.ClickEvent clickEvent) {
        root.removeComponent(loginLayout);
        buildMainView();
    }

    private void buildMainView() {
        root.addComponent(new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new VerticalLayout() {
                    // Sidebar
                    {
                        addStyleName("sidebar");
                        setWidth(null);
                        setHeight("100%");

                        // Branding element
                        addComponent(new CssLayout() {
                            {
                                addStyleName("branding");
                                Label logo = new Label(
                                        "<span>QuickTickets</span> Dashboard",
                                        ContentMode.HTML);
                                logo.setSizeUndefined();
                                addComponent(logo);
                                // addComponent(new Image(null, new
                                // ThemeResource(
                                // "img/branding.png")));
                            }
                        });

                        // Main menu
                        addComponent(menu);
                        setExpandRatio(menu, 1);

                        // User menu
                        addComponent(new VerticalLayout() {
                            {
                                setSizeUndefined();
                                addStyleName("user");
                                Image profilePic = new Image(
                                        null,
                                        new ThemeResource("img/profile-pic.png"));
                                profilePic.setWidth("34px");
                                addComponent(profilePic);
                                Label userName = new Label("User Name");
                                userName.setSizeUndefined();
                                addComponent(userName);

                                MenuBar.Command cmd = new MenuBar.Command() {
                                    @Override
                                    public void menuSelected(
                                            MenuBar.MenuItem selectedItem) {
                                        Notification
                                                .show("Not implemented in this demo");
                                    }
                                };
                                MenuBar settings = new MenuBar();
                                MenuBar.MenuItem settingsMenu = settings.addItem("",
                                        null);
                                settingsMenu.setStyleName("icon-cog");
                                settingsMenu.addItem("Settings", cmd);
                                settingsMenu.addItem("Preferences", cmd);
                                settingsMenu.addSeparator();
                                settingsMenu.addItem("My Account", cmd);
                                addComponent(settings);

                                Button exit = new NativeButton("Exit");
                                exit.addStyleName("icon-cancel");
                                exit.setDescription("Sign Out");
                                addComponent(exit);

                            }
                        });
                    }
                });
                // Content
            }
        });
    }

}
