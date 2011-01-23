package com.senacor.wicket;

import com.senacor.wicket.domain.Person;
import com.senacor.wicket.service.PersonService;
import com.zenika.wicket.contrib.jsr303validators.JSR303FormValidator;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @Inject
    private PersonService personService;

    private class PersonListModel extends LoadableDetachableModel<List<Person>> {
        @Override
        protected List<Person> load() {
            return personService.listPersons();
        }
    }

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public HomePage(final PageParameters parameters) {
        final WebMarkupContainer wmc = new WebMarkupContainer("super");
        wmc.setOutputMarkupId(true);
        add(wmc);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        wmc.add(feedbackPanel);
        final PropertyListView<Person> list = new PropertyListView<Person>("persons", new PersonListModel()) {
            @Override
            protected void populateItem(ListItem<Person> item) {
                item.add(new Label("id"));
                item.add(new Label("name"));
                item.add(new Label("lastname"));
                item.add(new Label("email"));
                item.add(new Label("birthday"));
            }
        };
        list.setOutputMarkupId(true);
        wmc.add(list);

        final Form<Person> f = new Form<Person>("form");
        f.setOutputMarkupId(true);
        wmc.add(f);
        f.setModel(new CompoundPropertyModel(new Person()));
        f.add(new TextField("name"));
        f.add(new TextField("lastname"));
        f.add(new TextField("email"));
        f.add(new TextField("birthday").add(new DatePicker()));
        f.add(new AjaxSubmitLink("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Person p = (Person) form.getDefaultModelObject();
                personService.savePerson(p);
                f.setModel(new CompoundPropertyModel(new Person()));
                target.addComponent(wmc);

            }

            @Override
            protected void onError(final AjaxRequestTarget target, final Form form) {
                target.addComponent(feedbackPanel);
            }
        });
        f.add(new JSR303FormValidator());


    }
}
