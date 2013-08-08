package com.nl.tudelft.rdfgearsUI.client.Dia;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;

public class ComponentLoadedEvent extends Event<LoadingFinishedHandler> {
    public static Type<LoadingFinishedHandler> TYPE = new Type<LoadingFinishedHandler>();

    public static ComponentLoadedEvent register(final EventBus eventBus,
	    final LoadingFinishedHandler handler) {
	return (ComponentLoadedEvent) eventBus.addHandler(ComponentLoadedEvent.TYPE, handler);
    }

    @Override
    public Type<LoadingFinishedHandler> getAssociatedType() {
	return ComponentLoadedEvent.TYPE;
    }

    @Override
    protected void dispatch(final LoadingFinishedHandler handler) {
	handler.onFinished(this);
    }
}
