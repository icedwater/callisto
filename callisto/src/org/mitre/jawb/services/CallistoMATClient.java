
/* ----------------------------------------------------------------------
 * 
 * Copyright (c) 2002-2011 The MITRE Corporation
 * 
 * Except as permitted below
 * ALL RIGHTS RESERVED
 * 
 * The MITRE Corporation (MITRE) provides this software to you without
 * charge to use for your internal purposes only. Any copy you make for
 * such purposes is authorized provided you reproduce MITRE's copyright
 * designation and this License in any such copy. You may not give or
 * sell this software to any other party without the prior written
 * permission of the MITRE Corporation.
 * 
 * The government of the United States of America may make unrestricted
 * use of this software.
 * 
 * This software is the copyright work of MITRE. No ownership or other
 * proprietary interest in this software is granted you other than what
 * is granted in this license.
 * 
 * Any modification or enhancement of this software must inherit this
 * license, including its warranty disclaimers. You hereby agree to
 * provide to MITRE, at no charge, a copy of any such modification or
 * enhancement without limitation.
 * 
 * MITRE IS PROVIDING THE PRODUCT "AS IS" AND MAKES NO WARRANTY, EXPRESS
 * OR IMPLIED, AS TO THE ACCURACY, CAPABILITY, EFFICIENCY,
 * MERCHANTABILITY, OR FUNCTIONING OF THIS SOFTWARE AND DOCUMENTATION. IN
 * NO EVENT WILL MITRE BE LIABLE FOR ANY GENERAL, CONSEQUENTIAL,
 * INDIRECT, INCIDENTAL, EXEMPLARY OR SPECIAL DAMAGES, EVEN IF MITRE HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You accept this software on the condition that you indemnify and hold
 * harmless MITRE, its Board of Trustees, officers, agents, and
 * employees, from any and all liability or damages to third parties,
 * including attorneys' fees, court costs, and other related costs and
 * expenses, arising out of your use of this software irrespective of the
 * cause of said liability.
 * 
 * The export from the United States or the subsequent reexport of this
 * software is subject to compliance with United States export control
 * and munitions control restrictions. You agree that in the event you
 * seek to export this software you assume full responsibility for
 * obtaining all necessary export licenses and approvals and for assuring
 * compliance with applicable reexport restrictions.
 * 
 * ----------------------------------------------------------------------
 * 
 * NOTICE
 * 
 * This software was produced for the U. S. Government
 * under Contract No. W15P7T-09-C-F600, and is
 * subject to the Rights in Noncommercial Computer Software
 * and Noncommercial Computer Software Documentation
 * Clause 252.227-7014 (JUN 1995).
 * 
 * (c) 2011 The MITRE Corporation. All Rights Reserved.
 * 
 * ----------------------------------------------------------------------
 *
 */


package org.mitre.jawb.services;

import org.mitre.jawb.gui.JawbDocument;
import org.mitre.jawb.gui.JawbFrame;
import org.mitre.jawb.gui.WorkspaceDashboard;

import java.util.List;
import java.util.HashMap;

public interface CallistoMATClient{

  public String [] listUsers();

  public boolean isActiveLearningEnabled();

  public List getBasenameInfoExtended(String folder);

  public List getQueueList(String folder, String userid);

  public JawbDocument importNextWorkspaceDoc (WorkspaceDashboard dash, 
                                              String folder,
                                              JawbFrame jf);

  public JawbDocument importSelectedWorkspaceDoc (WorkspaceDashboard dash,
                                                  String basename, 
                                                  String folder,
                                                  JawbFrame jf);

  public boolean saveWorkspaceDocument(boolean releaseLock, boolean markGold,
                                       boolean markReconciliation, 
                                       WorkspaceDashboard dash);

  public void releaseLock(String curLockId, String curFolder, 
                          String curBasename);

  public boolean openSuccessful();

  public String getWorkspaceDir();

  public String getWorkspaceKey();

  public String getTaskName();

  // this HashMap will be of type:
  // LinkedHashMap<String, MATCgiClient.ContentTagInfo> 
  // but since MATCgiClient.ContentTagInfo is a type that is not imported
  // here this will not be declared, but the map can be cast that way if
  // needed
  public HashMap getTagInfoMap();

  public boolean uploadLog();

}
