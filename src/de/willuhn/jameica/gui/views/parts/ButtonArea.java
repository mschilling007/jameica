/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/gui/views/parts/Attic/ButtonArea.java,v $
 * $Revision: 1.5 $
 * $Date: 2003/12/10 00:47:12 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.views.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.willuhn.jameica.Application;
import de.willuhn.jameica.GUI;
import de.willuhn.jameica.I18N;

/**
 * Diese Klasse erzeugt standardisierte Bereiche fuer die Dialog-Buttons.
 * @author willuhn
 */
public class ButtonArea
{

  private Composite buttonArea;

  /**
   * Erzeugt einen neuen Standard-Button-Bereich.
   * @param parent Composite, in dem die Buttons gezeichnet werden sollen.
   * @param numButtons Anzahl der Buttons, die hier drin gespeichert werden sollen.
   */
  public ButtonArea(Composite parent, int numButtons)
  {

    GridLayout layout = new GridLayout();
    layout.marginHeight=0;
    layout.marginWidth=0;
    layout.numColumns = numButtons;

    buttonArea = new Composite(parent, SWT.NONE);
    buttonArea.setLayout(layout);
    buttonArea.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
  }

  /**
   * Fuegt der Area einen Erstellen-Button hinzu.
   * Beim Click wird die Methode handleCreate() des Controllers ausgefuehrt.
   * @param name
   * @param controller
   */
  public void addCreateButton(String name, final Controller controller)
  {
    final Button button = new Button(buttonArea,SWT.NONE);
    button.setText(name);
    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    button.addMouseListener(new MouseAdapter() {
      public void mouseUp(MouseEvent e) {
        controller.handleCreate();
      }
    });
  }

  /**
   * Fuegt der Area einen Speichern-Button hinzu.
   * Beim Click wird die Methode handleStore() des Controllers ausgefuehrt.
   * @param name
   * @param controller
   */
  public void addStoreButton(final Controller controller)
  {
    final Button button = new Button(buttonArea,SWT.PUSH);
    button.setText(I18N.tr("Speichern"));
    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    GUI.shell.setDefaultButton(button);
    // TODO Submit bei <ENTER> geht scheinbar nur unter Windows
    button.addListener(SWT.Traverse, new Listener()
    {
      public void handleEvent(Event event)
      {
        if (event.detail == SWT.TRAVERSE_RETURN)
          controller.handleStore();
      }
    });
    button.addMouseListener(new MouseAdapter() {
      public void mouseUp(MouseEvent e) {
        controller.handleStore();
      }
    });
  }

  /**
   * Fuegt der Area einen Abbrechen-Button hinzu.
   * Beim Click wird die Methode handleCancel() des Controllers ausgefuehrt.
   * @param name
   * @param controller
   */
  public void addCancelButton(final Controller controller)
  {
    final Button button = new Button(buttonArea,SWT.NONE);
    button.setText(I18N.tr("Zur�ck"));
    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    button.addMouseListener(new MouseAdapter() {
      public void mouseUp(MouseEvent e) {
        controller.handleCancel();
      }
    });
  }

  /**
   * Fuegt der Area einen Loeschen-Button hinzu.
   * Beim Click wird die Methode handleDelete() des Controllers ausgefuehrt.
   * @param name
   * @param controller
   */
  public void addDeleteButton(final Controller controller)
  {
    final Button button = new Button(buttonArea,SWT.NONE);
    button.setText(I18N.tr("L�schen"));
    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    button.addMouseListener(new MouseAdapter() {
      public void mouseUp(MouseEvent e) {
        controller.handleDelete();
      }
    });
  }

  /**
   * Fuegt einen Custom Button hinzu.
   * @param text Beschriftung des Buttons.
   * @param adapter Adapter, der dem Button zugewiesen werden soll.
   */
  public void addCustomButton(String text, MouseAdapter adapter)
  {
    if (adapter == null)
    {
      // button without adapter makes no sense ;)
      Application.getLog().warn("a button without a mouseAdapter makes no sense - skipping");
      return;
    }
    if (text == null || "".equals(text))
    {
      // button without text makes no sense ;)
      Application.getLog().warn("a button without a text makes no sense - skipping");
      return;
    }

    final Button button = new Button(buttonArea,SWT.NONE);
    button.setText(text);
    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
    button.addMouseListener(adapter);
  }

}

/*********************************************************************
 * $Log: ButtonArea.java,v $
 * Revision 1.5  2003/12/10 00:47:12  willuhn
 * @N SearchDialog done
 * @N ErrorView
 *
 * Revision 1.4  2003/11/24 23:01:58  willuhn
 * @N added settings
 *
 * Revision 1.3  2003/11/24 17:27:50  willuhn
 * @N Context menu in table
 *
 * Revision 1.2  2003/11/22 20:43:05  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2003/11/21 02:10:21  willuhn
 * @N prepared Statements in AbstractDBObject
 * @N a lot of new SWT parts
 *
 **********************************************************************/