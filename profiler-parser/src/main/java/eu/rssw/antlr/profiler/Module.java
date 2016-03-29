package eu.rssw.antlr.profiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Module implements Comparable<Module> {
  private final int id, crc;
  private final String name, debugListingFile;
  private final Set<LineData> lineData = new HashSet<LineData>();

  public Module(int id, String name, String file, int crc) {
    this.id = id;
    this.name = name;
    this.debugListingFile = file;
    this.crc = crc;
  }

  public Module(int id, Module module) {
    this.id = id;
    this.name = module.getName();
    this.debugListingFile = module.getFile();
    this.crc = module.getCrc();
    this.lineData.addAll(module.getLineData());
  }

  public int getId() {
    return id;
  }

  public int getCrc() {
    return crc;
  }

  public String getName() {
    return name;
  }

  public Set<LineData> getLineData() {
    return lineData;
  }

  /**
   * Renvoie le nom complet de la classe (au format package.souspaquet.classe), ou le nom de procédure avec
   * sous-répertoire et extension
   */
  public String getModuleObject() {
    if (name.indexOf(' ') > 0) {
      return name.substring(name.indexOf(' ') + 1);
    } else {
      return name;
    }

  }

  /**
   * Renvoie le nom de la procédure interne (ou de la méthode) de ce module si elle est définie.
   * 
   * Renvoie une chaine vide si module principal de la procédure, ou instanciation de classe
   */
  public String getProcName() {
    if (name.indexOf(' ') > 0) {
      return name.substring(0, name.indexOf(' '));
    } else {
      return "";
    }
  }

  public String getFile() {
    return debugListingFile;
  }

  public float getCumulativeTime() {
    float result = 0;
    for (LineData data : lineData) {
      result += data.getActualTime();
    }
    return result;
  }

  public void addLineSummary(LineData data) {
    if (data.getLineNumber() == 0)
      return;
    lineData.add(data);
  }

  public void addLineToCover(int lineNumber) {
    if (lineNumber == 0)
      return;
    lineData.add(new LineData(lineNumber, 0, 0, 0));
  }

  public List<Integer> getLinesToCover() {
    List<Integer> rslt = new ArrayList<Integer>();
    for (LineData line : lineData) {
      rslt.add(line.getLineNumber());
    }
    Collections.sort(rslt);

    return rslt;
  }

  public List<Integer> getCoveredLines() {
    List<Integer> rslt = new ArrayList<Integer>();
    for (LineData line : lineData) {
      if (line.getExecCount() > 0)
        rslt.add(line.getLineNumber());
    }
    Collections.sort(rslt);

    return rslt;
  }

  @Override
  public String toString() {
    return "Module " + id + " - " + name + " - " + debugListingFile + " - " + crc;
  }

  @Override
  public int compareTo(Module o) {
    return Integer.valueOf(id).compareTo(o.getId());
  }
}