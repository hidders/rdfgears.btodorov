package com.nl.tudelft.rdfgearsUI.client.Dia;

import static com.google.gwt.query.client.GQuery.$;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.DOM;
import com.google.gwt.xml.client.Document;

public class RGFunctionParamBoolean extends RGFunctionParam {
    public String inputFormId;
    public boolean pValue = false;
    Node owner;

    public RGFunctionParamBoolean(String id, String value, String label, Node n) {
	super(id, label);
	pType = RGFunctionParamType.BOOLEAN;
	owner = n;
	if (value != null)
	    pValue = Boolean.valueOf(value);
    }

    @Override
    void display(Element container) {
	if (elementCache == null) {
	    initDisplayElement();

	    Element t = DOM.createInputCheck();
	    t.setPropertyBoolean("checked", pValue);
	    inputFormId = owner.canvas.createUniqueId();
	    t.setId(inputFormId);
	    formContainer.appendChild(t);

	    if (!desc.equals("")) {
		Element descContainer = DOM.createDiv();
		descContainer.setClassName("paramFormHelpText");
		descContainer.setInnerText(desc);
		elementCache.appendChild(descContainer);
	    }

	    container.appendChild(elementCache);

	    assignHandler(inputFormId);
	} else {
	    container.appendChild(elementCache);
	    $("#" + inputFormId).prop("checked", pValue);
	    // Log.debug("display property from cache");
	}

    }

    @Override
    void assignHandler(final String id) {
	$("#" + id).change(new Function() {
	    @Override
	    public void f() {
		pValue = $("#" + id).prop("checked");
		Log.debug("set pValue to : " + pValue);
	    }
	});
    }

    @Override
    void setValueFromString(String s) {
	pValue = Boolean.valueOf(s);

    }

    @Override
    com.google.gwt.xml.client.Element toXml(Document doc) {
	com.google.gwt.xml.client.Element var = doc.createElement("config");
	var.setAttribute("param", getId());
	var.appendChild(doc.createTextNode(Boolean.toString(pValue)));
	return var;
    }
}
