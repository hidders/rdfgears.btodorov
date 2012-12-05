package com.nl.tudelft.rdfgearsUI.client.Dia;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.draggable.client.Draggable.Draggable;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.DragFunction;
import gwtquery.plugins.draggable.client.events.DragContext;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;

import com.allen_sauer.gwt.log.client.Log;

public class TemplateListItem extends NavigationListItem{
	Element item, labelContainer, descContainer, menuContainer;
	Element menuCopy;
	private RGCanvas canvas;
	private String id;
	private String descText = "";
	private String name = "";
	int sortDescLength = 80;
	private String elementId;
	
	public TemplateListItem(String _id, String label, String desc, RGCanvas _canvas){		
		int h = 31;
		if(desc.length() > 0)
			h = 61;
		descText = desc;
		id = _id;
		name = label;
		canvas = _canvas;
		elementId = canvas.createUniqueId();
		item = DOM.createDiv();
		item.setId(elementId);
		item.setClassName("workflowListItem");
		item.setAttribute("style", "height:" + h +"px;");
		labelContainer = DOM.createDiv();
		labelContainer.setId("label-" + elementId);
		labelContainer.setClassName("workflowListItemLabel");
		labelContainer.setAttribute("style", "height:15px;");
		labelContainer.setInnerText(label);
		item.appendChild(labelContainer);
		
		if(desc.length() > 0){
			descContainer = DOM.createDiv();
			descContainer.setId("desc-" + elementId);
			descContainer.setClassName("workflowListItemDesc");
			descContainer.setAttribute("style", "height:30px;font-size:85%;");
			descContainer.setInnerText(getTruncatedDesc());
			item.appendChild(descContainer);
		}
		
		menuContainer = DOM.createDiv();
		menuContainer.setId("menu-" + elementId);
		menuContainer.setClassName("workflowListItemMenuBar");
		menuContainer.setAttribute("style", "height:16px;");
		
		menuCopy = createMenuItem("menu-open-" + elementId, "workflowListItemMenu", "Load");
		
		menuContainer.appendChild(menuCopy);
		
		item.appendChild(menuContainer);
	}
	
	private Element createMenuItem(String menuId, String className, String text){
		Element menuEl = DOM.createDiv();
		menuEl.setId(menuId);
		menuEl.setClassName(className);
		menuEl.setInnerText(text);
		
		return menuEl;
	}
	
	public void enableEventHandler(){
		$("#menu-open-" + elementId).click(new Function(){
			public void f(){
				canvas.openTemplate(id);
			}
		});
	}
	
	public Element getElement(){
		return item;
	}
	
	private String getTruncatedDesc(){
		if(descText != null){
			if(descText.length() > sortDescLength){
				return descText.substring(0, sortDescLength).replaceAll("\\w+$", "..."); //add ellipsis
			}
		}
		return descText;
	}

	@Override
	String getName() {
		return name;
	}

	@Override
	String getId() {
		return id;
	}

	@Override
	String getDesc() {
		return descText;
	}

	@Override
	void setVisible(boolean v) {
		if(!v){
//			Log.debug("hiding: " + name);
			item.addClassName("hidden");
		}else
			item.removeClassName("hidden");
	}
}
