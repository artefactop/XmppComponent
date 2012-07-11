package org.xmpp.processor;

import org.xmpp.component.ExternalComponent;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import org.xmpp.utils.throttle.ThrottleManager;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: pepe
 * Date: 11/07/12
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractNamespaceProcessor implements NamespaceProcessor {
    private ArrayList<String> whiteListGet;
    private ArrayList<String> whiteListSet;
    protected String namespace;
    private ExternalComponent externalComponent;
    private ThrottleManager throttleManager;

    public void setWhiteListGet(ArrayList<String> whiteListGet){
        this.whiteListGet = new ArrayList<String>(whiteListGet);
    }

    public void setWhiteListSet(ArrayList<String> whiteListSet){
        this.whiteListSet = new ArrayList<String>(whiteListSet);
    }

    public IQ createPacketError(final IQ iq, final PacketError.Condition condition) {
       return externalComponent.createPacketError(iq, condition);
    }

    protected boolean whiteListAcceptGet(final IQ iq){
        return null == whiteListGet || whiteListGet.contains(iq.getFrom().getDomain());
    }

    protected boolean whiteListAcceptSet(final IQ iq){
        return null == whiteListSet || whiteListSet.contains(iq.getFrom().getDomain());
    }

    protected boolean throttleAccept(final IQ iq){
        return null == throttleManager || throttleManager.accept(iq.getFrom().toBareJID());
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace){
        this.namespace = namespace;
    }

    public ExternalComponent getExternalComponent() {
        return externalComponent;
    }

    public void setExternalComponent(ExternalComponent externalComponent) {
        this.externalComponent = externalComponent;
    }

    public void setThrottleManager(ThrottleManager throttleManager) {
        this.throttleManager = throttleManager;
    }
}
