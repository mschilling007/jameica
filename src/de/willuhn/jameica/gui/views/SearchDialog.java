/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/gui/views/Attic/SearchDialog.java,v $
 * $Revision: 1.3 $
 * $Date: 2003/12/10 00:47:12 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.views;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.willuhn.jameica.Application;
import de.willuhn.jameica.GUI;
import de.willuhn.jameica.I18N;
import de.willuhn.jameica.rmi.DBIterator;
import de.willuhn.jameica.rmi.DBObject;
import de.willuhn.jameica.views.parts.Controller;
import de.willuhn.jameica.views.parts.Table;

/**
 * Basisklasse fuer Such-Dialoge.
 * Dieser Dialoge kann erstmal nur jeweils eine Tabelle enthalten, in
 * der ein Datensatz ausgewaehlt werden kann.
 * @author willuhn
 */
public abstract class SearchDialog
{

  private Table table;
  private String title;
  Shell shell;

  String id = null;

  /**
   * Setzt die Liste, die fuer den Dialog verwendet werden soll.
   * @param table
   */
  protected void setList(DBIterator list)
  {
    if (list == null)
    {
      Application.getLog().error("unable to init a search dialog without given list.");
      return;
    }
    this.table = new Table(list,new SearchController(null));
  }

  /**
   * Setzt den Titel des Suchdialogs.
   * @param title Titel des Such-Dialogs.
   */
  protected void setTitle(String title)
  {
    if (title == null || "".equals(title))
      Application.getLog().debug("given title for search dialog is null, skipping.");
    
    this.title = title;
  }

  /**
   * Fuegt der Tabelle des Such-Dialogs eine weitere Spalte hinzu.
   * @param title Ueberschrift der Spalte.
   * @param field Feld fuer den anzuzeigenden Wert.
   */
  protected void addColumn(String title, String field)
  {
    if (table == null)
    {
      Application.getLog().warn("table not initialized, skipping column " + title);
      return;
    }
    this.table.addColumn(title,field);
  }
  
  /**
   * Diese Funktion bitte aufrufen, um den Such-Dialog zu oeffnen.
   * @return den Wert des definierten Feldes des ausgewaehlten Objektes. 
   */
  public String open()
  {
    if (table == null)
    {
      Application.getLog().warn("table not initialized");
      return null;
    }

    try {
      Display display = GUI.display;
      shell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
      shell.setText(title == null ? I18N.tr("Suche") : title);
      shell.setLocation(display.getCursorLocation());
      shell.setLayout(new GridLayout(1,false));

      table.paint(shell);

      shell.pack();
      shell.setSize(shell.getBounds().width,300); // die Hoehe legen wir auf 300 Pixel fest (unabhaengig vom Inhalt)
      shell.open();
      while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) display.sleep();
      }
      return load(id);
    }
    catch (RemoteException e)
    {
      Application.getLog().error("unable to open search dialog");
      GUI.setActionText(I18N.tr("Fehler beim �ffnen des Such-Dialogs."));
    }
    return "";
  }

  /**
   * Diese Funktion muss von abgeleiteten Suchdialogen implementiert werden.
   * Sie wird von der open() Funktion aufgerufen und uebergibt ihr die ID
   * des ausgewaehlten Objektes. Es ist Sache des implementierenden Dialoges,
   * welchen Wert des Objektes der Suchdialog letztendlich zurueckgeben soll.
   * @param id ID des ausgewaehlten Objektes.
   * @return der tatsaechlich zurueckzugebende Wert.
   */
  protected abstract String load(String id);

  /**
   * Das ist eigentlich eher ein Dummy-Controller. Wir erstellen hier nur einen,
   * weil er von der Tabelle verlangt wird.
   * @author willuhn
   */
  class SearchController extends Controller
  {

    /**
     * @param object
     */
    public SearchController(DBObject object)
    {
      super(object);
    }

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleDelete()
     */
    public void handleDelete() {}

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleDelete(java.lang.String)
     */
    public void handleDelete(String id) {}

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleCancel()
     */
    public void handleCancel() {}

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleStore()
     */
    public void handleStore() {}

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleCreate()
     */
    public void handleCreate() {}

    /**
     * @see de.willuhn.jameica.views.parts.Controller#handleLoad(java.lang.String)
     */
    public void handleLoad(String id)
    {
      SearchDialog.this.id = id;
      SearchDialog.this.shell.dispose();
    }
    
  }

}

/*********************************************************************
 * $Log: SearchDialog.java,v $
 * Revision 1.3  2003/12/10 00:47:12  willuhn
 * @N SearchDialog done
 * @N ErrorView
 *
 * Revision 1.2  2003/12/08 16:19:06  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/12/08 15:41:09  willuhn
 * @N searchInput
 *
 **********************************************************************/