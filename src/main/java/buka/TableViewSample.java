package buka;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.Spieltag;
import buka.openLigaDB.SpieltagOpenLigaDB;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TableViewSample extends Application {

  public static void main(final String... args) {
    launch(args);
  }

  private final double defaultWidth = 800;
  private final TableView<BukaRow> table = new TableView<>();

  private TableColumn getTableColumn(final String label, final String key, final int widthPercent) {
    TableColumn partieCol = new TableColumn(label);
    partieCol.setCellValueFactory(new PropertyValueFactory<>(key));
    partieCol.prefWidthProperty().bind(table.widthProperty().multiply(widthPercent / 100));
    return partieCol;
  }

  @Override
  public void start(final Stage stage) {
    // model
    //    final Spieltag spieltag = new SpieltagOpenLigaDB();
    //    final List<Partie> partien = spieltag.getPartien();
    //    final DecimalFormat df = new DecimalFormat("0.00");
    //    partien.forEach(partie -> {
    //      final Tipp tippAverage = new TippBundesligaStatistikDeAverage(partie);
    //      final Tipp tippLeader = new TippBundesligaStatistikDeLeader(partie);
    //      System.out.println("Partie: " + partie.getMannschaftHeim().getName() + " : " + partie.getMannschaftAusw().getName());
    //      System.out.println(tippAverage.getPerson() + ": " + df.format(tippAverage.getToreHeim()) + " : " + df.format(tippAverage.getToreAusw()));
    //      System.out.println(tippLeader.getPerson() + ": " + tippLeader.getToreHeim() + " : " + tippLeader.getToreAusw());
    //      System.out.println(System.lineSeparator());
    //    });
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
    table.setEditable(false);
    table.setMinWidth(defaultWidth);
    final TableColumn partieCol = getTableColumn("Partie", "partie", 40);
    final TableColumn tippAverageCol = getTableColumn("Tipp ø", "tippAverage", 30);
    final TableColumn tipp1stCol = getTableColumn("Tipp 1st", "tippLeader", 30);
    table.setItems(data);
    table.getColumns().addAll(partieCol, tippAverageCol, tipp1stCol);
    Group group = ((Group) scene.getRoot());
    group.getChildren().addAll(table);
    stage.setScene(scene);
    stage.show();
  }
}