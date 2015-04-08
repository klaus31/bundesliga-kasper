package buka.bundesligaStatistikDe;

import buka.modelLibsAndDips.Partie;
import buka.modelLibsAndDips.TippOfUser;

public class TippBundesligaStatistikDeLeader extends TippBundesligaStatistikDe {

  private TippOfUser tipp = null;

  public TippBundesligaStatistikDeLeader(final Partie partie) {
    super(partie);
    final String leaderName = getLeaderName();
    this.getTippsOfUsers().forEach(tipp -> {
      if (leaderName.startsWith(tipp.getPerson())) {
        this.tipp = tipp;
      }
    });
  }

  private String getLeaderName() {
    String content = getContent();
    content = content.substring(content.indexOf("Punkte gesamt"));
    content = content.substring(content.indexOf("1."));
    content = content.substring(2, content.indexOf(System.lineSeparator())).trim();
    content = content.replaceAll("\u00A0", "");
    content = content.replaceFirst(" ", "");
    return content;
  }

  @Override
  public String getPerson() {
    return "Fuehrender Tipper";
  }

  @Override
  public Double getToreAusw() {
    return tipp == null ? null : tipp.getToreAusw();
  }

  @Override
  public Double getToreHeim() {
    return tipp == null ? null : tipp.getToreHeim();
  }
}
