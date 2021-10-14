package com.example.application.views.dashboard;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsArea;
import com.vaadin.flow.component.charts.model.PointPlacement;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@CssImport(value = "./chart-gridline.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
public class DashboardView extends Main {


    public DashboardView() {
        addClassName("dashboard-view");
        Chart chart = createViewEvents();
        add(chart);
        Button initialDrawChartButton = new Button("drawChart with reset", e -> {
            chart.drawChart(true);
        });
        add(initialDrawChartButton);
        Button toggleTooltipAndRedrawButton = new Button("Toggle tooltip and redraw");
        toggleTooltipAndRedrawButton.addClickListener(e -> {
            Boolean enabled = chart.getConfiguration().getTooltip().getEnabled();
            chart.getConfiguration().getTooltip().setEnabled(!enabled);
            chart.drawChart();
        });
        add(toggleTooltipAndRedrawButton);
    }

    private Chart createViewEvents() {
        // Header
        Select year = new Select();
        year.setItems("2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021");
        year.setValue("2021");
        year.setWidth("100px");

        HorizontalLayout header = createHeader("View events", "Cumulative (city/month)");
        header.add(year);

        // Chart
        Chart chart = new Chart(ChartType.LINE);
        Configuration conf = chart.getConfiguration();
        Tooltip tooltip = conf.getTooltip();
        tooltip.setEnabled(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Values");


        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setPointPlacement(PointPlacement.ON);
        conf.addPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Berlin", 189, 191, 191, 196, 201, 203, 209, 212, 229, 242, 244, 247));
        conf.addSeries(new ListSeries("London", 138, 146, 148, 148, 152, 153, 163, 173, 178, 179, 185, 187));
        conf.addSeries(new ListSeries("New York", 65, 65, 66, 71, 93, 102, 108, 117, 127, 129, 135, 136));
        conf.addSeries(new ListSeries("Tokyo", 0, 11, 17, 23, 30, 42, 48, 49, 52, 54, 58, 62));


        return chart;
    }


    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames("text-xl", "m-0");

        Span span = new Span(subtitle);
        span.addClassNames("text-secondary", "text-xs");

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

}
