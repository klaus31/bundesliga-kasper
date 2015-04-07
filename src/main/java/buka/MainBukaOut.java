package buka;

import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Spieltag;
import buka.openLigaDB.SpieltagOpenLigaDB;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MainBukaOut extends Application {

  public static void main(final String... args) {
    launch(args);
  }

  private final ObservableList<BukaRow> data = FXCollections.observableArrayList();
  private final double defaultHeight = Screen.getPrimary().getVisualBounds().getHeight();
  private final double defaultWidth = Screen.getPrimary().getVisualBounds().getWidth();
  private Spieltag spieltag;
  private final TableView<BukaRow> table = new TableView<>();

  private Node getSpieltagComboBox() {
    ObservableList<String> options = FXCollections.observableArrayList();
    int i = 1;
    while (i < 35) {
      options.add("Spieltag " + i++);
    }
    final ComboBox comboBox = new ComboBox(options);
    comboBox.setValue("Spieltag " + spieltag.getNumber());
    comboBox.valueProperty().addListener((ChangeListener<String>) (ov, oldSt, newSt) -> {
      int newSpieltagNumber = Integer.parseInt(newSt.split(" ")[1]);
      spieltag = new SpieltagOpenLigaDB(newSpieltagNumber);
      updateView();
    });
    return comboBox;
  }

  private TableColumn getTableColumn(final String label, final double widthPercent) {
    TableColumn result = new TableColumn(label);
    final DoubleExpression width = table.widthProperty().multiply(widthPercent / 100);
    result.minWidthProperty().bind(width);
    result.prefWidthProperty().bind(width);
    result.maxWidthProperty().bind(width);
    return result;
  }

  private TableColumn getTableColumn(final String label, final String key, final double widthPercent) {
    TableColumn result = getTableColumn(label, widthPercent);
    result.setCellValueFactory(new PropertyValueFactory<>(key));
    return result;
  }

  @Override
  public void start(final Stage stage) {
    spieltag = new SpieltagOpenLigaDB();
    stage.setWidth(defaultWidth);
    stage.setHeight(defaultHeight);
    table.setEditable(false);
    table.setMinWidth(defaultWidth);
    // javafx
    Scene scene = new Scene(new Group());
    stage.setTitle("Bundesliga Kasper - Spieltag " + spieltag.getNumber());
    final TableColumn colAnpfiff = getTableColumn("Anpfiff", "anpfiff", 9);
    final TableColumn colPartie = new TableColumn("Partie");
    colPartie.getColumns().add(getTableColumn("H", "partieHeim", 15));
    colPartie.getColumns().add(getTableColumn("A", "partieAusw", 15));
    final TableColumn colTippAverage = new TableColumn("Tipp ø");
    colTippAverage.getColumns().add(getTableColumn("H", "tippAverageHeim", 5));
    colTippAverage.getColumns().add(getTableColumn("A", "tippAverageAusw", 5));
    final TableColumn colTipp1st = new TableColumn("Tipp 1st");
    colTipp1st.getColumns().add(getTableColumn("H", "tippLeaderHeim", 5));
    colTipp1st.getColumns().add(getTableColumn("A", "tippLeaderAusw", 5));
    final TableColumn colQuote = new TableColumn("Quote");
    colQuote.getColumns().add(getTableColumn("H", "quoteSiegHeim", 5));
    colQuote.getColumns().add(getTableColumn("U", "quoteUnentschieden", 5));
    colQuote.getColumns().add(getTableColumn("A", "quoteSiegAusw", 5));
    table.setItems(data);
    table.getColumns().addAll(colAnpfiff, colPartie, colTippAverage, colTipp1st, colQuote);
    // layout
    BorderPane border = new BorderPane();
    HBox box1 = new HBox();
    box1.getChildren().add(getSpieltagComboBox());
    HBox box2 = new HBox();
    box2.getChildren().add(table);
    border.setTop(box1);
    border.setBottom(box2);
    Group group = ((Group) scene.getRoot());
    group.getChildren().add(border);
    stage.setScene(scene);
    updateView();
    stage.show();
  }

  private void updateView() {
    final List<Partie> partien = spieltag.getPartien();
    data.clear();
    partien.forEach(partie -> {
      data.add(new BukaRow(partie));
    });
  }
}