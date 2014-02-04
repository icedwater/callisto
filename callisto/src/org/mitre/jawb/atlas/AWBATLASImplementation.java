
/* ----------------------------------------------------------------------
 * 
 * Copyright (c) 2002-2009 The MITRE Corporation
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
 * (c) 2009 The MITRE Corporation. All Rights Reserved.
 * 
 * ----------------------------------------------------------------------
 *
 */
/*
 * Copyright (c) 2002-2006 The MITRE Corporation
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
 */

package org.mitre.jawb.atlas;

import org.mitre.jawb.tasks.Task;

import gov.nist.atlas.*;
import gov.nist.atlas.impl.ATLASImplementationImpl;
import gov.nist.atlas.impl.AnnotationInitializer;
import gov.nist.atlas.util.ATLASImplementation;
import gov.nist.atlas.Corpus;
import gov.nist.atlas.type.ATLASType;
import gov.nist.atlas.Id;
import gov.nist.atlas.util.ATLASElementSetFactory;
import gov.nist.atlas.util.ATLASImplementationManager;
import gov.nist.atlas.util.DefaultATLASElementSetFactory;
import gov.nist.atlas.spi.ImplementationDelegate;

import java.net.URL;
import java.util.Iterator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class AWBATLASImplementation extends ATLASImplementationImpl {

  public Parameter newParameter(ATLASType type, ATLASElement parent, Unit unit, String value) {
    value = (String) AWBAnnotationImpl.getEncodedValue(value);
    return super.newParameter(type, parent, unit, value);
  }

  public static final int DEBUG = 0;

  public AWBATLASImplementation (Task task) {
    if (task == null)
      throw new IllegalArgumentException ("task=null");
    this.task = task;
  }
  
    public Corpus newCorpus(ATLASType type, Id id, URL location) {
	return new AWBCorpusImpl(type, id, super.createImplementationDelegate(type), location);
    }

  public SimpleSignal newSimpleSignal(ATLASType type, ATLASElement parent, 
				      Id id, URL location, String track) {
    return new AWBSimpleSignal(type, parent, id, 
			       createImplementationDelegate(type), 
			       location, track);
  }

    public Annotation newAnnotation(ATLASType type, ATLASElement parent, Id id, AnnotationInitializer initializer) {
	Class c = task.getAnnotationClass(type);
	if (DEBUG>0)
	  System.err.println ("AWBAImpl.newAnnot: task="+task+
			      "\n\ttype="+type.getName()+
			      "\n\tclass="+c);
	try {
	    Constructor con = 
		c.getDeclaredConstructor(new Class[] {ATLASType.class, 
					      ATLASElement.class,
					      Id.class,
					      ImplementationDelegate.class,
					      AnnotationInitializer.class});
	    ImplementationDelegate delegate =
		createImplementationDelegate(type);
	    return (Annotation)con.newInstance(new Object[] {type, parent, id, 
							     delegate,
							     initializer});
	} catch (NoSuchMethodException e) {
	    throw new AnnotationInstantiationException
		("No constructor for " +c.getName(),e);
	} catch (InstantiationException e) {
	    throw new AnnotationInstantiationException
	        (c.getName() + " is an abstract class", e);
	} catch (IllegalAccessException e) {
	    throw new AnnotationInstantiationException
		("constructor for " + c.getName() + " is inaccessible", e);
	} catch (InvocationTargetException e) {
	    throw new AnnotationInstantiationException
 	        ("constructor for " + c.getName() + " throws an exception.", 
		 e);
	}

    }

    public Task getTask() {
	return task;
    }

    private Task task = null;
    private final static String NAME = "awbDefault";
    //private final ATLASElementSetFactory setFactory;

}
