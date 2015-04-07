package buka;

import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

  private final double defaultHeight = Screen.getPrimary().getVisualBounds().getHeight();
  private final double defaultWidth = Screen.getPrimary().getVisualBounds().getWidth();
  private final TableView<BukaRow> table = new TableView<>();

  private TableColumn getTableColumn(final String label, final String key, final double widthPercent) {
    TableColumn partieCol = new TableColumn(label);
    if (key != null) {
      partieCol.setCellValueFactory(new PropertyValueFactory<>(key));
    }
    final DoubleExpression width = table.widthProperty().multiply(widthPercent / 100);
    partieCol.minWidthProperty().bind(width);
    partieCol.prefWidthProperty().bind(width);
    partieCol.maxWidthProperty().bind(width);
    return partieCol;
  }

  @Override
  public void start(final Stage stage) {
    // model
    final Spieltag spieltag = new SpieltagOpenLigaDB();
    final List<Partie> partien = spieltag.getPartien();
    final ObservableList<BukaRow> data = FXCollections.observableArrayList();
    partien.forEach(partie -> {
      data.add(new BukaRow(partie));
    });
    // javafx
    Scene scene = new Scene(new Group());
    stage.setTitle("Bundesliga Kasper - Spieltag " + spieltag.getNumber());
    stage.setWidth(defaultWidth);
    stage.setHeight(defaultHeight);
    table.setEditable(false);
    table.setMinWidth(defaultWidth);
    table.setMinHeight(defaultHeight);
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
    Group group = ((Group) scene.getRoot());
    group.getChildren().addAll(table);
    stage.setScene(scene);
    stage.show();
  }
}